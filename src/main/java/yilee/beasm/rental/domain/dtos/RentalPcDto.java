package yilee.beasm.rental.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import yilee.beasm.rental.domain.enums.PcSpec;
import yilee.beasm.rental.domain.enums.PcType;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalPcDto {
    private Long id;

    private String code;

    private PcType type;

    private PcSpec spec;

    private int feeOfMonth;

    private LocalDate rentalStartDate;

    private LocalDate rentalEndDate;
}
