package com.thanglong.shop_giay.service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thanglong.shop_giay.dto.ReviewDTO;
import com.thanglong.shop_giay.entity.Product;
import com.thanglong.shop_giay.entity.Review;
import com.thanglong.shop_giay.entity.User;
import com.thanglong.shop_giay.repository.ProductRepository;
import com.thanglong.shop_giay.repository.ReviewRepository;
import com.thanglong.shop_giay.repository.UserRepository;

import jakarta.validation.Valid;


@Service
@Data 
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ProductRepository productRepo; 
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    // 1. Lấy tất cả đánh giá 
    public List<ReviewDTO> getAllReviews() {
        List<Review> reviews = reviewRepo.findAll();
        return reviews.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // 2. Lấy đánh giá theo Product ID 
    public List<ReviewDTO> getReviewsByProduct(Integer productId) {
        // Kiểm tra sản phẩm có tồn tại 
        if (productId == null) {
            throw new RuntimeException("Lỗi: Không thể xem vì chưa cung cấp ID sản phẩm!");
        }
        if (!productRepo.existsById(productId)) {
            throw new RuntimeException("Sản phẩm không tồn tại!");
        }
        
        List<Review> reviews = reviewRepo.findByProductIdOrderByCreatedDateDesc(productId);
        return reviews.stream()
                .map(this::mapToDTO)           
                .collect(Collectors.toList());
    }

    // 3. Tạo đánh giá mới
    public ReviewDTO createReview(@Valid ReviewDTO dto) {
        // Kiểm tra User có tồn tại không 
        User user = userService.getUserById(dto.getUserId());

        // Kiểm tra Product có tồn tại không
        Product product = productService.getProductById(dto.getProductId());

        // Map từ DTO -> Entity để lưu
        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());

        Review savedReview = reviewRepo.save(review);
        return mapToDTO(savedReview); // Trả về DTO đầy đủ thông tin
    }

    // 4. Update đánh giá (Chỉ cho sửa nội dung và số sao)
    public ReviewDTO updateReview(Integer id, ReviewDTO dto) {
        Review review = reviewRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đánh giá ID: " + id));

        if (dto.getRating() != null) {
            review.setRating(dto.getRating());
        }
        if (dto.getComment() != null && !dto.getComment().isEmpty()) { 
            review.setComment(dto.getComment());
        }

        Review updatedReview = reviewRepo.save(review);
        return mapToDTO(updatedReview);
    }

    // 5. Xóa đánh giá
    public void deleteReview(Integer id) {
        if (!reviewRepo.existsById(id)) {
            throw new RuntimeException("Không tìm thấy đánh giá để xóa!");
        }
        reviewRepo.deleteById(id);
    }

    // --- HÀM PHỤ: Chuyển đổi từ Entity sang DTO ---
    // Mục đích: Để API trả về có cả Tên User và Tên Product (Frontend đỡ phải gọi thêm API khác)
    private ReviewDTO mapToDTO(Review entity) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(entity.getId());
        dto.setComment(entity.getComment());
        dto.setRating(entity.getRating());
        dto.setCreatedDate(entity.getCreatedDate());
        
        // Map thông tin User
        dto.setUserId(entity.getUser().getId());
        
        // Map thông tin Product
        dto.setProductId(entity.getProduct().getId());
        dto.setProductName(entity.getProduct().getName()); 
        return dto;
    }
}