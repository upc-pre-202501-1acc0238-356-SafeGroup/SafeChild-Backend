// src/main/java/com/upc/aventurape/platform/iam/infrastructure/authorization/sfs/configuration/WebSecurityConfiguration.java
package pe.edu.upc.center.platform.iam.infrastructure.authorization.sfs.configuration;

import pe.edu.upc.center.platform.iam.infrastructure.authorization.sfs.pipeline.BearerAuthorizationRequestFilter;
import pe.edu.upc.center.platform.iam.infrastructure.hashing.bcrypt.BCryptHashingService;
import pe.edu.upc.center.platform.iam.infrastructure.security.CustomUserDetailsService;
import pe.edu.upc.center.platform.iam.infrastructure.tokens.jwt.BearerTokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration {

  private final CustomUserDetailsService customUserDetailsService;
  private final BearerTokenService tokenService;
  private final BCryptHashingService hashingService;
  private final AuthenticationEntryPoint unauthorizedRequestHandler;

  @Bean
  public BearerAuthorizationRequestFilter authorizationRequestFilter() {
    return new BearerAuthorizationRequestFilter(tokenService, customUserDetailsService);
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    var authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(customUserDetailsService);
    authenticationProvider.setPasswordEncoder(hashingService);
    return authenticationProvider;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return hashingService;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors(corsConfigurer -> corsConfigurer.configurationSource(request -> {
      var cors = new CorsConfiguration();
      cors.setAllowedOrigins(List.of("*"));
      cors.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE"));
      cors.setAllowedHeaders(List.of("*"));
      return cors;
    }));
    http.csrf(csrfConfigurer -> csrfConfigurer.disable())
        .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(unauthorizedRequestHandler))
        .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            authorizeRequests -> authorizeRequests.requestMatchers(
                "/api/v1/authentication/**", "/v3/api-docs/**", "/swagger-ui.html",
                "/swagger-ui/**", "/swagger-resources/**", "/webjars/**", "/api/v1/profiles/**",
                "/api/v1/publications/**")
                .permitAll()
                .anyRequest()
                .authenticated());
    http.authenticationProvider(authenticationProvider());
    http.addFilterBefore(authorizationRequestFilter(), UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  public WebSecurityConfiguration(
      CustomUserDetailsService customUserDetailsService,
      BearerTokenService tokenService, BCryptHashingService hashingService,
      AuthenticationEntryPoint authenticationEntryPoint) {

    this.customUserDetailsService = customUserDetailsService;
    this.tokenService = tokenService;
    this.hashingService = hashingService;
    this.unauthorizedRequestHandler = authenticationEntryPoint;
  }
}