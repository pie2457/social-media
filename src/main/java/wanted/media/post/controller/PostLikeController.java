package wanted.media.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wanted.media.post.service.PostLikeService;

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

        // 외부 SNS API 호출 부분 (기능 개발을 위한 요소로, 실제 동작하지 않음)
        try {
            String endpoint = postLikeService.makeEndpoint(postId);
            // 요구사항 시나리오에 따라 필요하지만 실제 동작하지 않기에 주석 처리함
//            RestTemplate restTemplate = new RestTemplate();
//            String response = restTemplate.postForObject(endpoint, null, String.class);
            // 외부 데이터와 동기 시키는게 정상적인 동작이나, 본 과제에서는 내부에서 좋아요 수를 증가 시키도록 처리함
            postLikeService.increaseLike(postId);
            return ResponseEntity.ok().body("좋아요 수 증가 완료");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("SNS API 호출 실패: " + e.getMessage());
        }
    }
}
