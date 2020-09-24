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
	public Object[] getMinMaxDates(){
		
		List<Object[]> minMaxDates = lineDataRepository.getMinMaxDates();
		List<String> origines = lineDataRepository.getOrigines();
		Object[] returnArray = {minMaxDates,origines};
		return returnArray;
	
	}
	
	@GetMapping("/api/yearData/{origine_name}/{minDate}")
	public List<Object[]> getOnInitChart(@PathVariable String origine_name,@PathVariable String minDate){
		
		List<Object[]> onInitChart;
		System.out.println(origine_name == "noOrigine");
		
		if (origine_name == "noOrigine") {
			onInitChart = lineDataRepository.getYearDataFrom( minDate);
		} else {
			onInitChart = lineDataRepository.getYearDataFromByOrigine(origine_name,minDate);
		}
		
		return onInitChart;
		
	}
	
	@GetMapping("/api/yearDataOnChoosenDate/{origine_name}/{minDate}/{maxDate}")
	public List<Object[]> getChoosenRange(@PathVariable String origine_name,@PathVariable String minDate, @PathVariable String maxDate){
		
		List<Object[]> choosenRangeData;
		System.out.println(origine_name);

		if (origine_name == "*") {
			choosenRangeData = lineDataRepository.getPeriodDatesValuesSumByDateByOrigine(origine_name,minDate, maxDate);
		} else {
			choosenRangeData = lineDataRepository.getPeriodDatesValuesSumByDate(minDate, maxDate);
		}
		return choosenRangeData;
		
	}
	
	
	

//	@GetMapping("/api/oneYearChart/{origine_name}/{firstDayOfYear}")
//	public List<Object[]> getDatesValuesSumByDate(@PathVariable String origine_name,@PathVariable String firstDayOfYear) throws ParseException {
//		
//		
//		List<Object[]> datesValuesSumByDate = lineDataRepository.getDatesValuesSumByDate(origine_name,firstDayOfYear);
//		
//		return datesValuesSumByDate;
//	}
//	
//	@GetMapping("/api/onChoosenRange/{leftLimit}/{rightLimit}")
//	public List<Object[]> getPeriodDatesValuesSumByDate(@PathVariable String leftLimit,@PathVariable String rightLimit) throws ParseException {
//		
//		List<Object[]> datesValuesSumByDate = lineDataRepository.getPeriodDatesValuesSumByDate(leftLimit, rightLimit);
//		
//		return datesValuesSumByDate;
//	}

	
}


