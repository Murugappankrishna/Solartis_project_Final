create database shopBillingManagement;
show databases;
use shopBillingManagement;
CREATE TABLE user_Credentials (
     id INT AUTO_INCREMENT PRIMARY KEY,
     username VARCHAR(50) NOT NULL,
     password VARCHAR(50) NOT NULL,
     role VARCHAR(50)	
);
CREATE TABLE product_details (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(50) NOT NULL,
    product_des VARCHAR(50),
    cost_price INT,
    selling_price INT,
    Stock INT,
    activeflag CHAR(1) DEFAULT 'Y',
    Tax_Percent INT,
    profit INT AS (selling_price - cost_price)
);
CREATE INDEX idx_product_id ON product_details(product_id);
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL
);
CREATE INDEX idx_user_id ON users(user_id);
CREATE TABLE cart (
    cart_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT,
    total_amount INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (product_id) REFERENCES product_details(product_id)
);
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
    taxPercent DECIMAL(5, 2),
    totalCostIncludingTax DECIMAL(10, 2),
    taxCost DECIMAL(10, 2),
    modeOfPayment VARCHAR(50),
    transactionDate DATE
);
-- Sample Data To Populate DB
INSERT INTO product_details (product_name, product_des, cost_price, selling_price, Stock, Tax_Percent)
VALUES 
  ('Laundry Detergent', 'Gentle on Fabrics', 50, 100, 200, 5),
  ('Shampoo', 'For All Hair Types', 30, 60, 150, 5),
  ('Shower Gel', 'Refreshing Shower Gel', 40, 80, 180, 5), 
  ('Soap Bars', 'Moisturizing Soap', 10, 20, 400, 5),
  ('Toothpaste', 'Cavity Protection', 20, 40, 200, 5),
  ('Toothbrush', 'Soft Bristles', 10, 20, 250, 5),
  ('Hand Soap', 'Antibacterial Formula', 20, 40, 200, 5),
  ('Dishwashing Liquid', 'Gentle on Hands', 30, 50, 150, 5),
  ('Air Freshener', 'Long-lasting Fragrance', 20, 40, 100, 5),
  ('Kitchen Towels', 'Absorbent Paper Towels', 30, 60, 150, 5),
  ('Face Wash', 'Deep Cleansing', 40, 80, 120, 5),
  ('Body Lotion', 'Hydrating Formula', 50, 100, 100, 5),
  ('Hand Sanitizer', 'Alcohol-based', 30, 60, 80, 5),
  ('Facial Tissues', 'Soft and Strong', 20, 40, 200, 5),
  ('Chips', 'Assorted Flavors', 10, 20, 300, 5),
  ('Chocolate Bars', 'Milk and Dark Chocolate', 10, 20, 250, 5),
  ('Cookies', 'Variety Pack', 20, 30, 200, 5),
  ('Nuts', 'Roasted and Salted', 30, 50, 150, 5),
  ('Candies', 'Assorted Flavors', 10, 20, 300, 5),
  ('Crackers', 'Whole Grain Crackers', 20, 30, 200, 5),
  ('Popcorn', 'Butter Flavor', 10, 20, 250, 5),
  ('Soda', 'Assorted Flavors', 10, 20, 300, 5),
  ('Energy Drink', 'Refreshing Beverage', 20, 30, 200, 5);

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

INSERT INTO transactionDB(userID,username,productID,productName,quantity,costPrice,sellingPrice,profit,totalPriceExcludingTax,taxPercent,totalCostIncludingTax,taxCost,modeOfPayment,transactionDate)
VALUES
(1,'ARJUN',1,'LAUNDRYDETERGENT',2,50,100,50,200,5,210,10,'CASH','2024-03-19'),
(2,'KARTHIK',2,'SHAMPOO',1,30,60,30,60,5,63,3,'CREDITCARD','2024-03-19'),
(3,'MANOJ',3,'SHOWERGEL',3,40,80,40,240,5,252,12,'CASH','2024-03-19'),
(4,'RAJESH',4,'SOAPBARS',4,10,20,10,80,5,84,4,'DEBITCARD','2024-03-19'),
(5,'SURESH',5,'TOOTHPASTE',2,20,40,20,80,5,84,4,'CASH','2024-03-19'),
(6,'GANESH',6,'TOOTHBRUSH',1,10,20,10,20,5,21,1,'CREDITCARD','2024-03-19'),
(7,'VENKAT',7,'HANDSOAP',3,20,40,20,120,5,126,6,'DEBITCARD','2024-03-19'),
(8,'SARAVANAN',8,'DISHWASHINGLIQUID',2,30,50,20,100,5,105,5,'CASH','2024-03-19'),
(9,'PRAKASH',9,'AIRFRESHENER',1,20,40,20,40,5,42,2,'CREDITCARD','2024-03-19'),
(1,'ARJUN',10,'KITCHENTOWELS',3,30,60,30,180,5,189,9,'DEBITCARD','2024-03-19'),
(2,'KARTHIK',11,'FACEWASH',1,40,80,40,80,5,84,4,'CASH','2024-03-19'),
(3,'MANOJ',12,'BODYLOTION',2,50,100,50,200,5,210,10,'CREDITCARD','2024-03-19'),
(4,'RAJESH',13,'HANDSANITIZER',1,30,60,30,60,5,63,3,'DEBITCARD','2024-03-19'),
(5,'SURESH',14,'FACIALTISSUES',2,20,40,20,80,5,84,4,'CASH','2024-03-19'),
(6,'GANESH',15,'CHIPS',3,10,20,10,60,5,63,3,'CREDITCARD','2024-03-19'),
(7,'VENKAT',16,'CHOCOLATEBARS',2,10,20,10,40,5,42,2,'DEBITCARD','2024-03-19'),
(8,'SARAVANAN',17,'COOKIES',1,20,30,10,30,5,31.5,1.5,'CASH','2024-03-19'),
(9,'PRAKASH',18,'NUTS',2,30,50,20,100,5,105,5,'CREDITCARD','2024-03-19'),
(1,'ARJUN',19,'CANDIES',1,10,20,10,20,5,21,1,'DEBITCARD','2024-03-19'),
(2,'KARTHIK',20,'CRACKERS',2,20,30,10,60,5,63,3,'CASH','2024-03-19'),
(3,'MANOJ',21,'POPCORN',1,10,20,10,20,5,21,1,'CREDITCARD','2024-03-19');


