package com.thanglong.shop_giay.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product_details")
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Liên kết về sản phẩm cha
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // Biến thể này là màu gì?
    @ManyToOne
    @JoinColumn(name = "color_id", nullable = false)
    private Color color;

    // Biến thể này là size gì?
    @ManyToOne
    @JoinColumn(name = "size_id", nullable = false)
    private Size size;

    @Column(name = "stock_quantity")
    private Integer stockQuantity = 0;

    @Column(name = "sold_quantity")
    private Integer soldQuantity = 0;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl; // Ảnh riêng cho biến thể (VD: Giày màu đỏ ảnh khác giày xanh)

    // Setter and getter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Integer getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(Integer soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    
}