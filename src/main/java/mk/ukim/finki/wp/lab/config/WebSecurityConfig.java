package mk.ukim.finki.wp.lab.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder; // Import this
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    // 1. ОТСТРАНЕТО: Не го инјектирајте PasswordEncoder преку конструктор ако не е дефиниран
    // public WebSecurityConfig(PasswordEncoder passwordEncoder) { ... }

    // 2. НОВО: Дефинирајте го PasswordEncoder тука
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/", "/home", "/assets/**", "/register", "/books", "/bookReservation", "/authors", "/authors/**").permitAll()
                        // 3. ПОПРАВЕНО: Додаден е '/' пред патеките за edit, delete и form
                        .requestMatchers("/admin/**", "/books/add", "/books/edit/**", "/books/delete/**", "/books/book-form/**").hasRole("ADMIN")
                        // 4. ПРЕПОРАКА: Сè друго бара најава
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .permitAll()
                        .failureUrl("/login?error=true")
                        .defaultSuccessUrl("/books", true)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/login")
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Бидејќи сега passwordEncoder е метод во истата класа, го повикуваме директно
        UserDetails user = User.builder()
                .username("username")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin123"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}