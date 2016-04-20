-- Table: promotion

-- DROP TABLE promotion CASCADE;

CREATE TABLE promotion
(
  id bigint NOT NULL AUTO_INCREMENT,
  promotion_code varchar(256),
  disclaimer	varchar(4096),
  web_site varchar(128),
  start_date datetime,
  end_date datetime,
  create_date timestamp default NOW(),
  CONSTRAINT pk_promotion PRIMARY KEY (id)
);
