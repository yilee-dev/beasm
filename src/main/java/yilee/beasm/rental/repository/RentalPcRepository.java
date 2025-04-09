package yilee.beasm.rental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yilee.beasm.rental.domain.entities.RentalPc;

public interface RentalPcRepository extends JpaRepository<RentalPc, Long> {
}
