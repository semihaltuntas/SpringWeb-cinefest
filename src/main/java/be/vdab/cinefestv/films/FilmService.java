package be.vdab.cinefestv.films;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class FilmService {
    private final FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public long findTotaal() {
        return filmRepository.findTotaalVrijePlaatsen();
    }

    Optional<Film> findById(Long id){
        return filmRepository.findById(id);
    }
}
