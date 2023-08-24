package com.example.marketsystempro.controller;

import com.example.marketsystempro.dto.SalesItemDto;
import com.example.marketsystempro.models.Sale;
import com.example.marketsystempro.models.SalesItem;
import com.example.marketsystempro.service.SalesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/sale")
@RequiredArgsConstructor
public class SaleController {
    private final SalesService salesService;

    private final ObjectMapper objectMapper;

    @PostMapping("/save")
    public Sale saveSale(@RequestBody List<SalesItemDto> salesItemDtos) {
        List<SalesItem> salesItems = Collections.singletonList(objectMapper.convertValue(salesItemDtos, SalesItem.class));
        return salesService.addSales(salesItems);
    }

    @PutMapping("/update//{salesPrice}/{salesItems}/{salesDate}/{salesNumber}")
    public Sale updateSale(@PathVariable(value = "salesPrice") BigDecimal salesPrice,
                           @PathVariable(value = "salesItems") List<SalesItem> salesItems,
                           @PathVariable(value = "salesDate") LocalDateTime salesDate,
                           @PathVariable(value = "salesNumber") String salesNumber) {
        Sale sale = salesService.updateSales(salesPrice, salesItems, salesDate, salesNumber);
        return sale;
    }

    @DeleteMapping("/delete/{salesNumber}")
    public Sale deleteSale(@PathVariable String salesNumber) {
        Sale sale = salesService.deleteSales(salesNumber);
        return sale;
    }

    @GetMapping("/{year}/{month}/{day}")
    public List<Sale> getInOneDaySales(@PathVariable String year, @PathVariable String month, @PathVariable String day) {
        String date = year + "-" + month + "-" + day;
        LocalDate localDate = LocalDate.parse(date);

        List<Sale> sales = salesService.getInOneDaySales(localDate);
        return sales;
    }

    @GetMapping("/byPrice/{firstPrice}/{endPrice}")
    public List<Sale> getSalesPrice(@PathVariable BigDecimal firstPrice, @PathVariable BigDecimal endPrice) {//5
        List<Sale> sales = salesService.getSalesPrice(firstPrice, endPrice);
        return sales;
    }

    @GetMapping("/sales")
    public List<Sale> getAllSales() {
        List<Sale> sales = salesService.getAllSales();
        return sales;
    }

    @GetMapping("/byTime/{startTime}/{endTime}")
    public List<Sale> getBetweenTwoTime(@PathVariable LocalDateTime startTime, @PathVariable LocalDateTime endTime) {
        List<Sale> sales = salesService.getBetweenTwoTime(startTime, endTime);
        return sales;
    }

    @PostMapping("/returnAll/{salesItems}")
    public void returnAllSales(@PathVariable List<SalesItem> salesItems) {
        salesService.returnAllSales(salesItems);
    }

    @PostMapping("/returnSales/{salesItem}/{salesNumber}")
    public void returnSalesProduct(SalesItem salesItem, String salesNumber) {
        salesService.returnSalesProduct(salesItem, salesNumber);
    }

    @GetMapping("/bySalesNumber/{salesNumber}")
    public Sale getSalesBySalesNumber(String salesNumber) {
        return salesService.getSalesBySalesNumber(salesNumber);
    }
}
