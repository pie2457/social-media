package wanted.media.post.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.media.exception.ErrorCode;
import wanted.media.post.domain.Post;
import wanted.media.post.repository.PostLikeRepository;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;

    @Transactional
    public String makeEndpoint(String postId) {
        // contentId를 통해 게시물의 SNS 유형 조회
        Post post = postLikeRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND.getMessage()));

        String snsType = post.getType().name();
        // 외부 SNS API 호출 부분 (기능 개발을 위한 요소로, 실제 동작하지 않음)
        snsApi(snsType, postId);

        // 좋아요 수 증가
        Long like = post.getLikeCount();
        post.addLikeCount(like);
        return postId;
    }

    // 외부 SNS API 호출 부분 (기능 개발을 위한 요소로, 실제 동작하지 않음)
    public void snsApi(String snsType, String postId) {
        String endpoint = "";

        // SNS 유형에 따른 외부 엔드포인트 구성
        switch (snsType.toLowerCase()) {
            case "facebook":
                endpoint = "https://www.facebook.com/likes/" + postId;
                break;
            case "twitter":
                endpoint = "https://www.twitter.com/likes/" + postId;
                break;
            case "instagram":
                endpoint = "https://www.instagram.com/likes/" + postId;
                break;
            case "threads":
                endpoint = "https://www.threads.net/likes/" + postId;
                break;
            default:
                endpoint = null;
        }

        // 요구사항 시나리오에 따라 필요하지만 실제 동작하지 않기에 주석 처리함
//        RestTemplate restTemplate = new RestTemplate();
//        String response = restTemplate.postForObject(endpoint, null, String.class);
    }
}
