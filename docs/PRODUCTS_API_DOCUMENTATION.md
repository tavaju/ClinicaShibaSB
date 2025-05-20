# Product API Documentation

This document provides details for retrieving product data from the Clinica Shiba Product API.

## Base URLs

The API can be accessed through two equivalent base URLs:

- `/api/products` - Standard API path
- `/products` - Alternative direct path

Both endpoints provide the same functionality, allowing for flexibility in how you access the data.

## Authentication

No authentication is required for retrieving product data.

## Retrieving Products

### Get All Products

Retrieve a list of all available products.

**Endpoint:** 
- `GET /api/products`
- `GET /products`

**Response:**
- Status Code: 200 OK
- Content Type: application/json
- Body: Array of product objects

**Example Request:**
```bash
curl -X GET http://localhost:8090/api/products
```

**Example Response:**
```json
[
  {
    "id": "1",
    "name": "Royal Canin Adult Medium",
    "description": "Alimento premium para perros adultos de razas medianas",
    "price": 54.99,
    "category": "Alimentos",
    "image": "/assets/images/products/food-royal-canin.jpg",
    "rating": 4.5,
    "inventoryStatus": "INSTOCK"
  },
  {
    "id": "2",
    "name": "Hills Science Diet",
    "description": "Alimento balanceado para perros con problemas digestivos",
    "price": 49.99,
    "category": "Alimentos",
    "image": "/assets/images/products/food-hills.jpg",
    "rating": 4.8,
    "inventoryStatus": "INSTOCK"
  }
  // Additional products...
]
```

### Get Product by ID

Retrieve details for a specific product by its ID.

**Endpoint:** 
- `GET /api/products/{id}`
- `GET /products/{id}`

**Path Parameters:**
- `id` (string, required): The unique identifier of the product

**Response:**
- Status Code: 200 OK (if found)
- Status Code: 404 Not Found (if no product exists with the given ID)
- Content Type: application/json
- Body: Product object

**Example Request:**
```bash
curl -X GET http://localhost:8090/api/products/1
```

**Example Response:**
```json
{
  "id": "1",
  "name": "Royal Canin Adult Medium",
  "description": "Alimento premium para perros adultos de razas medianas",
  "price": 54.99,
  "category": "Alimentos",
  "image": "/assets/images/products/food-royal-canin.jpg",
  "rating": 4.5,
  "inventoryStatus": "INSTOCK"
}
```

### Get Products by Category

Retrieve all products belonging to a specific category.

**Endpoint:** 
- `GET /api/products/category/{category}`
- `GET /products/category/{category}`

**Path Parameters:**
- `category` (string, required): The category name to filter by

**Response:**
- Status Code: 200 OK
- Content Type: application/json
- Body: Array of product objects

**Example Request:**
```bash
curl -X GET http://localhost:8090/api/products/category/Alimentos
```

**Example Response:**
```json
[
  {
    "id": "1",
    "name": "Royal Canin Adult Medium",
    "description": "Alimento premium para perros adultos de razas medianas",
    "price": 54.99,
    "category": "Alimentos",
    "image": "/assets/images/products/food-royal-canin.jpg",
    "rating": 4.5,
    "inventoryStatus": "INSTOCK"
  },
  {
    "id": "2",
    "name": "Hills Science Diet",
    "description": "Alimento balanceado para perros con problemas digestivos",
    "price": 49.99,
    "category": "Alimentos",
    "image": "/assets/images/products/food-hills.jpg",
    "rating": 4.8,
    "inventoryStatus": "INSTOCK"
  }
  // Additional products in the category...
]
```

### Get Products by Inventory Status

Retrieve all products with a specific inventory status.

**Endpoint:** 
- `GET /api/products/status/{status}`
- `GET /products/status/{status}`

**Path Parameters:**
- `status` (string, required): The inventory status to filter by. Possible values: `INSTOCK`, `LOWSTOCK`, `OUTOFSTOCK`

**Response:**
- Status Code: 200 OK
- Status Code: 400 Bad Request (if status is not a valid enum value)
- Content Type: application/json
- Body: Array of product objects

**Example Request:**
```bash
curl -X GET http://localhost:8090/api/products/status/INSTOCK
```

**Example Response:**
```json
[
  {
    "id": "1",
    "name": "Royal Canin Adult Medium",
    "description": "Alimento premium para perros adultos de razas medianas",
    "price": 54.99,
    "category": "Alimentos",
    "image": "/assets/images/products/food-royal-canin.jpg",
    "rating": 4.5,
    "inventoryStatus": "INSTOCK"
  },
  {
    "id": "4",
    "name": "Collar antiparasitario",
    "description": "Collar para prevención de pulgas y garrapatas",
    "price": 19.99,
    "category": "Salud",
    "image": "/assets/images/products/collar-antiparasites.jpg",
    "rating": 4.2,
    "inventoryStatus": "INSTOCK"
  }
  // Additional products with INSTOCK status...
]
```

## Product Model

Each product contains the following properties:

| Property | Type | Description |
|----------|------|-------------|
| id | string | Unique identifier for the product |
| name | string | Name of the product |
| description | string | Detailed description of the product |
| price | number | Price of the product in local currency |
| category | string | Category the product belongs to (e.g., Alimentos, Salud, Accesorios) |
| image | string | URL path to the product image |
| rating | number | Product rating on a scale of 0 to 5 |
| inventoryStatus | string | Current inventory status: INSTOCK, LOWSTOCK, or OUTOFSTOCK |

## Available Product Categories

The following product categories are currently available:

- Alimentos (Food)
- Accesorios (Accessories)
- Salud (Health)
- Higiene (Hygiene)
- Juguetes (Toys)
- Servicios (Services)
- Equipamiento (Equipment)

## Error Handling

The API will return appropriate HTTP status codes and error messages:

- 200 OK: Request successful
- 400 Bad Request: Invalid parameters
- 404 Not Found: Resource not found
- 500 Internal Server Error: Unexpected server error

Error responses include a JSON object with error details.

**Example Error Response:**
```json
{
  "status": "error",
  "message": "The requested resource was not found",
  "path": "/api/products/999"
}
``` 