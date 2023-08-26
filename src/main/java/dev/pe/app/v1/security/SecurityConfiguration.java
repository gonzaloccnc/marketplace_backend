package dev.pe.app.v1.security;

import dev.pe.app.v1.domain.handlers.BearerAccessDeniedHandler;
import dev.pe.app.v1.domain.handlers.BearerAuthenticationHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;
  private final BearerAuthenticationHandler bearerAuthenticationHandler;
  private final BearerAccessDeniedHandler bearerAccessDeniedHandler;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> {
          auth.requestMatchers(HttpMethod.GET,"/products/**", "/images/**", "/docs/**", "/swagger-ui/**").permitAll();
          auth.requestMatchers("/auth/**").permitAll();
          auth.anyRequest().authenticated();
        })
        .exceptionHandling(ex -> {
          //ex.authenticationEntryPoint(bearerAuthenticationHandler);
          //ex.accessDeniedHandler(bearerAccessDeniedHandler);
        })
        .sessionManagement(session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

}
