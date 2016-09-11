package com.karienomen.translator;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.elasticsearch.bootstrap.Elasticsearch;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;


@SpringBootApplication
public class TranslatorApplication {

	private static ConfigurableApplicationContext ctx;

	public static void main(String[] args) {

		ctx = SpringApplication.run(TranslatorApplication.class, args);

		startJob();

		stopContainers();


	}

	public static void startJob(){
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

	public static void stopContainers(){

		try{

			JSch jsch=new JSch();

			String user = ctx.getEnvironment().getProperty("jsch.username");
			String host = ctx.getEnvironment().getProperty("jsch.hostname");
			String passwd = ctx.getEnvironment().getProperty("jsch.password");

			System.out.println("stopCont user:" + user + ", host: " + host);

			Session session=jsch.getSession(user, host, 22);
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.setPassword(passwd);

			session.connect(30000);   // making a connection with timeout.
			System.out.println("session connect");

			Channel channel = session.openChannel("exec");
			ChannelExec channelExec = (ChannelExec) channel;
			channelExec.setCommand("docker ps");
//			channelExec.setInputStream(null);
			channelExec.setErrStream(System.err);
			channelExec.setOutputStream(System.out);

			channelExec.connect(30000);
			System.out.println("channel connect");

			String elasticsearchID = null;
			String redisID = null;
			BufferedReader reader = new BufferedReader(new InputStreamReader(channelExec.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
				if (line.contains("elasticsearch")) {
					elasticsearchID = line.substring(0, 5);
					System.out.println("Elasticsearch: " + elasticsearchID);
				}else if (line.contains("redis")) {
					redisID = line.substring(0, 5);
					System.out.println("Redis: " + redisID);
				}
			}

			channelExec.disconnect();
			channel = session.openChannel("exec");

			if (elasticsearchID != null && redisID != null){
				channelExec = (ChannelExec) channel;
				channelExec.setCommand("docker stop " + elasticsearchID +
						"& docker stop " + redisID);
				channelExec.setErrStream(System.err);
				channelExec.setOutputStream(System.out);

				channelExec.connect(3000);
			}

			channelExec.disconnect();
			session.disconnect();
		}
		catch(Exception e){
			e.getStackTrace();
		}
	}
}
