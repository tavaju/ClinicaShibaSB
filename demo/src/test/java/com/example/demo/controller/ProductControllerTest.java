package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllProducts() throws Exception {
        // Setup mock products
        List<Product> mockProducts = Arrays.asList(
                createSampleProduct("1", "Test Product 1", "Alimentos"),
                createSampleProduct("2", "Test Product 2", "Juguetes"));

        when(productService.getAllProducts()).thenReturn(mockProducts);

        // Perform GET request and validate
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].name", is("Test Product 1")))
                .andExpect(jsonPath("$[1].id", is("2")))
                .andExpect(jsonPath("$[1].name", is("Test Product 2")));
    }

    @Test
    public void testGetProductById() throws Exception {
        // Setup mock product
        Product mockProduct = createSampleProduct("1", "Test Product", "Alimentos");
        when(productService.getProductById("1")).thenReturn(mockProduct);

        // Test existing product
        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.name", is("Test Product")));

        // Test non-existent product
        when(productService.getProductById("999")).thenReturn(null);
        mockMvc.perform(get("/api/products/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateProduct() throws Exception {
        // Setup mock product
        Product inputProduct = createSampleProduct(null, "New Product", "Juguetes");
        Product savedProduct = createSampleProduct("1", "New Product", "Juguetes");

        when(productService.saveProduct(any(Product.class))).thenReturn(savedProduct);

        // Perform POST request
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputProduct)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.name", is("New Product")));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        // Setup mock product
        Product existingProduct = createSampleProduct("1", "Old Name", "Alimentos");
        Product updatedProduct = createSampleProduct("1", "Updated Name", "Alimentos");

        when(productService.getProductById("1")).thenReturn(existingProduct);
        when(productService.saveProduct(any(Product.class))).thenReturn(updatedProduct);

        // Perform PUT request
        mockMvc.perform(put("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.name", is("Updated Name")));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        // Setup mock product
        Product existingProduct = createSampleProduct("1", "Test Product", "Alimentos");
        when(productService.getProductById("1")).thenReturn(existingProduct);

        // Perform DELETE request
        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());

        // Test deleting non-existent product
        when(productService.getProductById("999")).thenReturn(null);
        mockMvc.perform(delete("/api/products/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetProductsByCategory() throws Exception {
        // Setup mock products
        List<Product> categoryProducts = Arrays.asList(
                createSampleProduct("1", "Food Product 1", "Alimentos"),
                createSampleProduct("2", "Food Product 2", "Alimentos"));

        when(productService.getProductsByCategory("Alimentos")).thenReturn(categoryProducts);

        // Perform GET request and validate
        mockMvc.perform(get("/api/products/category/Alimentos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].category", is("Alimentos")))
                .andExpect(jsonPath("$[1].category", is("Alimentos")));
    }

    /**
     * Helper method to create a sample product for testing
     */
    private Product createSampleProduct(String id, String name, String category) {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setDescription("This is a test description");
        product.setPrice(new BigDecimal("19.99"));
        product.setCategory(category);
        product.setImage("/assets/images/products/test.jpg");
        product.setRating(4.5);
        product.setInventoryStatus(Product.InventoryStatus.INSTOCK);
        return product;
    }
}