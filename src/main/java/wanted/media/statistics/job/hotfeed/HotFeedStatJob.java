package wanted.media.statistics.job.hotfeed;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static wanted.media.statistics.job.hotfeed.HotLikeFeedStep.HOT_LIKE_FEED_STAT_STEP;
import static wanted.media.statistics.job.hotfeed.HotViewFeedStep.HOT_VIEW_FEED_STAT_STEP;

@Slf4j
@Configuration
public class HotFeedStatJob {

    private static final String HOT_FEED_STAT_JOB = "hotFeedStatJob";

    @Bean
    public Job statHotFeedJob(
            @Qualifier(value = HOT_VIEW_FEED_STAT_STEP) Step statHotViewFeedStep,
            @Qualifier(value = HOT_LIKE_FEED_STAT_STEP) Step statHotLikeFeedStep,
            JobRepository jobRepository
    ) {
        return new JobBuilder(HOT_FEED_STAT_JOB, jobRepository)
                .start(statHotViewFeedStep)
                .next(statHotLikeFeedStep)
                .build();
    }
}
