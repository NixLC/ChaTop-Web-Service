package snx.rentals.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import snx.rentals.api.model.dto.DTO;
import snx.rentals.api.model.entity.GenericEntity;
import snx.rentals.api.repository.GenericRepository;
import snx.rentals.api.service.GenericService;

public abstract class GenericController<T extends GenericEntity<T>> {
    private final GenericService<T> service;

    public GenericController(GenericRepository<T> repository) {
        this.service = new GenericService<>(repository) {
        };
    }

    @GetMapping("")
    public ResponseEntity<Page<T>> getPage(Pageable pageable){
        return ResponseEntity.ok(service.getPage(pageable));
    }

    protected ResponseEntity<T> getOne(Integer id){
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping("/{id}")
    public abstract ResponseEntity<DTO<T>> get(@PathVariable Integer id);

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
