# Store Management API

This project implements a simple store management system with products, shopping cart, and orders. It features **role-based security** with **basic authentication**.

---

## Table of Contents

## Table of Contents

- [Project Setup](#project-setup)
  - [1. Prerequisites](#1-prerequisites)
  - [2. Clone the repository](#2-clone-the-repository)
  - [3. Users and Authentication](#3-users-and-authentication)
  - [4. API Endpoints](#4-api-endpoints)
    - [Products Endpoints](#product-endpoints-products)
    - [Shopping Cart](#shopping-cart-endpoints-shopping-cart)
    - [Orders](#order-endpoints-orders)
- [5. Testing with Postman](#5-testing-with-postman)
- [6. Other things I would have done](#6-other-things-i-would-have-done)
- [6. Postman collection](#7-postman-collection)

---

## Project Setup

### 1. Prerequisites
    - Java 17+
    - Maven
    - IDE (e.g., IntelliJ IDEA, Eclipse) or terminal

### 2. Clone the repository

   ```bash
   git clone https://your-repo-url.git
   cd your-project-directory
   ```

### 3. Users and Authentication

This application uses **basic HTTP authentication** with two in-memory users configured in the `SecurityConfiguration` class:

| Username | Password  | Role  |
| -------- | --------- | ----- |
| admin    | admin123  | ADMIN |
| user     | user123   | USER  |

- The **ADMIN** role has access to all endpoints, including creating, updating, and deleting products.
- The **USER** role has access to read operations and order placement.

### 4. API Endpoints

#### Product Endpoints (`/products`)

| HTTP Method | Endpoint               | Description                   | Roles Allowed          |
| ----------- | ---------------------- | -----------------------------|-----------------------|
| GET         | `/products`            | Get all products              | ADMIN, USER            |
| GET         | `/products/search/name?name={name}` | Search products by name | ADMIN, USER            |
| GET         | `/products/search/category?category={category}` | Search products by category | ADMIN, USER            |
| POST        | `/products`            | Create a new product          | ADMIN                  |
| PUT         | `/products/{id}`       | Update a product              | ADMIN                  |
| DELETE      | `/products/{id}`       | Delete a product              | ADMIN                  |

#### Shopping Cart Endpoints (`/shopping-cart`)

| HTTP Method | Endpoint                         | Description                       | Roles Allowed          |
| ----------- | --------------------------------| ---------------------------------|-----------------------|
| GET         | `/shopping-cart/{customerId}`   | Get shopping cart by customer ID | ADMIN, USER            |
| POST        | `/shopping-cart/{customerId}/add/{productId}?quantity={qty}` | Add product to cart | ADMIN, USER            |
| DELETE      | `/shopping-cart/{customerId}/remove/{productId}` | Remove product from cart         | ADMIN, USER            |
| DELETE      | `/shopping-cart/{customerId}/clear` | Clear the shopping cart          | ADMIN, USER            |

#### Order Endpoints (`/orders`)

| HTTP Method | Endpoint                      | Description                  | Roles Allowed          |
| ----------- | -----------------------------| ----------------------------|-----------------------|
| POST        | `/orders/{customerId}/place` | Place an order for a customer | ADMIN, USER           |
| GET         | `/orders/{orderId}`           | Get order details by ID       | ADMIN, USER           |

### 5. Testing with Postman

1. **Set up Basic Auth**

   For each request, under the **Authorization** tab in Postman:
    - Choose **Basic Auth**
    - Enter **Username** and **Password** based on the users:

      | Username | Password  |
      | -------- | --------- |
      | admin    | admin123  |
      | user     | user123   |

2. **Test Endpoints**

    - Use `GET /products` to retrieve all products.
    - Use `POST /products` (with admin credentials) to create a new product. Provide JSON body like:
      ```json
      {
        "name": "Sample Product",
        "category": "Toys",
        "price": 19.99,
        "description": "A fun toy for kids"
      }
      ```
    - Add products to cart via `POST /shopping-cart/{customerId}/add/{productId}?quantity=2`.
    - Place an order via `POST /orders/{customerId}/place`.

3. **Expected Responses**

    - Requests with **insufficient roles** (e.g., USER trying to create a product) will receive a `403 Forbidden`.
    - Requests without authentication will receive a `401 Unauthorized`.

Make sure to test each endpoint with proper credentials to verify role-based access control.

### 6. Other things I would have done

1. add swagger
2. generate controllers with open api
3. add unit tests for the remaining classes
4. add integration tests
5. add more explicit logs for the steps that need to be captured,
by something like Kibana watchers, for examples: order initiated, order successful, order failed
6. for the scope of this homework and for time considerations, security was done like this, 
even if this is definitely not the correct way of doing the user management part. As an example: I would use
Cognito from AWS, to store the passwords, emails and username or any other sensitive information, and will
have a client which does the CRUD operations on AWS Cognito endpoints.
7. for the scope of project I used hibernate, I haven't worked with it in 5 years, but I am guessing that this was the 
framework which you wanted to be tested in this excersise. I am assuming that some connection tables are not necessary,
and that hibernate has a way of doing them automatically, due to considerations of time I will submit this option.
8. have explicit error codes for OAT
9. I would have made a different branch, for each the initial commit and also a branch for the updates of the readme.md,
and I would have squashed into a single commit the readme.md updates.

### 7. Postman collection

   ```bash
   {
  "info": {
    "name": "Store Management API - Full",
    "_postman_id": "store-management-collection-full",
    "description": "Full collection with all secured Store Management endpoints",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Products",
      "item": [
        {
          "name": "Get All Products (ADMIN or USER)",
          "request": {
            "method": "GET",
            "url": {
              "raw": "{{baseUrl}}/products",
              "host": ["{{baseUrl}}"],
              "path": ["products"]
            },
            "auth": { "type": "basic", "basic": [{ "key": "username", "value": "user" }, { "key": "password", "value": "user123" }] }
          }
        },
        {
          "name": "Search Products by Name (ADMIN or USER)",
          "request": {
            "method": "GET",
            "url": {
              "raw": "{{baseUrl}}/products/search/name?name=IPHONE",
              "host": ["{{baseUrl}}"],
              "path": ["products", "search", "name"],
              "query": [{ "key": "name", "value": "IPHONE" }]
            },
            "auth": { "type": "basic", "basic": [{ "key": "username", "value": "user" }, { "key": "password", "value": "user123" }] }
          }
        },
        {
          "name": "Search Products by Category (ADMIN or USER)",
          "request": {
            "method": "GET",
            "url": {
              "raw": "{{baseUrl}}/products/search/category?category=ELECTRONICS",
              "host": ["{{baseUrl}}"],
              "path": ["products", "search", "category"],
              "query": [{ "key": "category", "value": "ELECTRONICS" }]
            },
            "auth": { "type": "basic", "basic": [{ "key": "username", "value": "user" }, { "key": "password", "value": "user123" }] }
          }
        },
        {
          "name": "Create Product (ADMIN only)",
          "request": {
            "method": "POST",
            "header": [{ "key": "Content-Type", "value": "application/json" }],
            "body": {
              "mode": "raw",
              "raw": "{\"productName\":\"NEW PRODUCT\",\"category\":\"ELECTRONICS\",\"subcategory\":\"GADGETS\",\"productType\":\"PHONE\",\"price\":499.99,\"stockQuantity\":10}"
            },
            "url": {
              "raw": "{{baseUrl}}/products",
              "host": ["{{baseUrl}}"],
              "path": ["products"]
            },
            "auth": { "type": "basic", "basic": [{ "key": "username", "value": "admin" }, { "key": "password", "value": "admin123" }] }
          }
        },
        {
          "name": "Update Product (ADMIN only)",
          "request": {
            "method": "PUT",
            "header": [{ "key": "Content-Type", "value": "application/json" }],
            "body": {
              "mode": "raw",
              "raw": "{\"productName\":\"UPDATED PRODUCT\",\"category\":\"ELECTRONICS\",\"subcategory\":\"GADGETS\",\"productType\":\"PHONE\",\"price\":599.99,\"stockQuantity\":20}"
            },
            "url": {
              "raw": "{{baseUrl}}/products/1",
              "host": ["{{baseUrl}}"],
              "path": ["products", "1"]
            },
            "auth": { "type": "basic", "basic": [{ "key": "username", "value": "admin" }, { "key": "password", "value": "admin123" }] }
          }
        },
        {
          "name": "Delete Product (ADMIN only)",
          "request": {
            "method": "DELETE",
            "url": {
              "raw": "{{baseUrl}}/products/1",
              "host": ["{{baseUrl}}"],
              "path": ["products", "1"]
            },
            "auth": { "type": "basic", "basic": [{ "key": "username", "value": "admin" }, { "key": "password", "value": "admin123" }] }
          }
        }
      ]
    },
    {
      "name": "Shopping Cart",
      "item": [
        {
          "name": "Get Cart (ADMIN or USER)",
          "request": {
            "method": "GET",
            "url": {
              "raw": "{{baseUrl}}/shopping-cart/1",
              "host": ["{{baseUrl}}"],
              "path": ["shopping-cart", "1"]
            },
            "auth": { "type": "basic", "basic": [{ "key": "username", "value": "user" }, { "key": "password", "value": "user123" }] }
          }
        },
        {
          "name": "Add To Cart (ADMIN or USER)",
          "request": {
            "method": "POST",
            "url": {
              "raw": "{{baseUrl}}/shopping-cart/1/add/2?quantity=3",
              "host": ["{{baseUrl}}"],
              "path": ["shopping-cart", "1", "add", "2"],
              "query": [{ "key": "quantity", "value": "3" }]
            },
            "auth": { "type": "basic", "basic": [{ "key": "username", "value": "user" }, { "key": "password", "value": "user123" }] }
          }
        },
        {
          "name": "Remove From Cart (ADMIN or USER)",
          "request": {
            "method": "DELETE",
            "url": {
              "raw": "{{baseUrl}}/shopping-cart/1/remove/2",
              "host": ["{{baseUrl}}"],
              "path": ["shopping-cart", "1", "remove", "2"]
            },
            "auth": { "type": "basic", "basic": [{ "key": "username", "value": "user" }, { "key": "password", "value": "user123" }] }
          }
        },
        {
          "name": "Clear Cart (ADMIN or USER)",
          "request": {
            "method": "DELETE",
            "url": {
              "raw": "{{baseUrl}}/shopping-cart/1/clear",
              "host": ["{{baseUrl}}"],
              "path": ["shopping-cart", "1", "clear"]
            },
            "auth": { "type": "basic", "basic": [{ "key": "username", "value": "user" }, { "key": "password", "value": "user123" }] }
          }
        }
      ]
    },
    {
      "name": "Orders",
      "item": [
        {
          "name": "Place Order (ADMIN or USER)",
          "request": {
            "method": "POST",
            "url": {
              "raw": "{{baseUrl}}/orders/1/place",
              "host": ["{{baseUrl}}"],
              "path": ["orders", "1", "place"]
            },
            "auth": { "type": "basic", "basic": [{ "key": "username", "value": "user" }, { "key": "password", "value": "user123" }] }
          }
        },
        {
          "name": "Get Order By ID (ADMIN or USER)",
          "request": {
            "method": "GET",
            "url": {
              "raw": "{{baseUrl}}/orders/1",
              "host": ["{{baseUrl}}"],
              "path": ["orders", "1"]
            },
            "auth": { "type": "basic", "basic": [{ "key": "username", "value": "user" }, { "key": "password", "value": "user123" }] }
          }
        }
      ]
    }
  ],
  "variable": [
    {
      "key": "baseUrl",
      "value": "http://localhost:8080",
      "type": "string"
    }
  ]
}
   ```