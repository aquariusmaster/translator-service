package com.karienomen.translator;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TranslatorApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext ctx = SpringApplication.run(TranslatorApplication.class, args);
		JobLauncher jobLauncher = ctx.getBean(JobLauncher.class);
		Job job = ctx.getBean("job", Job.class);
		JobParameters jobParameters = new JobParametersBuilder().toJobParameters();


		JobExecution jobExecution = null;
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
		BatchStatus batchStatus = jobExecution.getStatus();
	}
}
