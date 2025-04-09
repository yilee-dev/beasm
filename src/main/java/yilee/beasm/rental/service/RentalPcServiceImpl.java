package yilee.beasm.rental.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yilee.beasm.rental.domain.dtos.RentalPcDto;
import yilee.beasm.rental.domain.entities.RentalPc;
import yilee.beasm.rental.domain.entities.RentalPeriod;
import yilee.beasm.rental.repository.RentalPcRepository;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RentalPcServiceImpl implements RentalPcService {

    private final RentalPcRepository rentalPcRepository;

    @Override
    public RentalPcDto get(Long id) {
        RentalPc rentalPc = rentalPcRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Not found rentalPc"));

        return entityToDto(rentalPc);
    }

    @Override
    @Transactional
    public Long register(RentalPcDto dto) {
        RentalPc save = rentalPcRepository.save(dtoToEntity(dto));
        return save.getId();
    }

    @Override
    @Transactional
    public void modify(RentalPcDto dto) {
        RentalPc rentalPc = rentalPcRepository.findById(dto.getId())
                .orElseThrow(() -> new NoSuchElementException("Not found rentalPc"));
        rentalPc.changeCode(dto.getCode());
        rentalPc.changeType(dto.getType());
        rentalPc.changeSpec(dto.getSpec());
        rentalPc.changePeriod(new RentalPeriod(dto.getRentalStartDate(), dto.getRentalEndDate()));
        rentalPc.changeFeeOfMonth(dto.getFeeOfMonth());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        rentalPcRepository.deleteById(id);
    }
}
