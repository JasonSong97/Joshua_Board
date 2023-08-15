package coffee.pastry.joshuablog.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import coffee.pastry.joshuablog.core.auth.MyUserDetails;
import coffee.pastry.joshuablog.core.exception.ssr.Exception400;
import coffee.pastry.joshuablog.core.exception.ssr.Exception403;
import coffee.pastry.joshuablog.dto.user.UserRequestDto;
import coffee.pastry.joshuablog.model.user.User;
import coffee.pastry.joshuablog.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {

     private final UserService userService;
     private final HttpSession session;

     @PostMapping("/join")
     public String join(@Valid UserRequestDto.JoinInDto joinInDto, Errors errors) {
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

     @GetMapping("/s/user/{id}/updateProfileForm")
     public String profileUpdateForm(@PathVariable Long id, Model model,
               @AuthenticationPrincipal MyUserDetails myUserDetails) {
          if (id != myUserDetails.getUser().getId()) {
               throw new Exception403("권한이 없습니다. ");
          }

          User userPS = userService.회원프로필보기(id);
          model.addAttribute("user", userPS);
          return "user/profileUpdateForm";
     }

     @PostMapping("/s/user/{id}/updateProfile")
     public String profileUpdate(
               @PathVariable Long id,
               MultipartFile profile,
               @AuthenticationPrincipal MyUserDetails myUserDetails) {
          if (id != myUserDetails.getUser().getId()) {
               throw new Exception403("권한이 없습니다");
          }

          if (profile.isEmpty()) {
               throw new Exception400("profile", "사진이 전송되지 않았습니다");
          }

          User userPS = userService.프로필사진수정(profile, id);

          myUserDetails.setUser(userPS);
          session.setAttribute("sessionUser", userPS);
          return "redirect:/";
     }
}
