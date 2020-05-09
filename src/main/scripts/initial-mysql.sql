-- use to run mysql db docker image (with empty root password, port expose, volume mounting)
-- docker run --name sprng-mysql -e MYSQL_ALLOW_EMPTY_PASSWORD=true -d -p 3306:3306 -v ~/codeself/playground/sprng-mysql-volume/:/data/db mysql


-- connect to mysql and run as root user

-- create databases
CREATE DATABASE sfg_dev;
CREATE DATABASE sfg_prod;

-- create database service accounts
CREATE USER 'sfg_dev_user'@'%' IDENTIFIED BY 'shumyk';
CREATE USER 'sfg_prod_user'@'%' IDENTIFIED BY 'shumyk';

-- database grants
GRANT SELECT ON sfg_dev.* to 'sfg_dev_user'@'%';
GRANT UPDATE ON sfg_dev.* to 'sfg_dev_user'@'%';
GRANT DELETE ON sfg_dev.* to 'sfg_dev_user'@'%';
GRANT INSERT ON sfg_dev.* to 'sfg_dev_user'@'%';
GRANT SELECT ON sfg_prod.* to 'sfg_prod_user'@'%';
GRANT UPDATE ON sfg_prod.* to 'sfg_prod_user'@'%';
GRANT DELETE ON sfg_prod.* to 'sfg_prod_user'@'%';
GRANT INSERT ON sfg_prod.* to 'sfg_prod_user'@'%';