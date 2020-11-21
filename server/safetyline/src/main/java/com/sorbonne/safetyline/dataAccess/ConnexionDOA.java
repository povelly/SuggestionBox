package com.sorbonne.safetyline.dataAccess;

import com.sorbonne.safetyline.model.Connexion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnexionDOA extends JpaRepository<Connexion, String> {

}
