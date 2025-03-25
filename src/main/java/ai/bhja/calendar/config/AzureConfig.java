package ai.bhja.calendar.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "azure")
@Getter
@Setter
public class AzureConfig {

  private String tenantId;
  private String clientId;
  private String clientSecret;
  private String authorizationUrl;
  private String tokenUrl;
  private String scope;
  private String redirectUri;


}
