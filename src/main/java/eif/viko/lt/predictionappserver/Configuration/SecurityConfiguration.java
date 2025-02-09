package eif.viko.lt.predictionappserver.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;



// https://medium.com/@tericcabrel/implement-jwt-authentication-in-a-spring-boot-3-application-5839e4fd8fac
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthFilter jwtAuthenticationFilter;

    public SecurityConfiguration(
            JwtAuthFilter jwtAuthenticationFilter,
            AuthenticationProvider authenticationProvider
    ) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers("/auth/**").permitAll()  // Allow unauthenticated access to /auth/**
                                .requestMatchers("/swagger-ui/**").permitAll()  // Allow unauthenticated access to Swagger UI
                                .requestMatchers("/v3/api-docs/**").permitAll()  // Allow unauthenticated access to API docs
                                .requestMatchers("/v2/api-docs/**").permitAll()  // Allow unauthenticated access to API docs for Swagger 2
                        //.anyRequest().authenticated()               // Require authentication for any other requests
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // Use stateless session management
                )
                .authenticationProvider(authenticationProvider)                // Add your custom authentication provider
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);  // Add JWT filter

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Set allowed origins. For a desktop app, this could be a localhost address
        configuration.setAllowedOrigins(List.of("http://localhost:8005")); // Adjust if your desktop app has a different origin
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Include any methods your app may use
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type")); // Allow necessary headers
        configuration.setAllowCredentials(true); // Allows credentials (cookies, authorization headers) to be sent

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply CORS configuration to all endpoints

        return source;
    }
}