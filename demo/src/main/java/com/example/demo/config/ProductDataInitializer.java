package com.example.demo.config;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Configuration
public class ProductDataInitializer {

    @Bean
    public CommandLineRunner initProductData(@Autowired ProductRepository productRepository) {
        return args -> {
            // Only add sample data if the repository is empty
            if (productRepository.count() == 0) {
                List<Product> sampleProducts = Arrays.asList(
                    new Product(
                        UUID.randomUUID().toString(),
                        "Comida Premium para Perros",
                        "Alimento balanceado para perros de todas las edades con ingredientes naturales.",
                        new BigDecimal("39.99"),
                        "Alimentos",
                        "/assets/images/products/dog-food.jpg",
                        4.5,
                        Product.InventoryStatus.INSTOCK
                    ),
                    new Product(
                        UUID.randomUUID().toString(),
                        "Correa Retráctil",
                        "Correa retráctil de 5 metros con mango ergonómico y sistema de bloqueo.",
                        new BigDecimal("24.99"),
                        "Accesorios",
                        "/assets/images/products/retractable-leash.jpg",
                        4.0,
                        Product.InventoryStatus.LOWSTOCK
                    ),
                    new Product(
                        UUID.randomUUID().toString(),
                        "Juguete Dental para Perros",
                        "Juguete masticable que ayuda a mantener la higiene dental de tu mascota.",
                        new BigDecimal("12.50"),
                        "Juguetes",
                        "/assets/images/products/dental-toy.jpg",
                        3.5,
                        Product.InventoryStatus.OUTOFSTOCK
                    ),
                    new Product(
                        UUID.randomUUID().toString(),
                        "Shampoo Antipulgas",
                        "Shampoo especializado para eliminar y prevenir pulgas en perros.",
                        new BigDecimal("18.75"),
                        "Higiene",
                        "/assets/images/products/flea-shampoo.jpg",
                        4.2,
                        Product.InventoryStatus.INSTOCK
                    ),
                    new Product(
                        UUID.randomUUID().toString(),
                        "Suplemento Vitamínico",
                        "Suplemento vitamínico completo para fortalecer el sistema inmune de tu mascota.",
                        new BigDecimal("29.95"),
                        "Medicamentos",
                        "/assets/images/products/vitamin-supplement.jpg",
                        4.7,
                        Product.InventoryStatus.INSTOCK
                    ),
                    new Product(
                        UUID.randomUUID().toString(),
                        "Cama Ortopédica para Perros",
                        "Cama ortopédica diseñada para perros senior o con problemas articulares.",
                        new BigDecimal("59.99"),
                        "Camas",
                        "/assets/images/products/orthopedic-bed.jpg",
                        4.8,
                        Product.InventoryStatus.LOWSTOCK
                    ),
                    new Product(
                        UUID.randomUUID().toString(),
                        "Transportador de Viaje",
                        "Transportador seguro y cómodo para viajar con tu mascota.",
                        new BigDecimal("42.50"),
                        "Transportadores",
                        "/assets/images/products/pet-carrier.jpg",
                        4.0,
                        Product.InventoryStatus.INSTOCK
                    ),
                    new Product(
                        UUID.randomUUID().toString(),
                        "Collar Antipulgas",
                        "Collar antipulgas con protección de hasta 8 meses.",
                        new BigDecimal("32.99"),
                        "Accesorios",
                        "/assets/images/products/flea-collar.jpg",
                        4.3,
                        Product.InventoryStatus.INSTOCK
                    ),
                    new Product(
                        UUID.randomUUID().toString(),
                        "Pelotas de Juguete (Pack 3)",
                        "Set de 3 pelotas resistentes para jugar con tu perro.",
                        new BigDecimal("14.99"),
                        "Juguetes",
                        "/assets/images/products/ball-set.jpg",
                        4.1,
                        Product.InventoryStatus.INSTOCK
                    ),
                    new Product(
                        UUID.randomUUID().toString(),
                        "Comida Húmeda Premium",
                        "Alimento húmedo gourmet para perros exigentes, rico en proteínas.",
                        new BigDecimal("3.99"),
                        "Alimentos",
                        "/assets/images/products/wet-food.jpg",
                        4.4,
                        Product.InventoryStatus.LOWSTOCK
                    )
                );
                
                productRepository.saveAll(sampleProducts);
                
                System.out.println("Sample product data initialized successfully!");
            }
        };
    }
} 