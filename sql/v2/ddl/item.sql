alter table item add web_site varchar(256);
alter table fashion.item modify url varchar(255);
alter table fashion.item add constraint UK_URL unique (url);