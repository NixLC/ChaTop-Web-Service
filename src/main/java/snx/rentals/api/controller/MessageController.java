package snx.rentals.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import snx.rentals.api.model.dto.MessageDto;
import snx.rentals.api.model.entity.Message;
import snx.rentals.api.model.entity.Rental;
import snx.rentals.api.model.entity.User;
import snx.rentals.api.model.view.DtoViews;
import snx.rentals.api.service.MessageService;

import java.util.Collections;
import java.util.Map;


@RestController
@RequestMapping("/api/messages")
@JsonView({DtoViews.Write.class})
public class MessageController {
  private final MessageService messages;

  public MessageController(MessageService service) {
    this.messages = service;
  }

  @PostMapping("")
  HttpEntity<Map<String, String>> post(@Valid
                                       @JsonView(DtoViews.Write.class)
                                       @RequestBody MessageDto dto) {
    Message message = dto.toEntity();
    try {
      User user = messages.findUser(dto.getUserId());
      Rental rental = messages.findRental(dto.getRentalId());
      message.setUser(user);
      message.setRental(rental);
    }
    catch (EntityNotFoundException e) {
      final String errorMsg = "Cannot create " + Message.class.getSimpleName();
      throw new DataIntegrityViolationException(errorMsg, e);
    }
      messages.create(message);
    return ResponseEntity.ok(Collections.singletonMap("message", "Message send with success"));
  }
}
