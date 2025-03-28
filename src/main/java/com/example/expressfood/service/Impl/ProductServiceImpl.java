package com.example.expressfood.service.Impl;

import com.example.expressfood.dao.CategoryRepos;
import com.example.expressfood.dao.FeedBackRepos;
import com.example.expressfood.dao.ProductRepos;
import com.example.expressfood.dao.UniteRepos;
import com.example.expressfood.dto.request.ProductRequest;
import com.example.expressfood.dto.response.*;
import com.example.expressfood.entities.Category;
import com.example.expressfood.entities.Product;
import com.example.expressfood.exception.CategoryException;
import com.example.expressfood.exception.ErrorMessages;
import com.example.expressfood.exception.ProductException;
import com.example.expressfood.exception.UniteException;
import com.example.expressfood.service.IProductService;
import com.example.expressfood.shared.MessagesEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepos productRepos;

    private final CategoryRepos categoryRepos;

    private final UniteRepos uniteRepos;

    private final FeedBackRepos feedBackRepos;

    @Override
    //FIXME : Remove isValid()
    public ProductResponse addProduct(ProductRequest productRequest){
        String baseUrl = "http://localhost:8080/Photos/";
        Product product = productRequest.toEntity();
        product.setUnite(uniteRepos.findById(productRequest.getUnite())
                .orElseThrow(() -> new UniteException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage())));
        product.setCategory(categoryRepos.findById(productRequest.getCategory())
                .orElseThrow(() -> new CategoryException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage())));
        product.setIsDeleted(false);
        product.setCreatedAt(LocalDateTime.now());
        if(productRequest.getImageUrl() != null && productRequest.getImageUrl() != "")
            product.setImageUrl(baseUrl + productRequest.getImageUrl());
        else
            product.setImageUrl(baseUrl + "default_product_img.png");
        product.isValid();
        Product savedProduct =  productRepos.save(product);
        return ProductResponse.fromEntity(savedProduct);
    }

    @Override
    public MessageResponse deleteProduct(Long productId) {
        Product product = productRepos.findById(productId)
                .orElseThrow(() -> new ProductException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        MessageResponse messageResponse = new MessageResponse();
        product.setIsDeleted(true);
        productRepos.save(product);
        messageResponse.setMessage(MessagesEnum.PRODUCT_DELETED_SUCCESSFULLY.getMessage());
        return messageResponse;
    }

    @Override
    //FIXME : Remove isValid()
    public ProductResponse updateProduct(ProductRequest productRequest) {
        Product optionalProduct = productRepos.findById(productRequest.getProductId())
                .orElseThrow(() -> new ProductException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        Product product = productRequest.toEntity();
        product.setUnite(uniteRepos.findById(productRequest.getUnite())
                .orElseThrow(() -> new UniteException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage())));
        product.setCategory(categoryRepos.findById(productRequest.getCategory())
                .orElseThrow(() -> new CategoryException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage())));
        product.setIsDeleted(optionalProduct.getIsDeleted());
        if(product.getImageUrl() == null || product.getImageUrl()=="")
            product.setImageUrl(optionalProduct.getImageUrl());
        product.isValid();
        Product updatedProduct = productRepos.save(product);
        return ProductResponse.fromEntity(updatedProduct);
    }

    @Override
    public ProductResponse getProductById(Long id) {
       Product product = productRepos.findByProductIdAndIsDeletedFalse(id)
               .orElseThrow(() -> new ProductException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        ProductResponse productResponse = ProductResponse.fromEntity(product);
        if(product.getFeedBacks().isEmpty()){
            productResponse.setAvgRating(5);
        }else{
            double avgRating = feedBackRepos.getAverageRatingByProductId(product.getProductId());
            productResponse.setAvgRating(avgRating);
        }
        return productResponse;
    }

    @Override
    public PageResponse<ProductResponse> getProducts(int page, int size) {
        Page<Product> productPage = productRepos.findProductByIsDeletedFalse(PageRequest.of(page, size));
        PageResponse<ProductResponse> pageResponse = new PageResponse<>();
        pageResponse.setPage(page);
        pageResponse.setSize(size);
        pageResponse.setTotalPage(productPage.getTotalPages());
        List<ProductResponse> productResponse = productPage.getContent().stream()
                .map(product -> {
                    ProductResponse response = ProductResponse.fromEntity(product);
                    double avgRating = product.getFeedBacks().isEmpty() ? 5
                            : feedBackRepos.getAverageRatingByProductId(product.getProductId());
                    response.setAvgRating(avgRating);
                    return response;
                })
                .collect(Collectors.toList());
        pageResponse.setContent(productResponse);
        if (pageResponse.getContent().isEmpty())
            throw new ProductException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        else
            return pageResponse;
    }

    @Override
    public PageResponse<ProductResponse> getAvailableProducts(int page, int size) {
        Page<Product> productPage = productRepos.findProductByIsDeletedFalseAndIsAvailableTrue(PageRequest.of(page, size));
        PageResponse<ProductResponse> pageResponse = new PageResponse<>();
        pageResponse.setPage(page);
        pageResponse.setSize(size);
        pageResponse.setTotalPage(productPage.getTotalPages());
        List<ProductResponse> productResponse = productPage.getContent().stream()
                .map(product -> {
                    ProductResponse response = ProductResponse.fromEntity(product);
                    double avgRating = product.getFeedBacks().isEmpty() ? 5
                            : feedBackRepos.getAverageRatingByProductId(product.getProductId());
                    response.setAvgRating(avgRating);
                    return response;
                })
                .collect(Collectors.toList());
        pageResponse.setContent(productResponse);
        if (pageResponse.getContent().isEmpty())
            throw new ProductException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        else
            return pageResponse;
    }

    @Override
    public PageResponse<ProductResponse> getProductsByCategory(Long categoryId, int page, int size) {
        Category category = categoryRepos.findById(categoryId)
                .orElseThrow(() -> new CategoryException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        Page<Product> productPage = productRepos.findProductByCategoryAndIsDeletedFalse(category, PageRequest.of(page, size));
        PageResponse<ProductResponse> pageResponse = new PageResponse<>();
        pageResponse.setPage(page);
        pageResponse.setSize(size);
        pageResponse.setTotalPage(productPage.getTotalPages());
        List<ProductResponse> productResponseList = productPage.getContent().stream()
                .map(product -> {
                    ProductResponse response = ProductResponse.fromEntity(product);
                    double avgRating = product.getFeedBacks().isEmpty() ? 5
                            : feedBackRepos.getAverageRatingByProductId(product.getProductId());
                    response.setAvgRating(avgRating);
                    return response;
                })
                .collect(Collectors.toList());
        pageResponse.setContent(productResponseList);
        if (pageResponse.getContent().isEmpty())
            throw new ProductException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        else
            return pageResponse;
    }

    @Override
    public PageResponse<ProductResponse> getAvailableProductsByCategory(Long categoryId, int page, int size) {
        Category category = categoryRepos.findById(categoryId)
                .orElseThrow(() -> new CategoryException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        Page<Product> productPage = productRepos.findProductByCategoryAndIsDeletedFalseAndIsAvailableTrue(category, PageRequest.of(page, size));
        PageResponse<ProductResponse> pageResponse = new PageResponse<>();
        pageResponse.setPage(page);
        pageResponse.setSize(size);
        pageResponse.setTotalPage(productPage.getTotalPages());
        List<ProductResponse> productResponseList = productPage.getContent().stream()
                .map(product -> {
                    ProductResponse response = ProductResponse.fromEntity(product);
                    double avgRating = product.getFeedBacks().isEmpty() ? 5
                            : feedBackRepos.getAverageRatingByProductId(product.getProductId());
                    response.setAvgRating(avgRating);
                    return response;
                })
                .collect(Collectors.toList());
        pageResponse.setContent(productResponseList);
        if (pageResponse.getContent().isEmpty())
            throw new ProductException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        else
            return pageResponse;
    }

    @Override
    public PageResponse<ProductResponse> getProductsByKeyword(String keyword,int page, int size) {
        Page<Product> productPage = productRepos.findByNameContainingAndIsDeletedFalse(keyword, PageRequest.of(page,size));
        PageResponse<ProductResponse> pageResponse = new PageResponse<>();
        pageResponse.setPage(page);
        pageResponse.setSize(size);
        pageResponse.setTotalPage(productPage.getTotalPages());
        List<ProductResponse> productResponseList = productPage.getContent().stream()
                .map(product -> {
                    ProductResponse response = ProductResponse.fromEntity(product);
                    double avgRating = product.getFeedBacks().isEmpty() ? 5
                            : feedBackRepos.getAverageRatingByProductId(product.getProductId());
                    response.setAvgRating(avgRating);
                    return response;
                })
                .collect(Collectors.toList());
        pageResponse.setContent(productResponseList);
        if (pageResponse.getContent().isEmpty())
            throw new ProductException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        else
            return pageResponse;
    }

    @Override
    public PageResponse<ProductResponse> getAvailableProductsByKeyword(String keyword,Long categoryId, int page, int size) {
        Page<Product> productPage;
        if(categoryId == null){
            productPage = productRepos.findByNameContainingAndIsDeletedFalseAndIsAvailableTrue(keyword, PageRequest.of(page,size));
        }
        else{
            Category category = categoryRepos.findById(categoryId).orElseThrow();
            productPage = productRepos.findByCategoryAndNameContainingAndIsDeletedFalseAndIsAvailableTrue(category, keyword, PageRequest.of(page,size));
        }
        PageResponse<ProductResponse> pageResponse = new PageResponse<>();
        pageResponse.setPage(page);
        pageResponse.setSize(size);
        pageResponse.setTotalPage(productPage.getTotalPages());
        List<ProductResponse> productResponseList = productPage.getContent().stream()
                .map(product -> {
                    ProductResponse response = ProductResponse.fromEntity(product);
                    double avgRating = product.getFeedBacks().isEmpty() ? 5
                            : feedBackRepos.getAverageRatingByProductId(product.getProductId());
                    response.setAvgRating(avgRating);
                    return response;
                })
                .collect(Collectors.toList());
        pageResponse.setContent(productResponseList);
        if (pageResponse.getContent().isEmpty())
            throw new ProductException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        else
            return pageResponse;
    }

    @Override
    public MessageResponse changeProductAvailability(Long productId, boolean availability) {
        Product product = productRepos.findById(productId)
                .orElseThrow(() -> new ProductException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        product.setIsAvailable(availability);
        Product savedProduct = productRepos.save(product);
        if (savedProduct.getIsAvailable() ==  availability)
            return new MessageResponse(MessagesEnum.PRODUCT_AVAILABILITY_CHANGED_SUCCESSFULLY.getMessage());
        else
            throw new ProductException(ErrorMessages.UPDATE_RECORD_ERROR.getErrorMessage());
    }
}
