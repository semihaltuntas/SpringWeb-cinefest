package be.vdab.cinefestv.films;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FilmRepository {
    private final JdbcClient jdbcClient;

    public FilmRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public long findTotaalVrijePlaatsen() {
        String sql = """
                select sum(vrijePlaatsen)as totaalVrijePlaatsen
                from films
                """;
        return jdbcClient.sql(sql)
                .query(Long.class)
                .single();
    }

    public Optional<Film> findById(Long id) {
        var sql = """
                select id,titel,jaar,vrijePlaatsen,aankoopprijs
                from films
                where id = ?
                """;
        return jdbcClient.sql(sql)
                .param(id)
                .query(Film.class)
                .optional();
    }

    public List<Film> findAll() {
        var sql = """
                select id,titel,jaar,vrijePlaatsen,aankoopprijs
                from films
                order by titel
                """;
        return jdbcClient.sql(sql)
                .query(Film.class)
                .list();
    }

    public List<Film> findByJaar(int jaar) {
        var sql = """
                select id,titel,jaar,vrijePlaatsen,aankoopprijs
                from films
                where jaar = ?
                order by titel
                """;
        return jdbcClient.sql(sql)
                .param(jaar)
                .query(Film.class)
                .list();
    }

    void delete(long id) {
        var sql = """
                delete from films
                where id = ?
                """;
        jdbcClient.sql(sql)
                .param(id)
                .update();
    }
}
