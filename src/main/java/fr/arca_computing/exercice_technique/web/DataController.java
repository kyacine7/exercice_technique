package fr.arca_computing.exercice_technique.web;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import fr.arca_computing.exercice_technique.repository.LineDataRepository;

@RestController
public class DataController {

	@Autowired
	private LineDataRepository lineDataRepository;
	
	@GetMapping("/api/statistics")
	public List<Object[]> getStatistics(){
		List<Object[]> statisticts = lineDataRepository.getOrigineNameSumByOrigine();
		return statisticts;
	}

	@GetMapping("/api/minMaxDates")
	public List<Object[]> getMinMaxDates(){
		
		List<Object[]> minMaxDates = lineDataRepository.getMinMaxDates();
		return minMaxDates;
		
	}
	
	@GetMapping("/api/yearDataOnChoosenDate/{minDate}")
	public List<Object[]> getOnInitChart(@PathVariable String minDate){
		
		List<Object[]> onInitChart = lineDataRepository.getYearDataFrom(minDate);
		return onInitChart;
		
	}
	
	
	
	
	
	
	
	@GetMapping("/api/allYears")
	public List<Date> getAllYears(){
		List<Date> years = lineDataRepository.getAllYears();
		return years;
	}

	@GetMapping("/api/oneYearChart/{firstDayOfYear}")
	public List<Object[]> getDatesValuesSumByDate(@PathVariable String firstDayOfYear) throws ParseException {
		
//		firstDayOfYear = firstDayOfYear.replaceAll("T", " ");
//		firstDayOfYear = firstDayOfYear.substring(0,firstDayOfYear.indexOf("+"));
		
		List<Object[]> datesValuesSumByDate = lineDataRepository.getDatesValuesSumByDate(firstDayOfYear);
		
		return datesValuesSumByDate;
	}
	
	@GetMapping("/api/periodDatesValuesSum/{leftLimit}/{rightLimit}")
	public List<Object[]> getPeriodDatesValuesSumByDate(@PathVariable String leftLimit,@PathVariable String rightLimit) throws ParseException {
		
		List<Object[]> datesValuesSumByDate = lineDataRepository.getPeriodDatesValuesSumByDate(leftLimit, rightLimit);
		
		return datesValuesSumByDate;
	}

	
}


