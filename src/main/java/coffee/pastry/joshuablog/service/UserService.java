package coffee.pastry.joshuablog.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coffee.pastry.joshuablog.dto.user.UserRequestDto;
import coffee.pastry.joshuablog.model.user.UserRepository;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

     private final UserRepository userRepository;
     private final BCryptPasswordEncoder passwordEncoder;

     @Transactional
     public void 회원가입(UserRequestDto.JoinInDto joinInDto) {
          try {
               joinInDto.setPassword(passwordEncoder.encode(joinInDto.getPassword()));
               userRepository.save(joinInDto.toEntity());
          } catch (Exception e) {
               throw new RuntimeException("회원가입 오류 : " + e.getMessage());
          }

     }
}
