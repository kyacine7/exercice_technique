package fr.arca_computing.exercice_technique.batch.itemProcessor;

import java.sql.Date;
import java.sql.Time;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import fr.arca_computing.exercice_technique.batch.utils.OneLineRead;
import fr.arca_computing.exercice_technique.model.LineData;

@Component
public class LoadTXTtoDBItemProcessor implements ItemProcessor<OneLineRead, LineData>{
	

	@Override
	public LineData process(OneLineRead item) throws Exception {

//	//Preparing data for Mapping
	LineData lineData = new LineData();

	String origine = item.getOrigineName();
	Date date = new Date(Long.parseLong(item.getTimestamp()));
	Time time = new Time(Long.parseLong(item.getTimestamp()));
	int value = item.getValue();

	lineData.setOrigineName(origine);
	lineData.setValue(value);
	lineData.setDate(date);
	lineData.setTime(time);
	
	return lineData;
		
	}

}
