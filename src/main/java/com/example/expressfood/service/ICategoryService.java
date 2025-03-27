package com.example.expressfood.service;

import com.example.expressfood.dto.request.CategoryRequest;
import com.example.expressfood.dto.response.CategoryResponse;
import com.example.expressfood.dto.response.MessageResponse;
import com.example.expressfood.dto.response.PageResponse;

import java.util.List;

public interface ICategoryService {

    CategoryResponse addCategory(CategoryRequest categoryRequest);
    CategoryResponse updateCategory(CategoryRequest categoryRequest);
    MessageResponse deleteCategory(Long categoryId);
    List<CategoryResponse> getAllCategories();
    CategoryResponse getCategoryById(Long categoryId);

}
