package livphoto.repository;

import livphoto.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long> {

    List<Comments> findByPostId(Long postId);

    long countByPostId(Long postId);
}