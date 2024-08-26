package wanted.media.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wanted.media.post.domain.Post;
import wanted.media.post.domain.Type;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p " +
            "WHERE p.user.account = :account " +
            "AND (:type IS NULL OR p.type = :type) " +
            "AND ((:searchBy = 'title' AND LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "OR (:searchBy = 'content' AND LOWER(p.content) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "OR (:searchBy = 'title,content' AND (LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(p.content) LIKE LOWER(CONCAT('%', :search, '%')))))")
    Page<Post> findBySearchContaining(@Param("account") String account, @Param("type") Type type,
                                      @Param("searchBy") String searchBy, @Param("search") String search,
                                      Pageable pageable);
}
