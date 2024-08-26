package wanted.media.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wanted.media.post.dto.PostIdResponse;
import wanted.media.post.service.PostService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/likes/{postId}")
    public ResponseEntity<?> getLikes(@PathVariable(name = "postId") String postId) {
        try {
            String id = postService.increaseLike(postId);
            return ResponseEntity.ok().body(new PostIdResponse<>(id, "좋아요 수 증가 완료"));
        } catch (Exception e) {
            Map<String, String> errorMessage = new HashMap<>();
            errorMessage.put("좋아요 수 증가 실패", e.getMessage());
            return ResponseEntity.internalServerError().body(new PostIdResponse<>(postId, errorMessage));
        }
    }

}
