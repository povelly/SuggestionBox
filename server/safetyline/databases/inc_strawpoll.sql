create definer = xanadoo@localhost trigger inc_strawpoll
	after update
	on choice
	for each row
	UPDATE strawpoll SET strawpoll_completed = strawpoll_completed + 1
        WHERE strawpoll_id = NEW.strawpoll_id;

