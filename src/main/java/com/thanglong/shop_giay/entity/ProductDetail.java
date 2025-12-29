package com.thanglong.shop_giay.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "product_details")
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Liên kết về sản phẩm cha
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "color_id", nullable = false)
    private Color color;

    private Integer size;

    @Column(name = "stock_quantity")
    private Integer stockQuantity = 0;

    @Column(name = "sold_quantity")
    private Integer soldQuantity = 0;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl; 

    
}