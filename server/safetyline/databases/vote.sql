create table vote
(
	id_user_transition varchar(50) not null,
	id_choice_transition int not null
)
comment 'Join table between user and choices';

create index vote_Choice_id_choice_fk
	on vote (id_choice_transition);

create index vote_User_id_user_fk
	on vote (id_user_transition);

