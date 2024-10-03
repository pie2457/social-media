package wanted.media.statistics.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class StatisticJobScheduler {

    private final JobLauncher jobLauncher;
    private final Job statHotFeedJob;
    private final Job statHotFeedHashtagJob;

    @Async("schedulerExecutor")
    @Scheduled(cron = "0 1 * * *")
    public void statHotFeed() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("date", LocalDate.now() + UUID.randomUUID().toString())
                    .toJobParameters();
            jobLauncher.run(statHotFeedJob, jobParameters);
        } catch (Exception e) {
            log.error("HOT FEED STATISTIC BATCH FAIL!, {}", e.getMessage());
        }
    }

    @Async("schedulerExecutor")
    @Scheduled(cron = "0 2 * * *")
    public void setStatHotFeedHashtagJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("date", LocalDate.now() + UUID.randomUUID().toString())
                    .toJobParameters();
            jobLauncher.run(statHotFeedHashtagJob, jobParameters);
        } catch (Exception e) {
            log.error("HOT FEED HASHTAG STATISTIC BATCH FAIL!, {}", e.getMessage());
        }
    }
}
