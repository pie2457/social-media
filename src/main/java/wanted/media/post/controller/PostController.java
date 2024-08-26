package wanted.media.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wanted.media.post.dto.PostIdResponse;
import wanted.media.post.service.PostService;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/likes/{postId}")
    public ResponseEntity getLikes(@PathVariable(name = "postId") String postId) {
        if (postId == null) {
            return ResponseEntity.badRequest().body("잘못된 요청입니다.");
        }

        try {
            String id = postService.increaseLike(postId);
            return ResponseEntity.ok().body(new PostIdResponse(id, "좋아요 수 증가 완료"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("좋아요 수 증가 실패 : " + e.getMessage());
        }
    }
}
