package com.example.demo.service;

import com.example.demo.model.Product;
import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    List<Product> getProductsByCategory(String category);

    List<Product> getProductsByInventoryStatus(Product.InventoryStatus status);

    Product getProductById(String id);

    Product saveProduct(Product product);

    void deleteProduct(String id);
}