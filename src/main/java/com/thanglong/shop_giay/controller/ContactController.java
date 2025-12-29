package com.thanglong.shop_giay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thanglong.shop_giay.dto.ContactDTO;
import com.thanglong.shop_giay.service.GuestContactProcessor;

@RestController
@RequestMapping("/api/contact")
public class ContactController {
    @Autowired
    private GuestContactProcessor guestContactProcessor;

    @PostMapping("/send")
    public ResponseEntity<?> processContact(@RequestBody ContactDTO dto){
        try {
            guestContactProcessor.processContact(dto);
            return ResponseEntity.ok("Gửi thành công.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        
    }
}
