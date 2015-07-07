# --- !Ups
CREATE TABLE Dog (
  id            INT(10) NOT NULL AUTO_INCREMENT,
  name          VARCHAR(100),
  favorite_food VARCHAR(100),
  PRIMARY KEY (id)
);
# --- !Downs
DROP TABLE Dog;