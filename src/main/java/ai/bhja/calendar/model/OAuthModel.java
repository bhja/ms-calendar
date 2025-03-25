package ai.bhja.calendar.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OAuthModel {

  @JsonProperty("token_type")
  private String tokenType;
  private String scope;
  @JsonProperty("access_token")
  private String accessToken;
  @JsonProperty("expires_in")
  private long expiresIn;
  @JsonProperty("ext_expires_in")
  private long extExpiresIn;
  @JsonProperty("refresh_token")
  private String refreshToken;
}
