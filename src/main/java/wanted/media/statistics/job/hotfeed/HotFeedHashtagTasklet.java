package wanted.media.statistics.job.hotfeed;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
import wanted.media.statistics.domain.StatisticsHashTag;
import wanted.media.statistics.domain.StatisticsPost;
import wanted.media.statistics.repository.StatisticsHashTagRepository;
import wanted.media.statistics.repository.StatisticsPostRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Collectors;

@StepScope
@Component
@RequiredArgsConstructor
public class HotFeedHashtagTasklet implements Tasklet {

    private final StatisticsPostRepository statisticsPostRepository;
    private final StatisticsHashTagRepository hashTagRepository;

    @Override
    public RepeatStatus execute(
            StepContribution contribution,
            ChunkContext chunkContext
    ) {
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);

        var posts = statisticsPostRepository.findAllByPostCreatedAtBetween(
                yesterday.with(LocalTime.MIN),
                yesterday.with(LocalTime.MAX)
        );

        String hashtagsOfHotFeed = posts.stream()
                .map(StatisticsPost::getHashtag)
                .distinct()
                .collect(Collectors.joining(","));

        hashTagRepository.save(
                StatisticsHashTag.builder()
                        .hashtags(hashtagsOfHotFeed)
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        return RepeatStatus.FINISHED;
    }
}
