package coffee.pastry.joshuablog.model.board;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Long> {

     @Query("select b from Board b join fetch b.user where b.id = :id")
     Optional<Board> findByIdJoinFetchUser(@Param("id") Long id);

     @Query("select b from Board b join fetch b.user where b.id = :id")
     Optional<Board> findByIdFetchUser(@Param("id") Long id);
}
