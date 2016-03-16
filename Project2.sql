CREATE TABLE Staff 
(
   sid INT NOT NULL, 
   phone CHAR(12) UNIQUE NOT NULL,
   salary INT, 
   firstName VARCHAR(30), 
   lastName VARCHAR(30), 
   PRIMARY KEY (sid)
);

CREATE TABLE GeneralStaff (
   sid INT NOT NULL, 
   position VARCHAR(10), 
   PRIMARY KEY (sid), 
   FOREIGN KEY (sid) REFERENCES Staff);

CREATE TABLE DeliveryStaff 
(
   sid INT NOT NULL,
   PRIMARY KEY (sid), 
   FOREIGN KEY (sid) REFERENCES Staff
);

CREATE TABLE AreaCode (
   postalCode CHAR(6) NOT NULL, 
   PRIMARY KEY (postalCode));

CREATE TABLE Customers 
(
   cid INT NOT NULL, 
   firstName VARCHAR(30), 
   lastName VARCHAR(30), 
   address VARCHAR(30), 
   phone CHAR(12), 
   PRIMARY KEY (cid)
); 
  
CREATE TABLE Order (oid INT NOT NULL, 
   method VARCHAR(30) NOT NULL, 
   PRIMARY KEY (oid));

CREATE TABLE Menu (mid INT NOT NULL, 
   name VARCHAR(30) NOT NULL, 
   description VARCHAR(50),
   price DECIMAL(5, 2) NOT NULL, 
   PRIMARY KEY (mid));

CREATE TABLE Beverage (mid INT NOT NULL, 
   category VARCHAR(30) NOT NULL, 
   PRIMARY KEY (mid), 
   FOREIGN KEY (mid) REFERENCES Menu);

CREATE TABLE Food (mid INT NOT NULL, 
   category VARCHAR(30) NOT NULL, 
   PRIMARY KEY (mid), 
   FOREIGN KEY (mid) REFERENCES Menu);

CREATE TABLE Assigned_to 
(
   sid INT NOT NULL, 
   postalCode CHAR(6) NOT NULL, 
   PRIMARY KEY (sid, postalCode), 
   FOREIGN KEY (sid) REFERENCES DeliveryStaff, 
   FOREIGN KEY (postalCode) REFERENCES AreaCode
); 

CREATE TABLE Delivers_to (sid INT NOT NULL, 
   cid INT NOT NULL, 
   PRIMARY KEY (sid, cid), 
   FOREIGN KEY (sid) REFERENCES DeliveryStaff, 
   FOREIGN KEY (cid) REFERENCES Customers);

CREATE TABLE Done_by (
   oid INT NOT NULL, 
   cid INT NOT NULL, 
   PRIMARY KEY (oid, cid), 
   FOREIGN KEY (oid) REFERENCES Order
   ON DELETE CASCADE, 
   FOREIGN KEY (cid) REFERENCES Customers
);

CREATE TABLE Used_to (oid INT NOT NULL, 
   mid INT NOT NULL, 
   PRIMARY KEY (oid, mid), 
   FOREIGN KEY (oid) REFERENCES Order
   ON DELETE CASCADE, 
   FOREIGN KEY (mid) REFERENCES Menu);

CREATE TABLE Lives_in 
(
   cid INT NOT NULL, 
   postalCode CHAR(6) NOT NULL, 
   PRIMARY KEY (cid, postalCode), 
   FOREIGN KEY (cid) REFERENCES Customers, 
   FOREIGN KEY (postalCode) REFERENCES AreaCode
); 

CREATE TABLE Comes_from 
(
   oid INT NOT NULL, 
   postalCode CHAR(6) NOT NULL,
   PRIMARY KEY (oid, postalCode), 
   FOREIGN KEY (oid) REFERENCES Order 
   ON DELETE CASCADE, 
   FOREIGN KEY (postalCode) REFERENCES AreaCode
);

