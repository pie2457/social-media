package wanted.media.statistics.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StatisticsPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String postId;

    @ColumnDefault("0")
    private Long viewCount = 0L;

    @ColumnDefault("0")
    private Long likeCount = 0L;

    @Enumerated(EnumType.STRING)
    private PostStatType statType;

    private LocalDateTime postCreatedAt;
}

