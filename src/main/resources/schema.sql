CREATE TABLE customer (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255),
                          email VARCHAR(255) UNIQUE
);

CREATE TABLE shopping_cart (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               customer_id BIGINT UNIQUE,
                               CONSTRAINT fk_cart_customer FOREIGN KEY (customer_id) REFERENCES customer(id)
);

CREATE TABLE product (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY, -- <== this is required
                         product_name VARCHAR(255),
                         category VARCHAR(255),
                         subcategory VARCHAR(255),
                         product_type VARCHAR(255),
                         price DOUBLE,
                         stock_quantity INT
);

CREATE TABLE shopping_cart_item (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    cart_id BIGINT,
                                    product_id BIGINT,
                                    quantity INT,
                                    CONSTRAINT fk_sci_cart FOREIGN KEY (cart_id) REFERENCES shopping_cart(id),
                                    CONSTRAINT fk_sci_product FOREIGN KEY (product_id) REFERENCES product(id)
);

CREATE TABLE order_entity (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              customer_id BIGINT,
                              placed_at TIMESTAMP,
                              CONSTRAINT fk_order_customer FOREIGN KEY (customer_id) REFERENCES customer(id)
);

CREATE TABLE order_item (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            order_id BIGINT,
                            product_id BIGINT,
                            quantity INT,
                            CONSTRAINT fk_oi_order FOREIGN KEY (order_id) REFERENCES order_entity(id),
                            CONSTRAINT fk_oi_product FOREIGN KEY (product_id) REFERENCES product(id)
);
