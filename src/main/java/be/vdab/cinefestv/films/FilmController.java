package be.vdab.cinefestv.films;

import be.vdab.cinefestv.reservaties.NieuweReservatie;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@RestController
@RequestMapping("films")
public class FilmController {
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    private record IdTitelJaarVrijePlaatsen(Long id, String titel, int jaar, int vrijePlaatsen) {
        IdTitelJaarVrijePlaatsen(Film film) {
            this(film.getId(), film.getTitel(), film.getJaar(), film.getVrijePlaatsen());
        }
    }
    @GetMapping("totaalvrijeplaatsen")
    long totaalVrijePlaatsen() {
        return filmService.findTotaal();
    }

    @GetMapping("{id}")
    IdTitelJaarVrijePlaatsen findById(@PathVariable long id) {
        return filmService.findById(id)
                .map(film -> new IdTitelJaarVrijePlaatsen(film))
                .orElseThrow(() -> new FilmNietGevondenException(id));
    }

    @GetMapping
    Stream<IdTitelJaarVrijePlaatsen> findAllFilms() {
        return filmService.findAll()
                .stream()
                .map(film -> new IdTitelJaarVrijePlaatsen(film));
    }

    @GetMapping(params = "jaar")
    Stream<IdTitelJaarVrijePlaatsen> findByJaar(int jaar) {
        return filmService.findByJaar(jaar)
                .stream()
                .map(film -> new IdTitelJaarVrijePlaatsen(film));
    }

    @DeleteMapping("{id}")
    void deleteById(@PathVariable long id) {
        filmService.delete(id);
        System.out.println("Film is verwijderd id:" + id);
    }

    @PostMapping
    long create(@RequestBody @Valid NieuweFilm nieuweFilm) {
        var id = filmService.create(nieuweFilm);
        return id;
    }
    @PatchMapping("{id}/titel")
    void updateTitel(@PathVariable long id,
                     @RequestBody @NotBlank String titel){
        filmService.updateTitel(id,titel);
    }
    @PostMapping("{id}/reservaties")
    long reserveer(@PathVariable long id,
                   @RequestBody @Valid NieuweReservatie nieuweReservatie){
        return filmService.reserveer(id,nieuweReservatie);
    }
}
