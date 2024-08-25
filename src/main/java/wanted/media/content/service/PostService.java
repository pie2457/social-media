package wanted.media.content.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.media.content.domain.Post;
import wanted.media.content.repository.PostRepository;
import wanted.media.user.repository.UserRepository;

import java.util.List;

@Service
public class PostService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<Post> findPosts(String account) {
        if (account == null || account.isEmpty()) {
            throw new IllegalStateException("해당하는 태그를 찾을 수 없습니다.");
        }

        return postRepository.findBySearchContaining(account);
    }

}
