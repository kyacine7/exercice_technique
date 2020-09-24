package fr.arca_computing.exercice_technique.itemProcessor;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import fr.arca_computing.exercice_technique.model.LineData;
import fr.arca_computing.exercice_technique.model.OneStampLine;

@Component
public class LoadTXTtoDBItemProcessor implements ItemProcessor<OneStampLine, LineData>{
	

	@Override
	public LineData process(OneStampLine item) throws Exception {

//		//Preparing data for Mapping
		LineData lineData = new LineData();
		Timestamp timestamp = new Timestamp(item.getTimestamp());
		
//		//Mapping data And converting 
		lineData.setOrigineName(item.getOrigineName());
		lineData.setValue(item.getValue());
		lineData.setTimestamp(timestamp);
		
		return lineData;
	}

}
