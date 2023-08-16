package coffee.pastry.joshuablog.dto.user;

import javax.validation.constraints.NotEmpty;

import coffee.pastry.joshuablog.model.user.User;
import lombok.Getter;
import lombok.Setter;

public class UserRequestDto {

     @Getter
     @Setter
     public static class JoinInDto {

          @NotEmpty
          private String username;
          @NotEmpty
          private String password;
          @NotEmpty
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

     @Getter
     @Setter
     public static class UpdateInDto {

          @NotEmpty
          private String username;
          @NotEmpty
          private String password;
          @NotEmpty
          private String email;

          public User toEntity() {
               return User.builder()
                         .username(username)
                         .password(password)
                         .email(email)
                         .build();
          }
     }
}
