package wanted.media.statistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.media.statistics.domain.StatisticsPost;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticsPostRepository extends JpaRepository<StatisticsPost, Integer> {

    List<StatisticsPost> findAllByPostCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
