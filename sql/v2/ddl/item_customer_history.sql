-- Table: item_customer_history

-- DROP TABLE fashion.item_customer_history CASCADE;

CREATE TABLE fashion.item_customer_history
(
  id bigint AUTO_INCREMENT,
  item_customer_id bigint references item_customer(id),
  create_time timestamp default NOW(),
  reason varchar(64),
  CONSTRAINT pk_item_customer_history PRIMARY KEY (id)
);
