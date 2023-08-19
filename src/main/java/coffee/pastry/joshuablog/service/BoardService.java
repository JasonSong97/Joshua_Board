package coffee.pastry.joshuablog.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coffee.pastry.joshuablog.core.exception.ssr.Exception400;
import coffee.pastry.joshuablog.core.exception.ssr.Exception403;
import coffee.pastry.joshuablog.core.exception.ssr.Exception500;
import coffee.pastry.joshuablog.core.util.MyParseUtil;
import coffee.pastry.joshuablog.dto.board.BoardRequestDto;
import coffee.pastry.joshuablog.model.board.Board;
import coffee.pastry.joshuablog.model.board.BoardQueryRepository;
import coffee.pastry.joshuablog.model.board.BoardRepository;
import coffee.pastry.joshuablog.model.reply.Reply;
import coffee.pastry.joshuablog.model.reply.ReplyReopsitory;
import coffee.pastry.joshuablog.model.user.User;
import coffee.pastry.joshuablog.model.user.UserRepository;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class BoardService {

     private final BoardRepository boardRepository;
     private final UserRepository userRepository;
     private final ReplyReopsitory replyReopsitory;
     private final BoardQueryRepository boardQueryRepository;

     @Transactional
     public void 글쓰기(BoardRequestDto.SaveInDto saveInDto, Long userId) {
          try {
               User userPS = userRepository.findById(userId).orElseThrow(
                         () -> new Exception400("id", "유저를 찾을 수 없습니다. "));

               String thumbnail = MyParseUtil.getThumbnail(saveInDto.getContent());

               boardRepository.save(saveInDto.toEntity(userPS, thumbnail));
          } catch (Exception e) {
               throw new Exception500("글쓰기 실패 : " + e.getMessage());
          }
     }

     public Page<Board> 게시글목록보기(int page, String keyword) {
          if (keyword.isBlank()) {
               return boardQueryRepository.findAll(page);
          } else {
               Page<Board> boardPGPS = boardQueryRepository.findAllByKeyword(page, keyword);
               return boardPGPS;
          }
     }

     public Board 게시글상세보기(Long id) {
          Board boardPS = boardRepository.findByIdJoinFetchUser(id).orElseThrow(
                    () -> new Exception400("id", "게시글 아이디를 찾을 수 없습니다. "));
          return boardPS;
     }

     @Transactional
     public void 게시글삭제(Long id, Long userId) {
          Board boardPS = boardRepository.findByIdFetchUser(id).orElseThrow(
                    () -> new Exception400("id", "게시글 아이디를 찾을 수 없습니다. "));

          if (boardPS.getUser().getId() != userId) {
               throw new Exception403("권한이 없습니다. ");
          }

          try {
               boardRepository.delete(boardPS);
          } catch (Exception e) {
               throw new Exception500("게시글 삭제 실패 : " + e.getMessage());
          }
     }

     @Transactional
     public void 게시글수정(Long id, Board requestBoard) {
          Board boardPS = boardRepository.findById(id).orElseThrow(
                    () -> new Exception400("id", "게시글 아이디를 찾을 수 없습니다. "));
          boardPS.chaengeTitle(requestBoard.getTitle());
          boardPS.changeContent(requestBoard.getContent());
     }

     /**
      * 
      * 댓글
      */
     @Transactional
     public void 댓글쓰기(User user, Long boardId, Reply requestReply) {
          Board boardPS = boardRepository.findById(boardId).orElseThrow(
                    () -> new Exception400("id", "게시글 아이디를 찾을 수 없습니다. "));
          requestReply.writeReply(user, boardPS);
          replyReopsitory.save(requestReply);
     }
}
