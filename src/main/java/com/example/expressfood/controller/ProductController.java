package com.example.expressfood.controller;

import com.example.expressfood.dto.request.ProductRequest;
import com.example.expressfood.dto.response.MessageResponse;
import com.example.expressfood.dto.response.PageResponse;
import com.example.expressfood.dto.response.ProductResponse;
import com.example.expressfood.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService iProductService;

    @PostMapping("/product")
    @PostAuthorize("hasAuthority('ADMIN')")
    public ProductResponse addProduct(@RequestBody ProductRequest productRequest){
       return iProductService.addProduct(productRequest);
    }
    @DeleteMapping("/product/{productId}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public void deleteProduct(@PathVariable("productId") Long productId){
        iProductService.deleteProduct(productId);
    }
    @PutMapping("/product")
    @PostAuthorize("hasAuthority('ADMIN')")
    public ProductResponse updateProduct(@RequestBody ProductRequest product){
        return iProductService.updateProduct(product);
    }

    @GetMapping("/product/{productId}")
    @PostAuthorize("hasAuthority('USER')")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("productId") Long productId){
        ProductResponse product = iProductService.getProductById(productId);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/product")
    @PostAuthorize("hasAuthority('ADMIN')")
    public PageResponse<ProductResponse> getProducts(@RequestParam(required = false, defaultValue = "0") int page,
                                                     @RequestParam(required = false, defaultValue = "10") int size){
        return iProductService.getProducts(page, size);
    }

    @GetMapping("/product/available")
    @PostAuthorize("hasAuthority('USER')")
    public PageResponse<ProductResponse> getAvailableProducts(@RequestParam(required = false, defaultValue = "0") int page,
                                                     @RequestParam(required = false, defaultValue = "10") int size){
        return iProductService.getAvailableProducts(page, size);
    }

    @GetMapping("/product/category")
    @PreAuthorize("hasAuthority('ADMIN')")
    public PageResponse<ProductResponse> getProductsByCategory(@RequestParam Long categoryId,
                                                     @RequestParam(required = false, defaultValue = "0") int page,
                                                     @RequestParam(required = false, defaultValue = "10") int size){
        return iProductService.getProductsByCategory(categoryId, page, size);
    }
    @GetMapping("/product/available/category")
    @PreAuthorize("hasAuthority('USER')")
    public PageResponse<ProductResponse> getAvailableProductsByCategory(@RequestParam Long categoryId,
                                                               @RequestParam(required = false, defaultValue = "0") int page,
                                                               @RequestParam(required = false, defaultValue = "10") int size){
        return iProductService.getAvailableProductsByCategory(categoryId, page, size);
    }


    @GetMapping("/product/search")
    @PreAuthorize("hasAuthority('ADMIN')")
    public PageResponse<ProductResponse> getProductsByKeyword(@RequestParam("keyword") String keyword,
                                                    @RequestParam(required = false, defaultValue = "0") int page,
                                                    @RequestParam(required = false, defaultValue = "10") int size){
        return iProductService.getProductsByKeyword(keyword, page, size);
    }


    @GetMapping("/product/available/search")
    @PreAuthorize("hasAuthority('USER')")
    public PageResponse<ProductResponse> getAvailableProductsByKeyword(@RequestParam("keyword") String keyword,
                                                    @RequestParam(required = false) Long categoryId,
                                                    @RequestParam(required = false, defaultValue = "0") int page,
                                                    @RequestParam(required = false, defaultValue = "10") int size){
        return iProductService.getAvailableProductsByKeyword(keyword,categoryId, page, size);
    }

    @GetMapping("/product/availability")
    @PreAuthorize("hasAuthority('ADMIN')")
    public MessageResponse changeProductAvailability(@RequestParam Long productId,@RequestParam boolean availability){
        return iProductService.changeProductAvailability(productId, availability);
    }
}
