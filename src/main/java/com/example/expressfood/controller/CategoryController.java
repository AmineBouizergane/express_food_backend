package com.example.expressfood.controller;

import com.example.expressfood.dto.request.CategoryRequest;
import com.example.expressfood.dto.request.UniteRequest;
import com.example.expressfood.dto.response.CategoryResponse;
import com.example.expressfood.dto.response.MessageResponse;
import com.example.expressfood.dto.response.PageResponse;
import com.example.expressfood.dto.response.UniteResponse;
import com.example.expressfood.service.ICategoryService;
import com.example.expressfood.service.ICookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryController {

    @Autowired
    ICategoryService iCategoryService;

    @PostMapping("/category")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CategoryResponse addCategory(@RequestBody CategoryRequest categoryRequest){
        return iCategoryService.addCategory(categoryRequest);
    }

    @PutMapping("/category")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CategoryResponse updateCategory(@RequestBody CategoryRequest categoryRequest){
        return iCategoryService.updateCategory(categoryRequest);
    }

    @DeleteMapping("/category")
    @PreAuthorize("hasAuthority('ADMIN')")
    public MessageResponse deleteCategory(@RequestParam Long categoryId){
        return iCategoryService.deleteCategory(categoryId);
    }

    @GetMapping("/category")
    @PreAuthorize("hasAuthority('USER')")
    public List<CategoryResponse> getAllCategories(){
        return iCategoryService.getAllCategories();
    }

    @GetMapping("/category/{categoryId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CategoryResponse getCategoryById(@PathVariable("categoryId") Long categoryId){
        return iCategoryService.getCategoryById(categoryId);
    }
}
