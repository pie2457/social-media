package wanted.media.content.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import wanted.media.user.domain.User;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="contents")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "content_id", nullable = false)
    private Long id;
    private Long likeCount;
    @Size(max = 50)
    private String type;
    @Size(max = 150)
    private String title;
    private String content;
    private String hashtags;
    private Long viewCount;
    private Long shareCount;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

}
