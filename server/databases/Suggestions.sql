create table Suggestions
(
	Id_recommendation int auto_increment
		primary key,
	suggestion_content varchar(500) null comment 'txt file containing the recommendation',
	author varchar(50) null,
	date datetime default CURRENT_TIMESTAMP not null
)
comment 'suggestions by coworkers';

