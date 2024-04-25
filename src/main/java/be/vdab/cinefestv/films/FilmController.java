package be.vdab.cinefest.films;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilmController {
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }
    @GetMapping("film/totaalVrijePlaatsen")
    long totaalVrijePlaatsen(){
        return filmService.findTotaal();
    }
}
