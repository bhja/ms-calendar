package ai.bhja.calendar.service;

import ai.bhja.calendar.config.AzureConfig;
import ai.bhja.calendar.exception.AuthException;
import ai.bhja.calendar.model.OAuthModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Slf4j
public class AzureAuthService {

  private final AzureConfig config;
  private final OkHttpClient okHttpClient;
  private final ObjectMapper objectMapper;
  private final ConcurrentHashMap<String, OAuthModel> details = new ConcurrentHashMap<>();

  public AzureAuthService(final AzureConfig config, final OkHttpClient okHttpClient, final
  ObjectMapper objectMapper) {
    this.config = config;
    this.okHttpClient = okHttpClient;
    this.objectMapper = objectMapper;
  }

  public String getAuthUrl() {
    UriBuilder uriBuilder = UriComponentsBuilder.fromUriString(config.getAuthorizationUrl());

    uriBuilder.queryParams(queryParams("authorization", null));
    return uriBuilder.toUriString();
  }

  /**
   * Build query params for the request
   * @param type
   * @param value
   * @return
   */
  private MultiValueMap queryParams(String type, String value) {
    MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
    queryParams.set("client_id", config.getClientId());
    queryParams.set("client_secret", config.getClientSecret());
    queryParams.set("redirect_uri", config.getRedirectUri());

    switch (type) {
      case "authorization" -> {
        queryParams.set("response_type", "code");
        queryParams.set("scope", config.getScope());
      }
      case "code" -> {
        queryParams.set("grant_type", "authorization_code");
        queryParams.set("code", value);
      }
      case "refresh_token" -> {
        queryParams.set("grant_type", "refresh_token");
        queryParams.set("refresh_token", value);
      }
    }
    return queryParams;
  }

  /**
   * Exchange the code and retrieve the token
   *
   * @param code
   * @return
   */
  public OAuthModel authExchangeCode(String code) {
    //Exchange the code for token
    MultiValueMap<String, String> params = queryParams("code", code);
    return getToken(params);
  }

  /**
   * Returns the Oauth details based on the request.
   * @param queryParams
   * @return
   */
  public OAuthModel getToken(MultiValueMap<String, String> queryParams) {
    UriBuilder uriBuilder = UriComponentsBuilder.fromUriString(config.getTokenUrl());

    FormBody.Builder builder = new FormBody.Builder();
    queryParams.forEach((k, v) -> builder.add(k, v.stream().collect(Collectors.joining(","))));
    try {
      Call call =
          okHttpClient.newCall(
              new Request.Builder().url(uriBuilder.toUriString()).post(builder.build()).build());
      try (Response response = call.execute()) {
        if (response.isSuccessful()) {
          try (ResponseBody body = response.body()) {
            String resBody = body != null ? body.string() : "";
            return objectMapper.readValue(resBody, OAuthModel.class);
          }
        } else {
          throw new RuntimeException("Error " + response.code() + response.message());
        }
      }
    } catch (IOException e) {
      //Just for this one. Not the right way.
      log.error("Could not process the request {}", e.getMessage());
      throw new AuthException();

    }
  }

  /**
   * Refresh token is used to retrieve a new access token.
   * Depending on the use case. The expires in can be used to validate the expiry and a refresh can be invoked.
   *
   * @param refreshToken
   * @return
   */
  public OAuthModel refreshToken(String refreshToken) {
    return getToken(queryParams("refresh_token", refreshToken));
  }

}
