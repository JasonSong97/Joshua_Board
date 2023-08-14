package coffee.pastry.joshuablog.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import coffee.pastry.joshuablog.core.auth.MyUserDetails;
import coffee.pastry.joshuablog.dto.board.BoardRequestDto;
import coffee.pastry.joshuablog.model.board.Board;
import coffee.pastry.joshuablog.service.BoardService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardController {

     private final BoardService boardService;

     @GetMapping({ "/", "/board" })
     public String main(@RequestParam(defaultValue = "0") Integer page, Model model) {
          PageRequest pageRequest = PageRequest.of(page, 8, Sort.by("id").descending());
          Page<Board> boardPG = boardService.게시글목록보기(pageRequest);
          model.addAttribute("boardPG", boardPG);
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
