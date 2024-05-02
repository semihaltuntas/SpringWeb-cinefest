package be.vdab.cinefestv.films;

import com.mysql.cj.jdbc.JdbcStatement;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@Transactional
@Sql("/films.sql")
@AutoConfigureMockMvc
class FilmControllerTest {
    private final static String FILMS_TABLE = "films";
    private final static Path TEST_RESOURCES = Path.of("src/test/resources");
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

    @Test
    void findByJaarVindtDeJuisteFilms() throws Exception {
//      1 ->  System.out.println(JdbcTestUtils.countRowsInTableWhere(
//                jdbcClient, FILMS_TABLE, "jaar = '2024'"));
        mockMvc.perform(get("/films").param("jaar", "2024"))
                .andExpectAll(status().isOk(),
                        jsonPath("length()").value(JdbcTestUtils.countRowsInTableWhere(
                                jdbcClient, FILMS_TABLE, "jaar = '2024'")));
    }

    @Test
    void deleteByIdVindtJuisteFilm() throws Exception {
        var id = idVanTest1Film();
        mockMvc.perform(delete("/films/{id}", id))
                .andExpect(status().isOk());
        assertThat(JdbcTestUtils.countRowsInTableWhere(
                jdbcClient, FILMS_TABLE, "id= " + id)).isZero();
    }

    @Test
    void createVoegtDeFilm() throws Exception {
        var jsonData = Files.readString(TEST_RESOURCES.resolve("correcteFilm.json"));
        var responseBody = mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData))
                .andExpectAll(status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println("id van responseBody-> "+ responseBody);
        assertThat(JdbcTestUtils.countRowsInTableWhere(jdbcClient, FILMS_TABLE,
                "titel = 'test4' and id =" + responseBody)).isOne();
    }
}