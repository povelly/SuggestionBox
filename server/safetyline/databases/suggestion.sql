create table if not exists suggestion
(
	suggestion_id int auto_increment
		primary key,
	suggestion_content varchar(500) null comment 'txt containing the suggestion',
	suggestion_creation_date datetime default CURRENT_TIMESTAMP not null,
	suggestion_author varchar(50) default 'Anonymous' null
);