*Need to add this table to the diagram to match delivery staff to distinct orders.
CREATE TABLE Fulfills (
   sid INT NOT NULL, 
   oid INT NOT NULL, 
   delivered CHAR(1) DEFAULT ‘N’, 
   PRIMARY KEY (sid, oid), 
   FOREIGN KEY (sid) REFERENCES DeliveryStaff, 
   FOREIGN KEY (oid) REFERENCES Order
   ON DELETE CASCADE
);




INSERT INTO Staff VALUES(1, '438-046-1796', 3030, 'Jerry', 'Sanchez');
INSERT INTO Staff VALUES(2, '514-792-8340', 3390, 'Susan', 'Perez');
INSERT INTO Staff VALUES(3, '514-590-2898', 3190, 'Chris', 'Flores');
INSERT INTO Staff VALUES(4, '514-317-7526', 2890, 'Joe', 'Kelly');
INSERT INTO Staff VALUES(5, '438-637-4331', 2370, 'Jacqueline', 'Carter');

SELECT * FROM Staff;




INSERT INTO Staff VALUES(6, '514-743-7636', NULL, 'Walter', 'Hill');
INSERT INTO Staff VALUES(7, '514-134-9922', NULL, 'Virginia', 'Mitchell');
INSERT INTO Staff VALUES(8, '438-294-1394', NULL, 'Louise', 'Thompson');
INSERT INTO Staff VALUES(9, '514-331-7527', NULL, 'Eric', 'Long');
INSERT INTO Staff VALUES(10, '438-610-3574', NULL, 'Paul', 'Peterson');

INSERT INTO GeneralStaff VALUES(2, 'Manager');
INSERT INTO GeneralStaff VALUES(5, 'Cook');
INSERT INTO GeneralStaff VALUES(7, 'Waitress');
INSERT INTO GeneralStaff VALUES(8, 'Waitress');
INSERT INTO GeneralStaff VALUES(10, 'Cook');

INSERT INTO DeliveryStaff VALUES(1);
INSERT INTO DeliveryStaff VALUES(3);
INSERT INTO DeliveryStaff VALUES(4);
INSERT INTO DeliveryStaff VALUES(6);
INSERT INTO DeliveryStaff VALUES(9);

INSERT INTO AreaCode VALUES('H4M2C3');
INSERT INTO AreaCode VALUES('H4M2G9');
INSERT INTO AreaCode VALUES('H4M2H3');
INSERT INTO AreaCode VALUES('H4M2K3');
INSERT INTO AreaCode VALUES('H4M2K4');

INSERT INTO Customers VALUES(1, 'Emanuel', 'Moody', '123 Front street',  '514-212-6366'); 
INSERT INTO Customers VALUES(2, 'Vanessa', 'Hampton', '12 Grand street', '438-783-2583'); 
INSERT INTO Customers VALUES(3, 'Rose', 'Rhodes', '1232 Parc street', '514-271-0615'); 
INSERT INTO Customers VALUES(4, 'Tina', 'Morris', '989 Juliette street', '514-439-1412'); 
INSERT INTO Customers VALUES(5, 'Louise', 'Nelson', '223 Cordner street', '438-253-6201'); 

INSERT INTO Order VALUES(1, 'online');
INSERT INTO Order VALUES(2, 'phone');
INSERT INTO Order VALUES(3, 'online');
INSERT INTO Order VALUES(4, 'online');
INSERT INTO Order VALUES(5, 'phone');
INSERT INTO Order VALUES(6, 'phone');
INSERT INTO Order VALUES(7, 'phone');

INSERT INTO Menu VALUES(1, 'Cheese', 'Tomato sauce, mozzarella', 11.99);
INSERT INTO Menu VALUES(2, 'All Dressed', 
   'Pepperoni, mushrooms, green peppers, mozzarella', 12.99);
INSERT INTO Menu VALUES(3, 'Pepperoni', 'Pepperoni, mozzarella', 12.99);
INSERT INTO Menu VALUES(4, 'Bacon', 'Mozzarella, bacon, mushrooms', 12.99);
INSERT INTO Menu VALUES(5, 'Smoked Meat', 'Mozzarella, smoked meat', 12.99);
INSERT INTO Menu VALUES(6, 'Mexican Beef', 
   'Onions, hot peppers, beef, mozzarella', 12.99);
