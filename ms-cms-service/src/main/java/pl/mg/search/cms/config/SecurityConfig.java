package pl.mg.search.cms.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.DelegatingJwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class SecurityConfig {

    @Order(1)
    @Bean
    public SecurityFilterChain actuatorHttpSecurity(HttpSecurity http) throws Exception {
        http
                .securityMatcher(request -> request.getServletPath().startsWith("/actuator/"))
                .authorizeHttpRequests((requests) -> requests
                        .anyRequest().permitAll()
                );
        return http.build();
    }

    @Bean
    public SecurityFilterChain apiSecurity(HttpSecurity http) throws Exception {

        DelegatingJwtGrantedAuthoritiesConverter authoritiesConverter =
                // Using the delegating converter multiple converters can be combined
                new DelegatingJwtGrantedAuthoritiesConverter(
                        // First add the default converter
                        new JwtGrantedAuthoritiesConverter(),
                        // Second add our custom Keycloak specific converter
                        new KeycloakJwtRolesConverter());

        http
                .securityMatcher(request -> request.getServletPath().startsWith("/api/"))
                .authorizeHttpRequests((exchanges) -> exchanges
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec
                        .jwt(jwtConfigurer -> jwtConfigurer
                                .jwtAuthenticationConverter(
                                        jwt -> new JwtAuthenticationToken(jwt, authoritiesConverter.convert(jwt))
                                )
                        ))
                .csrf(Customizer.withDefaults());
        return http.build();
    }
}
