-- Table: item_customer

-- DROP TABLE fashion.item_customer CASCADE;

CREATE TABLE fashion.item_customer
(
  id bigint AUTO_INCREMENT,
  item_id bigint references item(id),
  customer_id bigint references customer(id),
  active tinyint,
  CONSTRAINT pk_item_customer PRIMARY KEY (id)
);
