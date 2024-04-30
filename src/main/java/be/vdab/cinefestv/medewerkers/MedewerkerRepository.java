package be.vdab.cinefestv.medewerkers;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MedewerkerRepository {
    private final JdbcClient jdbcClient;

    public MedewerkerRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    List<Medewerker> findByStukVoornaamEnStukFamilienaam(String stukVoornaam, String stukFamilienaam) {
        var sql = """
                select id,voornaam,familienaam
                from medewerkers
                where voornaam like ? and familienaam like ?
                order by voornaam,familienaam
                """;
        return jdbcClient.sql(sql)
                .params("%" + stukVoornaam + "%", "%" + stukFamilienaam + "%")
                .query(Medewerker.class)
                .list();
    }

    public List<Medewerker> findAllMedewerker() {
        var sql = """
                select id,voornaam,familienaam
                from medewerkers
                order by id
                """;
        return jdbcClient.sql(sql)
                .query(Medewerker.class)
                .list();
    }
}
