package coffee.pastry.joshuablog.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import coffee.pastry.joshuablog.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserApiController {

     private final UserService userService;

     @GetMapping("/api/user/{username}/sameUsername")
     public ResponseEntity<?> sameUsername(@PathVariable String username) {
          userService.유저네임중복체크(username);
          return ResponseEntity.ok().build();
     }
}
