create database java_s2i_db; 
use java_s2i_db;

create table if not exists location(
id		int primary key auto_increment,
`name`	varchar(30) not null unique
);
create table if not exists `user`(
id		int primary key auto_increment,
`name`	varchar(30) not null,
surname varchar(30) not null,
email 	varchar(30) not null unique, 
`password` varchar(150) not null,
location_id int,
foreign key (location_id) references location(id)
);
create table if not exists post(
id		int primary key auto_increment,
title	varchar(30) not null,
post_date datetime default current_timestamp,
content  text not null,
event_type enum("SPORT", "MUSIC", "BOOK_EXCHANGE", "FOOD", "OTHER"),
user_id int,
foreign key (user_id) references `user`(id)
);
insert into location (`name`) values 
("Alessandrino"),
("Appio Claudio"),
("Appio Latino"),
("Appio Pignatelli"),
("Ardeatino"),
("Aurelio"),
("Della Vittoria"),
("Don Bosco"),
("Flaminio"),
("Gianicolense"),
("Monte Sacro"),
("Ostiense"),
("Parioli"),
("Pinciano"),
("Pietralata"),
("Ponte Mammolo"),
("Portuense"),
("Prenestino-Centocelle"),
("Prenestino-Labicano"),
("Salario"),
("San Basilio"),
("Tor di Quinto"),
("Trionfale"),
("Trieste");