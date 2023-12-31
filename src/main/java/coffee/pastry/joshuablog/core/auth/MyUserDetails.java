package coffee.pastry.joshuablog.core.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import coffee.pastry.joshuablog.model.user.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MyUserDetails implements UserDetails {

     private User user;

     public MyUserDetails(User user) {
          this.user = user;
     }

     @Override
     public Collection<? extends GrantedAuthority> getAuthorities() {
          Collection<GrantedAuthority> collector = new ArrayList<>();
          collector.add(() -> user.getRole());
          return collector;
     }

     @Override
     public String getPassword() {
          return user.getPassword();
     }

     @Override
     public String getUsername() {
          return user.getUsername();
     }

     @Override
     public boolean isAccountNonExpired() {
          return true;
     }

     @Override
     public boolean isAccountNonLocked() {
          return true;
     }

     @Override
     public boolean isCredentialsNonExpired() {
          return true;
     }

     @Override
     public boolean isEnabled() {
          return true;
     }
}
