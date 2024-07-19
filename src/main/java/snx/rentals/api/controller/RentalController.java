package snx.rentals.api.controller;

import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MultipartFile;
import snx.rentals.api.model.dto.DTO;
import snx.rentals.api.model.dto.RentalDto;
import snx.rentals.api.model.entity.Rental;
import snx.rentals.api.model.entity.User;
import snx.rentals.api.service.RentalService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/rentals")
public class RentalController extends GenericController<Rental> {
  public static final String NO_ID = "IdNotAllowed";
  private final RentalService rentals;

  private static final String UPLOAD_BASE_PATH = "src/main/resources/static";
  private static final String UPLOAD_WEB_URL = "/uploads/rentals/";
  private static final String UPLOAD_LOCAL_DIR = UPLOAD_BASE_PATH + UPLOAD_WEB_URL;

  public RentalController(RentalService service) {
    super(service);
    this.rentals = service;
  }

  @GetMapping("")
  public ResponseEntity<Map<String, List<DTO<Rental>>>> getSome(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size) {
    return ResponseEntity.ok(getPage(page, size));
  }

  @GetMapping("/{id}")
  HttpEntity<DTO<Rental>> getOne(@PathVariable Integer id) {
    return ResponseEntity.ok(get(id));
  }

  // Prevent id binding because JPA will update the entity if exists in the database
  // Prevent ownerId binding via request parameters because it's already set by @PathVariable
  @InitBinder("rentalDto")
  public void initBinder(WebDataBinder binder) {
    binder.setDisallowedFields("id");
    binder.setDisallowedFields("ownerId");
  }


  @PostMapping(path = "/{ownerId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  HttpEntity<Map<String, String>> createOne(@PathVariable Integer ownerId, @ModelAttribute RentalDto dto, @RequestParam("picture") MultipartFile picture) {
    if (!uploadDirIsWritable()) {
      throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Can't upload file");
    }
    try {
      User owner = rentals.findOwner(ownerId);
      Rental candidate = dto.toEntity();
      candidate.setOwner(owner);
      candidate = rentals.create(candidate);

      handleFileUpload(candidate, picture);
    }
    catch (EntityNotFoundException e) {
      final String errorMsg = "Cannot insert " + Rental.class.getSimpleName() + " because it violates some integrity constraints";
      throw new DataIntegrityViolationException(errorMsg, e);
    }
    catch (IOException e) {
      throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Can't upload file");
    }
    return ResponseEntity.ok(Collections.singletonMap("message", "Rental created"));
  }

  @PutMapping(path = "/{id}")
  HttpEntity<Map<String, String>> updateOne(@PathVariable Integer id, @ModelAttribute RentalDto dto) {
    Rental dbDomain = rentals.get(id);
    updateRental(dbDomain, dto);
    rentals.update(dbDomain);
    return ResponseEntity.ok(Collections.singletonMap("message", "Rental updated "));
  }

  private void updateRental(Rental existing, RentalDto newValues) {
    existing.setName(newValues.getName());
    existing.setSurface(newValues.getSurface());
    existing.setPrice(newValues.getPrice());
    existing.setDescription(newValues.getDescription());
  }

  private void handleFileUpload(Rental candidate, MultipartFile picture) throws IOException {
    // TODO : check file type or at least file extension to allow only pictures
    File rentalUploadDir = new File(UPLOAD_LOCAL_DIR + candidate.getId() + "/");
    Path localPath = writePictureFile(picture, rentalUploadDir);
    String webPath = localPath.toString().substring(UPLOAD_BASE_PATH.length());
    candidate.setPicture(webPath);
    rentals.update(candidate);
  }

  private boolean uploadDirIsWritable() {
    return new File(UPLOAD_LOCAL_DIR).canWrite();
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
