CREATE TABLE department(
	departmentId NUMBER(3) PRIMARY KEY,
    name varchar(255) NOT NULL,
    location varchar (255) NOT NULL
    );

CREATE SEQUENCE seq_id_dep
MINVALUE 1
START WITH 1
INCREMENT BY 1
CACHE 3;

ALTER TABLE department
    MODIFY departmentId DEFAULT seq_id_dep.nextval;

INSERT INTO department (name, location) VALUES ('Finance', 'Rome');

SELECT * FROM department;

SELECT * FROM department WHERE departmentId = 1;





