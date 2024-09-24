package wanted.media.statistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.media.statistics.domain.StatisticsHashTag;

public interface StatisticsHashTagRepository extends JpaRepository<StatisticsHashTag, Integer> {

}
