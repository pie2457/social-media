package wanted.media.post.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.media.exception.CustomException;
import wanted.media.exception.ErrorCode;
import wanted.media.post.domain.Post;
import wanted.media.post.domain.Type;
import wanted.media.post.repository.PostRepository;
import wanted.media.user.repository.UserRepository;

@Service
public class PostService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Transactional(readOnly = true)
    public Page<Post> findPosts(String account, Type type, String orderBy, String sortDirection, String searchBy, String search, int page, int pageCount) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), orderBy);
        Pageable pageable = PageRequest.of(page, pageCount, sort);

        // 예시: 검색 조건에 맞는 게시물이 없는 경우 예외 처리
        Page<Post> posts = postRepository.findBySearchContaining(account, type, searchBy, search, pageable);
        if (posts.isEmpty()) {
            throw new CustomException(ErrorCode.ENTITY_NOT_FOUND);
        }

        return posts;
    }
}
