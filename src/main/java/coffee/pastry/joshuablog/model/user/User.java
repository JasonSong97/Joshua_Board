package coffee.pastry.joshuablog.model.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "user_tb")
@AllArgsConstructor
@Getter
public class User {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
     @Column(nullable = false, unique = true, length = 100)
     private String username;
     @Column(nullable = false, length = 60)
     private String password;
     @Column(nullable = false, length = 50)
     private String email;
     private String role;
     private String profile;
     private Boolean status;

     private String oauth;

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

     public void changeProfile(String profile) {
          this.profile = profile;
     }

     public void update(String password, String email) {
          this.password = password;
          this.email = email;
     }
}
