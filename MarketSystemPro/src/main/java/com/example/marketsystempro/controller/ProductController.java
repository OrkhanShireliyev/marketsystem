package com.example.marketsystempro.controller;

import com.example.marketsystempro.dto.ProductDto;
import com.example.marketsystempro.dto.ResponseDto;
import com.example.marketsystempro.models.Product;
import com.example.marketsystempro.models.ProductCategory;
import com.example.marketsystempro.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor

public class ProductController {
    private final ProductService productService;

    private final ObjectMapper objectMapper;

    @GetMapping("/products")
    public ResponseEntity<ResponseDto> getAllProducts() {
        List<Product> list = productService.getAllProducts();
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(),list));
    }

    @PutMapping("/update/{barCode}/{name}/{productCategory}/{price}/{id}")
    public ResponseEntity<ResponseDto> updateProduct(
            @PathVariable String barCode, @PathVariable String name, @PathVariable ProductCategory productCategory,
            @PathVariable BigDecimal price, @PathVariable Long id) {
        Product product = productService.updateProduct(barCode, name, productCategory, price, id);
        return ResponseEntity.ok(ResponseDto.of("Successfully updated", HttpStatus.OK.value(),product));
    }

    @DeleteMapping("/delete/{barCode}")
    public ResponseEntity<ResponseDto> deleteProduct(@PathVariable String barCode) {
        Product deleteProduct = productService.deleteProduct(barCode);
        return ResponseEntity.ok(ResponseDto.of("Successfully deleted", HttpStatus.OK.value(), deleteProduct));
    }
//    @CrossOrigin("*")
    @PostMapping("/save")
    public ResponseEntity<ResponseDto> saveProduct(@RequestBody ProductDto productDto) throws JsonProcessingException {
        System.out.println(productDto);
//        ProductDto productDto1=objectMapper.readValue(productDto, ProductDto.class);
        Product product = objectMapper.convertValue(productDto, Product.class);
        Product product1 = productService.saveProduct(product);
        return ResponseEntity.ok(ResponseDto.of("Successfully added", HttpStatus.OK.value(), product1));
    }

    @GetMapping("/byCategory/{productCategory}")
    public ResponseEntity<ResponseDto> getProductByCategory(@PathVariable ProductCategory productCategory) {
        List<Product> list = productService.getProductByCategory(productCategory);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), list));
    }

    @GetMapping("/byName/{name}")
    public ResponseEntity<Product> getProductByName(@PathVariable String name) {
        Product product = productService.getProductByName(name);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/byBarCode/{barCode}")
    public ResponseEntity<ResponseDto> getProductByBarcode(@PathVariable String barCode) {
        Product product = productService.getProductByBarcode(barCode);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(),product));
    }

    @GetMapping("/{firstPrice}/{endPrice}")
    public ResponseEntity<ResponseDto> getProductByBetweenPrice(@PathVariable BigDecimal firstPrice, @PathVariable BigDecimal endPrice) {
        List<Product> products = productService.getProductByBetweenPrice(firstPrice, endPrice);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(),products));
    }
}
