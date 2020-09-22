package fr.arca_computing.exercice_technique.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.arca_computing.exercice_technique.model.LineData;

public interface LineDataRepository extends JpaRepository<LineData, Long>{

	
	@Query(value="SELECT origine_name,SUM(value) "
			+ "FROM exercice_technique_db.line_data "
			+ "GROUP BY origine_name "
			+ "ORDER BY origine_name", nativeQuery = true)
	List<Object[]> getOrigineNameSumByOrigine();
	
	@Query(value="SELECT DATE(MIN(timestamp)),DATE(MAX(timestamp))"
			+ "FROM exercice_technique_db.line_data;", nativeQuery = true)
	List<Object[]> getMinMaxDates(); 

	@Query(value="SELECT DATE(timestamp), SUM(value) " 
			+ "FROM exercice_technique_db.line_data "
			+ "WHERE (DATEDIFF(timestamp, :minDay)) < 365 "
			+ "GROUP BY DATE(timestamp);", nativeQuery = true)
	List<Object[]> getYearDataFrom(@Param("minDay") String minDay);
	
	

	
	
	
	
	
	
	
	@Query(value="SELECT DATE(timestamp) "
			+ "FROM exercice_technique_db.line_data "
			+ "GROUP BY YEAR(line_data.timestamp)", nativeQuery = true)
	List<Date> getAllYears();
	
	@Query(value="SELECT DATE(timestamp),SUM(value) "
			+ "FROM exercice_technique_db.line_data "
			+ "WHERE YEAR(line_data.timestamp)=YEAR(:timestamp) "
			+ "GROUP BY DATE(line_data.timestamp)", nativeQuery = true)
	List<Object[]> getDatesValuesSumByDate(@Param("timestamp") String timestamp); 
	
	@Query(value="SELECT DATE(timestamp), SUM(value) "
			+ "FROM exercice_technique_db.line_data "
			+ "WHERE timestamp BETWEEN (DATE(:leftLimit)) AND (DATE(:rightLimit)) "
			+ "GROUP BY DATE(timestamp);", nativeQuery = true)
	List<Object[]> getPeriodDatesValuesSumByDate(@Param("leftLimit") String leftLimit, @Param("rightLimit") String rightLimit);

	
}
