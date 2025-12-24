package com.thanglong.shop_giay.service;


import org.springframework.stereotype.Service;

import com.thanglong.shop_giay.dto.ContactDTO;
import com.thanglong.shop_giay.entity.Contact;
@Service
public class GuestContactProcessor extends ContactProcessingTemplate {

    // create Contact 
    

    @Override
    protected boolean validateContact(ContactDTO dto) {
        System.out.println("GuestContactProcessor: Đang kiểm tra dữ liệu khách vãng lai...");

        //check validate
        if( dto.getName() == null || dto.getName().isEmpty()  ){
            throw new RuntimeException("Không được để trống tên");
        }

        if( dto.getEmail() == null || !dto.getEmail().contains("@")){
            throw new RuntimeException("Không được để trống email");
        }
        int countEmail =  dto.getMessage().length();
        if( countEmail < 2){
             throw new RuntimeException("Email quá ngắn " + countEmail );
        }

        int countChar =  dto.getMessage().length();
        if( countChar > 1000){
             throw new RuntimeException("Message cần nhỏ hơn 1000 kí tự. Hiện tại có: " + countChar );
        }
        return true;
    }

    @Override
    protected void doAfterSave(Contact contact) {
        System.out.println("Gửi email xác nhận lại tới email: " + contact.getEmail());
        System.out.println("Gửi liên hệ thành công chúng tôi sẽ phản hổi bạn trong 24h.");
    }
}
