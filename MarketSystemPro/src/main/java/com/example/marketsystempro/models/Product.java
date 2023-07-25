package com.example.marketsystempro.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
@Builder
public class Product {
   @Id
   @SequenceGenerator(name = "pro_increment",allocationSize =1 )
   @GeneratedValue(generator = "pro_increment")
   private Long id;
   private String barCode;
   private String name;
   @Enumerated()
   private ProductCategory productCategory;
   private BigDecimal price;
   private Integer count;

}
