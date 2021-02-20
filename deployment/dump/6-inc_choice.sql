create definer = root@localhost trigger inc_choice
	after insert
	on vote
	for each row
	UPDATE choice SET choice.voters_count = choice.voters_count + 1
         WHERE choice_id = NEW.choice_id;

