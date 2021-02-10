create table if not exists choice
(
	choice_id int auto_increment
		primary key,
	voters_count int default 0 not null,
	strawpoll_id int not null,
	choice_content varchar(200) not null,
	constraint choice_strawpoll_strawpoll_id_fk
		foreign key (strawpoll_id) references strawpoll (strawpoll_id) ON DELETE CASCADE
);

