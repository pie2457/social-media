package wanted.media.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.media.user.domain.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByAccount(String account);
}
