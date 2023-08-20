package com.example.marketsystempro.repo;

import com.company.marketsystem.models.Sale;
import com.company.marketsystem.models.SalesItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SalesRepo extends JpaRepository<Sale,Long> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Sale s set s.salesPrice=:salesPrice ,s.salesItems=:salesItems, s.salesDate = :salesDate where s.salesNumber = :salesNumber ")
    Sale updateSale(
            @Param(value = "salesPrice") BigDecimal salesPrice, @Param(value = "salesItems") List<SalesItem> salesItems,
            @Param(value = "salesDate") LocalDateTime salesDate, @Param(value = "salesNumber") String salesNumber);
    Sale getSaleBySalesNumber(String salesNumber);

    @Query("select s from Sale s where s.salesPrice between :startPrice and :endPrice ")
    List<Sale> getSalesBySalesPriceBetweenAndSalesPrice(@Param("startPrice") BigDecimal startPrice,@Param("endPrice") BigDecimal endPrice);

    @Query("select s from Sale s where s.salesDate= :localDate ")
    List<Sale>getSalesBySalesDate(@Param("localDate") LocalDate localDate);

    @Query("select s from Sale s where s.salesDate between :startDate and :endDate ")
    List<Sale> getSalesBySalesDateBetweenAndSalesDate(@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate);

    List<Sale> getSalesBySalesDateBetween(LocalDateTime startTime,LocalDateTime endTime);
}
