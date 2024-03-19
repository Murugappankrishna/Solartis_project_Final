create database ShopBillingManagement;
show databases;
use ShopBillingManagement;
CREATE TABLE user_Credentials (
     id INT AUTO_INCREMENT PRIMARY KEY,
     username VARCHAR(50) NOT NULL,
     password VARCHAR(50) NOT NULL,
     role VARCHAR(50)	
);
INSERT INTO user_Credentials (username, password, role) VALUES
('Ramesh', '-938314596', 'SalesPerson'),
('Ragunath', '65654852', 'Admin'),
('Arun', '3003018', 'SalesPerson'),
('Siva', '3530625', 'Admin'),
('Harish', '-1224453307', 'SalesPerson'),
('Vijay', '112208527', 'SalesPerson');

-- Product Table
CREATE TABLE product_details (
     product_id INT AUTO_INCREMENT PRIMARY KEY,
     product_name VARCHAR(50) NOT NULL,
     product_des VARCHAR(50),
     cost_price INT,
     selling_price INT,
     Stock INT
);
ALTER TABLE product_details
ADD COLUMN activeflag CHAR(1) DEFAULT 'Y';

ALTER TABLE product_details
ADD COLUMN profit INT AS (selling_price - cost_price);
ALTER TABLE product_details
DROP COLUMN profit;
Alter Table product_details ADD COLUMN Tax_Percent int;
INSERT INTO product_details (product_name, product_des, cost_price, selling_price, Stock) VALUES
('Soap', 'Personal Care', 20, 30, 100),
('Rice', 'Household Essentials', 50, 70, 200),
('Shampoo', 'Personal Care', 40, 60, 150),
('Biscuit', 'Snacks', 10, 15, 80),
('Cereal', 'Household Essentials', 30, 45, 120);
CREATE INDEX idx_product_id ON product_details(product_id);
-- User Table
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL
);
INSERT INTO users (username) VALUES
('Arjun'),
('Karthik'),
('Manoj'),
('Rajesh'),
('Suresh'),
('Ganesh'),
('Venkat'),
('Saravanan'),
('Prakash');
-- table cart
CREATE TABLE cart (
    cart_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (product_id) REFERENCES product_details(product_id)
);
ALTER TABLE cart
ADD COLUMN total_amount INT;
-- transcation table
 CREATE TABLE transactionDB (
    transactionID INT AUTO_INCREMENT PRIMARY KEY,
    userID INT,
    username VARCHAR(50),
    productID INT,
    productName VARCHAR(50),
    quantity INT,
    costPrice DECIMAL(10, 2),
    sellingPrice DECIMAL(10, 2),
    profit DECIMAL(10, 2),
    totalPriceExcludingTax DECIMAL(10, 2),
    unitPrice DECIMAL(10, 2),
    taxPercent DECIMAL(5, 2),
    totalCostIncludingTax DECIMAL(10, 2),
    taxCost DECIMAL(10, 2),
    modeOfPayment VARCHAR(50),
    transactionDate DATE
);
ALTER TABLE transactionDB
DROP COLUMN unitPrice;


-- Trial quries
show tables;
describe user_credentials;
describe product_details;
describe cart;
describe transactionDB;
select*from user_Credentials;
select * from product_details;
select*from users;
select*from cart;
select role from user_Credentials where username= 'Siva' AND password = '3530625';
INSERT INTO cart (user_id, product_id, quantity)
VALUES
(1,2 ,10);
INSERT INTO cart (user_id, product_id, quantity, total_amount)
VALUES
(1, 2, 10, (SELECT selling_price * 10 FROM product_details WHERE product_id = 2));

SELECT u.username, pd.product_name, c.quantity,c.total_amount
FROM cart c
JOIN users u ON c.user_id = u.user_id
JOIN product_details pd ON c.product_id = pd.product_id WHERE u.user_id = 2;
DELETE FROM cart where cart_id=1;
UPDATE product_details SET TAX_Percent=3 WHERE product_id IN (4);
DELETE FROM cart where cart_id IN(130,131);
UPDATE user_Credentials SET role='Manager' where   id in (2,4,7,8,10,11,12);
SELECT cart_id FROM cart WHERE user_id = 1;
DELETE cart FROM cart INNER JOIN (SELECT cart_id FROM cart WHERE user_id = 1) AS subquery ON cart.cart_id = subquery.cart_id;
SELECT cart_id FROM cart WHERE user_id = 2;
select username from users where user_id=1;
select product_id from cart;
select Stock from product_details where product_id=1;
select product_name from product_details where Stock<20;
select*from transactionDB;
Delete from transactionDB  where  transactionID in(72,73,74,75);
Delete from cart  where  cart_id   in(175);
INSERT INTO transactionDB (userID, username, productID, productName, quantity, costPrice, sellingPrice, profit, totalPriceExcludingTax, taxPercent, totalCostIncludingTax, taxCost, transactionDate)
SELECT
    c.user_id AS userID,
    u.username AS username,
    c.product_id AS productID,
    pd.product_name AS productName,
    c.quantity AS quantity,
    pd.cost_price AS costPrice,
    pd.selling_price AS sellingPrice,
    (pd.selling_price - pd.cost_price) * c.quantity AS profit,
    pd.selling_price * c.quantity AS totalPriceExcludingTax,
    pd.Tax_Percent  AS taxPercent,
  (pd.selling_price * c.quantity) + ((pd.selling_price * c.quantity) * (pd.Tax_Percent / 100)) AS totalCostIncludingTax,
   (pd.selling_price * c.quantity) * (pd.Tax_Percent  / 100) AS taxCost,
        
    CURDATE() AS transactionDate
FROM
    cart c
JOIN users u ON c.user_id = u.user_id JOIN product_details pd ON c.product_id = pd.product_id;



select sum(taxCost) from transactionDB where transactionDate = '2024-08-18';
SELECT productName,taxCost  FROM transactionDB;
SELECT productName,quantity, profit FROM transactionDB where modeOfPayment='CASH';
INSERT INTO cart (user_id, product_id, quantity, total_amount) 
VALUES (1, 9, 10, 
        (SELECT selling_price * 10
         FROM product_details 
         WHERE product_id = 9
         AND activeflag = 'Y')
);

INSERT INTO cart (user_id, product_id, quantity, total_amount) 
SELECT 1, 2, 10, selling_price * 10 
FROM product_details 
WHERE product_id = 2 AND activeflag = 'Y';
SELECT SUM(taxCost) AS totalTaxPaid FROM transactionDB WHERE MONTH(transactionDate) = 03 AND YEAR(transactionDate) = 2024
