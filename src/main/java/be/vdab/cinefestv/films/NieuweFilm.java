package be.vdab.cinefestv.films;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record NieuweFilm(@NotBlank String titel,@Positive int jaar) {
}
