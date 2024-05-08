package be.vdab.cinefestv.films;

import java.time.LocalDateTime;

public record ReservatieMetFilm(long id, String titel, int plaatsen, LocalDateTime besteld) {
}
