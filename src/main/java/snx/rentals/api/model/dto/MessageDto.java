package snx.rentals.api.model.dto;

import lombok.Getter;
import lombok.Setter;
import snx.rentals.api.model.entity.Message;

@Getter
@Setter
public class MessageDto implements DTO<Message>{
  private Integer id;
  private Integer rentalId;
  private Integer userId;
  private String message;
  private String createdAt;
  private String updatedAt;

  @Override
  public Message toPartialEntity() {
    Message m = new Message();
    m.setMessage(message);
    return m;
  }
}
