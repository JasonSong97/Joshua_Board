package coffee.pastry.joshuablog.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import coffee.pastry.joshuablog.model.board.Board;
import coffee.pastry.joshuablog.service.BoardService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class BoardApiController {

     private final BoardService boardService;

     @PostMapping("/board/{id}")
     public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Board board) {
          boardService.게시글수정(id, board);
          return ResponseEntity.ok().body(null);
     }

}
