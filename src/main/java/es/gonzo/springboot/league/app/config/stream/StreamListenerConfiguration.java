package es.gonzo.springboot.league.app.config.stream;

import es.gonzo.springboot.league.app.models.messaging.BidRequest;
import es.gonzo.springboot.league.app.services.BidService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Configuration
public class StreamListenerConfiguration {

    public static final String ROUTING_EVENT_TYPE = "event_type";
    public static final String BID_SINK = "functionRouter-in-0";
    public static final String COMMUNITY_SOURCE = "communityChannel-out-0";

    @Bean
    public Consumer<Message<BidRequest>> createConsumer(final BidService bidService) {
        return bidService::createBidBy;
    }

    @Bean
    public Consumer<Message<BidRequest>> cancelConsumer(final BidService bidService) {
        return bidService::cancelBidBy;
    }
}
