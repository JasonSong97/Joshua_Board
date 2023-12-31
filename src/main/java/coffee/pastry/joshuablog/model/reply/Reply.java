package coffee.pastry.joshuablog.model.reply;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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
@Table(name = "reply_tb", uniqueConstraints = {
          @UniqueConstraint(name = "love_uk", columnNames = { "boardId", "userId" }) })
@Entity
public class Reply {

     /**
      * 1명의 User N개의 댓글, 1개의 댓글 1명의 User => User:Reply=1:N
      * 1개의 Board N개의 댓글, 1개의 댓글 1개의 Board => Board:Reply=N:1
      */

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
     @Column(nullable = false, length = 200)
     private String content;
     @ManyToOne
     @JoinColumn(name = "boardId")
     private Board board;
     @ManyToOne
     @JoinColumn(name = "userId")
     private User user;
     // @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
     private LocalDateTime createdAt;
     // @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
     private LocalDateTime updatedAt;

     @PrePersist
     protected void onCreate() {
          this.createdAt = LocalDateTime.now();
     }

     @PreUpdate
     protected void onUpdate() {
          this.updatedAt = LocalDateTime.now();
     }

     public void writeReply(User user, Board board) {
          this.user = user;
          this.board = board;
     }
}
