create table Choice
(
	id_choice int auto_increment
		primary key,
	voters_cont int default 0 not null,
	content_choice varchar(200) not null,
	id_strawpoll int not null,
	constraint Choice_strawpoll_id_strawpoll_fk
		foreign key (id_strawpoll) references Strawpoll (id_strawpoll)
);

