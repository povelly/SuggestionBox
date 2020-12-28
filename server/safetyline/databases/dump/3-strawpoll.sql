create table if not exists strawpoll
(
	strawpoll_id int auto_increment
		primary key,
	strawpoll_creation_date datetime default CURRENT_TIMESTAMP not null,
	strawpoll_expiration_date datetime not null,
	title varchar(150) not null,
	strawpoll_author varchar(50) default 'Anonymous' null comment 'Administrator who created the strawpoll
'
);

