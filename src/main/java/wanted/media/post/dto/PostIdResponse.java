package wanted.media.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostIdResponse<T> {
    private final String postId;
    private final T messgae;
}
