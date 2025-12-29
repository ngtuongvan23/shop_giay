package com.thanglong.shop_giay.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thanglong.shop_giay.dto.ProductDTO;
import com.thanglong.shop_giay.entity.Brand;
import com.thanglong.shop_giay.entity.Category;
import com.thanglong.shop_giay.entity.Product;
import com.thanglong.shop_giay.repository.BrandRepository;
import com.thanglong.shop_giay.repository.CategoryRepository;
import com.thanglong.shop_giay.repository.ProductRepository;

import jakarta.validation.Valid;



@Service
public class ProductService {
    @Autowired
    ProductRepository productRepo;
    @Autowired
    CategoryRepository categoryRepo;
    @Autowired
    BrandRepository brandRepo;

    //1. hàm create Product từ DTO
    public Product createProduct(@Valid ProductDTO dto){
        // find obj category & brand 
        Category category = categoryRepo.findById(dto.getCategoryId())
                            .orElseThrow(() -> new RuntimeException("Không tìm thấy Category với ID: " + dto.getCategoryId()));
        Brand brand = brandRepo.findById(dto.getBrandId())
                            .orElseThrow(() -> new RuntimeException("Không tìm thấy Brand với ID: " + dto.getBrandId()));

        Product product = Product.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .category(category)
                .brand(brand)
                .stockQuantity(dto.getQuantity())
                .build();
                
        // save 
        productRepo.save(product);
        return product;
    }

    // 2. hàm read Product
    public List<Product> getAllProducts(){
        return productRepo.findAll();
    }
    
    public Product getProductById(Integer id){
        Product product = productRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy Product với ID: " + id));
        return product;
    }

    // 3. hàm update Product 
    public Product updateById(Integer id, ProductDTO dto ){
        // step 1: find the old one
        Product existingProduct = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm theo ID: " + id));

        // step 2: find the outsource factors
        if(dto.getCategoryId() != null){
            Category newCategory = categoryRepo.findById(dto.getCategoryId())
                    .orElseThrow(()-> new RuntimeException("Category với ID " + dto.getCategoryId() + " không tồn tại"));
            //save newCategory
            existingProduct.setCategory(newCategory);
        }
        if (dto.getBrandId() != null) {
            Brand newBrand = brandRepo.findById(dto.getBrandId())
                    .orElseThrow(() -> new RuntimeException("Không tồn tại Brand ID " + dto.getBrandId()));
            
            existingProduct.setBrand(newBrand);
        }

        // step 3: update 
        if(dto.getName() != null){
            existingProduct.setName(dto.getName());
        }
        if (dto.getPrice() != null) { 
            existingProduct.setPrice(dto.getPrice()); 
        }   
        if (dto.getDescription() != null) {
            existingProduct.setDescription(dto.getDescription());
        }

        //step 4: save new one
        return productRepo.save(existingProduct);
    }

    // 4. hàm delete product
    public String deleteById(Integer id){
        //kiểm tra 
        if( !productRepo.existsById(id)){
            throw new RuntimeException("Không tồn tại sản phẩm Id: " + id);
        }
        //xóa
        productRepo.deleteById(id);
        return "Xóa sản phẩm thành công sản phẩm có " + id;
    }
    }
    
    
