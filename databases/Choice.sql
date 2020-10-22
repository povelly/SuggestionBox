create table Choice
(
	id_choice int auto_increment
		primary key,
	voters_cont int default 0 not null,
	content_choice varchar(200) not null,
	id_strawpoll int not null
);

create index Choice_strawpoll_id_strawpoll_fk
	on Choice (id_strawpoll);

