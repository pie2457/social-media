package wanted.media.statistics.job.hotfeed;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class HotFeedHashTagsJob {

    private static final String HOT_FEED_HASHTAG_STAT_JOB = "hotFeedHashTagStatJob";
    private static final String HOT_FEED_HASHTAG_STAT_STEP = "hotFeedHashTagStatStep";

    @Bean
    public Job statHotFeedHashtagJob(
            @Qualifier(HOT_FEED_HASHTAG_STAT_STEP) Step hotFeedHashtagStatStep,
            JobRepository jobRepository
    ) {
        return new JobBuilder(HOT_FEED_HASHTAG_STAT_JOB, jobRepository)
                .start(hotFeedHashtagStatStep)
                .build();
    }

    @JobScope
    @Bean(HOT_FEED_HASHTAG_STAT_STEP)
    public Step hotFeedHashtagStatStep(
            HotFeedHashtagTasklet hotFeedHashtagTasklet,
            PlatformTransactionManager transactionManager,
            JobRepository jobRepository
    ) {
        return new StepBuilder(HOT_FEED_HASHTAG_STAT_STEP, jobRepository)
                .tasklet(hotFeedHashtagTasklet, transactionManager)
                .build();
    }
}
