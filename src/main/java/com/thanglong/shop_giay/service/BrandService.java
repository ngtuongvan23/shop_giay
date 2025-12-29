package com.thanglong.shop_giay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thanglong.shop_giay.dto.BrandDTO;
import com.thanglong.shop_giay.entity.Brand;
import com.thanglong.shop_giay.repository.BrandRepository;

import jakarta.validation.Valid;

@Service
public class BrandService {
    @Autowired
    BrandRepository brancRepository;
    

    //1. Create
    public Brand createBrand(@Valid BrandDTO dto){
        //gan
        Brand brand = new Brand();
        brand.setName(dto.getName());
        brand.setLogoUrl(dto.getLogoUrl());
        //save
        brancRepository.save(brand);
        return brand;
    }

    //2. Read
    public List<Brand> getAllBrands(){
         return brancRepository.findAll();
    }

    public Brand getBrandById(Integer id){
        return brancRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy Brand " + id));
    }

    //3. Update
    public Brand updateById(Integer id, BrandDTO dto){
        Brand existingBrand = brancRepository.findById(id)
                                     .orElseThrow(() -> new RuntimeException("Không tồn tại brand có id là " + id));
        if(dto.getName() != null ){
            existingBrand.setName(dto.getName());
        }
        if(dto.getLogoUrl() != null){
            existingBrand.setLogoUrl(dto.getLogoUrl());
        }
        return brancRepository.save(existingBrand);
    }

    //4. Delete 
    public String deleteById(Integer id){
        if(!brancRepository.existsById(id)){
            throw new RuntimeException("Không tồn tại brand id " + id);
        }
        brancRepository.deleteById(id);
        return "Xóa thành công brand có id " + id;
    }
}
