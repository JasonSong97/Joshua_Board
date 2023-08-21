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
import coffee.pastry.joshuablog.service.LoveService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class BoardApiController {

     private final BoardService boardService;
     private final LoveService loveService;

     @PostMapping("/board/{id}")
     public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Board board) {
          boardService.게시글수정(id, board);
          return ResponseEntity.ok().body(null);
     }

     /**
      * Replt
      */
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

     /**
      * Love
      */
     @PostMapping("/board/{boardId}/love")
     public ResponseEntity<?> love(@PathVariable Long boardId,
               @AuthenticationPrincipal MyUserDetails myUserDetails) {
          loveService.하트누르기(boardId, myUserDetails.getUser().getId());
          return ResponseEntity.ok().build();
     }

     @DeleteMapping("/board/{boardId}/love")
     public ResponseEntity<?> unLove(@PathVariable Long boardId,
               @AuthenticationPrincipal MyUserDetails myUserDetails) {
          loveService.하트취소하기(boardId, myUserDetails.getUser().getId());
          return ResponseEntity.ok().build();
     }
}
