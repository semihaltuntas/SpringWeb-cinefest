package be.vdab.cinefestv.reservaties;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record NieuweReservatie (@NotNull @Email String emailAdres, @Positive int plaatsen){

}
