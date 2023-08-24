package com.example.marketsystempro.dto;

import com.example.marketsystempro.models.Sale;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesItemDto {
    private Long id;
    private String productBarCode;
    private Integer salesItemCount;
    private LocalDateTime localDateTime;
    private Sale sales;

}
