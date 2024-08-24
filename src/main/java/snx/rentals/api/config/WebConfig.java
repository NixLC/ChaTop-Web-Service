package snx.rentals.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static snx.rentals.api.config.SecurityConfig.RENTAL_UPLOAD_WEB;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Value("${snx.app.upload_dir}")
  private String UPLOAD_ROOT_DIR;

  // Map filesystem directory to web location
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler(RENTAL_UPLOAD_WEB + "/**")
            .addResourceLocations("file:" + UPLOAD_ROOT_DIR + RENTAL_UPLOAD_WEB +"/");
  }
}
