package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Cacheable(value = "productsCache", key = "'allProducts'")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    @Cacheable(value = "productsCache", key = "'category_' + #category")
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    @Override
    @Cacheable(value = "productsCache", key = "'status_' + #status")
    public List<Product> getProductsByInventoryStatus(Product.InventoryStatus status) {
        return productRepository.findByInventoryStatus(status);
    }

    @Override
    @Cacheable(value = "productsCache", key = "#id")
    public Product getProductById(String id) {
        Optional<Product> productOpt = productRepository.findById(id);
        return productOpt.orElse(null);
    }

    @Override
    @CachePut(value = "productsCache", key = "#product.id")
    @CacheEvict(value = "productsCache", key = "'allProducts'")
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    @CacheEvict(value = "productsCache", allEntries = true)
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }
} 