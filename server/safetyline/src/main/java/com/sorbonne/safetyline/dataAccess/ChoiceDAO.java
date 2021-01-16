package com.sorbonne.safetyline.dataAccess;

import com.sorbonne.safetyline.model.Choice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChoiceDAO extends JpaRepository<Choice, Integer> {
}
