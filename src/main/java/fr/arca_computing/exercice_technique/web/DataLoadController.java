package fr.arca_computing.exercice_technique.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.arca_computing.exercice_technique.model.Dates;
import fr.arca_computing.exercice_technique.model.LineData;
import fr.arca_computing.exercice_technique.model.Origine;
import fr.arca_computing.exercice_technique.repository.DatesRepository;
import fr.arca_computing.exercice_technique.repository.LineDataRepository;
import fr.arca_computing.exercice_technique.repository.OrigineRepository;

@RestController
public class DataLoadController {

	
	@Autowired
	LineDataRepository lineDataRepository;
	
	@Autowired
	OrigineRepository origineRepository;
	
	@Autowired
	DatesRepository datesRepository;
	
	String INPUT_FILE_NAME = "./src/main/resources/data-files/data.txt";

	ArrayList<String> benchMark = new ArrayList<String>() ; 
	


	
	@GetMapping("/api/loadData")
	public String loadData() throws NumberFormatException, IOException{

//		String s = "367028";
		Long L = lineDataRepository.count();
		
		benchMark.add(LocalTime.now().toString());

		Files.lines(Paths.get(INPUT_FILE_NAME)).skip(L).forEach( stringL -> {

			String[] splited = stringL.split(",");

			Date date = new Date(Long.parseLong(splited[0]));
//			System.out.print(date+"\r");
			
			Time time = new Time(Long.parseLong(splited[0]));
//			System.out.println(time);

			int value = Integer.parseInt(splited[1]);
//			System.out.println(i);

//			System.out.println(splited[2]);

			LineData oneLine = new LineData();

			oneLine.setDates(new Dates(date));
			oneLine.setTime(time);
			oneLine.setOrigineName(new Origine(splited[2]));
			oneLine.setValue(value);


			datesRepository.save(new Dates(date));
			origineRepository.save(new Origine(splited[2]));

			lineDataRepository.save(oneLine);
			
			benchMark.add(LocalTime.now().toString());
			if (benchMark.size() == 10000) {
				System.out.println(benchMark.get(0));
				System.out.println(benchMark.get(benchMark.size()-1));
				System.out.println(benchMark.size());
			}
			
		});
		
		benchMark.add(LocalTime.now().toString());

		System.out.println(benchMark);
		System.out.println("////////");
		
		return "completed";
	}

	
	
	
}
