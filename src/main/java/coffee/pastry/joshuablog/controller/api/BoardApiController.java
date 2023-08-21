package coffee.pastry.joshuablog.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import coffee.pastry.joshuablog.core.auth.MyUserDetails;
import coffee.pastry.joshuablog.model.board.Board;
import coffee.pastry.joshuablog.model.reply.Reply;
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

     // Reply
     @PostMapping("/board/{boardId}/reply")
     public ResponseEntity<?> replySave(@PathVariable Long boardId, @RequestBody Reply reply,
               @AuthenticationPrincipal MyUserDetails myUserDetails) {
          boardService.댓글쓰기(myUserDetails.getUser(), boardId, reply);
          return ResponseEntity.ok().build();
     }

     @DeleteMapping("/board/{boardId}/reply/{replyId}")
     public ResponseEntity<?> replyDelete(@PathVariable Long replyId) {
          boardService.댓글삭제(replyId);
          return ResponseEntity.ok().build();
     }
}
