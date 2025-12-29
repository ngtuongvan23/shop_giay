package com.thanglong.shop_giay.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thanglong.shop_giay.utils.ShopConfig;

@RestController
@RequestMapping("/api/config")
public class ConfigController {

    @GetMapping("/info")
    public ShopConfig getShopInfo() {
        ShopConfig config = ShopConfig.getInstance();
        return config;
    }

    @PostMapping("/update-vat/{vatNew}")
    public ResponseEntity<?> updateShipFee(@PathVariable float vatNew) {
        ShopConfig config = ShopConfig.getInstance();

        // Thay đổi giá trị trên đối tượng Singleton
        if (vatNew < 0 || vatNew > 1) {
            return ResponseEntity.badRequest().body("VAT phải từ 0 đến 1 (ví dụ 0.1 là 10%)");
        }
        config.setVatRate(vatNew); 
        return ResponseEntity.ok().body("Đã cập nhật VAT toàn hệ thống thành: " + vatNew);
    }
}