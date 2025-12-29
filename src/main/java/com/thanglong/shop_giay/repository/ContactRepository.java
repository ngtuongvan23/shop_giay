package com.thanglong.shop_giay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thanglong.shop_giay.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
}