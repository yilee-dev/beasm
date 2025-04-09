package yilee.beasm.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import yilee.beasm.security.filter.JwtCheckFilter;
import yilee.beasm.security.handler.ApiLoginFailHandler;
import yilee.beasm.security.handler.ApiLoginSuccessHandler;
import yilee.beasm.security.handler.CustomAccessDeniedHandler;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class CustomSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/api/members/login")
                        .successHandler(new ApiLoginSuccessHandler())
                        .failureHandler(new ApiLoginFailHandler())
                        .permitAll())
                .exceptionHandling(config -> config.accessDeniedHandler(new CustomAccessDeniedHandler()))
                .addFilterBefore(new JwtCheckFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(config -> config.configurationSource(corsConfigurationSource()));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Cache-Control"));
        configuration.setAllowedMethods(List.of("POST", "GET", "PUT", "DELETE", "HEAD", "OPTIONS"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", configuration);
        return urlBasedCorsConfigurationSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
