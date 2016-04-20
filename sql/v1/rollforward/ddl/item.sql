-- Table: item

-- DROP TABLE item CASCADE;

CREATE TABLE item
(
  id bigint NOT NULL AUTO_INCREMENT,
  item_name varchar(256),
  item_number	varchar(256),
  description	varchar(4096),
  item_type varchar(64),
  url varchar(4096),
  date_created timestamp DEFAULT NOW(),
  date_updated timestamp DEFAULT NOW(),
  available tinyint,
  CONSTRAINT pk_item PRIMARY KEY (id)
);