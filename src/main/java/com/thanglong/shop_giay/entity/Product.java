package com.thanglong.shop_giay.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "product_name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;


    @Column(length = 100)
    private String material;

    @Column(nullable = false)
    private BigDecimal price; 

    @Column(name = "stock_quantity")
    private Integer stockQuantity = 0;

    @Column(name = "sold_quantity")
    private Integer soldQuantity = 0;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @PrePersist
    public void prePersist() {
        if (this.createdDate == null) {
            this.createdDate = LocalDateTime.now();
        }
    }

    //Contructor
    public Product() {
    }
    public Product(Integer id, String name, Category category, Brand brand, String material, BigDecimal price,
            Integer stockQuantity, Integer soldQuantity, String description, String imageUrl,
            LocalDateTime createdDate) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.brand = brand;
        this.material = material;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.soldQuantity = soldQuantity;
        this.description = description;
        this.imageUrl = imageUrl;
        this.createdDate = createdDate;
    }


    // Builder Product Design Pattern
    public static class ProductBuilder{
        private Product product;
        // contructor 
        public ProductBuilder() {
            this.product = new Product();
        }

        // set
        public ProductBuilder name(String name){
            product.setName(name);
            return this;  
        }
        public ProductBuilder category(Category category){
            product.setCategory(category);
            return this;  
        }
        public ProductBuilder brand(Brand brand) {
            this.product.setBrand(brand);
            return this;
        }
        public ProductBuilder material(String material) {
            this.product.setMaterial(material);
            return this;
        }
        public ProductBuilder price(BigDecimal price) {
            this.product.setPrice(price);
            return this;
        }
        public ProductBuilder stockQuantity(Integer stockQuantity) {
            this.product.setStockQuantity(stockQuantity);
            return this;
        }
        public ProductBuilder description(String description) {
            this.product.setDescription(description);
            return this;
        }
        public ProductBuilder imageUrl(String imageUrl) {
            this.product.setImageUrl(imageUrl);
            return this;
        }

        // build
        public Product build(){
            return product;
        }
    }

    public static ProductBuilder builder() {
        return new ProductBuilder();
    }
}