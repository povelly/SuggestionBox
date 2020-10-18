create table vote
(
	id_user_transition varchar(50) not null,
	id_choice_transition int not null,
	constraint vote_Choice_id_choice_fk
		foreign key (id_choice_transition) references Choice (id_choice),
	constraint vote_User_id_user_fk
		foreign key (id_user_transition) references User (id_user)
)
comment 'Join table between user and choices';

