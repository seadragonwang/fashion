-- Table: product_detail

-- DROP TABLE product_detail CASCADE;

CREATE TABLE product_detail
(
  id bigint NOT NULL AUTO_INCREMENT,
  product_id long references product(id),
  price float(6,2),
  date_created timestamp DEFAULT NOW(),
  available tinyint,
  CONSTRAINT pk_product_detail PRIMARY KEY (id)
);
