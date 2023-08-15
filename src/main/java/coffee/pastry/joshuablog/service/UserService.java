package coffee.pastry.joshuablog.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
          try {
               joinInDto.setPassword(passwordEncoder.encode(joinInDto.getPassword()));
               userRepository.save(joinInDto.toEntity());
          } catch (Exception e) {
               throw new RuntimeException("회원가입 오류 : " + e.getMessage());
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
                         .orElseThrow(() -> new Exception500("로그인 된 유저가 DB에 존재하지 않음"));
               userPS.changeProfile(uuidImageName);
               return userPS;
          } catch (Exception e) {
               throw new Exception500("프로필 사진 등록 실패 : " + e.getMessage());
          }
     }
}
