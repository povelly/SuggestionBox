create table if not exists suggestion
(
	suggestion_id int auto_increment
		primary key,
	suggestion_content varchar(65000) null comment 'txt containing the suggestion
Mysql max size for a text is 65535 for 5.0.3
Not unlimited size',
	suggestion_creation_date datetime default CURRENT_TIMESTAMP not null,
	suggestion_author varchar(50) default 'Anonymous' null
);

