package com.thanglong.shop_giay.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thanglong.shop_giay.dto.ContactDTO;
import com.thanglong.shop_giay.entity.Contact;
import com.thanglong.shop_giay.repository.ContactRepository;

@Service
public abstract class ContactProcessingTemplate {
    @Autowired
    protected ContactRepository contactRepository;

    public final void processContact(ContactDTO dto){
        //step 1: Validate data
        validateContact(dto);
        //step 2: save to database
        Contact savedContact = saveContact(dto);
        //step 3: do after save 
        doAfterSave(savedContact);
    }
    
    //step 1
    protected abstract boolean validateContact(ContactDTO dto);

    //step 2
    protected Contact saveContact(ContactDTO dto){

        Contact existingContact = new Contact();

        existingContact.setName(dto.getName());
        existingContact.setEmail(dto.getEmail());
        existingContact.setPhone(dto.getPhoneNumber());
        existingContact.setMessage(dto.getMessage());
        existingContact.setStatus("NEW");
        existingContact.setCreatedAt(LocalDateTime.now());

        contactRepository.save(existingContact);
        System.out.println("Saved Contact");
        return existingContact;
    }

    //step 3
    protected abstract void doAfterSave(Contact contact);
}
