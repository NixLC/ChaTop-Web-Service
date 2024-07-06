package snx.rentals.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import snx.rentals.api.model.entity.Message;
import snx.rentals.api.repository.MessageRepository;

@RestController
@RequestMapping("/api/messages")
public class MessageController extends GenericController<Message> {
  public MessageController(MessageRepository repository) {
    super(repository);
  }
}
