package com.thanglong.shop_giay.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.thanglong.shop_giay.dto.CategoryDTO;
import com.thanglong.shop_giay.entity.Category;
import com.thanglong.shop_giay.repository.CategoryRepository;

import jakarta.validation.Valid;



@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepo;

    //1. Create
    public Category createCategory(@Valid CategoryDTO dto){
        //gán
        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        //save
        return categoryRepo.save(category);
         
    }

    //2. Read
    public List<Category> getAllCategories(){
         return categoryRepo.findAll();
    }

    public Category getCategoryById(Integer id){
        return categoryRepo.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy Category " + id));
    }

    //3. Update
    public Category updateById(Integer id, CategoryDTO dto){
        Category existingCategory = categoryRepo.findById(id)
                                     .orElseThrow(() -> new RuntimeException("Không tồn tại danh mục có id là " + id));
        if(dto.getName() != null ){
            existingCategory.setName(dto.getName());
        }
        if(dto.getDescription() != null){
            existingCategory.setDescription(dto.getDescription());
        }
        return categoryRepo.save(existingCategory);
    }

    //4. Delete 
    public String deleteById(Integer id){
        if(!categoryRepo.existsById(id)){
            throw new RuntimeException("Không tồn tại category id " + id);
        }
        categoryRepo.deleteById(id);
        return "Xóa thành công danh mục có id " + id;
    }
    
}
