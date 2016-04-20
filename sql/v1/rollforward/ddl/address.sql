-- Table: address

-- DROP TABLE address CASCADE;

use fashion;

CREATE TABLE address
(
  id bigint NOT NULL AUTO_INCREMENT,
  address_name varchar (256),
  address_line1	varchar(256) NOT NULL,
  address_line2	varchar(64),
  city varchar(64),
  county varchar(64),
  state varchar(2),
  zip varchar(10),
  date_created timestamp DEFAULT NOW(),
  date_updated timestamp DEFAULT NOW(),
  CONSTRAINT pk_address PRIMARY KEY (id)
);
