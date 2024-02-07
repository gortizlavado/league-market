package es.gonzo.springboot.league.app.stream;

import es.gonzo.springboot.league.app.models.BidRequest;
import es.gonzo.springboot.league.app.support.SupportRabbitTestConfig;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest(properties = {
        "cloud.stream.default.consumer.autoStartup=false" //TODO is it works?
})
@ExtendWith(SpringExtension.class)
@AutoConfigureEmbeddedDatabase
@Import(SupportRabbitTestConfig.class)
class RabbitListenerConfigurationTest {

    private static final String QUEUE_NAME = "executedBid.market-microservice";
    private static final String EXCHANGE_NAME = "executedBid";

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    RabbitAdmin rabbitAdmin;

    @Test
    //TODO review how to improve the asserts
    void checkRabbitConfiguration() {
        Assertions.assertNotNull(rabbitAdmin.getQueueInfo(QUEUE_NAME));
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());

        BidRequest payload = BidRequest.builder()
                .idSale(1L)
                .idUserBid(UUID.randomUUID())
                .amount(BigDecimal.valueOf(1L)).build();

        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "#", payload);

        Message createMessage = rabbitTemplate.receive(QUEUE_NAME);
        Assertions.assertNotNull(createMessage);
    }

}