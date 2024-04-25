package be.vdab.cinefest.films;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class FilmRepository {
    private final JdbcClient jdbcClient;

    public FilmRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public long findTotaalVrijePlaatsen(){
        String sql = """
                select sum(vrijePlaatsen)as totaalVrijePlaatsen
                from films
                """;
        return jdbcClient.sql(sql)
                .query(Long.class)
                .single();
    }
}
