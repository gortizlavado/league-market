package es.gonzo.springboot.league.app.batch.reader;

import es.gonzo.springboot.league.app.entity.ChangeOwner;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ChangeOwnerRowMapper implements RowMapper<ChangeOwner> {

    public static final String ID_COLUMN = "id";
    public static final String PLAYER_COLUMN = "player_id";
    public static final String LAST_USER_COLUMN = "user_last_owner_id";
    public static final String NEW_USER_COLUMN = "user_new_owner_id";
    public static final String COMMUNITY_COLUMN = "community_id";

    @Override
    public ChangeOwner mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ChangeOwner.builder()
                .id((long) rs.getInt(ID_COLUMN))
                .idPlayer(rs.getObject(PLAYER_COLUMN, UUID.class))
                .idUserLastOwner(rs.getObject(LAST_USER_COLUMN, UUID.class))
                .idUserNewOwner(rs.getObject(NEW_USER_COLUMN, UUID.class))
                .idCommunity(rs.getObject(COMMUNITY_COLUMN, UUID.class))
                .build();
    }
}
