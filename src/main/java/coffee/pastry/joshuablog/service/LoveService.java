package coffee.pastry.joshuablog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coffee.pastry.joshuablog.model.love.LoveRepository;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class LoveService {

     private final LoveRepository loveRepository;

     @Transactional
     public void 하트취소하기(Long boardId, Long userId) {
          loveRepository.mLove(boardId, userId);
     }

     @Transactional
     public void 하트누르기(Long boardId, Long userId) {
          loveRepository.mUnLove(boardId, userId);
     }
}