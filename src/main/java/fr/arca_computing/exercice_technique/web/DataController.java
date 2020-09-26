package fr.arca_computing.exercice_technique.web;

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
		
		if (origine_name.hashCode() == "noOrigine".hashCode()) {
			onInitChart = lineDataRepository.getYearDataFrom( minDate);
		} else {
			onInitChart = lineDataRepository.getYearDataFromByOrigine(origine_name,minDate);
		}
		
		return onInitChart;
		
	}
	
	@GetMapping("/api/onChoosenRange/{origine_name}/{minDate}/{maxDate}")
	public List<Object[]> getChoosenRange(@PathVariable String origine_name,@PathVariable String minDate, @PathVariable String maxDate){
		
		List<Object[]> choosenRangeData;

		if (origine_name.hashCode() == "noOrigine".hashCode()) {
			choosenRangeData = lineDataRepository.getPeriodDatesValuesSumByDate(minDate, maxDate);
		} else {
			choosenRangeData = lineDataRepository.getPeriodDatesValuesSumByDateByOrigine(origine_name,minDate, maxDate);
		}
		return choosenRangeData;
		
	}
	
	
}


