package coffee.pastry.joshuablog.core.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import coffee.pastry.joshuablog.model.user.User;
import coffee.pastry.joshuablog.model.user.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

     private final UserRepository userRepository;

     @Override
     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
          User userPS = userRepository.findByUsername(username).orElseThrow(
                    () -> new UsernameNotFoundException("Bad Credential") // failureHandler
          );
          return new MyUserDetails(userPS);
     }
}
