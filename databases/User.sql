create table User
(
	id_user varchar(50) not null
		primary key,
	password varchar(50) not null,
	isAdmin tinyint(1) not null
);

