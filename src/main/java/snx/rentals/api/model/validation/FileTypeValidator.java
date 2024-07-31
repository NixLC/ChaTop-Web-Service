package snx.rentals.api.model.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

public class FileTypeValidator implements ConstraintValidator<ValidFileType, MultipartFile> {
  private String[] allowedTypes;

  @Override
  public void initialize(ValidFileType constraintAnnotation) {
    this.allowedTypes = constraintAnnotation.types();
  }

  @Override
  public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
    String contentType = value.getContentType();
    final boolean result = Arrays.asList(allowedTypes).contains(contentType);
    if (!result) {
      String problemMessage = "Invalid file type '" + contentType + "'"
                            + ". Supported file types are " + Arrays.toString(allowedTypes);

      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(problemMessage).addConstraintViolation();
    }
    return result;
  }
}