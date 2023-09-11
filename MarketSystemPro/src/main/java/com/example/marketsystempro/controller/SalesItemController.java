package com.example.marketsystempro.controller;

import com.company.marketsystem.models.SalesItem;
import com.company.marketsystem.service.SalesItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/salesItem")
@RequiredArgsConstructor
public class SalesItemController {
     private final SalesItemService salesItemService;

     public List<SalesItem> getSalesItemsBysalesNumber(String salesNumber){
       return salesItemService.getSalesItemsBysalesNumber(salesNumber);
     }

}
