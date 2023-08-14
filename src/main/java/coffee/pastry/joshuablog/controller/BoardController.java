package coffee.pastry.joshuablog.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import coffee.pastry.joshuablog.core.auth.MyUserDetails;
import coffee.pastry.joshuablog.dto.board.BoardRequestDto;
import coffee.pastry.joshuablog.service.BoardService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardController {

     private final BoardService boardService;

     @GetMapping({ "/", "/board" })
     public String main() {
          return "board/main";
     }

     @GetMapping("/s/board/saveForm")
     public String saveForm() {
          return "board/saveForm";
     }

     @PostMapping("/s/board/save")
     public String save(BoardRequestDto.SaveInDto saveInDto, @AuthenticationPrincipal MyUserDetails myUserDetails) {
          boardService.글쓰기(saveInDto, myUserDetails.getUser().getId());
          return "redirect:/";
     }
}
