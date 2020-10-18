create table Strawpoll
(
	id_strawpoll int auto_increment
		primary key,
	creation_date datetime default CURRENT_TIMESTAMP null,
	deadline_time datetime not null,
	title varchar(150) not null
);

