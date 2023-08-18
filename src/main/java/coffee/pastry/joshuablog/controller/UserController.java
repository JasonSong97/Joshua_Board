package coffee.pastry.joshuablog.controller;

import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import coffee.pastry.joshuablog.core.auth.MyUserDetails;
import coffee.pastry.joshuablog.core.exception.ssr.Exception400;
import coffee.pastry.joshuablog.core.exception.ssr.Exception403;
import coffee.pastry.joshuablog.core.util.Script;
import coffee.pastry.joshuablog.dto.user.UserRequestDto;
import coffee.pastry.joshuablog.dto.user.UserRequestDto.JoinInDto;
import coffee.pastry.joshuablog.model.kakao.KakaoProfile;
import coffee.pastry.joshuablog.model.kakao.OAuthToken;
import coffee.pastry.joshuablog.model.user.User;
import coffee.pastry.joshuablog.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {

     private final UserService userService;
     private final HttpSession session;

     @PostMapping("/join")
     public String join(@Valid UserRequestDto.JoinInDto joinInDto, Errors errors) {
          userService.회원가입(joinInDto);
          return "redirect:/loginForm";
     }

     @GetMapping("/joinForm")
     public String joinForm() {
          return "user/joinForm";
     }

     @GetMapping("/loginForm")
     public String loginForm() {
          return "user/loginForm";
     }

     @GetMapping("/s/user/{id}/updateProfileForm")
     public String profileUpdateForm(@PathVariable Long id, Model model,
               @AuthenticationPrincipal MyUserDetails myUserDetails) {
          if (id != myUserDetails.getUser().getId()) {
               throw new Exception403("권한이 없습니다. ");
          }

          User userPS = userService.회원프로필보기(id);
          model.addAttribute("user", userPS);
          return "user/profileUpdateForm";
     }

     @PostMapping("/s/user/{id}/updateProfile")
     public String profileUpdate(
               @PathVariable Long id,
               MultipartFile profile,
               @AuthenticationPrincipal MyUserDetails myUserDetails) {
          if (id != myUserDetails.getUser().getId()) {
               throw new Exception403("권한이 없습니다. ");
          }

          if (profile.isEmpty()) {
               throw new Exception400("profile", "사진이 전송되지 않았습니다. ");
          }

          User userPS = userService.프로필사진수정(profile, id);

          myUserDetails.setUser(userPS);
          session.setAttribute("sessionUser", userPS);
          return "redirect:/";
     }

     @GetMapping("/s/user/{id}/updateForm")
     public String updateForm(@PathVariable Long id, Model model,
               @AuthenticationPrincipal MyUserDetails myUserDetails) {
          if (id != myUserDetails.getUser().getId()) {
               throw new Exception403("권한이 없습니다. ");
          }
          User userPS = userService.회원정보보기(id);
          model.addAttribute("user", userPS);
          return "user/updateForm";
     }

     @PostMapping("/s/user/{id}/update")
     public @ResponseBody String update(
               @PathVariable Long id,
               @Valid UserRequestDto.UpdateInDto updateInDTO,
               Errors errors,
               @AuthenticationPrincipal MyUserDetails myUserDetails) {
          if (id != myUserDetails.getUser().getId()) {
               throw new Exception403("권한이 없습니다. ");
          }

          User user = userService.회원수정(id, updateInDTO);

          // 세션 동기화
          myUserDetails.setUser(user);
          session.setAttribute("sessionUser", user);
          return Script.href("회원정보 수정 성공", "/");
     }

     /**
      * 카카오
      */
     @GetMapping("/auth/kakao/callback")
     public @ResponseBody String kakaoCallback(String code) {
          // POST 방식을 사용하여 카카오 쪽으로 key=value 데이터를 요청함.
          RestTemplate rt = new RestTemplate();

          // HttpHeader 오브젝트 생성.
          HttpHeaders headers = new HttpHeaders();
          headers.add("Content-type",
                    "application/x-www-form-urlencoded;charset=utf-8");

          // HttpBody 오브젝트 생성.
          MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
          params.add("grant_type", "authorization_code");
          params.add("client_id", "536973069c3a0abd183851a68226f823");
          params.add("redirect_uri", "http://localhost:8080/auth/kakao/callback");
          params.add("code", code);

          // HttpHeader와 HttpBody를 하나의 오브젝트에 담기(하단의 exchange라는 함수가 HttpEntity를 받기 때문)
          HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

          // Http Post방식으로 요청하기. responsed의 응답을 받음.
          ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST,
                    kakaoTokenRequest, String.class);

          ObjectMapper objectMapper = new ObjectMapper();
          OAuthToken oauthToken = null;

          try {
               oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
          } catch (JsonMappingException e) {
               e.printStackTrace();
          } catch (JsonProcessingException e) {
               e.printStackTrace();
          }

          System.out.println("kakao access token: " + oauthToken.getAccess_token());

          RestTemplate rt2 = new RestTemplate();

          // HttpHeader 오브젝트 생성.
          HttpHeaders headers2 = new HttpHeaders();
          headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
          headers2.add("Content-type",
                    "application/x-www-form-urlencoded;charset=utf-8");

          // HttpHeader와 HttpBody를 하나의 오브젝트에 담기(하단의 exchange라는 함수가 HttpEntity를 받기 때문)
          HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = new HttpEntity<>(headers2);

          // Http Post방식으로 요청하기. responsed의 응답을 받음.
          ResponseEntity<String> response2 = rt2.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST,
                    kakaoProfileRequest2, String.class);

          ObjectMapper objectMapper2 = new ObjectMapper();
          KakaoProfile kakaoProfile = null;

          try {
               kakaoProfile = objectMapper2.readValue(response2.getBody(),
                         KakaoProfile.class);
          } catch (JsonMappingException e) {
               e.printStackTrace();
          } catch (JsonProcessingException e) {
               e.printStackTrace();
          }

          System.out.println("카카오 아이디 : " + kakaoProfile.getId());
          System.out.println("카카오 이메일 : " + kakaoProfile.getKakao_account().getEmail());
          System.out.println("===========================================================");
          System.out.println(
                    "블로그 서버 유저네임 : " + kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId());
          System.out.println("블로그 서버 이메일 : " + kakaoProfile.getKakao_account().getEmail());
          UUID garbagePassword = UUID.randomUUID();
          System.out.println("블로그 서버 비밀번호 : " + garbagePassword);

          User kakaoUser = User.builder()
                    .username(kakaoProfile.getKakao_account().getHas_email() + "_" +
                              kakaoProfile.getId())
                    .password(garbagePassword.toString())
                    .email(kakaoProfile.getKakao_account().getEmail())
                    .build();
          JoinInDto kakaoDto = new JoinInDto();
          kakaoDto.setUsername(kakaoUser.getUsername());
          kakaoDto.setPassword(kakaoUser.getPassword());
          kakaoDto.setEmail(kakaoUser.getEmail());
          userService.회원가입(kakaoDto);
          // 이미 가입된 사람인지 확인 후 처리
          // User originUser = userService.회원찾기(kakaoUser.getUsername());

          // if (originUser.getUsername() == null) {
          // // 회원 X
          // System.out.println("기존 회원이 아닙니다. ");
          //

          // }
          // System.out.println("자동 로그인 진행합니다.");

          // Authentication authentication = authenticationManager
          // .authenticate(new
          // UsernamePasswordAuthenticationToken(kakaoUser.getUsername(),
          // joshuaKey));
          // SecurityContextHolder.getContext().setAuthentication(authentication);

          return "회원가입 완료 : 카카오 정보 : " + response2.getBody();

     }
}
