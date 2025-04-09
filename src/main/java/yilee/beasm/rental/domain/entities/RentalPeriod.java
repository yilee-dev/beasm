package yilee.beasm.rental.domain.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RentalPeriod {
    private LocalDate rentalStartDate;
    private LocalDate rentalEndDate;
}
