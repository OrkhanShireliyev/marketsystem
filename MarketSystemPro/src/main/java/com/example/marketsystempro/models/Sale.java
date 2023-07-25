package com.example.marketsystempro.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sales")
public class Sale {
    @Id
    @SequenceGenerator(name = "sale_increment",allocationSize =1 )
    @GeneratedValue(generator = "sale_increment")
    private Long id;
    private String salesNumber;
    private BigDecimal salesPrice;
    @OneToMany(mappedBy="sales",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<SalesItem> salesItems;
    private LocalDateTime salesDate;
}
