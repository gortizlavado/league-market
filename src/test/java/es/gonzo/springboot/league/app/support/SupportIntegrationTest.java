package es.gonzo.springboot.league.app.support;

import es.gonzo.springboot.league.app.Application;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = Application.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureEmbeddedDatabase
public class SupportIntegrationTest {
}
