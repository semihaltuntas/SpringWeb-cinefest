package be.vdab.cinefestv.medewerkers;

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
@Sql("/medewerkers.sql")
@AutoConfigureMockMvc
class MedewerkerControllerTest {
    private final static String MEDEWERKERS_TABLE = "medewerkers";
    private final MockMvc mockMvc;
    private final JdbcClient jdbcClient;

    public MedewerkerControllerTest(MockMvc mockMvc, JdbcClient jdbcClient) {
        this.mockMvc = mockMvc;
        this.jdbcClient = jdbcClient;
    }

    @Test
    void findByStukVoornaamEnStukFamilienaamVindtJuisteMedewerkers()
            throws Exception {
        mockMvc.perform(get("/medewerkers")
                        .param("stukVoornaam", "testvoornaam")
                        .param("stukFamilienaam", "testfamilienaam"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("length()")
                                .value(JdbcTestUtils.countRowsInTableWhere(
                                        jdbcClient, MEDEWERKERS_TABLE,
                                        """
                                        voornaam like '%testvoornaam%' and
                                        familienaam like '%testfamilienaam%'
                                        """)));
    }
}
