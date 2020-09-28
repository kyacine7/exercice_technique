package fr.arca_computing.exercice_technique.batch.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class JobCSVtoDBListener implements JobExecutionListener {

	@Override
	public void beforeJob(JobExecution jobExecution) {
		System.out.println("Before "+jobExecution.getJobInstance().getJobName()+" execution");
	}

	@Override
	public void afterJob(JobExecution jobExecution) {

		if(jobExecution.getStatus().equals(BatchStatus.COMPLETED)){
			System.out.println("Success!");
		}else{
			jobExecution.getExecutionContext().put("STATUS", "FAILED");
			System.out.println("Failed!");
		}
		
	}

}
