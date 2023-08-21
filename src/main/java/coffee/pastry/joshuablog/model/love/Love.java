package coffee.pastry.joshuablog.model.love;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import coffee.pastry.joshuablog.model.board.Board;
import coffee.pastry.joshuablog.model.user.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "love_tb")
@Entity
public class Love {

     /**
      * 1명의 User N개의 Love + 1개의 Love 1명의 User => Love:User = N:1
      * 1개의 Board N개의 Love + 1개의 Love 1개의 Board => Love:Board = N:1
      */

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
     @ManyToOne // 무한참조 check
     private Board board;
     @ManyToOne // 무한참모 check
     private User user;
     private LocalDateTime createdAt;

     @PrePersist
     protected void onCreate() {
          this.createdAt = LocalDateTime.now();
     }
}
