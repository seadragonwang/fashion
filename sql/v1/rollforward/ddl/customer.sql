-- Table: customer

-- DROP TABLE customer CASCADE;

CREATE TABLE customer
(
	id			bigint AUTO_INCREMENT,
	first_name		varchar(128),
	last_name		varchar(128),
	email			varchar(128),
	passwd		varchar(256),
	address_id	bigint references address(id),
	phone_number	varchar(32),
	date_of_birth		date,
  	date_created timestamp DEFAULT NOW(),
  	date_updated timestamp DEFAULT NOW(),
  	CONSTRAINT pk_customer PRIMARY KEY (id)
);
