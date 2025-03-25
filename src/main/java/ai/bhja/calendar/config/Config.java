package ai.bhja.calendar.config;

import java.time.Duration;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

  @Bean
  public OkHttpClient okHttpClient(@Value("${calendar" +
          ".connection-timeout:8}") String connectionTimeout,
      @Value("${calendar.read-timeout:8}") String readTimeout) {
    return new OkHttpClient.Builder().connectTimeout(
            Duration.ofSeconds(Long.valueOf(connectionTimeout)))
        .readTimeout(Duration.ofSeconds(Long.valueOf(readTimeout))).build();
  }
}
