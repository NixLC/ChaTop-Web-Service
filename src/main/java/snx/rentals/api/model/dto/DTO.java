package snx.rentals.api.model.dto;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public interface DTO<T> {
  DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");
  static String instantToString(Instant instant, DateTimeFormatter formatter) {
    return formatter.withZone(ZoneId.systemDefault())
                              .format(instant);
  }
  T toEntity();
}
