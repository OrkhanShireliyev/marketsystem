package com.example.marketsystempro.repo;

import com.company.marketsystem.models.SalesItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesItemRepo extends JpaRepository<SalesItem,Long> {

    @Query("select s from SalesItem s where s.sales.salesNumber= :salesNumber ")
    List<SalesItem> getSalesItemsBysalesNumber(@Param("salesNumber") String salesNumber);
}
