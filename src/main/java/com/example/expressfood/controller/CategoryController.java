package com.example.expressfood.controller;

import com.example.expressfood.dto.request.CategoryRequest;
import com.example.expressfood.dto.response.CategoryResponse;
import com.example.expressfood.dto.response.MessageResponse;
import com.example.expressfood.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService iCategoryService;

    @PostMapping("/category")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CategoryResponse addCategory(@RequestBody CategoryRequest categoryRequest){
        return iCategoryService.addOrUpdateCategory(categoryRequest);
    }

    @PutMapping("/category")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CategoryResponse updateCategory(@RequestBody CategoryRequest categoryRequest){
        return iCategoryService.addOrUpdateCategory(categoryRequest);
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
