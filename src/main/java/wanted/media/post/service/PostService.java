package wanted.media.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.media.post.domain.Post;
import wanted.media.post.domain.Type;
import wanted.media.post.repository.PostRepository;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public Page<Post> findPosts(String account, Type type, String orderBy, String sortDirection, String searchBy, String search, int page, int pageCount) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), orderBy);
        Pageable pageable = PageRequest.of(page, pageCount, sort);

        Page<Post> posts = postRepository.findBySearchContaining(account, type, searchBy, search, pageable);

        return posts;
    }
}
