package yilee.beasm.rental.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yilee.beasm.rental.domain.dtos.RentalPcDto;
import yilee.beasm.rental.service.RentalPcService;

import java.util.Map;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class ApiRentalPcController {
    private final RentalPcService rentalPcService;

    @GetMapping("/pc/{id}")
    public ResponseEntity<RentalPcDto> getRentalPc(@PathVariable("id") Long id) {
        RentalPcDto rentalPcDto = rentalPcService.get(id);
        return ResponseEntity.status(HttpStatus.OK).body(rentalPcDto);
    }

    @PostMapping("/pc")
    public ResponseEntity<Long> postRentalPc(@RequestBody RentalPcDto dto) {
        Long register = rentalPcService.register(dto);
        return ResponseEntity.status(HttpStatus.OK).body(register);
    }
}
