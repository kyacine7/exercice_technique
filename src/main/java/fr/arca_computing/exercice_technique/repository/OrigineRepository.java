package fr.arca_computing.exercice_technique.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.arca_computing.exercice_technique.model.Origine;

public interface OrigineRepository extends JpaRepository<Origine,String > {

}
