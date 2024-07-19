package snx.rentals.api.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import snx.rentals.api.model.dto.MessageDto;
import snx.rentals.api.model.entity.Message;
import snx.rentals.api.model.entity.Rental;
import snx.rentals.api.model.entity.User;
import snx.rentals.api.service.MessageService;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
public class MessageController extends GenericController<Message> {
  private final MessageService messages;

  public MessageController(MessageService service) {
    super(service);
    this.messages = service;
  }

  @PostMapping("")
  HttpEntity<Map<String, String>> post(@RequestBody MessageDto dto) {
    Message message = dto.toEntity();
    try {
      User user = messages.findUser(dto.getUserId());
      Rental rental = messages.findRental(dto.getRentalId());
      message.setUser(user);
      message.setRental(rental);
    }
    catch (EntityNotFoundException e) {
      final String errorMsg = "Cannot insert " + Message.class.getSimpleName() + " because it violates some integrity constraints";
      throw new DataIntegrityViolationException(errorMsg, e);
    }
      messages.create(message);
    return ResponseEntity.ok(Collections.singletonMap("message", "Message send with success"));
  }
}
