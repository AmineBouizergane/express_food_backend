package com.example.expressfood.service;

import com.example.expressfood.dto.request.CategoryRequest;
import com.example.expressfood.dto.request.ProductRequest;
import com.example.expressfood.dto.response.MessageResponse;
import com.example.expressfood.dto.response.PageResponse;
import com.example.expressfood.dto.response.ProductResponse;

public interface IProductService {
    ProductResponse addProduct(ProductRequest productRequest);
    MessageResponse deleteProduct(Long productId);
    ProductResponse updateProduct(ProductRequest productRequest);
    ProductResponse getProductById(Long id);
    PageResponse<ProductResponse> getProducts(int page, int size);
    PageResponse<ProductResponse> getAvailableProducts(int page, int size);
    PageResponse<ProductResponse> getProductsByCategory(Long categoryId, int page, int size);
    PageResponse<ProductResponse> getAvailableProductsByCategory(Long categoryId, int page, int size);
    PageResponse<ProductResponse>  getProductsByKeyword(String keyword,int page, int size);
    PageResponse<ProductResponse>  getAvailableProductsByKeyword(String keyword, Long categoryId, int page, int size);
    MessageResponse changeProductAvailability(Long productId, boolean availability);
}
