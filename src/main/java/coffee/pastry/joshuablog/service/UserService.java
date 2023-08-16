package coffee.pastry.joshuablog.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import coffee.pastry.joshuablog.core.exception.csr.ExceptionApi400;
import coffee.pastry.joshuablog.core.exception.ssr.Exception400;
import coffee.pastry.joshuablog.core.exception.ssr.Exception500;
import coffee.pastry.joshuablog.core.util.MyFileUtil;
import coffee.pastry.joshuablog.dto.user.UserRequestDto;
import coffee.pastry.joshuablog.model.user.User;
import coffee.pastry.joshuablog.model.user.UserRepository;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

     @Value("${file.path}")
     private String uploadFolder;
     private final UserRepository userRepository;
     private final BCryptPasswordEncoder passwordEncoder;

     @Transactional
     public void 회원가입(UserRequestDto.JoinInDto joinInDto) {
          Optional<User> userOP = userRepository.findByUsername(joinInDto.getUsername());
          if (userOP.isPresent()) {
               throw new Exception400("username", "유저네임이 중복되었습니다. ");
          }
          try {
               joinInDto.setPassword(passwordEncoder.encode(joinInDto.getPassword()));
               userRepository.save(joinInDto.toEntity());
          } catch (Exception e) {
               throw new Exception500("회원가입 실패 : " + e.getMessage());
          }

     }

     public User 회원프로필보기(Long id) {
          User userPS = userRepository.findById(id).orElseThrow(
                    () -> new Exception400("id", "해당 유저가 존재하지 않습니다. "));
          return userPS;
     }

     @Transactional
     public User 프로필사진수정(MultipartFile profile, Long id) {
          try {
               String uuidImageName = MyFileUtil.write(uploadFolder, profile);

               User userPS = userRepository.findById(id)
                         .orElseThrow(() -> new Exception500("로그인 된 유저가 DB에 존재하지 않습니다. "));
               userPS.changeProfile(uuidImageName);
               return userPS;
          } catch (Exception e) {
               throw new Exception500("프로필 사진 등록 실패 : " + e.getMessage());
          }
     }

     public void 유저네임중복체크(String username) {
          Optional<User> userOP = userRepository.findByUsername(username);
          if (userOP.isPresent()) {
               throw new ExceptionApi400("username", "유저네임이 중복되었습니다. ");
          }
     }

     public User 회원정보보기(Long id) {
          User userPS = userRepository.findById(id).orElseThrow(
                    () -> new Exception400("id", "해당 유저가 존재하지 않습니다. "));
          return userPS;
     }

     @Transactional
     public User 회원수정(Long id, UserRequestDto.UpdateInDto updateInDTO) {
          User userPS = userRepository.findById(id)
                    .orElseThrow(() -> new Exception400("id", "해당 유저가 존재하지 않습니다. "));
          updateInDTO.setPassword(passwordEncoder.encode(updateInDTO.getPassword()));
          userPS.update(updateInDTO.getPassword(), updateInDTO.getEmail());
          return userPS;
     }
}
