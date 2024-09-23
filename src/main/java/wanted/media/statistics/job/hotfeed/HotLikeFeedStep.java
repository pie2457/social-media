package wanted.media.statistics.job.hotfeed;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import wanted.media.post.domain.Post;
import wanted.media.statistics.domain.PostStatType;
import wanted.media.statistics.domain.StatisticsPost;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@Configuration
public class HotLikeFeedStep {

    public static final String HOT_LIKE_FEED_STAT_STEP = "hotLikeFeedStatStep";
    private static final int CHUNK_SIZE = 100;
    private static final String HOT_LIKE_FEED_READER = "hotLikeFeedReader";
    private static final String HOT_LIKE_FEED_PROCESSOR = "hotLikeFeedProcessor";
    private static final String HOT_LIKE_FEED_WRITER = "hotLikeFeedWriter";

    @JobScope
    @Bean(HOT_LIKE_FEED_STAT_STEP)
    public Step statHotLikeFeedStep(
            @Qualifier(value = HOT_LIKE_FEED_READER) ItemReader<Post> statHotLikeFeedItemReader,
            @Qualifier(value = HOT_LIKE_FEED_PROCESSOR) ItemProcessor<Post, StatisticsPost> statHotLikeFeedItemProcessor,
            @Qualifier(value = HOT_LIKE_FEED_WRITER) ItemWriter<StatisticsPost> statHotLikeFeedItemWriter,
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager
    ) {
        return new StepBuilder(HOT_LIKE_FEED_STAT_STEP, jobRepository)
                .<Post, StatisticsPost>chunk(CHUNK_SIZE, transactionManager)
                .reader(statHotLikeFeedItemReader)
                .processor(statHotLikeFeedItemProcessor)
                .writer(statHotLikeFeedItemWriter)
                .build();
    }

    @StepScope
    @Bean(HOT_LIKE_FEED_READER)
    public JpaPagingItemReader<Post> statHotLikeFeedItemReader(EntityManagerFactory entityManagerFactory) {
        var yesterday = LocalDate.now().minusDays(1);
        
        return new JpaPagingItemReaderBuilder<Post>()
                .name(HOT_LIKE_FEED_READER)
                .entityManagerFactory(entityManagerFactory)
                .pageSize(CHUNK_SIZE)
                .maxItemCount(CHUNK_SIZE)
                .queryString("SELECT p FROM Post p WHERE p.createdAt between :start and :end ORDER BY p.likeCount DESC")
                .parameterValues(Map.of(
                        "start", yesterday.atStartOfDay(),
                        "end", yesterday.atTime(LocalTime.MAX)
                ))
                .build();
    }

    @StepScope
    @Bean(HOT_LIKE_FEED_PROCESSOR)
    public ItemProcessor<Post, StatisticsPost> statHotLikeFeedItemProcessor() {
        return post -> StatisticsPost.builder()
                .postId(post.getId())
                .likeCount(post.getLikeCount())
                .viewCount(post.getViewCount())
                .postCreatedAt(post.getCreatedAt())
                .statType(PostStatType.LIKE_STAT)
                .build();
    }

    @StepScope
    @Bean(HOT_LIKE_FEED_WRITER)
    public JpaItemWriter<StatisticsPost> statHotLikeFeedItemWriter(EntityManagerFactory entityManagerFactory) {
        return new JpaItemWriterBuilder<StatisticsPost>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }
}
