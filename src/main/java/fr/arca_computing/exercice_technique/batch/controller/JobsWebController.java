package fr.arca_computing.exercice_technique.batch.controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchEvent.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

	private ExecutorService asyncExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	Map<String,JobParameter> parameters = new HashMap<>();
	JobParameters jobParameters;

	boolean jobIsRunnig = false;
	
	@GetMapping("/api/loadData")
	public  Double loadData() throws IOException{
		parameters.put("time", new JobParameter(System.currentTimeMillis()));
		jobParameters = new JobParameters(parameters);

		if (!jobIsRunnig) {
			jobIsRunnig = true;
			asyncExecutor.execute(()->{
				runJob();
				jobIsRunnig = false;
			});
		}

		
		Long dbLines = lineDataRepository.count();
		Long fileLines = Files.lines(Paths.get("./src/main/resources/data-files/data.txt"), Charset.defaultCharset()).count();
		
		Double rate = (double) dbLines/fileLines*100;
		
		return rate;

	}


	@Async
	private void runJob() {

		try {
			jobExecution = jobLauncher.run(job, jobParameters);
		} catch (JobExecutionAlreadyRunningException e) {
			e.printStackTrace();
		} catch (JobRestartException e) {
			e.printStackTrace();
		} catch (JobInstanceAlreadyCompleteException e) {
			e.printStackTrace();
		} catch (JobParametersInvalidException e) {
			e.printStackTrace();
		}

		//while loop to REST PUT Processing progress

	}

}
