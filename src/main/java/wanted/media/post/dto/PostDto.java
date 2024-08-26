package wanted.media.post.dto;

import wanted.media.post.domain.Post;
import wanted.media.post.domain.Type;

import java.time.LocalDateTime;

public record PostDto(
        String id,
        Long likeCount,
        Type type,
        String title,
        String content,
        String hashtags,
        Long viewCount,
        Long shareCount,
        LocalDateTime updatedAt,
        LocalDateTime createdAt,
        String account
) {
    public static PostDto allPosts(Post post) {
        return new PostDto(
                post.getId(),
                post.getLikeCount(),
                post.getType(),
                post.getTitle(),
                post.getContent(),
                post.getHashtags(),
                post.getViewCount(),
                post.getShareCount(),
                post.getUpdatedAt(),
                post.getCreatedAt(),
                post.getUser().getAccount()
        );
    }
}
