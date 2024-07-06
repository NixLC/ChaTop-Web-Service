package snx.rentals.api.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import snx.rentals.api.model.dto.DTO;
import snx.rentals.api.model.dto.WrapperDto;
import snx.rentals.api.model.entity.GenericEntity;
import snx.rentals.api.repository.GenericRepository;
import snx.rentals.api.service.GenericService;

import java.util.List;

public abstract class GenericController<T extends GenericEntity<T>> {
    private final GenericService<T> service;
    private final String collectionName;

    public GenericController(GenericRepository<T> repository) {
        this.service = new GenericService<>(repository) {};
        this.collectionName = repository.getCollectionName();
    }

    @GetMapping("")
    public ResponseEntity<WrapperDto<T>> getPage(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        List<DTO<T>> entities = service.getPage(pageable).map(GenericEntity::toDTO).toList();
        return ResponseEntity.ok(new WrapperDto<>(entities, collectionName));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DTO<T>> get(@PathVariable Integer id) {
        T entity = service.get(id);
        return ResponseEntity.ok(entity.toDTO());
    }

    @PutMapping("")
    public ResponseEntity<T> update(@RequestBody T updated){
        return ResponseEntity.ok(service.update(updated));
    }

    @PostMapping("")
    public ResponseEntity<T> create(@RequestBody T created){
        return ResponseEntity.ok(service.create(created));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.ok("Ok");
    }
}
