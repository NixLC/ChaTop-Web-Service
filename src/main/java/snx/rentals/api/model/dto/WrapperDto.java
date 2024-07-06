package snx.rentals.api.model.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class WrapperDto<T> {
  @JsonIgnore
  private final List<DTO<T>> dtoList;

  @JsonIgnore
  private final String propertyName;

  @JsonAnyGetter
  public Map<String, List<DTO<T>>> getContent() {
    return Collections.singletonMap(propertyName, dtoList);
  }

  public WrapperDto(List<DTO<T>> dtoList, String propertyName) {
    this.propertyName = propertyName;
    this.dtoList = dtoList;
  }
}
