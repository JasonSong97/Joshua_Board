package coffee.pastry.joshuablog.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/hello")
@Controller
public class HelloController {

     // user
     @GetMapping("/joinForm")
     public String joinForm() {
          return "user/joinForm";
     }

     @GetMapping("/loginForm")
     public String loginForm() {
          return "user/loginForm";
     }

     @GetMapping("/user/updateForm")
     public String userUpdateForm() {
          return "user/updateForm";
     }

     @GetMapping("/user/profileUpdateForm")
     public String profileUpdateForm() {
          return "user/profileUpdateForm";
     }

     // board
     @GetMapping({ "/", "/board" })
     public String main() {
          return "board/main";
     }

     @GetMapping("/board/{id}")
     public String boardDetail() {
          return "board/detail";
     }

     @GetMapping("/board/saveForm")
     public String boardSaveForm() {
          return "board/saveForm";
     }

     @GetMapping("/board/{id}/updateForm")
     public String boardUpdateForm() {
          return "board/updateForm";
     }
}