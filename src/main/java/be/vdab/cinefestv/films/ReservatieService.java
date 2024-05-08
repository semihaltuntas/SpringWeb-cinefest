package be.vdab.cinefestv.films;

import be.vdab.cinefestv.reservaties.ReservatieMetFilm;
import be.vdab.cinefestv.reservaties.ReservatieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ReservatieService {
    private final ReservatieRepository reservatieRepository;

    public ReservatieService(ReservatieRepository reservatieRepository) {
        this.reservatieRepository = reservatieRepository;
    }

    List<ReservatieMetFilm> findByEmailAdres(String emailAdres) {
        return reservatieRepository.findByEmail(emailAdres);
    }
}
