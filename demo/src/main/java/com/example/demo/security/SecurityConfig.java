package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthEntryPoint jwtAuthEntryPoint;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        /*
         * Se recomienda desactivar CSRF cuando se la comunicación se están manejando
         * páginas web
         * donde la comunicación entre la página y el servidor es mediante peticiones
         * HTTP
         */
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests -> requests
                        /* H2 */
                        .requestMatchers("/h2/**").permitAll()                        /*login */
                        .requestMatchers("/login", "/administrador/login", "/veterinario/login", "/auth/login").permitAll()

                        /* Mascota: solo veterinario y admin pueden modificar (PUT/POST/DELETE) */
                        .requestMatchers("/mascota/add", "/mascota/update/**", "/mascota/delete/**", "/mascota/deactivate/**").hasAnyAuthority("ADMIN", "VET")
                        .requestMatchers("/mascota/**").permitAll()

                        /* Cliente: solo veterinario y admin pueden modificar (PUT/POST/DELETE) */
                        .requestMatchers("/cliente/add", "/cliente/update/**", "/cliente/delete/**").hasAnyAuthority("ADMIN", "VET")
                        .requestMatchers("/cliente/**").permitAll()

                        /* Veterinario: solo admin puede modificar (PUT/POST/DELETE) */
                        .requestMatchers("/veterinario/add", "/veterinario/update/**", "/veterinario/delete/**", "/veterinario/deactivate/**").hasAuthority("ADMIN")
                        .requestMatchers("/veterinario/**").permitAll()


                        .requestMatchers("cliente/details").hasAuthority("CLIENTE")
                        .requestMatchers("administrador/details").hasAuthority("ADMIN")
                        .requestMatchers("veterinario/details").hasAuthority("VET")

                        .anyRequest().permitAll()
                )
                .exceptionHandling( exception -> exception.authenticationEntryPoint(jwtAuthEntryPoint));

               http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * Permite autenticar a los usuarios con usuario y contrasena
     * Al autenticar devuelve un onjeto Authentication que posteriormente se puede usar a traves de SecurityContextHolder
     * para obtener el usuario autenticado
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    
    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter();
    }

    


}