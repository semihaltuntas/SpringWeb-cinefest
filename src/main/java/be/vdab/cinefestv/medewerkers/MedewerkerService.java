package be.vdab.cinefestv.medewerkers;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MedewerkerService {
    private final MedewerkerRepository medewerkerRepository;

    public MedewerkerService(MedewerkerRepository medewerkerRepository) {
        this.medewerkerRepository = medewerkerRepository;
    }

    public List<Medewerker> findByStukVoornaamEnStukFamilienaam(
            String stukVoornaam, String stukFamilienaam) {
        return medewerkerRepository.findByStukVoornaamEnStukFamilienaam(
                stukVoornaam, stukFamilienaam);
    }

    public List<Medewerker> findAllMedewerkers() {
        return medewerkerRepository.findAllMedewerker();
    }
}
