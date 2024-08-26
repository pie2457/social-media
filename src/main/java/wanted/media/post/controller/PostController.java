package wanted.media.post.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wanted.media.post.domain.Post;
import wanted.media.post.domain.Type;
import wanted.media.post.dto.PostDto;
import wanted.media.post.service.PostService;
import wanted.media.post.dto.PostDetailResponse;
import wanted.media.post.dto.PostIdResponse;

import java.util.List;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService posetService;

    /**
     * @param postId
     * @return PostDetailResponse
     */
    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailResponse> getPost(@PathVariable String postId) {
        Post post = posetService.getPost(postId);
        PostDetailResponse result = PostDetailResponse.builder()
                .postId(post.getId())
                .likeCount(post.getLikeCount())
                .type(post.getType())
                .title(post.getTitle())
                .content(post.getContent())
                .hashtags(post.getHashtags())
                .viewCount(post.getViewCount())
                .shareCount(post.getShareCount())
                .updatedAt(post.getUpdatedAt())
                .createdAt(post.getCreatedAt())
                .userId(post.getUser().getUserId())
                .account(post.getUser().getAccount())
                .email(post.getUser().getEmail())
                .build();
        return ResponseEntity.ok(result);
    }
  
    @GetMapping
    public ResponseEntity<List<PostDto>> list(@RequestParam(value = "hashtag", required = true) String account,
                                              @RequestParam(value = "type", required = false) Type type,
                                              @RequestParam(value = "orderBy", defaultValue = "createdAt") String orderBy,
                                              @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection,
                                              @RequestParam(value = "search_by", defaultValue = "title, content") String searchBy,
                                              @RequestParam(value = "search", required = false) String search,
                                              @RequestParam(value = "page", defaultValue = "0") int page,
                                              @RequestParam(value = "page_count", defaultValue = "10") int pageCount) {
        Page<Post> postPage = postService.findPosts(account, type, orderBy, sortDirection, searchBy, search, page, pageCount);
        List<PostDto> postDtos = postPage.getContent().stream()
                .map(PostDto::allPosts)
                .collect(Collectors.toList());

        if (postDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

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
