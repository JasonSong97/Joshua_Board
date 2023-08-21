package coffee.pastry.joshuablog.model.love;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LoveRepository extends JpaRepository<Love, Long> {

     @Modifying
     @Query(value = "insert into love(boardId, userId, createdAt) values(:boardId, :userId, now())", nativeQuery = true)
     int mLove(Long boardId, Long userId);

     @Modifying
     @Query(value = "delete from love where boardId = :boardId and userId = :userId ", nativeQuery = true)
     int mUnLove(Long boardId, Long userId);
}
