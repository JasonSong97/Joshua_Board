package coffee.pastry.joshuablog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class SecurityConfig {

     @Bean
     BCryptPasswordEncoder passwordEncoder() {
          return new BCryptPasswordEncoder();
     }

     @Bean // 권한 주소 설정
     SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
          http.csrf().disable();
          http.formLogin()
                    .loginPage("/loginForm")
                    .loginProcessingUrl("/login")
                    .successHandler(((request, response, authentication) -> {
                         log.debug("디버그 : 로그인 성공");
                    }))
                    .failureHandler(((request, response, exception) -> {
                         log.debug("디버그 : 로그인 실패 : " + exception.getMessage());
                    }));

          http.authorizeRequests(
                    authorize -> authorize.antMatchers("/s/**").authenticated()
                              .anyRequest().permitAll());

          return http.build();
     }
}
