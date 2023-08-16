package coffee.pastry.joshuablog.model.board;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BoardQueryRepository {

     private final EntityManager em;
     private final int SIZE = 8;

     public Page<Board> findAll(int page) {
          int startPosition = page * 8;

          List<Board> boardListPS = em.createQuery("select b from Board b join fetch b.user order by b.id desc")
                    .setFirstResult(startPosition)
                    .setMaxResults(SIZE)
                    .getResultList();

          Long totalCount = em.createQuery("select count(b) from Board b", Long.class).getSingleResult();
          return new PageImpl<>(boardListPS, PageRequest.of(page, SIZE), totalCount);
     }

     public Page<Board> findAllByKeyword(int page, String keyword) {
          int startPosition = page * SIZE;
          List<Board> boardListPS = em
                    .createQuery(
                              "select b from Board b join fetch b.user where b.title like :keyword order by b.id desc")
                    .setParameter("keyword", "%" + keyword + "%")
                    .setFirstResult(startPosition) // startPosition
                    .setMaxResults(SIZE) // size
                    .getResultList();
          Long totalCount = em.createQuery("select count(b) from Board b where b.title like :keyword", Long.class)
                    .setParameter("keyword", "%" + keyword + "%")
                    .getSingleResult();
          return new PageImpl<>(boardListPS, PageRequest.of(page, SIZE), totalCount);
     }
}
