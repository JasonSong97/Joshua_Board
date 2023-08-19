package coffee.pastry.joshuablog.model.board;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import coffee.pastry.joshuablog.model.reply.Reply;
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
@Table(name = "board_tb")
@Entity
public class Board {

     /**
      * 1명의 User N개의 게시글 + 1개의 게시글 1명의 User => User:Board=1:N
      */

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
     private String title;
     @Lob // 4GB
     private String content;
     @Lob // 4GB
     private String thumbnail;
     @ManyToOne
     private User user;
     @OneToMany(mappedBy = "board", fetch = FetchType.EAGER)
     @JsonIgnoreProperties({ "board" }) // 무한참조 방지...드디어...
     @OrderBy("id desc")
     private List<Reply> replys;

     private LocalDateTime createdAt;
     private LocalDateTime updatedAt;

     @PrePersist
     protected void onCreate() {
          this.createdAt = LocalDateTime.now();
     }

     @PreUpdate
     protected void onUpdate() {
          this.updatedAt = LocalDateTime.now();
     }

     public void chaengeTitle(String title) {
          this.title = title;
     }

     public void changeContent(String content) {
          this.content = content;
     }
}
