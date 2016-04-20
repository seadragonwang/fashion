-- Table: item_image

-- DROP TABLE item_image CASCADE;

CREATE TABLE item_image
(
  id bigint NOT NULL AUTO_INCREMENT,
  item_id long references item(id),
  big_image_url varchar(2048),
  small_image_url varchar(2048),
  date_created timestamp DEFAULT NOW(),
  date_updated timestamp DEFAULT NOW(),
  CONSTRAINT pk_item_image PRIMARY KEY (id)
);
