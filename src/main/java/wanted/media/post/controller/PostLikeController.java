package wanted.media.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wanted.media.post.service.PostLikeService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/likes/{postId}")
    public ResponseEntity getLikes(@PathVariable(name = "postId") String postId) {
        if (postId == null) {
            return ResponseEntity.badRequest().body("잘못된 요청입니다.");
        }

        try {
            String id = postLikeService.makeEndpoint(postId);
            Map<String, Object> response = new HashMap<>();
            response.put("postId", id);
            response.put("message", "좋아요 수 증가 완료");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("SNS API 호출 실패: " + e.getMessage());
        }
    }
}
