package wanted.media.content.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wanted.media.content.domain.Post;
import wanted.media.content.domain.Type;
import wanted.media.content.dto.PostDto;
import wanted.media.content.service.PostService;

import java.util.List;
import java.util.stream.Collectors;

import static org.hibernate.query.sqm.tree.SqmNode.log;


@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping
    public List<PostDto> list(@RequestParam(value = "hashtag", required = true) String account,
                              @RequestParam(value = "type", required = false) Type type,
                              @RequestParam(value = "orderBy", defaultValue = "createdAt") String orderBy,
                              @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection,
                              @RequestParam(value = "search_by", defaultValue = "title, content") String searchBy,
                              @RequestParam(value = "search", required = false) String search) {
        List<Post> posts = postService.findPosts(account, type, orderBy, sortDirection, searchBy, search);
        log.info("Content List : " + posts);
        return posts.stream()
                .map(PostDto::allPosts)
                .collect(Collectors.toList());
    }
}