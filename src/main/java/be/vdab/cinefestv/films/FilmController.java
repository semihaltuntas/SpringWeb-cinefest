package be.vdab.cinefestv.films;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilmController {
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }
    @GetMapping("films/totaalvrijeplaatsen")
    long totaalVrijePlaatsen(){
        return filmService.findTotaal();
    }
    @GetMapping("films/{id}")
    Film findById(@PathVariable long id){
        return filmService.findById(id)
                .orElseThrow(()->new FilmNietGevondenException(id));
    }
}
