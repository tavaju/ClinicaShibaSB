package com.example.demo.service;

import com.example.demo.model.Cart;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private static final String SESSION_CART_KEY = "cart";

    private final ProductRepository productRepository;

    @Autowired
    public CartServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute(SESSION_CART_KEY);
        if (cart == null) {
            cart = new Cart();
            session.setAttribute(SESSION_CART_KEY, cart);
        }
        return cart;
    }

    @Override
    public void addToCart(String productId, int quantity, HttpSession session) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
        }
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty()) {
            throw new IllegalArgumentException("Producto no encontrado.");
        }
        Product product = productOpt.get();
        if (product.getInventoryStatus() == Product.InventoryStatus.OUTOFSTOCK) {
            throw new IllegalArgumentException("El producto está agotado.");
        }
        Cart cart = getCart(session);
        cart.addProduct(product, quantity);
        session.setAttribute(SESSION_CART_KEY, cart); // <-- Importante
    }

    @Override
    public void removeFromCart(String productId, HttpSession session) {
        Cart cart = getCart(session);
        cart.removeProduct(productId);
        session.setAttribute(SESSION_CART_KEY, cart); // <-- Importante
    }

    @Override
    public void updateQuantity(String productId, int quantity, HttpSession session) {
        if (quantity < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa.");
        }
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty()) {
            throw new IllegalArgumentException("Producto no encontrado.");
        }
        Product product = productOpt.get();
        Cart cart = getCart(session);
        cart.updateQuantity(product, quantity);
        session.setAttribute(SESSION_CART_KEY, cart);
    }

    @Override
    public void clearCart(HttpSession session) {
        Cart cart = getCart(session);
        cart.clear();
        session.setAttribute(SESSION_CART_KEY, cart); // <-- Importante
    }
}