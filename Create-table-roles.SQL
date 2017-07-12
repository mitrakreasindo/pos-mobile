CREATE TABLE roles 
(
  name varchar(255) NOT NULL,
  table_name varchar(30),
  refers boolean DEFAULT FALSE,
  alters boolean DEFAULT FALSE,
  deletes boolean DEFAULT FALSE,
  inserts boolean DEFAULT FALSE,
  updates boolean DEFAULT FALSE,
  selects boolean DEFAULT FALSE,
  CONSTRAINT UK_roles UNIQUE (name),
  CONSTRAINT CK1_roles CHECK (name IS NOT NULL),
  CONSTRAINT CK2_roles CHECK (name IN ('Owner', 'Manager', 'HR Manager', 'Cashier', 'Stockist', 'Sales/Marketing', 'User', 'Stockist'))
);