create table if not exists vote
(
	vote_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	user_id varchar(50) not null,
	choice_id int not null,
	constraint vote_choice_choice_id_fk
		foreign key (choice_id) references choice (choice_id) ON DELETE CASCADE,
	constraint vote_user_user_id_fk
		foreign key (user_id) references user (user_id) ON DELETE CASCADE
);

