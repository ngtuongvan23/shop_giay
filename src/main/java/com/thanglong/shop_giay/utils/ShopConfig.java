package com.thanglong.shop_giay.utils;

import lombok.Data;

@Data
public class ShopConfig {
    //singleton
    private static ShopConfig instance;
    private String shopName;
    private double defaultShippingFee;
    private double vatRate;
    private boolean isMaintenanceMode;

    private ShopConfig(){
        this.shopName = "SHOP_GIAY";
        this.defaultShippingFee = 30000; // 30k
        this.vatRate = 0.08; // 8%
        this.isMaintenanceMode = false;
    }

    public static ShopConfig getInstance(){
        if(instance == null){
            instance = new ShopConfig();     
        }
        return instance;    
    }
}
