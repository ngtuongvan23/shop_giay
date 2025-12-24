package com.thanglong.shop_giay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thanglong.shop_giay.entity.News;

public interface NewsRepository extends JpaRepository<News,Integer> {
}
