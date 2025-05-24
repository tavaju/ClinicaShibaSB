package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product testProduct1;
    private Product testProduct2;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Create test products
        testProduct1 = new Product(
                "1",
                "Test Product 1",
                "Test Description 1",
                new BigDecimal("29.99"),
                "Alimentos",
                "/test-image1.jpg",
                4.5,
                Product.InventoryStatus.INSTOCK);

        testProduct2 = new Product(
                "2",
                "Test Product 2",
                "Test Description 2",
                new BigDecimal("19.99"),
                "Juguetes",
                "/test-image2.jpg",
                3.5,
                Product.InventoryStatus.LOWSTOCK);
    }

    @Test
    public void testGetAllProducts() {
        // Arrange
        when(productRepository.findAll()).thenReturn(Arrays.asList(testProduct1, testProduct2));

        // Act
        List<Product> products = productService.getAllProducts();

        // Assert
        assertEquals(2, products.size());
        assertEquals("Test Product 1", products.get(0).getName());
        assertEquals("Test Product 2", products.get(1).getName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void testGetProductById() {
        // Arrange
        when(productRepository.findById("1")).thenReturn(Optional.of(testProduct1));
        when(productRepository.findById("999")).thenReturn(Optional.empty());

        // Act & Assert
        Product foundProduct = productService.getProductById("1");
        assertNotNull(foundProduct);
        assertEquals("1", foundProduct.getId());
        assertEquals("Test Product 1", foundProduct.getName());

        Product notFoundProduct = productService.getProductById("999");
        assertNull(notFoundProduct);

        verify(productRepository, times(1)).findById("1");
        verify(productRepository, times(1)).findById("999");
    }

    @Test
    public void testGetProductsByCategory() {
        // Arrange
        when(productRepository.findByCategory("Alimentos")).thenReturn(List.of(testProduct1));
        when(productRepository.findByCategory("NonExistingCategory")).thenReturn(List.of());

        // Act & Assert
        List<Product> foodProducts = productService.getProductsByCategory("Alimentos");
        assertEquals(1, foodProducts.size());
        assertEquals("Test Product 1", foodProducts.get(0).getName());

        List<Product> emptyList = productService.getProductsByCategory("NonExistingCategory");
        assertTrue(emptyList.isEmpty());

        verify(productRepository, times(1)).findByCategory("Alimentos");
        verify(productRepository, times(1)).findByCategory("NonExistingCategory");
    }

    @Test
    public void testSaveProduct() {
        // Arrange
        when(productRepository.save(any(Product.class))).thenReturn(testProduct1);

        // Act
        Product savedProduct = productService.saveProduct(testProduct1);

        // Assert
        assertNotNull(savedProduct);
        assertEquals("Test Product 1", savedProduct.getName());
        verify(productRepository, times(1)).save(testProduct1);
    }

    @Test
    public void testDeleteProduct() {
        // Act
        productService.deleteProduct("1");

        // Assert
        verify(productRepository, times(1)).deleteById("1");
    }

    @Test
    public void testGetProductsByInventoryStatus() {
        // Arrange
        when(productRepository.findByInventoryStatus(Product.InventoryStatus.INSTOCK))
                .thenReturn(List.of(testProduct1));
        when(productRepository.findByInventoryStatus(Product.InventoryStatus.LOWSTOCK))
                .thenReturn(List.of(testProduct2));

        // Act
        List<Product> instockProducts = productService.getProductsByInventoryStatus(Product.InventoryStatus.INSTOCK);
        List<Product> lowstockProducts = productService.getProductsByInventoryStatus(Product.InventoryStatus.LOWSTOCK);

        // Assert
        assertEquals(1, instockProducts.size());
        assertEquals("Test Product 1", instockProducts.get(0).getName());
        assertEquals(1, lowstockProducts.size());
        assertEquals("Test Product 2", lowstockProducts.get(0).getName());

        verify(productRepository, times(1)).findByInventoryStatus(Product.InventoryStatus.INSTOCK);
        verify(productRepository, times(1)).findByInventoryStatus(Product.InventoryStatus.LOWSTOCK);
    }
}