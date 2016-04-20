alter table fashion.customer modify date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
alter table fashion.customer modify date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;