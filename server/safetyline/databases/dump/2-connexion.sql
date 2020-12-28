create table if not exists connexion
(
	session_id varchar(50) not null
		primary key,
	user_id varchar(50) not null,
	connexion_expiration_date datetime not null,
	constraint connexion_user_user_id_fk
		foreign key (user_id) references user (user_id)
);

