package wanted.media.content.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wanted.media.content.domain.Post;
import wanted.media.content.domain.Type;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p " +
            "WHERE p.user.account LIKE %:account% " +
            "AND (:type IS NULL OR p.type = :type) " +
            "AND ((:searchBy = 'title' AND LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "OR (:searchBy = 'content' AND LOWER(p.content) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "OR (:searchBy = 'title,content' AND (LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(p.content) LIKE LOWER(CONCAT('%', :search, '%')))))")
    List<Post> findBySearchContaining(@Param("account") String account, @Param("type") Type type,
                                      @Param("searchBy") String searchBy, @Param("search") String search,
                                      Sort sort);
}
