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
            "AND p.type LIKE :type")
    List<Post> findBySearchContaining(@Param("account") String account, @Param("type") Type type, Sort sort);

    List<Post> findByType(@Param("type") Type type);
}
