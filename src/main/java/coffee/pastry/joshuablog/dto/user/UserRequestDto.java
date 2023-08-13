package coffee.pastry.joshuablog.dto.user;

import coffee.pastry.joshuablog.model.user.User;
import lombok.Getter;
import lombok.Setter;

public class UserRequestDto {

     @Getter
     @Setter
     public static class JoinInDto {

          private String username;
          private String password;
          private String email;

          public User toEntity() {
               return User.builder()
                         .username(username)
                         .password(password)
                         .email(email)
                         .role("USER") // ENUM(check)
                         .status(true)
                         .profile("person.png")
                         .build();
          }
     }
}
