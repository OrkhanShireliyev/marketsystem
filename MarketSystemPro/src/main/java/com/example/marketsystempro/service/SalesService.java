package com.example.marketsystempro.service;

import com.example.marketsystempro.models.Product;
import com.example.marketsystempro.models.Sale;
import com.example.marketsystempro.models.SalesItem;
import com.example.marketsystempro.repo.SalesRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SalesService {
    private final SalesRepo salesRepo;
    private final ProductService productService;

    public Sale addSales(List<SalesItem> salesItems) {
        BigDecimal price = BigDecimal.valueOf(0);
        for (SalesItem salesItem : salesItems) {
            Product product = productService.getProductByBarcode(salesItem.getProductBarCode());

            if (product.getCount() - salesItem.getSalesItemCount() != 0) {
                product.setCount(product.getCount() - salesItem.getSalesItemCount());
                        productService.updateProduct(
                        productService.addProductWithScanner().getBarCode(), productService.addProductWithScanner().getName(),
                        productService.addProductWithScanner().getProductCategory(),
                        productService.addProductWithScanner().getPrice(), productService.addProductWithScanner().getId());
            }
            BigDecimal price1 = product.getPrice().multiply(BigDecimal.valueOf(salesItem.getSalesItemCount().longValue()));
            price = price.add(price1);
        }
        Sale sale = new Sale(null, UUID.randomUUID().toString(), price, salesItems, LocalDateTime.now());
        for (SalesItem salesItem : salesItems) {
            salesItem.setSales(sale);
        }
        sale.setSalesItems(salesItems);

        return sale;
    }

    public Sale updateSales(BigDecimal salesPrice, List<SalesItem> salesItems, LocalDateTime salesDate, String salesNumber) {
        Sale sale = salesRepo.updateSale(salesPrice, salesItems, salesDate, salesNumber);
        return sale;
    }

    public Sale deleteSales(String salesNumber) {
        Sale sale = salesRepo.getSaleBySalesNumber(salesNumber);
        salesRepo.delete(sale);
        return sale;
    }

    public List<Sale> getBetweenTwoTime(LocalDateTime startTime, LocalDateTime endTime) {
        List<Sale> sales = salesRepo.getSalesBySalesDateBetweenAndSalesDate(startTime, endTime);
        return sales;
    }

    public List<Sale> getInOneDaySales(LocalDate localDate) {

        LocalDateTime localDateTime = LocalDateTime.of(localDate, LocalTime.of(00, 00));
        LocalDateTime localDateTime1 = LocalDateTime.of(localDate, LocalTime.of(23, 59));
//        TypedQuery<Sale> saleTypedQuery = entityManager.createQuery("select s from Sale s where s.salesDate between :startTime and :endTime ", Sale.class);
//        saleTypedQuery.setParameter("startTime", localDateTime);
//        saleTypedQuery.setParameter("endTime", localDateTime1);

        List<Sale> sales = salesRepo.getSalesBySalesDateBetween(localDateTime,localDateTime1);
        return sales;
    }

    public List<Sale> getSalesPrice(BigDecimal startPrice, BigDecimal endPrice) {
        List<Sale> sales = salesRepo.getSalesBySalesPriceBetweenAndSalesPrice(startPrice, endPrice);
        return sales;
    }

    public Sale getSalesBySalesNumber(String salesNumber) {
        Sale sale = salesRepo.getSaleBySalesNumber(salesNumber);
        return sale;
    }

    public void returnSalesProduct(SalesItem salesItem, String salesNumber) {

        Product product = productService.getProductByBarcode(salesItem.getProductBarCode());

        Sale sale1 = getSalesBySalesNumber(salesNumber);
        SalesItem salesItem1 = sale1.getSalesItems().stream().filter(salesItem2 -> salesItem2.getProductBarCode().equals(salesItem.getProductBarCode())).findAny().get();
        if (sale1.getSalesItems().size() == 1 && salesItem1.getSalesItemCount() == salesItem.getSalesItemCount()) {
            deleteSales(salesNumber);
            product.setCount(product.getCount() + salesItem.getSalesItemCount());
            productService.updateProduct(product.getBarCode(), product.getName(), product.getProductCategory(), product.getPrice(), product.getId());
        } else {
            salesItem1.setSalesItemCount(salesItem1.getSalesItemCount() - salesItem.getSalesItemCount());
            Integer endCount = salesItem1.getSalesItemCount();
            BigDecimal price = product.getPrice();
            BigDecimal endPrice = BigDecimal.valueOf(endCount).multiply(price);
            sale1.getSalesItems().remove(salesItem1);
            sale1.getSalesItems().add(salesItem1);
            sale1.setSalesPrice(endPrice);
            updateSales(sale1.getSalesPrice(), sale1.getSalesItems(), sale1.getSalesDate(), sale1.getSalesNumber());
            product.setCount(product.getCount() + salesItem.getSalesItemCount());
            productService.updateProduct(product.getBarCode(), product.getName(), product.getProductCategory(), product.getPrice(), product.getId());
        }
    }

    public void returnAllSales(List<SalesItem> salesItems) {

        for (SalesItem salesItem : salesItems) {
//            Sale sale = getSalesBySalesNumber(salesItem.getSales().getSalesNumber());
            Product product = productService.getProductByBarcode(salesItem.getProductBarCode());
            deleteSales(salesItem.getSales().getSalesNumber());
            product.setCount(product.getCount() + salesItem.getSalesItemCount());
            productService.updateProduct(product.getBarCode(), product.getName(), product.getProductCategory(), product.getPrice(), product.getId());
        }
    }

    public List<Sale> getAllSales() {
        List<Sale> sales = salesRepo.findAll();
        return sales;
    }
}
