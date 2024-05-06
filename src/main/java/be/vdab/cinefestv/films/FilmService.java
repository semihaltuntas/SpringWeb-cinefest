package be.vdab.cinefestv.films;

import jdk.dynalink.linker.LinkerServices;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
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

    public List<Film> findAll(){
        return filmRepository.findAll();
    }
    public List<Film> findByJaar(int jaar){
        return filmRepository.findByJaar(jaar);
    }
    @Transactional
    void delete(long id){
        filmRepository.delete(id);
    }
    @Transactional
    long create(NieuweFilm nieuweFilm){
        var film= new Film(0,nieuweFilm.titel(), nieuweFilm.jaar(), 0, BigDecimal.ZERO);
        return filmRepository.create(film);
    }
    @Transactional
    void updateTitel(long id,String titel){
        filmRepository.updateTitel(id,titel);
    }
}
