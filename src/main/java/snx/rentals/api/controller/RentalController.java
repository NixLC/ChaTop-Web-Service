package snx.rentals.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MultipartFile;
import snx.rentals.api.model.dto.DTO;
import snx.rentals.api.model.dto.RentalDto;
import snx.rentals.api.model.dto.WrapperDto;
import snx.rentals.api.model.entity.Rental;
import snx.rentals.api.model.entity.User;
import snx.rentals.api.model.validation.ValidFileType;
import snx.rentals.api.model.view.DtoViews;
import snx.rentals.api.service.RentalService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import static snx.rentals.api.config.SecurityConfig.RENTAL_UPLOAD_WEB;

@RestController
@RequestMapping("/api/rentals")
@Validated
public class RentalController extends GenericController<Rental> {
  private final RentalService rentals;
  @Value("${snx.app.upload_dir}")
  private String UPLOAD_ROOT_DIR;

  public RentalController(RentalService service) {
    super(service);
    this.rentals = service;
  }

  @GetMapping("")
  public ResponseEntity<WrapperDto<Rental>> getSome(
      @RequestParam(name = "page", defaultValue = "1", required = false) int page,
      @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
    return ResponseEntity.ok(getPage(page, size));
  }

  @GetMapping("/{id}")
  @JsonView(DtoViews.Read.class)
  HttpEntity<DTO<Rental>> getOne(@PathVariable Integer id) {
    return ResponseEntity.ok(get(id));
  }

  // Prevent id binding because JPA would try to update the entity instead of creating a new one if its id is found in the database
  // Prevent ownerId binding via request parameters because we shouldn't be able to modify a rental owner
  @InitBinder("rentalDto")
  public void initBinder(WebDataBinder binder) {
    binder.setDisallowedFields("id");
    binder.setDisallowedFields("ownerId");
  }

  @PostMapping(path = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  HttpEntity<Map<String, String>> createOne(@AuthenticationPrincipal User userDetails,
                                            @Valid
                                            @ModelAttribute RentalDto dto,
                                            @ValidFileType(types = { "image/jpeg", "image/png", "image/webp" })
                                            @RequestParam("picture") MultipartFile picture) {
    if (picture.isEmpty()) {
      throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "No file selected");
    }
    if (!uploadDirIsWritable()) {
      throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Can't upload file");
    }
    Rental candidate = dto.toEntity();
    try {
      candidate.setOwner(userDetails);
      candidate = rentals.create(candidate);
      handleFileUpload(candidate, picture);
    }
    catch (EntityNotFoundException e) {
      final String errorMsg = "Cannot create " + Rental.class.getSimpleName() + " because it violates some integrity constraints";
      throw new DataIntegrityViolationException(errorMsg, e);
    }
    catch (IOException e) {
      rentals.delete(candidate.getId());
      throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Can't upload file");
    }
    return ResponseEntity.ok(Collections.singletonMap("message", "Rental created"));
  }

  @PutMapping(path = "/{id}")
  HttpEntity<Map<String, String>> updateOne(@PathVariable Integer id,
                                            @Valid
                                            @ModelAttribute RentalDto dto) {
    Rental dbDomain = rentals.get(id);
    updateRental(dbDomain, dto);
    rentals.update(dbDomain);
    return ResponseEntity.ok(Collections.singletonMap("message", "Rental updated"));
  }

  private void updateRental(Rental existing, RentalDto newValues) {
    existing.setName(newValues.getName());
    existing.setSurface(newValues.getSurface());
    existing.setPrice(newValues.getPrice());
    existing.setDescription(newValues.getDescription());
  }

  private void handleFileUpload(Rental candidate, MultipartFile picture) throws IOException {
    File rentalUploadDir = new File(UPLOAD_ROOT_DIR + RENTAL_UPLOAD_WEB + "/" + candidate.getId() + "/");
    Path localPath = writePictureFile(picture, rentalUploadDir);
    String webPath = localPath.toString().substring(UPLOAD_ROOT_DIR.length());
    candidate.setPicture(webPath);
    rentals.update(candidate);
  }

  private boolean uploadDirIsWritable() {
    return new File(UPLOAD_ROOT_DIR).canWrite();
  }

  private Path writePictureFile(MultipartFile picture, File directory) throws IOException {
    if (!directory.exists()) {
      directory.mkdirs();
    }
    String extension = FilenameUtils.getExtension(picture.getOriginalFilename());
    String fileName = UUID.randomUUID().toString();
    String fullPath = extension == null || extension.isEmpty() ? directory + "/" + fileName
                                                               : directory + "/" + fileName + "." + extension;
    return Files.write(Path.of(fullPath), picture.getBytes());
  }
}
