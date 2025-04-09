package yilee.beasm.rental.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yilee.beasm.rental.domain.dtos.RentalPcDto;
import yilee.beasm.rental.domain.entities.RentalPc;
import yilee.beasm.rental.domain.entities.RentalPeriod;

public interface RentalPcService {

    RentalPcDto get(Long id);

    Long register(RentalPcDto dto);

    void modify(RentalPcDto dto);

    void delete(Long id);

    default RentalPc dtoToEntity(RentalPcDto dto) {
        return RentalPc.builder()
                .id(dto.getId())
                .code(dto.getCode())
                .type(dto.getType())
                .spec(dto.getSpec())
                .feeOfMonth(dto.getFeeOfMonth())
                .period(new RentalPeriod(dto.getRentalStartDate(), dto.getRentalEndDate()))
                .build();
    }

    default RentalPcDto entityToDto(RentalPc entity) {
        return RentalPcDto.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .type(entity.getType())
                .spec(entity.getSpec())
                .feeOfMonth(entity.getFeeOfMonth())
                .rentalStartDate(entity.getPeriod().getRentalStartDate())
                .rentalEndDate(entity.getPeriod().getRentalEndDate())
                .build();
    }
}
