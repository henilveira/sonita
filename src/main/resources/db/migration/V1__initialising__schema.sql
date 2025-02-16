CREATE TABLE product (
    id UUID PRIMARY KEY,       -- O UUID será gerado no código Java
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);
