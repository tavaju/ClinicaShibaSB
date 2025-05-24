package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

@Entity
public class Product {

    @Id
    private String id;

    @NotBlank(message = "El nombre del producto es requerido")
    private String name;

    @NotBlank(message = "La descripción del producto es requerida")
    @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
    @Column(length = 1000)
    private String description;

    @NotNull(message = "El precio del producto es requerido")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que cero")
    private BigDecimal price;

    @NotBlank(message = "La categoría del producto es requerida")
    private String category;

    @NotBlank(message = "La URL de la imagen es requerida")
    private String image;

    @NotNull(message = "La calificación del producto es requerida")
    @Min(value = 0, message = "La calificación mínima es 0")
    @Max(value = 5, message = "La calificación máxima es 5")
    private Double rating;

    @NotNull(message = "El estado del inventario es requerido")
    @Enumerated(EnumType.STRING)
    private InventoryStatus inventoryStatus;

    public enum InventoryStatus {
        INSTOCK, LOWSTOCK, OUTOFSTOCK
    }

    // Constructors
    public Product() {
    }

    public Product(String id, String name, String description, BigDecimal price, String category,
            String image, Double rating, InventoryStatus inventoryStatus) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.image = image;
        this.rating = rating;
        this.inventoryStatus = inventoryStatus;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Transient
    public String getFormattedPrice() {
        if (price == null) {
            return "";
        }
        // Create a Colombian locale
        Locale colombianLocale = new Locale("es", "CO");
        // Get the number formatter for the locale
        NumberFormat formatter = NumberFormat.getNumberInstance(colombianLocale);
        // Set maximum fraction digits to 0 (no decimal places)
        formatter.setMaximumFractionDigits(0);
        // Format the price
        return "$ " + formatter.format(price);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public InventoryStatus getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(InventoryStatus inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }
}