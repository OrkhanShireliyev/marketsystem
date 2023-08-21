package com.example.marketsystempro.dto;

import com.example.marketsystempro.models.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private String barCode;
    private String name;
    private ProductCategory productCategory;
    private BigDecimal price;
    private Integer count;

}
