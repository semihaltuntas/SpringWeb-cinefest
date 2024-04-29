package be.vdab.cinefestv.films;

import com.mysql.cj.jdbc.JdbcStatement;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Sql("/films.sql")
@AutoConfigureMockMvc
class FilmControllerTest {
    private final static String FILMS_TABLE = "films";
    private final MockMvc mockMvc;
    private final JdbcClient jdbcClient;

    public FilmControllerTest(MockMvc mockMvc, JdbcClient jdbcClient) {
        this.mockMvc = mockMvc;
        this.jdbcClient = jdbcClient;
    }

    @Test
    void findTotaalVanVrijeplaatsenVindtHetJuistAantalVrijePlaatse() throws Exception {
        Long vrijePlaatsen = jdbcClient.sql(
                        "select sum(vrijePlaatsen)from films")
                .query(Long.class)
                .single();
        System.out.println(vrijePlaatsen);
        mockMvc.perform(get("/films/totaalvrijeplaatsen"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$").value(vrijePlaatsen));
    }

    private long idVanTest1Film() {
        var sql = """
                select id from films where titel = 'test1'
                """;
        return jdbcClient.sql(sql)
                .query(Long.class)
                .single();
    }

    @Test
    void findByIdMetEenBestaandeIdVindtDeFilm() throws Exception {
        var id = idVanTest1Film();
        mockMvc.perform(get("/films/{id}", id))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("id").value(id),
                        jsonPath("titel").value("test1"),
                        jsonPath("jaar").value(2024));
    }

    @Test
    void findByIdMetEenOnbestaandeIdVindtDeFilm() throws Exception {
        mockMvc.perform(get("/films/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());

    }

    @Test
    void findAllVindtAlleFilms() throws Exception {
        System.out.println(JdbcTestUtils.countRowsInTable(jdbcClient, FILMS_TABLE));
        mockMvc.perform(get("/films"))
                .andExpectAll(status().isOk(),
                        jsonPath("length()")
                                .value(JdbcTestUtils.countRowsInTable(jdbcClient, FILMS_TABLE)));
    }
}