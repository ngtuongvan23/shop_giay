package com.thanglong.shop_giay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.thanglong.shop_giay.entity.Review;

public interface ReviewRepository  extends JpaRepository<Review,Integer>{
    // Tìm tất cả review của 1 sản phẩm, sắp xếp mới nhất lên đầu
    List<Review> findByProductIdOrderByCreatedDateDesc(Integer productId);
}
