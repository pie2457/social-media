package wanted.media.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.media.post.domain.Post;

public interface PostLikeRepository extends JpaRepository<Post, String> {
}
