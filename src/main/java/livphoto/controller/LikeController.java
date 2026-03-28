package livphoto.controller;

import livphoto.model.Like;
import livphoto.repository.LikeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/likes")
public class LikeController {

    private final LikeRepository likeRepository;

    public LikeController(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @PostMapping
    public String createLike(@RequestBody Like like) {

        Long userId = like.getUser().getId();
        Long postId = like.getPost().getId();

        boolean exists = likeRepository.existsByUserIdAndPostId(userId, postId);

        if (exists) {
            return "Ya diste like a este post 😅";
        }

        likeRepository.save(like);
        return "Like guardado ❤️";
    }

    @GetMapping
    public List<Like> getAllLikes() {
        return likeRepository.findAll();
    }

    @GetMapping("/count/{postId}")
    public long countLikesByPost(@PathVariable Long postId) {
        return likeRepository.countByPostId(postId);
    }
}