INSERT INTO Menu VALUES(7, 'Vegetarian', 'Mushrooms, onions, green peppers, mozzarella', 12.99);
INSERT INTO Menu VALUES(8, 'Hawaiian', 'Ham, pineapple, mozzarella', 12.99);
INSERT INTO Menu VALUES(9, 'Philly', 'Mushrooms, steak, onions, green peppers, mozzarella', 13.99);
INSERT INTO Menu VALUES(10, 'Soft Drinks 355ml', NULL, 1.25);
INSERT INTO Menu VALUES(11, 'Soft Drinks 2L', NULL, 2.99);
INSERT INTO Menu VALUES(12, 'Garden Salad', NULL, 3.99);
INSERT INTO Menu VALUES(13, 'Cheese Bread', NULL, 4.99);
INSERT INTO Menu VALUES(14, 'Fries Small', NULL, 1.49);
INSERT INTO Menu VALUES(15, 'Fries Large', NULL, 1.99);
INSERT INTO Menu VALUES(16, 'Chicken Wings 10pcs', NULL, 7.99);
INSERT INTO Menu VALUES(17, 'Chicken Wings 20pcs', NULL, 13.99);

INSERT INTO Beverage VALUES(10, 'Non-alcohol');
INSERT INTO Beverage VALUES(11, 'Non-alcohol');

INSERT INTO Food VALUES(1, 'Pizza');
INSERT INTO Food VALUES(2, 'Pizza');
INSERT INTO Food VALUES(3, 'Pizza');
INSERT INTO Food VALUES(4, 'Pizza');
INSERT INTO Food VALUES(5, 'Pizza');
INSERT INTO Food VALUES(6, 'Pizza');
INSERT INTO Food VALUES(7, 'Pizza');
INSERT INTO Food VALUES(8, 'Pizza');
INSERT INTO Food VALUES(9, 'Pizza');
INSERT INTO Food VALUES(12, 'Sides');
INSERT INTO Food VALUES(13, 'Sides');
INSERT INTO Food VALUES(14, 'Sides');
INSERT INTO Food VALUES(15, 'Sides');
INSERT INTO Food VALUES(16, 'Sides');
INSERT INTO Food VALUES(17, 'Sides');

INSERT INTO Assigned_to VALUES(1, 'H4M2C3');
INSERT INTO Assigned_to VALUES(3, 'H4M2G9');
INSERT INTO Assigned_to VALUES(4, 'H4M2H3');
INSERT INTO Assigned_to VALUES(6, 'H4M2K3');
INSERT INTO Assigned_to VALUES(9, 'H4M2K4');

INSERT INTO Delivers_to VALUES(6, 1);
INSERT INTO Delivers_to VALUES(6, 2);
INSERT INTO Delivers_to VALUES(1, 3);
INSERT INTO Delivers_to VALUES(3, 4);
INSERT INTO Delivers_to VALUES(4, 5);

INSERT INTO Done_by VALUES(1, 1);
INSERT INTO Done_by VALUES(2, 2);
INSERT INTO Done_by VALUES(3, 3);
INSERT INTO Done_by VALUES(4, 4);
INSERT INTO Done_by VALUES(5, 5);
INSERT INTO Done_by VALUES(6, 1);
INSERT INTO Done_by VALUES(7, 3);

INSERT INTO Used_to VALUES(1, 13);
INSERT INTO Used_to VALUES(1, 2);
INSERT INTO Used_to VALUES(2, 14);
INSERT INTO Used_to VALUES(3, 15);
INSERT INTO Used_to VALUES(4, 16);
INSERT INTO Used_to VALUES(5, 17);
INSERT INTO Used_to VALUES(6, 3);
INSERT INTO Used_to VALUES(6, 11);
INSERT INTO Used_to VALUES(7, 5);
INSERT INTO Used_to VALUES(7, 10);
INSERT INTO Used_to VALUES(7, 12);
INSERT INTO Used_to VALUES(7, 16);

