package com.example.marketsystempro.service;

import com.example.marketsystempro.dto.ProductDto;
import com.example.marketsystempro.models.Product;
import com.example.marketsystempro.models.ProductCategory;
import com.example.marketsystempro.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class ProductService {

    private  final ProductRepo productRepo;

    public Product addProductWithScanner() {
        System.out.println("product melumatlari");
        Scanner scId = new Scanner(System.in);
        System.out.println("productin id-sini daxil edin");
        Long id = scId.nextLong();
        Scanner sc1 = new Scanner(System.in);
        System.out.println("productin barcode-ni daxil edin");
        String barcode = sc1.nextLine();
        System.out.println("productin name-ni daxil edin");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        System.out.println("productin productCategory-ni daxil edin");
        Scanner scanner1 = new Scanner(System.in);
        System.out.println(
                "MEAT_AND_GOURMET yazanda bu catagory olacaq \n" +
                        "DRY_FOOD yazanda bu catagory olacaq ,\n" +
                        "SWEETS   yazanda bu catagory olacaq ,\n" +
                        "DRINKS   yazanda bu catagory olacaq ,\n" +
                        "FRUIT_AND_VEGETABLES yazanda bu catagory olacaq ,\n" +
                        "DAIRY_PRODUCTS yazanda bu catagory olacaq ,\n" +
                        "CLEANİNG_PRODUCTS yazanda bu catagory olacaq ;");
        ProductCategory productCategory = ProductCategory.valueOf(scanner1.nextLine());
        System.out.println("productin price-ni daxil edin");
        Scanner scanner2 = new Scanner(System.in);
        BigDecimal price = scanner2.nextBigDecimal();
        System.out.println("productin count-unu daxil edin");
        Scanner scanner3 = new Scanner(System.in);
        Integer count = scanner3.nextInt();
        Product product = new Product(id, barcode, name, productCategory, price, count);
        return product;
    }

    public Product saveProduct(Product product) {
       Product product1= productRepo.save(product);
       return product1;
    }

    public Product updateProduct(String barCode, String name, ProductCategory productCategory, BigDecimal price, Long id) {
        Product product = productRepo.updateProduct(barCode, name, productCategory, price, id);
        return product;
    }

    public Product deleteProduct(String barCode) {
        Product product = productRepo.getProductByBarCode(barCode);
        productRepo.delete(product);
        return product;
    }

    public List<Product> getProductByCategory(ProductCategory productCategory) {
        List<Product> products = productRepo.getProductsByProductCategory(productCategory);
        return products;
    }

    public List<Product> getProductByBetweenPrice(BigDecimal firstPrice, BigDecimal endPrice) {
        List<Product> products = productRepo.getProductsByPriceBetweenAndPrice(firstPrice, endPrice);
        return products;
    }

    public Product getProductByName(String name) {
        Product product = productRepo.getProductByName(name);
        return product;
    }

    public Product getProductByBarcode(String barCode) {
        Product product = productRepo.getProductByBarCode(barCode);
        return product;
    }

    public List<Product> getAllProducts() {
        List<Product> products = productRepo.findAll();
        return products;
    }

    public void getProductByMenuCategory() {
        Boolean aBoolean = true;

        ProductDto product = null;
        List<ProductDto> products = new ArrayList<>();

        System.out.println(
                " 1-i  basanda   MEAT_AND_GOURMET,\n" +
                        " 2-ni basanda   DRY_FOOD,\n" +
                        " 3-u  basanda   SWEETS,\n" +
                        " 4-u  basanda   DRINKS,\n" +
                        " 5-i  basanda   FRUIT_AND_VEGETABLES,\n" +
                        " 6-ni basanda   DAIRY_PRODUCTS,\n" +
                        " 7-ni basanda   CLEANİNG_PRODUCTS;");

        Scanner scanner2 = new Scanner(System.in);
        System.out.println("Secmek istediyiniz operation nomresini daxil edin: ");
        int operation2 = scanner2.nextInt();

        while (aBoolean) {
            if (operation2 == 1) {
                if (product.getProductCategory().equals(ProductCategory.MEAT_AND_GOURMET)) {
                    products.add(product);
                    for (ProductDto product1 : products) {
                        System.out.println(product1);
                    }
                }
            } else if (operation2 == 2) {
                if (product.getProductCategory().equals(ProductCategory.DRY_FOOD)) {
                    products.add(product);
                    for (ProductDto product1 : products) {
                        System.out.println(product1);
                    }
                }
            } else if (operation2 == 3) {
                if (product.getProductCategory().equals(ProductCategory.SWEETS)) {
                    products.add(product);
                    for (ProductDto product1 : products) {
                        System.out.println(product1);
                    }
                }
            } else if (operation2 == 4) {
                if (product.getProductCategory().equals(ProductCategory.DRINKS)) {
                    products.add(product);
                    for (ProductDto product1 : products) {
                        System.out.println(product1);
                    }
                }
            } else if (operation2 == 5) {
                if (product.getProductCategory().equals(ProductCategory.FRUIT_AND_VEGETABLES)) {
                    products.add(product);
                    for (ProductDto product1 : products) {
                        System.out.println(product1);
                    }
                }
            } else if (operation2 == 6) {
                if (product.getProductCategory().equals(ProductCategory.DAIRY_PRODUCTS)) {
                    products.add(product);
                    for (ProductDto product1 : products) {
                        System.out.println(product1);
                    }
                }
            } else if (operation2 == 7) {
                if (product.getProductCategory().equals(ProductCategory.CLEANİNG_PRODUCTS)) {
                    products.add(product);
                    for (ProductDto product1 : products) {
                        System.out.println(product1);
                    }
                }
            } else {
                aBoolean = false;
            }
        }
    }
}
