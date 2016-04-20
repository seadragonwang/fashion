alter table fashion.item modify date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
alter table fashion.item modify date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;