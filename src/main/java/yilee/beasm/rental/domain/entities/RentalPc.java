package yilee.beasm.rental.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yilee.beasm.rental.domain.enums.PcSpec;
import yilee.beasm.rental.domain.enums.PcType;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RentalPc {
    @Id @GeneratedValue
    private Long id;

    private String code;

    @Enumerated(EnumType.STRING)
    private PcType type;

    @Enumerated(EnumType.STRING)
    private PcSpec spec;

    private int feeOfMonth;

    @Embedded
    private RentalPeriod period;

    public void changeCode(String code) {
        this.code = code;
    }

    public void changeType(PcType type) {
        this.type = type;
    }

    public void changeSpec(PcSpec spec) {
        this.spec = spec;
    }

    public void changeFeeOfMonth(int feeOfMonth) {
        this.feeOfMonth = feeOfMonth;
    }

    public void changePeriod(RentalPeriod period) {
        this.period = period;
    }
}
