package com.example.marketsystempro.service;


import com.example.marketsystempro.models.SalesItem;
import com.example.marketsystempro.repo.SalesItemRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesItemService {
    private final SalesItemRepo salesItemRepo;

    public List<SalesItem> getSalesItemsBysalesNumber(String salesNumber) {
        List<SalesItem> salesItems=salesItemRepo.getSalesItemsBysalesNumber(salesNumber);
        return salesItems;
    }


}
