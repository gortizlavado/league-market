package es.gonzo.springboot.league.app.support;

import es.gonzo.springboot.league.app.Application;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = Application.class)
@DataJpaTest
@AutoConfigureEmbeddedDatabase
public class SupportUnitTest {
}
