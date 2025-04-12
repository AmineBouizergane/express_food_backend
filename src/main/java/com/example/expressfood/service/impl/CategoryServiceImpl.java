package com.example.expressfood.service.impl;

import com.example.expressfood.dao.CategoryRepos;
import com.example.expressfood.dao.ProductRepos;
import com.example.expressfood.dto.request.CategoryRequest;
import com.example.expressfood.dto.response.CategoryResponse;
import com.example.expressfood.dto.response.MessageResponse;
import com.example.expressfood.entities.Category;
import com.example.expressfood.exception.CategoryException;
import com.example.expressfood.exception.ErrorMessages;
import com.example.expressfood.service.ICategoryService;
import com.example.expressfood.shared.MessagesEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {

    private final CategoryRepos categoryRepos;
    private final ProductRepos productRepos;

    @Override
    public CategoryResponse addOrUpdateCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryRequest, category);
        Category savedCategory =  categoryRepos.save(category);
        CategoryResponse categoryResponse = new CategoryResponse();
        BeanUtils.copyProperties(savedCategory, categoryResponse);
        return categoryResponse;
    }

    @Override
    public MessageResponse deleteCategory(Long categoryId) {
        Category category = categoryRepos.findById(categoryId)
                .orElseThrow(() -> new CategoryException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        Long productsCount = productRepos.countProductsByCategory(category);
        if(productsCount > 0)
            throw new CategoryException(ErrorMessages.CANT_BE_DELETE.getErrorMessage());
        categoryRepos.delete(category);
        return new MessageResponse(MessagesEnum.CATEGORY_DELETED_SUCCESSFULLY.getMessage());
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<Category> categoryPage = categoryRepos.findAll();
        List<CategoryResponse> categoryResponseList = new ArrayList<>();
        categoryPage.forEach( c -> {
            CategoryResponse categoryResponse = new CategoryResponse();
            BeanUtils.copyProperties(c, categoryResponse);
            categoryResponseList.add(categoryResponse);
        });
        return categoryResponseList;
    }

    @Override
    public CategoryResponse getCategoryById(Long categoryId) {
        Category category = categoryRepos.findById(categoryId)
                .orElseThrow(() -> new CategoryException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        CategoryResponse categoryResponse = new CategoryResponse();
        BeanUtils.copyProperties(category, categoryResponse);
        return categoryResponse;
    }
}
