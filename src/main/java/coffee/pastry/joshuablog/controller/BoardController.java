package coffee.pastry.joshuablog.controller;

import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
     public String main(@RequestParam(defaultValue = "0") int page, Model model) {
          Page<Board> boardPG = boardService.게시글목록보기(page);
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

     @GetMapping("/board/{id}")
     public String detail(@PathVariable Long id, Model model) {
          Board board = boardService.게시글상세보기(id);
          model.addAttribute("board", board);
          return "board/detail";
     }
}
