package be.vdab.cinefestv.medewerkers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Stream;

@RestController
public class MedewerkerController {
    private final MedewerkerService medewerkerService;

    public MedewerkerController(MedewerkerService medewerkerService) {
        this.medewerkerService = medewerkerService;
    }

    @GetMapping(value = "medewerkers", params = {"stukVoornaam", "stukFamilienaam"})
    List<Medewerker> findByStukVoornaamEnStukFamilienaam(
            String stukVoornaam, String stukFamilienaam) {
        return medewerkerService.findByStukVoornaamEnStukFamilienaam(
                stukVoornaam, stukFamilienaam);
    }
    @GetMapping("medewerkers")
    List<Medewerker> findAllMedewerkers(){
        return medewerkerService.findAllMedewerkers()
                .stream()
                .toList();
    }
}
