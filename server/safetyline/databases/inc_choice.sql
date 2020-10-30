create definer = xanadoo@localhost trigger inc_choice
	after insert
	on vote
	for each row
	UPDATE Choice SET Choice.voters_cont = Choice.voters_cont + 1
         WHERE id_choice = NEW.id_choice_transition;

