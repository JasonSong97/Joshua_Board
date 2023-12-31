package coffee.pastry.joshuablog.controller;

import javax.servlet.http.HttpSession;

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
import coffee.pastry.joshuablog.model.user.User;
import coffee.pastry.joshuablog.service.BoardService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardController {

     private final BoardService boardService;
     private final HttpSession session;

     @PostMapping("/s/board/{id}/delete")
     public String delete(@PathVariable Long id, @AuthenticationPrincipal MyUserDetails myUserDetails) {
          boardService.게시글삭제(id, myUserDetails.getUser().getId());
          return "redirect:/";
     }

     @GetMapping({ "/", "/board" })
     public String main(
               @RequestParam(defaultValue = "0") int page,
               @RequestParam(defaultValue = "") String keyword,
               Model model) {
          Page<Board> boardPG = boardService.게시글목록보기(page, keyword);
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

     @GetMapping("/s/board/{id}/updateForm")
     public String updateForm(@PathVariable Long id,
               Model model) {
          User user = (User) session.getAttribute("principal");
          model.addAttribute("board", boardService.게시글상세보기(id));
          return "board/updateForm";
     }
}
