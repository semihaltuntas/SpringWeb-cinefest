package be.vdab.cinefestv.films;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class OnvoldoendePlaatsenException extends RuntimeException{
    public OnvoldoendePlaatsenException() {
        super("Onvoldoende vrije Plaatsen");
    }
}
