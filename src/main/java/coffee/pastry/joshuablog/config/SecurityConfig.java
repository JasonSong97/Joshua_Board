package coffee.pastry.joshuablog.config;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import coffee.pastry.joshuablog.core.auth.MyUserDetails;
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
          http.headers().frameOptions().disable();
          http.formLogin()
                    .loginPage("/loginForm")
                    .loginProcessingUrl("/login")
                    .successHandler(((request, response, authentication) -> {
                         log.debug("디버그 : 로그인 성공");

                         MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
                         HttpSession session = request.getSession();
                         session.setAttribute("sessionUser", myUserDetails.getUser());

                         response.sendRedirect("/");
                    }))
                    .failureHandler(((request, response, exception) -> {
                         log.debug("디버그 : 로그인 실패 : " + exception.getMessage());
                         response.sendRedirect("/loginForm");
                    }));

          http.authorizeRequests(
                    authorize -> authorize.antMatchers("/s/**").authenticated()
                              .anyRequest().permitAll());

          return http.build();
     }
}
