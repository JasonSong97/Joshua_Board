package coffee.pastry.joshuablog.dto.board;

import coffee.pastry.joshuablog.model.board.Board;
import coffee.pastry.joshuablog.model.user.User;
import lombok.Getter;
import lombok.Setter;

public class BoardRequestDto {

     @Getter
     @Setter
     public static class SaveInDto {

          private String title;
          private String content;

          public Board toEntity(User user) {
               return Board.builder()
                         .user(user)
                         .title(title)
                         .content(content)
                         .thumbnail(null)
                         .build();
          }
     }
}
