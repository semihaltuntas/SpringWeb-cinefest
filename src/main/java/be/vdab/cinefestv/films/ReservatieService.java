package be.vdab.cinefestv.films;

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
