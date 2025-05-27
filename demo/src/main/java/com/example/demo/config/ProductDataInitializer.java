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
                        List<Product> products = productRepository.findAll();
                        if (products.isEmpty()) {
                                // Product 1
                                productRepository.save(new Product(
                                                "1",
                                                "Royal Canin Adult Medium",
                                                "Alimento premium para perros adultos de razas medianas",
                                                new BigDecimal("184000"),
                                                "Alimentos",
                                                "https://www.tierragro.com/cdn/shop/files/3182550793612.jpg?v=1720805361&width=1200",
                                                4.5,
                                                Product.InventoryStatus.INSTOCK));
                                // Product 2
                                productRepository.save(new Product(
                                                "2",
                                                "Hills Science Diet",
                                                "Alimento balanceado para perros con problemas digestivos",
                                                new BigDecimal("175000"),
                                                "Alimentos",
                                                "https://pxmshare.colgatepalmolive.com/JPEG_1500/AVMPT6Y_OA7ekwj6jsVf9.jpg",
                                                4.8,
                                                Product.InventoryStatus.INSTOCK));
                                // Product 3
                                productRepository.save(new Product(
                                                "3",
                                                "Cepillo Furminator",
                                                "Cepillo deslanador profesional para perros de pelo medio",
                                                new BigDecimal("89000"),
                                                "Accesorios",
                                                "https://d23qt3x1ychzdy.cloudfront.net/dev_images_products/ccb6db55e1dd603a159a3dfd120fb886_1675192391.jpg",
                                                4.7,
                                                Product.InventoryStatus.LOWSTOCK));
                                // Product 4
                                productRepository.save(new Product(
                                                "4",
                                                "Collar antiparasitario",
                                                "Collar para prevención de pulgas y garrapatas",
                                                new BigDecimal("67000"),
                                                "Salud",
                                                "https://m.media-amazon.com/images/I/614ih2uBfkL._AC_SL1001_.jpg",
                                                4.2,
                                                Product.InventoryStatus.INSTOCK));
                                // Product 5
                                productRepository.save(new Product(
                                                "5",
                                                "Champú hipoalergénico",
                                                "Champú suave para perros con piel sensible",
                                                new BigDecimal("45000"),
                                                "Higiene",
                                                "https://m.media-amazon.com/images/I/711p19MdsML._AC_SL1500_.jpg",
                                                4.0,
                                                Product.InventoryStatus.INSTOCK));
                                // Product 6
                                productRepository.save(new Product(
                                                "6",
                                                "Vacuna Múltiple Canina",
                                                "Vacuna para prevención de enfermedades comunes",
                                                new BigDecimal("120000"),
                                                "Salud",
                                                "https://co.virbac.com/files/live/sites/virbac-co/files/predefined-files/products/600x600/canigenmha2ltriple-large.jpg",
                                                5.0,
                                                Product.InventoryStatus.OUTOFSTOCK));
                                // Product 7
                                productRepository.save(new Product(
                                                "7",
                                                "Snacks dentales",
                                                "Golosinas para mejorar la salud dental",
                                                new BigDecimal("35000"),
                                                "Alimentos",
                                                "https://m.media-amazon.com/images/I/71DYKgVli0L._AC_SL1500_.jpg",
                                                4.1,
                                                Product.InventoryStatus.INSTOCK));
                                // Product 8
                                productRepository.save(new Product(
                                                "8",
                                                "Cama ortopédica",
                                                "Cama con espuma viscoelástica para perros mayores",
                                                new BigDecimal("250000"),
                                                "Accesorios",
                                                "https://m.media-amazon.com/images/I/712ReNHJccL._AC_SL1500_.jpg",
                                                4.9,
                                                Product.InventoryStatus.LOWSTOCK));
                                // Product 9
                                productRepository.save(new Product(
                                                "9",
                                                "Correa retráctil",
                                                "Correa con sistema de extensión y bloqueo automático",
                                                new BigDecimal("75000"),
                                                "Accesorios",
                                                "https://m.media-amazon.com/images/I/61R-uNDrUUL._AC_SL1500_.jpg",
                                                4.3,
                                                Product.InventoryStatus.INSTOCK));
                                // Product 10
                                productRepository.save(new Product(
                                                "10",
                                                "Juguete Kong",
                                                "Juguete resistente para masticadores fuertes",
                                                new BigDecimal("65000"),
                                                "Juguetes",
                                                "https://cdn.amplifi.pattern.com/b1af88d2-ac44-48bf-8be1-51e283088a40_medium",
                                                4.7,
                                                Product.InventoryStatus.INSTOCK));
                                // Product 11
                                productRepository.save(new Product(
                                                "11",
                                                "Transportadora de viaje",
                                                "Transportadora para mascotas aprobada para viajes aéreos",
                                                new BigDecimal("320000"),
                                                "Accesorios",
                                                "https://m.media-amazon.com/images/I/71KLUcxRWML._AC_SL1500_.jpg",
                                                4.6,
                                                Product.InventoryStatus.INSTOCK));
                                // Product 12
                                productRepository.save(new Product(
                                                "12",
                                                "Microchip de identificación",
                                                "Microchip subcutáneo para identificación permanente",
                                                new BigDecimal("150000"),
                                                "Servicios",
                                                "https://m.media-amazon.com/images/I/61UhaQ90AzL._AC_SL1080_.jpg",
                                                5.0,
                                                Product.InventoryStatus.INSTOCK));
                                // Product 13
                                productRepository.save(new Product(
                                                "13",
                                                "Dispensador automático de comida",
                                                "Dispensador programable para alimentación controlada",
                                                new BigDecimal("385000"),
                                                "Equipamiento",
                                                "https://m.media-amazon.com/images/I/613o6jzABvL._AC_SL1500_.jpg",
                                                5.0,
                                                Product.InventoryStatus.LOWSTOCK));
                                // Product 14
                                productRepository.save(new Product(
                                                "14",
                                                "Vitaminas y suplementos",
                                                "Complemento vitamínico para perros mayores",
                                                new BigDecimal("95000"),
                                                "Salud",
                                                "https://m.media-amazon.com/images/I/71jZmYRbhlL._AC_SL1500_.jpg",
                                                4.4,
                                                Product.InventoryStatus.INSTOCK));
                                // Product 15
                                productRepository.save(new Product(
                                                "15",
                                                "Peine antipulgas",
                                                "Peine de dientes finos para detección y eliminación de pulgas",
                                                new BigDecimal("28000"),
                                                "Higiene",
                                                "https://m.media-amazon.com/images/I/31hfjpE4GRL._SX300_SY300_QL70_FMwebp_.jpg",
                                                4.2,
                                                Product.InventoryStatus.INSTOCK));
                                // Product 16
                                productRepository.save(new Product(
                                                "16",
                                                "Protector de asiento para coche",
                                                "Cubierta impermeable para proteger el asiento del coche",
                                                new BigDecimal("110000"),
                                                "Accesorios",
                                                "https://m.media-amazon.com/images/I/81UFafTRYQL._AC_SL1500_.jpg",
                                                4.7,
                                                Product.InventoryStatus.INSTOCK));
                                // Product 17
                                productRepository.save(new Product(
                                                "17",
                                                "Cortauñas profesional",
                                                "Cortauñas de acero inoxidable para un corte preciso",
                                                new BigDecimal("60000"),
                                                "Higiene",
                                                "https://m.media-amazon.com/images/I/71hz7en4TYL._AC_SL1500_.jpg",
                                                4.3,
                                                Product.InventoryStatus.INSTOCK));
                                // Product 18
                                productRepository.save(new Product(
                                                "18",
                                                "Traje post-operatorio",
                                                "Traje protector para después de cirugías",
                                                new BigDecimal("105000"),
                                                "Salud",
                                                "https://m.media-amazon.com/images/I/7186S6AcxSL._AC_SL1500_.jpg",
                                                4.8,
                                                Product.InventoryStatus.LOWSTOCK));
                                // Product 19
                                productRepository.save(new Product(
                                                "19",
                                                "Kit dental canino",
                                                "Kit completo para la higiene dental de tu mascota",
                                                new BigDecimal("85000"),
                                                "Higiene",
                                                "https://m.media-amazon.com/images/I/61cHroxxigL._AC_SL1500_.jpg",
                                                4.5,
                                                Product.InventoryStatus.INSTOCK));
                                // Product 20
                                productRepository.save(new Product(
                                                "20",
                                                "Arnés antitirón",
                                                "Arnés especial para evitar tirones durante el paseo",
                                                new BigDecimal("95000"),
                                                "Accesorios",
                                                "https://m.media-amazon.com/images/I/71eROBTwkVL._AC_SL1500_.jpg",
                                                4.9,
                                                Product.InventoryStatus.INSTOCK));
                                System.out.println("Productos de ejemplo cargados con éxito.");
                        }
                };
        }
}