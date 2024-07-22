package snx.rentals.api.model.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

public class UrlSerializer extends JsonSerializer<String> {
  @Value("${snx.app.host}")
  private String host;
  @Value("${server.port}")
  private String serverPort;
  @Value("${snx.app.protocol}")
  private String protocol;
  @Bean
  public UrlValidator urlValidator() { return new UrlValidator(); }

  @Override
  public void serialize(String value, JsonGenerator gen, SerializerProvider provider) throws IOException {
    if(value != null) {
      if(urlValidator().isValid(value)) {
        gen.writeString(value);
      }
      else {
        gen.writeString(protocol + "://" + host + ":" + serverPort + value);
      }
    }
    else {
      gen.writeNull();
    }
  }

  @Override
  public Class<String> handledType() {
    return String.class;
  }
}
