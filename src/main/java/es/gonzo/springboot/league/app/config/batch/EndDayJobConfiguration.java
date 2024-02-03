package es.gonzo.springboot.league.app.config.batch;

import es.gonzo.springboot.league.app.batch.listener.ChangeablePlayersStepListener;
import es.gonzo.springboot.league.app.batch.listener.EndDayJobExecutionListener;
import es.gonzo.springboot.league.app.batch.writer.ChangeOwnerWriter;
import es.gonzo.springboot.league.app.entity.ChangeOwner;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class EndDayJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job endOfDay(EndDayJobExecutionListener listener, ChangeablePlayersStepListener stepListener, JdbcCursorItemReader<ChangeOwner> changeOwnerReaderItemReader, ChangeOwnerWriter changeOwnerItemWriter) {
        return this.jobBuilderFactory.get("endOfDay")
                .listener(listener)
                //.validator()
                .start(fetchChangeablePlayersStep(stepListener, changeOwnerReaderItemReader, changeOwnerItemWriter))
                .build();
    }

    private Step fetchChangeablePlayersStep(ChangeablePlayersStepListener stepListener, JdbcCursorItemReader<ChangeOwner> changeOwnerReaderItemReader, ChangeOwnerWriter changeOwnerItemWriter) {
        return this.stepBuilderFactory.get("fetchChangeablePlayersStep")
                .<ChangeOwner, ChangeOwner>chunk(10)
                .reader(changeOwnerReaderItemReader)
                //.processor()
                .writer(changeOwnerItemWriter)
                .listener(stepListener)
                .build();
    }
}
