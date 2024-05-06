package be.vdab.cinefestv.films;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

class FilmTest {
    @Test
    void reserveerVermindertDeVrijePlaatsen() {
        var film = new Film(0, "test", 2000, 2, BigDecimal.ONE);
        film.reserveer(2);
        assertThat(film.getVrijePlaatsen()).isEqualTo(0);
    }

    @Test
    void reserveerMisluktBijOnvoldoendeVrijePlaatsen() {
        var film = new Film(0, "test", 2000, 2, BigDecimal.ONE);
        assertThatExceptionOfType(OnvoldoendePlaatsenException.class).isThrownBy(
                () -> film.reserveer(3));
    }
}