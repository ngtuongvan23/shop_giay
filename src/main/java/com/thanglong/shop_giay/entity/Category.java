package com.thanglong.shop_giay.entity;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "category_name", nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;


    public Category() {
	}
    
}