package coffee.pastry.joshuablog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import coffee.pastry.joshuablog.dto.user.UserRequestDto;
import coffee.pastry.joshuablog.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {

     private final UserService userService;

     @PostMapping("/join")
     public String join(UserRequestDto.JoinInDto joinInDto) {
          userService.회원가입(joinInDto);
          return "redirect:/loginForm";
     }

     @GetMapping("/joinForm")
     public String joinForm() {
          return "user/joinForm";
     }

     @GetMapping("/loginForm")
     public String loginForm() {
          return "user/loginForm";
     }
}