INSERT INTO Lives_in VALUES(1, 'H4M2K3');
INSERT INTO Lives_in VALUES(2, 'H4M2K3');
INSERT INTO Lives_in VALUES(3, 'H4M2C3');
INSERT INTO Lives_in VALUES(4, 'H4M2G9');
INSERT INTO Lives_in VALUES(5, 'H4M2H3');

INSERT INTO Comes_from VALUES(1, 'H4M2K3');
INSERT INTO Comes_from VALUES(2, 'H4M2K3');
INSERT INTO Comes_from VALUES(3, 'H4M2C3');
INSERT INTO Comes_from VALUES(4, 'H4M2G9');
INSERT INTO Comes_from VALUES(5, 'H4M2H3');
INSERT INTO Comes_from VALUES(6, 'H4M2K3');
INSERT INTO Comes_from VALUES(7, 'H4M2C3');

INSERT INTO Fulfills VALUES(6, 1, 'Y');
INSERT INTO Fulfills VALUES(6, 2, 'N');
INSERT INTO Fulfills VALUES(1, 3, 'Y');
INSERT INTO Fulfills VALUES(3, 4, 'N');
INSERT INTO Fulfills VALUES(4, 5, 'N');
INSERT INTO Fulfills VALUES(6, 6, 'N');
INSERT INTO Fulfills VALUES(1, 7, 'N');

SELECT * FROM Staff;
SELECT * FROM GeneralStaff;
SELECT * FROM DeliveryStaff;
SELECT * FROM AreaCode;
SELECT * FROM Customers;
SELECT * FROM Order;
SELECT * FROM Menu;
SELECT * FROM Beverage;
SELECT * FROM Food;
SELECT * FROM Assigned_to;
SELECT * FROM Delivers_to;
SELECT * FROM Done_by;
SELECT * FROM Used_to;
SELECT * FROM Lives_in;
SELECT * FROM Comes_from;
SELECT * FROM Fulfills;

SELECT * 
FROM Order 
WHERE oid IN (SELECT oid 
   FROM Fulfills 
   WHERE delivered='Y');

SELECT postalCode, COUNT(DISTINCT oid) AS NumberOfOrders 
FROM Comes_from 
GROUP BY postalCode;

SELECT c.firstName, c.lastName, t.NumberOfOrders 
FROM Customers c, (SELECT cid, 
   COUNT(DISTINCT oid) AS NumberOfOrders 
   FROM Done_by 
   GROUP BY cid) t 
WHERE c.cid=t.cid;

SELECT c.firstName AS FirstName, 
c.lastName AS LastName,
COUNT(DISTINCT d.oid) AS NumberOfOrders
FROM Done_by d, Customers c 
WHERE d.cid=c.cid 
GROUP BY d.cid;

SELECT * 
FROM Menu 
WHERE price >= 10.00 
AND price <=20.00;

SELECT s.firstName, s.lastName 
FROM Staff s, Customers c 
WHERE s.phone = c.phone 
AND s.firstName = c.firstName 
AND s.lastName = c.lastName;

Update Staff 
SET salary=salary+200 
WHERE salary<2500;

Update Menu 
SET Menu.price=Menu.price+1 
WHERE Menu.mid IN (SELECT mid 
   FROM Food) 
AND Menu.price < 9;

Delete FROM Menu 
WHERE Menu.mid IN (Select mid 
   FROM Used_to
   GROUP BY mid 
   HAVING COUNT(*) < 1);

Delete FROM Order 
WHERE Order.oid IN (Select oid 
   FROM Fulfills 
   WHERE delivered = 'Y');

CREATE VIEW mostPopularDishes AS 
SELECT name 
FROM Menu 
WHERE mid IN (SELECT mid 
   FROM Used_To 
   GROUP BY mid 
   HAVING COUNT(mid) >= 2);

CREATE VIEW dailyRevenue AS 
SELECT SUM (price) AS Sum
FROM (SELECT name, price, mid 
   FROM Menu 
   WHERE mid IN (SELECT mid 
      FROM Used_To)) AS OrderedDishes;

ALTER TABLE Staff
ADD CHECK (salary > 0);

ALTER TABLE Menu 
ADD CHECK (price > 0); 
