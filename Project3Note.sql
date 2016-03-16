CREATE TRIGGER fulfilled 
AFTER UPDATE On Fulfills 
FOR EACH ROW 
DELETE FROM Order 
WHERE oid IN (SELECT oid 
    FROM Fulfills 
    WHERE delivered='Y')

CREATE TRIGGER fulfilled AFTER UPDATE On Fulfills FOR EACH ROW DELETE FROM Order WHERE oid IN (SELECT oid FROM Fulfills WHERE delivered='Y')

UPDATE Fulfills 
SET delivered='Y'
WHERE oid=1

UPDATE Fulfills SET delivered='Y' WHERE oid=1

UPDATE Fulfills SET delivered='N' WHERE oid=1

