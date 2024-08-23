package wanted.media.content.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.media.content.domain.Content;

public interface ContentRepository extends JpaRepository<Content, Long> {
}
