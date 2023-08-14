package coffee.pastry.joshuablog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coffee.pastry.joshuablog.dto.board.BoardRequestDto;
import coffee.pastry.joshuablog.model.board.Board;
import coffee.pastry.joshuablog.model.board.BoardQueryRepository;
import coffee.pastry.joshuablog.model.board.BoardRepository;
import coffee.pastry.joshuablog.model.user.User;
import coffee.pastry.joshuablog.model.user.UserRepository;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class BoardService {

     private final BoardRepository boardRepository;
     private final UserRepository userRepository;
     private final BoardQueryRepository boardQueryRepository;

     @Transactional
     public void 글쓰기(BoardRequestDto.SaveInDto saveInDto, Long userId) {
          try {
               User userPS = userRepository.findById(userId).orElseThrow(
                         () -> new RuntimeException("유저를 찾을 수 없습니다. "));
               boardRepository.save(saveInDto.toEntity(userPS));
          } catch (Exception e) {
               throw new RuntimeException("글쓰기 실패 : " + e.getMessage());
          }
     }

     public Page<Board> 게시글목록보기(int page) {
          return boardQueryRepository.findAll(page);
     }
}
