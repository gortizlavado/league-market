package es.gonzo.springboot.league.app;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class EndDayJobRunnerApplication {

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job endOfDay;

	public static void main(String[] args) {
		new SpringApplicationBuilder(EndDayJobRunnerApplication.class)
				.profiles("job")
				.run(args);
	}

	@Scheduled(cron = "0 3 * * * ?") // Every day at 3:00 AM
	public void perform() throws Exception {
		//JobParameters jobParameters = new JobParameters();
		jobLauncher.run(endOfDay, new JobParameters());
	}

}
