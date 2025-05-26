package com.example.demo.service;

import com.example.demo.model.Cart;
import com.example.demo.model.Product;

import jakarta.servlet.http.HttpSession;

public interface CartService {
    Cart getCart(HttpSession session);

    void addToCart(String productId, int quantity, HttpSession session);

    void removeFromCart(String productId, HttpSession session);

    void updateQuantity(String productId, int quantity, HttpSession session);

    void clearCart(HttpSession session);
}