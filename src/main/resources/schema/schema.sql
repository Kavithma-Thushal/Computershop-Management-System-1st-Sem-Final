DROP DATABASE IF EXISTS computershop;
CREATE DATABASE computershop;
USE computershop;

CREATE TABLE customer
(
    id      VARCHAR(5),
    name    VARCHAR(50) NOT NULL,
    nic     VARCHAR(12) NOT NULL UNIQUE,
    email   VARCHAR(50),
    contact VARCHAR(12),
    address VARCHAR(50) NOT NULL,
    CONSTRAINT PRIMARY KEY (id)
);

CREATE TABLE employee
(
    id       VARCHAR(5),
    name     VARCHAR(50) NOT NULL,
    contact  VARCHAR(12) NOT NULL,
    jobRole  VARCHAR(20) NOT NULL,
    username VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(20) NOT NULL UNIQUE,
    CONSTRAINT PRIMARY KEY (id)
);

CREATE TABLE supplier
(
    id      VARCHAR(5),
    name    VARCHAR(50) NOT NULL,
    contact VARCHAR(12) NOT NULL,
    address VARCHAR(50) NOT NULL,
    CONSTRAINT PRIMARY KEY (id)
);

CREATE TABLE items
(
    code        VARCHAR(5),
    description VARCHAR(100),
    unitPrice   DECIMAL NOT NULL,
    qtyOnHand   INT     NOT NULL,
    CONSTRAINT PRIMARY KEY (code)
);

CREATE TABLE salary
(
    code       VARCHAR(5),
    employeeId VARCHAR(5) NOT NULL,
    amount     DECIMAL    NOT NULL,
    date       DATE,
    CONSTRAINT PRIMARY KEY (code),
    CONSTRAINT FOREIGN KEY (employeeId) REFERENCES employee (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE orders
(
    id         VARCHAR(5),
    customerId VARCHAR(5) NOT NULL,
    CONSTRAINT PRIMARY KEY (id),
    CONSTRAINT FOREIGN KEY (customerId) REFERENCES customer (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE custombuilds
(
    code       VARCHAR(5),
    customerId VARCHAR(5) NOT NULL,
    employeeId VARCHAR(5) NOT NULL,
    CONSTRAINT PRIMARY KEY (code),
    CONSTRAINT FOREIGN KEY (customerId) REFERENCES customer (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (employeeId) REFERENCES employee (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE repairs
(
    code       VARCHAR(5),
    customerId VARCHAR(5) NOT NULL,
    employeeId VARCHAR(5) NOT NULL,
    details    VARCHAR(100),
    getDate    DATE       NOT NULL,
    acceptDate DATE,
    CONSTRAINT PRIMARY KEY (code),
    CONSTRAINT FOREIGN KEY (employeeId) REFERENCES employee (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (customerId) REFERENCES customer (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE delivery
(
    code       VARCHAR(5),
    customerId VARCHAR(5)  NOT NULL,
    employeeId VARCHAR(5)  NOT NULL,
    orderId    VARCHAR(5)  NOT NULL,
    location   VARCHAR(50) NOT NULL,
    date       DATE        NOT NULL,
    CONSTRAINT PRIMARY KEY (code),
    CONSTRAINT FOREIGN KEY (employeeId) REFERENCES employee (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (customerId) REFERENCES customer (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (orderId) REFERENCES orders (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE order_details
(
    orderId  VARCHAR(5) NOT NULL,
    itemCode VARCHAR(5) NOT NULL,
    qty      INT        NOT NULL,
    total    DECIMAL,
    date     DATE,
    CONSTRAINT PRIMARY KEY (orderId, itemCode),
    CONSTRAINT FOREIGN KEY (orderId) REFERENCES orders (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (itemCode) REFERENCES items (code) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE supplier_details
(
    supplierId VARCHAR(5) NOT NULL,
    itemCode   VARCHAR(5) NOT NULL,
    qty        INT,
    date       DATE,
    CONSTRAINT PRIMARY KEY (supplierId, itemCode),
    CONSTRAINT FOREIGN KEY (supplierId) REFERENCES supplier (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (itemCode) REFERENCES items (code) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE build_details
(
    buildCode VARCHAR(5) NOT NULL,
    itemCode  VARCHAR(5) NOT NULL,
    qty       INT        NOT NULL,
    total     DECIMAL,
    date      DATE,
    CONSTRAINT PRIMARY KEY (buildCode, itemCode),
    CONSTRAINT FOREIGN KEY (buildCode) REFERENCES custombuilds (code) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (itemCode) REFERENCES items (code) ON DELETE CASCADE ON UPDATE CASCADE
);