package wanted.media.content.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.media.content.domain.Post;
import wanted.media.content.domain.Type;
import wanted.media.content.repository.PostRepository;
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

//        if (search == null || search.isEmpty()) {
//            return postRepository.findAll();
//            //throw new IllegalStateException("해당하는 태그를 찾을 수 없습니다.");
//        }

        return postRepository.findBySearchContaining(account, type, searchBy, search, pageable);
    }

}
