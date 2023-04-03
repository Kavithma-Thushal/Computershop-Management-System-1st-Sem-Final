CREATE DATABASE IF NOT EXISTS computershop;
USE computershop;

CREATE TABLE customers
(
    id      VARCHAR(20),
    name    VARCHAR(20) NOT NULL,
    nic     VARCHAR(20) NOT NULL UNIQUE,
    email   VARCHAR(20),
    contact VARCHAR(20),
    address VARCHAR(20) NOT NULL,
    CONSTRAINT PRIMARY KEY (id)
);

CREATE TABLE employees
(
    id       VARCHAR(20),
    name     VARCHAR(20) NOT NULL,
    contact  VARCHAR(20) NOT NULL,
    jobrole  VARCHAR(20) NOT NULL,
    username VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(20) NOT NULL UNIQUE,
    CONSTRAINT PRIMARY KEY (id)
);

CREATE TABLE suppliers
(
    id      VARCHAR(20),
    name    VARCHAR(20) NOT NULL,
    contact VARCHAR(20) NOT NULL,
    address VARCHAR(20) NOT NULL,
    CONSTRAINT PRIMARY KEY (id)
);

CREATE TABLE items
(
    code        VARCHAR(20),
    description VARCHAR(20),
    unitprice   DECIMAL NOT NULL,
    qtyonhand   INT NOT NULL,
    CONSTRAINT PRIMARY KEY (code)
);

CREATE TABLE salary
(
    code       VARCHAR(20),
    employeeid VARCHAR(20),
    amount     DECIMAL NOT NULL,
    datetime   DATETIME,
    CONSTRAINT PRIMARY KEY (code),
    CONSTRAINT FOREIGN KEY (employeeid) REFERENCES employees (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE orders
(
    id          VARCHAR(20),
    customerid  VARCHAR(20),
    qty         INT,
    datetime    DATETIME,
    CONSTRAINT PRIMARY KEY (id),
    CONSTRAINT FOREIGN KEY (customerid) REFERENCES customers (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE custombuilds
(
    code        VARCHAR(20),
    customerid  VARCHAR(20),
    description VARCHAR(20) NOT NULL,
    datetime    DATETIME,
    CONSTRAINT PRIMARY KEY (code),
    CONSTRAINT FOREIGN KEY (customerid) REFERENCES customers (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE repairs
(
    code           VARCHAR(20),
    employeeid     VARCHAR(20),
    customerid     VARCHAR(20),
    details        VARCHAR(20),
    getdatetime    DATETIME NOT NULL,
    acceptdatetime DATETIME,
    CONSTRAINT PRIMARY KEY (code),
    CONSTRAINT FOREIGN KEY (employeeid) REFERENCES employees (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (customerid) REFERENCES customers (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE delivery
(
    code       VARCHAR(20),
    employeeid VARCHAR(20),
    customerid VARCHAR(20),
    orderid    VARCHAR(20),
    details    VARCHAR(20),
    location   VARCHAR(20) NOT NULL,
    CONSTRAINT PRIMARY KEY (code),
    CONSTRAINT FOREIGN KEY (employeeid) REFERENCES employees (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (customerid) REFERENCES customers (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (orderid) REFERENCES orders (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE order_details
(
    orderid   VARCHAR(20),
    itemcode  VARCHAR(20),
    unitprice DECIMAL NOT NULL,
    qty       INT NOT NULL,
    CONSTRAINT PRIMARY KEY (orderid, itemcode),
    CONSTRAINT FOREIGN KEY (orderid) REFERENCES orders (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (itemcode) REFERENCES items (code) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE supplier_details
(
    supplierid VARCHAR(20),
    itemcode   VARCHAR(20),
    qty        INT,
    CONSTRAINT PRIMARY KEY (supplierid, itemcode),
    CONSTRAINT FOREIGN KEY (supplierid) REFERENCES suppliers (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (itemcode) REFERENCES items (code) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE build_details
(
    buildcode   VARCHAR(20),
    itemcode    VARCHAR(20),
    description VARCHAR(20),
    qty         INT,
    CONSTRAINT PRIMARY KEY (buildcode, itemcode),
    CONSTRAINT FOREIGN KEY (buildcode) REFERENCES custombuilds (code) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (itemcode) REFERENCES items (code) ON DELETE CASCADE ON UPDATE CASCADE
);
