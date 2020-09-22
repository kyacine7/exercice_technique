package fr.arca_computing.exercice_technique;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import fr.arca_computing.exercice_technique.repository.LineDataRepository;

@SpringBootApplication
public class ExerciceTechniqueApplication implements CommandLineRunner {

	@Autowired
	private  LineDataRepository lineDataRepository;

	public static void main(String[] args) {
		SpringApplication.run(ExerciceTechniqueApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
//		String firstDayOfYear = "2008-12-13 23:50:11.641";
////		firstDayOfYear = firstDayOfYear.replaceAll("T", " ");
////		firstDayOfYear = firstDayOfYear.substring(0,firstDayOfYear.indexOf("+")) +"0000";
//
//		System.out.println(this.lineDataRepository.getOnInitData(firstDayOfYear).size());
	}

}
