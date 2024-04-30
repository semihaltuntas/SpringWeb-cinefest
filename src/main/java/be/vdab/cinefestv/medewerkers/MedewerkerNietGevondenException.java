package be.vdab.cinefestv.medewerkers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MedewerkerNietGevondenException extends RuntimeException {
    public MedewerkerNietGevondenException(long id) {
        super("Medewerker niet gevonden. id:  " + id);
    }
}
