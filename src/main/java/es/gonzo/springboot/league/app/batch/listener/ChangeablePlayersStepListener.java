package es.gonzo.springboot.league.app.batch.listener;


import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
@Slf4j
public class ChangeablePlayersStepListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("before Step: {}", stepExecution.getStepName());
        final Set<Map.Entry<String, Object>> entries = stepExecution.getExecutionContext().entrySet();
        log.info("entries: {}", entries);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("after Step");
        return stepExecution.getExitStatus();
    }
}
