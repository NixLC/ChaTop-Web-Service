package snx.rentals.api.model.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.*;

@Data
public class WrapperDto<T> {
  @JsonIgnore
  private final String propertyName;
  @JsonIgnore
  private final List<DTO<T>> dtoList;

  private final int currentPage;
  private final int totalPages;
  private final int pageSize;
  private final long totalElements;

  @JsonAnyGetter
  public Map<String, List<DTO<T>>> getContent() {
    return Collections.singletonMap(propertyName, dtoList);
  }

  public WrapperDto(Page<DTO<T>> dtoPage, String propertyName) {
    this.propertyName = propertyName;
    this.currentPage = dtoPage.getNumber() + 1;
    this.totalPages = dtoPage.getTotalPages();
    this.pageSize = dtoPage.getSize();
    this.totalElements = dtoPage.getTotalElements();
    this.dtoList = dtoPage.getContent();
  }
}
