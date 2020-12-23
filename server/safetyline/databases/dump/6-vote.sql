create table if not exists vote
(
	user_id varchar(50) not null,
	choice_id int not null,
	constraint vote_choice_choice_id_fk
		foreign key (choice_id) references choice (choice_id),
	constraint vote_user_user_id_fk
		foreign key (user_id) references user (user_id)
);

