package wanted.media.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.media.user.domain.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
