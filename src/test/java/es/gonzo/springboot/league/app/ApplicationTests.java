package es.gonzo.springboot.league.app;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Application.class)
@AutoConfigureEmbeddedDatabase
class ApplicationTests {

	@Test
	void contextLoads() {
	}

}
