package com.sh.lulu.auth.config;

import com.sh.lulu.auth.security.JwtAccessDeniedHandler;
import com.sh.lulu.auth.security.JwtAuthenticationEntryPoint;
import com.sh.lulu.auth.security.jwt.JWTConfigurer;
import com.sh.lulu.auth.security.jwt.TokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import java.util.Optional;
import java.util.Random;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@AllArgsConstructor
public class WebSecurityConfig implements WebSecurityCustomizer {

   private final TokenProvider tokenProvider;
   private final CorsFilter corsFilter;
   private final JwtAuthenticationEntryPoint authenticationErrorHandler;
   private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }


   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      http
              // we don't need CSRF because our token is invulnerable
              .csrf().disable()

              .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

              .exceptionHandling()
              .authenticationEntryPoint(authenticationErrorHandler)
              .accessDeniedHandler(jwtAccessDeniedHandler)

              // create no session
              .and()
              .sessionManagement()
              .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

              .and()
              .authorizeRequests()
              .antMatchers("/auth/authenticate","/auth/extend").permitAll()
              // .antMatchers("/auth/register").permitAll()
              // .antMatchers("/auth/activate").permitAll()
              // .antMatchers("/auth/account/reset-password/init").permitAll()
              // .antMatchers("/auth/account/reset-password/finish").permitAll()

              .anyRequest().authenticated()
              .and()
              .apply(securityConfigurerAdapter());
      return http.build();
   }

   private JWTConfigurer securityConfigurerAdapter() {
      return new JWTConfigurer(tokenProvider);
   }

   @Override
   public void customize(WebSecurity web) {
      web.ignoring()
              .antMatchers(HttpMethod.OPTIONS, "/**");
   }


   @Configuration
   public class AuditorConfig implements AuditorAware<Integer> {

      @Override
      public Optional<Integer> getCurrentAuditor() {
         return Optional.of(new Random().nextInt(1000));
      }

   }
}
