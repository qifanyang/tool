DROP TABLE  IF EXISTS `user`;

CREATE TABLE user (
  USERNAME varchar(16) default '' NOT NULL ,
  PASSWORD varchar(50) default '' NOT NULL ,
  PRIMARY KEY  (USERNAME)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

commit;