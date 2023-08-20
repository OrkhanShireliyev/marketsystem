package com.example.marketsystempro.repo;

import com.example.marketsystempro.models.Product;
import com.example.marketsystempro.models.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Product p set p.barCode=:barCode, p.name=:name ,p.productCategory=:productCategory, p.price = :price where p.id = :id ")
    Product updateProduct(
            @Param(value = "barCode") String barCode, @Param(value = "name") String name,
            @Param(value = "productCategory") ProductCategory productCategory, @Param(value = "price") BigDecimal price, @Param(value = "id") Long id);


    Product getProductByBarCode(String barCode);

    List<Product> getProductsByProductCategory(ProductCategory productCategory);

    @Query("select p from Product p where p.price between :firstPrice and :endPrice ")
    List<Product>getProductsByPriceBetweenAndPrice(@Param("firstPrice") BigDecimal firstPrice, @Param("endPrice") BigDecimal endPrice);

    Product getProductByName(String name);




}
