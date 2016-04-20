-- Table: product

-- DROP TABLE product CASCADE;

CREATE TABLE product
(
  id bigint NOT NULL AUTO_INCREMENT,
  color varchar (256),
  product_size	varchar(256) NOT NULL,
  product_line2	varchar(64),
  original_price float(6,2),
  date_created timestamp DEFAULT NOW(),
  date_updated timestamp DEFAULT NOW(),
  available tinyint,
  item_id long references item(id), 
  CONSTRAINT pk_product PRIMARY KEY (id)
);
