package snx.rentals.api.repository;

import org.springframework.stereotype.Repository;
import snx.rentals.api.model.entity.Message;

@Repository
public interface MessageRepository extends GenericRepository<Message> {
  @Override
  default Class<Message> getEntityClass() { return Message.class; }
}
