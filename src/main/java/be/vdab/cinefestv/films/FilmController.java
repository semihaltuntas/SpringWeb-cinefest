package be.vdab.cinefestv.films;

import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@RestController
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

    @GetMapping("films/totaalvrijeplaatsen")
    long totaalVrijePlaatsen() {
        return filmService.findTotaal();
    }

    @GetMapping("films/{id}")
    IdTitelJaarVrijePlaatsen findById(@PathVariable long id) {
        return filmService.findById(id)
                .map(film -> new IdTitelJaarVrijePlaatsen(film))
                .orElseThrow(() -> new FilmNietGevondenException(id));
    }

    @GetMapping("films")
    Stream<IdTitelJaarVrijePlaatsen> findAllFilms() {
        return filmService.findAll()
                .stream()
                .map(film -> new IdTitelJaarVrijePlaatsen(film));
    }

    @GetMapping(value = "films", params = "jaar")
    Stream<IdTitelJaarVrijePlaatsen> findByJaar(int jaar) {
        return filmService.findByJaar(jaar)
                .stream()
                .map(film -> new IdTitelJaarVrijePlaatsen(film));
    }

    @DeleteMapping("films/{id}")
    void deleteById(@PathVariable long id) {
        filmService.delete(id);
        System.out.println("Film is verwijderd id:" + id);
    }

    @PostMapping("films")
    long create(@RequestBody NieuweFilm nieuweFilm) {
        var id = filmService.create(nieuweFilm);
        return id;
    }
}
