package com.example.marketsystempro.dto;

import com.company.marketsystem.models.SalesItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleDto {
    private Long id;
    private String salesNumber;
    private BigDecimal salesPrice;
    private List<SalesItem> salesItems;
    private LocalDateTime salesDate;

}
