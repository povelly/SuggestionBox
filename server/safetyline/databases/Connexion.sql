create table Connexion
(
	ticket_id varchar(50) not null comment 'id generated randomly',
	id_user varchar(50) not null,
	expiration_date datetime not null,
	constraint connexion_ticket_id_uindex
		unique (ticket_id)
)
comment 'table of connected users';

alter table Connexion
	add primary key (ticket_id);

