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
event_type enum("sport", "music", "book exchange", "food", "other"),
user_id int,
foreign key (user_id) references `user`(id)
);
insert into location (`name`) values 
("Aosta"),
("Torino"),
("Milano"),
("Trento"),
("Bolzano"),
("Venezia"),
("Trieste"),
("Genova"),
("Bologna"),
("Firenze"),
("Perugia"),
("Ancona"),
("L'Aquila"),
("Campobasso"),
("Napoli"),
("Bari"),
("Potenza"),
("Catanzaro"),
("Palermo"),
("Roma"),
("Cagliari");
