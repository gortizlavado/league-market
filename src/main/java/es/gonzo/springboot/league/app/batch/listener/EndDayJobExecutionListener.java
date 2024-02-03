package es.gonzo.springboot.league.app.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@Component
@Slf4j
public class EndDayJobExecutionListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("Starting job: {} at {}", jobExecution.getJobConfigurationName(), LocalDateTime.now());
        final Date changeDate = jobExecution.getJobParameters().getDate("change_date", Date.from(Instant.now()));
        log.info("Date parameter from this job: {}", changeDate);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("Finished job: {} at {}", jobExecution.getJobConfigurationName(), LocalDateTime.now());
        if (jobExecution.getStatus() == BatchStatus.COMPLETED ) {
            log.info("Finished as COMPLETED");
        } else if (jobExecution.getStatus() == BatchStatus.FAILED) {
            log.info("Finished as FAILED");
        }
    }
}
