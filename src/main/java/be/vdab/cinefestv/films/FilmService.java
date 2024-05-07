package be.vdab.cinefestv.films;

import be.vdab.cinefestv.reservaties.NieuweReservatie;
import be.vdab.cinefestv.reservaties.Reservatie;
import be.vdab.cinefestv.reservaties.ReservatieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class FilmService {
    private final FilmRepository filmRepository;
    private final ReservatieRepository reservatieRepository;

    public FilmService(FilmRepository filmRepository, ReservatieRepository reservatieRepository) {
        this.filmRepository = filmRepository;
        this.reservatieRepository = reservatieRepository;
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
    @Transactional
    long reserveer(long filmId, NieuweReservatie nieuweReservatie) {
        var reservatie = new Reservatie(0,filmId, nieuweReservatie.emailAdres(),
                nieuweReservatie.plaatsen(), LocalDateTime.now());
        var film = filmRepository.findAndLockById(filmId)
                .orElseThrow(() -> new FilmNietGevondenException(filmId));
        film.reserveer(reservatie.getPlaatsen());
        filmRepository.updateVrijePlaatsen(filmId, film.getVrijePlaatsen());
        return reservatieRepository.create(reservatie);
    }
}
