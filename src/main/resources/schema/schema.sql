DROP
DATABASE IF EXISTS computershop;
CREATE
DATABASE computershop;
USE
computershop;

CREATE TABLE customers
(
    id      VARCHAR(5),
    name    VARCHAR(50) NOT NULL,
    nic     VARCHAR(12) NOT NULL UNIQUE,
    email   VARCHAR(50),
    contact VARCHAR(12),
    address VARCHAR(50) NOT NULL,
    CONSTRAINT PRIMARY KEY (id)
);

CREATE TABLE employees
(
    id       VARCHAR(5),
    name     VARCHAR(50) NOT NULL,
    contact  VARCHAR(12) NOT NULL,
    jobRole  VARCHAR(20) NOT NULL,
    username VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(20) NOT NULL UNIQUE,
    CONSTRAINT PRIMARY KEY (id)
);

CREATE TABLE suppliers
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
    employeeId VARCHAR(5),
    amount     DECIMAL NOT NULL,
    date       DATE,
    CONSTRAINT PRIMARY KEY (code),
    CONSTRAINT FOREIGN KEY (employeeId) REFERENCES employees (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE orders
(
    id         VARCHAR(5),
    customerId VARCHAR(5),
    CONSTRAINT PRIMARY KEY (id),
    CONSTRAINT FOREIGN KEY (customerId) REFERENCES customers (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE custombuilds
(
    code       VARCHAR(5),
    customerId VARCHAR(5),
    employeeId VARCHAR(5),
    CONSTRAINT PRIMARY KEY (code),
    CONSTRAINT FOREIGN KEY (customerId) REFERENCES customers (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (employeeId) REFERENCES employees (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE repairs
(
    code       VARCHAR(5),
    employeeId VARCHAR(5),
    customerId VARCHAR(5),
    details    VARCHAR(100),
    getDate    DATE NOT NULL,
    acceptDate DATE,
    CONSTRAINT PRIMARY KEY (code),
    CONSTRAINT FOREIGN KEY (employeeId) REFERENCES employees (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (customerId) REFERENCES customers (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE delivery
(
    code       VARCHAR(5),
    employeeId VARCHAR(5),
    customerId VARCHAR(5),
    orderId    VARCHAR(5),
    details    VARCHAR(100),
    location   VARCHAR(50) NOT NULL,
    CONSTRAINT PRIMARY KEY (code),
    CONSTRAINT FOREIGN KEY (employeeId) REFERENCES employees (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (customerId) REFERENCES customers (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (orderId) REFERENCES orders (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE order_details
(
    orderId  VARCHAR(5),
    itemCode VARCHAR(5),
    qty      INT NOT NULL,
    date     DATE,
    CONSTRAINT PRIMARY KEY (orderId, itemCode),
    CONSTRAINT FOREIGN KEY (orderId) REFERENCES orders (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (itemCode) REFERENCES items (code) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE supplier_details
(
    supplierId VARCHAR(5),
    itemCode   VARCHAR(5),
    qty        INT,
    CONSTRAINT PRIMARY KEY (supplierId, itemCode),
    CONSTRAINT FOREIGN KEY (supplierId) REFERENCES suppliers (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (itemCode) REFERENCES items (code) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE build_details
(
    buildCode VARCHAR(5),
    itemCode  VARCHAR(5),
    qty       INT,
    date      DATE,
    CONSTRAINT PRIMARY KEY (buildCode, itemCode),
    CONSTRAINT FOREIGN KEY (buildCode) REFERENCES custombuilds (code) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (itemCode) REFERENCES items (code) ON DELETE CASCADE ON UPDATE CASCADE
);