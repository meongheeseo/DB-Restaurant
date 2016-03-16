CREATE PROCEDURE CALC_SALARY(INOUT SID_IN INT,                  
 IN RAISE_PERCENT DECIMAL(2,2),                                          
 OUT EMPLOYEE_FIRSTNAME VARCHAR(30),  
 OUT EMPLOYEE_LASTNAME VARCHAR(30),                                          
 OUT SALARY_RESULT INT)                                          
 LANGUAGE SQL                                                        
BEGIN                                                                
 DECLARE CURRENT_SALARY INT DEFAULT 0;                        
 SELECT FIRSTNAME, LASTNAME, SALARY INTO EMPLOYEE_FIRSTNAME, EMPLOYEE_LASTNAME, CURRENT_SALARY FROM STAFF
  WHERE SID=SID_IN;                                               
 SET SALARY_RESULT = CURRENT_SALARY * RAISE_PERCENT + CURRENT_SALARY;                
END@   