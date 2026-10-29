package tn.saber.GestionEmployes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import tn.saber.GestionEmployes.security.JwtService;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final JwtService jwtService;

  public SecurityConfig(JwtService jwtService) {
    this.jwtService = jwtService;
  }


  // =====================================================
  // PASSWORD ENCODER
  // =====================================================

  @Bean
  public PasswordEncoder passwordEncoder() {

    return new BCryptPasswordEncoder();
  }


  // =====================================================
  // JWT DECODER
  // =====================================================

  @Bean
  public JwtDecoder jwtDecoder() {

    return NimbusJwtDecoder
      .withSecretKey(jwtService.getKey())
      .build();
  }


  // =====================================================
  // JWT AUTHENTICATION CONVERTER
  // =====================================================

  @Bean
  public JwtAuthenticationConverter jwtAuthenticationConverter() {

    JwtAuthenticationConverter converter =
      new JwtAuthenticationConverter();

    converter.setJwtGrantedAuthoritiesConverter(
      jwt -> {

        String role = jwt.getClaimAsString("role");

        if (role == null) {
          return List.of();
        }

        return List.of(
          new org.springframework.security.core.authority
            .SimpleGrantedAuthority(
            "ROLE_" + role
          )
        );
      }
    );

    return converter;
  }


  // =====================================================
  // CORS
  // =====================================================

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {

    CorsConfiguration configuration =
      new CorsConfiguration();

    configuration.setAllowedOrigins(
      List.of("http://localhost:4200")
    );

    configuration.setAllowedMethods(
      List.of(
        "GET",
        "POST",
        "PUT",
        "DELETE",
        "OPTIONS"
      )
    );

    configuration.setAllowedHeaders(
      List.of(
        "Authorization",
        "Content-Type",
        "Accept"
      )
    );

    configuration.setExposedHeaders(
      List.of("Authorization")
    );

    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source =
      new UrlBasedCorsConfigurationSource();

    source.registerCorsConfiguration(
      "/**",
      configuration
    );

    return source;
  }


  // =====================================================
  // SECURITY
  // =====================================================

  @Bean
  public SecurityFilterChain securityFilterChain(
    HttpSecurity http) throws Exception {

    http

      // CORS
      .cors(cors -> {})

      // CSRF
      .csrf(csrf -> csrf.disable())

      // STATELESS
      .sessionManagement(session ->
        session.sessionCreationPolicy(
          SessionCreationPolicy.STATELESS
        )
      )

      // AUTHORIZATION
      .authorizeHttpRequests(auth -> auth

        // ---------------------------------
        // AUTH PUBLIC
        // ---------------------------------

        .requestMatchers(
          "/api/auth/register",
          "/api/auth/login"
        ).permitAll()


        // ---------------------------------
        // EMPLOYEE READ
        // ADMIN + USER
        // ---------------------------------

        .requestMatchers(
          org.springframework.http.HttpMethod.GET,
          "/api/employees/**"
        ).hasAnyRole(
          "ADMIN",
          "USER"
        )


        // ---------------------------------
        // EMPLOYEE CREATE
        // ADMIN ONLY
        // ---------------------------------

        .requestMatchers(
          org.springframework.http.HttpMethod.POST,
          "/api/employees/**"
        ).hasRole("ADMIN")


        // ---------------------------------
        // EMPLOYEE UPDATE
        // ADMIN ONLY
        // ---------------------------------

        .requestMatchers(
          org.springframework.http.HttpMethod.PUT,
          "/api/employees/**"
        ).hasRole("ADMIN")


        // ---------------------------------
        // EMPLOYEE DELETE
        // ADMIN ONLY
        // ---------------------------------

        .requestMatchers(
          org.springframework.http.HttpMethod.DELETE,
          "/api/employees/**"
        ).hasRole("ADMIN")

        // Department : consultation ADMIN + USER
        .requestMatchers(
          org.springframework.http.HttpMethod.GET,
          "/api/departments",
          "/api/departments/**"
        ).hasAnyRole("ADMIN", "USER")

        // Department : écriture ADMIN uniquement
        .requestMatchers(
          org.springframework.http.HttpMethod.POST,
          "/api/departments",
          "/api/departments/**"
        ).hasRole("ADMIN")

        .requestMatchers(
          org.springframework.http.HttpMethod.PUT,
          "/api/departments",
          "/api/departments/**"
        ).hasRole("ADMIN")

        .requestMatchers(
          org.springframework.http.HttpMethod.DELETE,
          "/api/departments",
          "/api/departments/**"
        ).hasRole("ADMIN")

        // ---------------------------------
        // O// THER ROUTES
        // ---------------------------------

        .anyRequest().authenticated()
      )


      // ---------------------------------
      // JWT
      // ---------------------------------

      .oauth2ResourceServer(oauth2 ->
        oauth2.jwt(jwt ->
          jwt.jwtAuthenticationConverter(
            jwtAuthenticationConverter()
          )
        )
      );

    return http.build();
  }
}
