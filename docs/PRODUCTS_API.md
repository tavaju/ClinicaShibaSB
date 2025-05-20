# Clinica Shiba - Products API Documentation

## Overview

This document provides detailed information on the Products API for the Clinica Shiba application. The API allows frontend applications to retrieve, create, update, and delete product information for the shop component.

## API Endpoints

### Get All Products

Retrieves all products available in the shop.

- **URL**: `/api/products`
- **Method**: `GET`
- **Auth Required**: No
- **Permissions Required**: None

#### Success Response

- **Code**: 200 OK
- **Content**: Array of product objects
  
```json
[
  {
    "id": "1",
    "name": "Comida Premium para Perros",
    "description": "Alimento balanceado para perros de todas las edades con ingredientes naturales.",
    "price": 39.99,
    "category": "Alimentos",
    "image": "/assets/images/products/dog-food.jpg",
    "rating": 4.5,
    "inventoryStatus": "INSTOCK"
  },
  {
    "id": "2",
    "name": "Correa Retráctil",
    "description": "Correa retráctil de 5 metros con mango ergonómico y sistema de bloqueo.",
    "price": 24.99,
    "category": "Accesorios",
    "image": "/assets/images/products/retractable-leash.jpg",
    "rating": 4.0,
    "inventoryStatus": "LOWSTOCK"
  }
]
```

### Get Product by ID

Retrieves a specific product by its ID.

- **URL**: `/api/products/:id`
- **Method**: `GET`
- **URL Params**: `id=[string]` (required)
- **Auth Required**: No
- **Permissions Required**: None

#### Success Response

- **Code**: 200 OK
- **Content**: Product object
  
```json
{
  "id": "1",
  "name": "Comida Premium para Perros",
  "description": "Alimento balanceado para perros de todas las edades con ingredientes naturales.",
  "price": 39.99,
  "category": "Alimentos",
  "image": "/assets/images/products/dog-food.jpg",
  "rating": 4.5,
  "inventoryStatus": "INSTOCK"
}
```

#### Error Response

- **Code**: 404 Not Found
- **Content**: Empty

### Get Products by Category

Retrieves products filtered by category.

- **URL**: `/api/products/category/:category`
- **Method**: `GET`
- **URL Params**: `category=[string]` (required)
- **Auth Required**: No
- **Permissions Required**: None

#### Success Response

- **Code**: 200 OK
- **Content**: Array of product objects

### Get Products by Inventory Status

Retrieves products filtered by inventory status.

- **URL**: `/api/products/status/:status`
- **Method**: `GET`
- **URL Params**: `status=[string]` (required, one of: INSTOCK, LOWSTOCK, OUTOFSTOCK)
- **Auth Required**: No
- **Permissions Required**: None

#### Success Response

- **Code**: 200 OK
- **Content**: Array of product objects

#### Error Response

- **Code**: 400 Bad Request
- **Content**: Empty

### Create Product

Creates a new product.

- **URL**: `/api/products`
- **Method**: `POST`
- **Auth Required**: Yes (in future implementation)
- **Permissions Required**: Admin (in future implementation)
- **Data Constraints**:

```json
{
  "name": "[string, required]",
  "description": "[string, required]",
  "price": "[number, required, > 0]",
  "category": "[string, required]",
  "image": "[string, required, valid URL]",
  "rating": "[number, required, 0-5]",
  "inventoryStatus": "[string, required, one of: INSTOCK, LOWSTOCK, OUTOFSTOCK]"
}
```

#### Success Response

- **Code**: 201 Created
- **Content**: Created product object with ID

#### Error Response

- **Code**: 400 Bad Request
- **Content**: Validation errors

### Update Product

Updates an existing product.

- **URL**: `/api/products/:id`
- **Method**: `PUT`
- **URL Params**: `id=[string]` (required)
- **Auth Required**: Yes (in future implementation)
- **Permissions Required**: Admin (in future implementation)
- **Data Constraints**: Same as Create Product, with ID included

#### Success Response

- **Code**: 200 OK
- **Content**: Updated product object

#### Error Response

- **Code**: 400 Bad Request
- **Content**: Validation errors
  
- **Code**: 404 Not Found
- **Content**: Empty

### Delete Product

Deletes a product.

- **URL**: `/api/products/:id`
- **Method**: `DELETE`
- **URL Params**: `id=[string]` (required)
- **Auth Required**: Yes (in future implementation)
- **Permissions Required**: Admin (in future implementation)

#### Success Response

- **Code**: 204 No Content
- **Content**: Empty

#### Error Response

- **Code**: 404 Not Found
- **Content**: Empty

## Data Model

### Product

| Field          | Type   | Description                                             | Constraints                               |
|----------------|--------|---------------------------------------------------------|-------------------------------------------|
| id             | string | Unique identifier                                       | Required, auto-generated                  |
| name           | string | Product name                                            | Required                                  |
| description    | string | Product description                                     | Required, max 1000 chars                  |
| price          | number | Price in local currency                                 | Required, > 0                             |
| category       | string | Product category                                        | Required                                  |
| image          | string | URL to product image                                    | Required                                  |
| rating         | number | Product rating                                          | Required, between 0-5                     |
| inventoryStatus| string | Stock status                                            | Required, one of the predefined statuses  |

## Error Handling

All endpoint errors follow a standard format:

- **400 Bad Request**: Validation errors with field-specific messages
- **404 Not Found**: Resource not found
- **500 Internal Server Error**: Unexpected server error

## Rate Limiting

The API implements rate limiting to prevent abuse:

- **Limit**: 100 requests per minute per IP address
- **Response when exceeded**: HTTP 429 Too Many Requests

## Caching

The Products API implements caching for improved performance:

- **Cache Duration**: Product data is cached in memory
- **Cache Headers**: Static resources (images) include cache headers for 30 days

## Sample Usage

### JavaScript Fetch Example

```javascript
// Get all products
fetch('/api/products')
  .then(response => response.json())
  .then(data => console.log(data))
  .catch(error => console.error('Error fetching products:', error));

// Get products by category
fetch('/api/products/category/Alimentos')
  .then(response => response.json())
  .then(data => console.log(data))
  .catch(error => console.error('Error fetching products by category:', error));
```

## Future Enhancements

- Authentication and authorization for administrative endpoints
- Enhanced filtering options (price range, search by name)
- Shopping cart integration
- Product reviews 