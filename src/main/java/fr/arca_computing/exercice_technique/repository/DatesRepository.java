package fr.arca_computing.exercice_technique.repository;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.arca_computing.exercice_technique.model.Dates;

public interface DatesRepository extends JpaRepository<Dates, Date>{

}
