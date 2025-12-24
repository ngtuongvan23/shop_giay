package com.thanglong.shop_giay.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "colors")
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "color_name", nullable = false, length = 50)
    private String name;
    //mã màu
    @Column(name = "hex_code", length = 10)
    private String hexCode;

     // Setter and getter
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getHexCode() {
        return hexCode;
    }
    public void setHexCode(String hexCode) {
        this.hexCode = hexCode;
    }


;
}