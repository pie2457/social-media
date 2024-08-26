package wanted.media.post.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wanted.media.post.domain.Post;
import wanted.media.post.domain.Type;
import wanted.media.post.dto.PostDto;
import wanted.media.post.service.PostService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

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

}
