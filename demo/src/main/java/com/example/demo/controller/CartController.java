package com.example.demo.controller;

import com.example.demo.model.Cart;
import com.example.demo.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<Cart> getCart(HttpSession session) {
        return ResponseEntity.ok(cartService.getCart(session));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody Map<String, Object> payload, HttpSession session) {
        String productId = (String) payload.get("productId");
        Integer quantity = (Integer) payload.get("quantity");
        if (productId == null || quantity == null) {
            return ResponseEntity.badRequest().body("productId y quantity son requeridos.");
        }
        try {
            cartService.addToCart(productId, quantity, session);
            return ResponseEntity.ok(cartService.getCart(session));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/remove")
    public ResponseEntity<?> removeFromCart(@RequestBody Map<String, Object> payload, HttpSession session) {
        String productId = (String) payload.get("productId");
        if (productId == null) {
            return ResponseEntity.badRequest().body("productId es requerido.");
        }
        cartService.removeFromCart(productId, session);
        return ResponseEntity.ok(cartService.getCart(session));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateQuantity(@RequestBody Map<String, Object> payload, HttpSession session) {
        String productId = (String) payload.get("productId");
        Integer quantity = (Integer) payload.get("quantity");
        if (productId == null || quantity == null) {
            return ResponseEntity.badRequest().body("productId y quantity son requeridos.");
        }
        try {
            cartService.updateQuantity(productId, quantity, session);
            return ResponseEntity.ok(cartService.getCart(session));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/clear")
    public ResponseEntity<?> clearCart(HttpSession session) {
        cartService.clearCart(session);
        return ResponseEntity.ok(cartService.getCart(session));
    }
}