CREATE TABLE employee (
	id NUMBER(3) PRIMARY KEY,
    firstName varchar(255) NOT NULL,
    lastName varchar(255) NOT NULL,
    department varchar(255) NOT NULL
    email varchar(255) NOT NULL UNIQUE,
    phoneNumber varchar(255) NOT NULL,
	salary NUMBER NOT NULL
	    CHECK (salary > 1)
	);

CREATE SEQUENCE seq_id_empl
MINVALUE 1
START WITH 1
INCREMENT BY 1
CACHE 3;

ALTER TABLE employee
    MODIFY id DEFAULT seq_id_empl.nextval;

INSERT INTO EMPLOYEE (firstName, lastName, department, email, phoneNumber, salary)
VALUES ('Ora', 'Dinn', 'Finance', 'o.dinn@rare.com', '010203', 2000);

SELECT * FROM employee;


