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
3. definitely not have securityConfig done like this
4. add unit tests for the remaining classes
5. add integration tests
6. add more explicit logs for the steps that need to be captured,
by something like Kibana watchers, for examples: order initiated, order successful, order failed
7. For the scope of this homework and for time considerations, security was done like this, 
even if this is definitely not the correct way of doing the user management part. As an example: I would use
Cognito from AWS, to store the passwords, emails and username or any other sensitive information, and will
have a client which does the CRUD operations on AWS Cognito endpoints.