create table if not exists user
(
	user_id varchar(50) not null
		primary key,
	password varchar(64) not null comment 'hash SHA1',
	is_admin tinyint not null,
	last_name varchar(50) not null,
	first_name varchar(50) not null
);

