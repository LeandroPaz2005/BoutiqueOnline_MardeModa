package BoutiqueOnline.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SeguridadConfiguracion {

    private final UserDetailsService userDetailsService;

    public SeguridadConfiguracion(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain FilterChain(HttpSecurity http) throws Exception{
    return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
            .anyRequest().permitAll()
            )
            .formLogin(login -> login
            .loginPage("/usuario/login")
            .defaultSuccessUrl("/", true)
                    .permitAll()
            )
            .logout(logout -> logout.permitAll())
            .build();
    }
    
    /*
    @Bean
    public DefaultSecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                //permisos para ADMIN
                .requestMatchers(
                        "/administrador/**",
                        "/administrador/panelAdmin",
                        "/productos/**").
                        hasRole("ADMIN")
                //permisos para USER  
                .requestMatchers(
                        "/usuario/inicio",
                        "/usuario/compras",
                        "/usuario/detalles/**",
                        "/usuario/cerrar",
                        "/order",
                        "/saveOrder",
                        "/getCart",
                        "/cart",
                        "/delete/cart/**")
                .hasRole("USER")
                //acceso libre(ambos roles y visitantes)
                .requestMatchers(
                        "/",
                       
                        "/usuario/**",
                        "/usuario/login",
                        "/usuario/registro",
                        "/usuario/procesarregistro",
                        "/productoHome/**",
                        "/buscar",
                        "/css/**",
                        "/js/**",
                        "/img/**",
                        "/images/**"
                ).permitAll()
                //todo lo demas requiere autenticacion
                .anyRequest().authenticated()
                )
                .formLogin(login -> login
                .loginPage("/usuario/login")
                .defaultSuccessUrl("/usuario/acceder", true)
                .permitAll()
                )
                .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                );

        return http.build();
    }
*/
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
