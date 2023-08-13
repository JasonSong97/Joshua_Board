package coffee.pastry.joshuablog.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import coffee.pastry.joshuablog.core.auth.MyUserDetails;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardController {

     @GetMapping({ "/", "/board" })
     public String main(@AuthenticationPrincipal MyUserDetails myUserDetails) {
          return "board/main";
     }
}
