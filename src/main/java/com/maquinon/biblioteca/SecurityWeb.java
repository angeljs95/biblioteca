package com.maquinon.biblioteca;


import com.maquinon.biblioteca.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityWeb {

    @Autowired
    public UsuarioService usuarioService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioService).passwordEncoder(new BCryptPasswordEncoder());
    }

    /*
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .anyRequest().permitAll()
                        .and().formLogin()
                        .loginPage("/login")
                        .loginProcessingUrl("/logincheck")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/inicio")
                        .permitAll()
                        .and().logout()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/").
                        permitAll()
                )
                .httpBasic(withDefaults());
        return http.build();
    }*/

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/admin/*")
                        .hasRole("ADMIN")
                        .anyRequest()

                        .permitAll())
                .formLogin(
                        formLogin-> formLogin
                                .loginPage("/login")
                                .loginProcessingUrl("/logincheck")
                                .usernameParameter("email")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/inicio")
                                .permitAll())
                .logout( logout-> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login").
                        permitAll() )
              //  .httpBasic(withDefaults())
                .csrf(csrf->csrf.disable())
              .httpBasic(withDefaults());
        return http.build();
    }




    }

