package wanted.media.post.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import wanted.media.exception.ErrorCode;
import wanted.media.post.domain.Post;
import wanted.media.post.repository.PostLikeRepository;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;

    @Transactional(readOnly = true)
    public String makeEndpoint(String postId) {
        // contentId를 통해 게시물의 SNS 유형 조회
        Post post = postLikeRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND.getMessage()));
        String snsType = post.getType().name();

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
        return endpoint;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void increaseLike(String postId) {
        postLikeRepository.incrementLikeCount(postId);
    }
}
