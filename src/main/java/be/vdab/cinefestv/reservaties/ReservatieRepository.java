package be.vdab.cinefestv.reservaties;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public List<ReservatieMetFilm> findByEmail(String emailAdres) {
        var sql = """
                select reservaties.id,titel,emailAdres,plaatsen,besteld
                from reservaties inner join films on reservaties.filmId = films.id
                where emailAdres = ?
                order by id desc
                """;
        return jdbcClient.sql(sql)
                .param(emailAdres)
                .query(ReservatieMetFilm.class)
                .list();
    }

}
