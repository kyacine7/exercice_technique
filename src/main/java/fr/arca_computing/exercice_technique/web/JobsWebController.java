package fr.arca_computing.exercice_technique.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.arca_computing.exercice_technique.model.LineData;
import fr.arca_computing.exercice_technique.repository.LineDataRepository;


@RestController
public class JobsWebController {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job job;

	private JobExecution jobExecution;

	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private LineDataRepository lineDataRepository;


	Map<String,JobParameter> parameters = new HashMap<>();
	JobParameters jobParameters;

	@GetMapping("/api/loadData")
	public BatchStatus loadData() throws Exception{

		parameters.put("time", new JobParameter(System.currentTimeMillis()));
		jobParameters = new JobParameters(parameters);

		jobExecution = jobLauncher.run(job, jobParameters);

		return jobExecution.getStatus();
	}
	
	@GetMapping("/api/loadedData")
	public List<LineData> getCountryByName(){
		List<LineData> lineData =  lineDataRepository.findAll();
		return lineData;
	}

	
	
	
	@GetMapping("/api/stopLoading")
	private void stopLoadingJob() {

		String jobIdentifier =  "jobCSVtoDB";
		JobExecution jobExecution = jobRepository.getLastJobExecution(jobIdentifier,jobParameters);

		System.out.println(jobExecution.getAllFailureExceptions());
	}

}
