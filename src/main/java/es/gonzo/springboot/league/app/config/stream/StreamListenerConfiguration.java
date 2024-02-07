package es.gonzo.springboot.league.app.config.stream;

import es.gonzo.springboot.league.app.models.BidRequest;
import es.gonzo.springboot.league.app.services.BidService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Configuration
public class StreamListenerConfiguration {

    public static final String ROUTING_EVENT_TYPE = "event_type";

    @Bean
    public Consumer<Message<BidRequest>> createConsumer(final BidService bidService) {
        return bidService::createBidBy;
    }

    @Bean
    public Consumer<Message<BidRequest>> cancelConsumer(final BidService bidService) {
        return bidService::cancelBidBy;
    }
}
