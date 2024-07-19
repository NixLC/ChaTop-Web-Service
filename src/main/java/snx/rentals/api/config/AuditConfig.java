package snx.rentals.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// Add JPA's @CreatedDate & @LastModifiedDate support
@Configuration
@EnableJpaAuditing
public class AuditConfig {
}
