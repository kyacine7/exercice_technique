package fr.arca_computing.exercice_technique.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.arca_computing.exercice_technique.model.LineData;

public interface LineDataRepository extends JpaRepository<LineData, Long>{

	
	@Query(value="SELECT origine_name,SUM(value) "
			+ "FROM line_data "
			+ "GROUP BY origine_name "
			+ "ORDER BY origine_name;", nativeQuery = true)
	List<Object[]> getOrigineNameSumByOrigine();
	
	@Query(value="SELECT date,MAX(date) "
			+ "FROM dates;", nativeQuery = true)
	List<Object[]> getMinMaxDates(); 

	
	@Query(value="SELECT date, SUM(value) " 
			+ "FROM line_data "
			+ "WHERE (DATEDIFF(date, :minDay) < 365) "
			+ "GROUP BY date "
			+ "ORDER BY date;", nativeQuery = true)
	List<Object[]> getYearDataFrom(
			@Param("minDay") String minDate);

	@Query(value="SELECT date, SUM(value) " 
			+ "FROM line_data "
			+ "WHERE (DATEDIFF(date, :minDay) < 365) "
			+ "AND origine_name = :origine_name " ///////////////
			+ "GROUP BY date "
			+ "ORDER BY date;", nativeQuery = true)
	List<Object[]> getYearDataFromByOrigine(
			@Param("origine_name") String origine_name, 
			@Param("minDay") String minDate);
	
	
	@Query(value="SELECT origine_name " 
			+ "FROM line_data "
			+ "GROUP BY origine_name "
			+ "ORDER BY origine_name;", nativeQuery = true)
	List<String> getOrigines();
	
	
	@Query(value="SELECT DATE(date), SUM(value) "
			+ "FROM line_data "
			+ "WHERE (date BETWEEN (DATE(:leftLimit)) AND (DATE(:rightLimit))) "
			+ "GROUP BY date "
			+ "ORDER BY date;", nativeQuery = true)
	List<Object[]> getPeriodDatesValuesSumByDate(
			@Param("leftLimit") String leftLimit, 
			@Param("rightLimit") String rightLimit);

	@Query(value="SELECT DATE(date), SUM(value) "
			+ "FROM line_data "
			+ "WHERE (date BETWEEN (DATE(:leftLimit)) AND (DATE(:rightLimit))) "
			+ "AND origine_name=:origine_name "      /////////////
			+ "GROUP BY date "
			+ "ORDER BY date ;", nativeQuery = true)
	List<Object[]> getPeriodDatesValuesSumByDateByOrigine(
			@Param("origine_name") String origine_name,
			@Param("leftLimit") String leftLimit, 
			@Param("rightLimit") String rightLimit);
	
	
	@Query(value="SELECT DATE(date),SUM(value) "
			+ "FROM line_data "
			+ "WHERE YEAR(line_data.date)=YEAR(:timestamp) "
			+ "GROUP BY date "
			+ "ORDER BY date", nativeQuery = true)
	List<Object[]> getDatesValuesSumByDate(@Param("timestamp") String timestamp);

	


	
}
