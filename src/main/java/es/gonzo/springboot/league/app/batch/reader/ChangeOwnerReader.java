package es.gonzo.springboot.league.app.batch.reader;

import es.gonzo.springboot.league.app.entity.ChangeOwner;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class ChangeOwnerReader {

    @Bean
    public JdbcCursorItemReader<ChangeOwner> changeOwnerReaderItemReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<ChangeOwner>()
                .dataSource(dataSource)
                .name("ChangeOwnerReader")
                .sql("select ID, PLAYER_ID, USER_LAST_OWNER_ID, USER_NEW_OWNER_ID, COMMUNITY_ID from CHANGE_OWNER WHERE CHANGE_DATE = CURRENT_DATE")
                .rowMapper(new ChangeOwnerRowMapper())
                .build();

    }

}
