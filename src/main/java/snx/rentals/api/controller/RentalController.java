package snx.rentals.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import snx.rentals.api.model.dto.DTO;
import snx.rentals.api.model.entity.Rental;
import snx.rentals.api.repository.RentalRepository;

@RestController
@RequestMapping("/api/rentals")
public class RentalController extends GenericController<Rental>{
    public RentalController(RentalRepository repository) {
        super(repository);
    }

    @Override
    public ResponseEntity<DTO<Rental>> get(Integer id) {
        return null;
    }
}
