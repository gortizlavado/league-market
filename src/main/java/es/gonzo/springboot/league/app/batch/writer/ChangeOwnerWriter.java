package es.gonzo.springboot.league.app.batch.writer;

import es.gonzo.springboot.league.app.entity.ChangeOwner;
import es.gonzo.springboot.league.app.models.messaging.ChangeOwnerRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

import static es.gonzo.springboot.league.app.config.stream.StreamListenerConfiguration.COMMUNITY_SOURCE;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChangeOwnerWriter implements ItemWriter<ChangeOwner> {

    private final StreamBridge streamBridge;

    @Override
    public void write(List<? extends ChangeOwner> items) {
        log.info("there are: {}", items.size());
        items.stream()
            .map(item ->
                    ChangeOwnerRequest.builder()
                            .idCommunity(item.getIdCommunity().toString())
                            .idPlayer(item.getIdPlayer().toString())
                            .idUserLastOwner(item.getIdUserLastOwner().toString())
                            .idUserNewOwner(item.getIdUserNewOwner().toString())
                    .build())
            .forEach(coRequest -> {
                log.info("Request for community: {}", coRequest.getIdCommunity());
                log.info("[Player={}] From {} to {}", coRequest.getIdPlayer(), coRequest.getIdUserLastOwner(), coRequest.getIdUserNewOwner());
                streamBridge.send(COMMUNITY_SOURCE, MessageBuilder.withPayload(coRequest).build());
            });

    }
}
