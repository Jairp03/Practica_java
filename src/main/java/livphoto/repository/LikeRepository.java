package livphoto.repository;

import livphoto.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    long countByPostId(Long postId);

    boolean existsByUserIdAndPostId(Long userId, Long postId);
}