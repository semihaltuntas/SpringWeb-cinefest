package be.vdab.cinefestv.films;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ReservatieRepository {
    private final JdbcClient jdbcClient;

    public ReservatieRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public long create(Reservatie reservatie) {
        var sql = """
                insert into reservaties(filmId,emailAdres,plaatsen,besteld)
                values (?,?,?,?)
                """;
        var keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql(sql)
                .params(reservatie.getFilmId(), reservatie.getEmailAdres(),
                        reservatie.getPlaatsen(), reservatie.getBesteld())
                .update(keyHolder);
        return keyHolder.getKey().longValue();
    }
}
