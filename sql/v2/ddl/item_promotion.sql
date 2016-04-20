-- Table: item_promotion

-- DROP TABLE fashion.item_promotion CASCADE;

CREATE TABLE fashion.item_promotion
(
  id bigint NOT NULL AUTO_INCREMENT,
  discount float,
  item_id bigint references item(id),
  promotion_id bigint references promotion(id),
  CONSTRAINT pk_item_promotion PRIMARY KEY (id)
);
