package livphoto.controller;

import livphoto.model.Like;
import livphoto.model.Post;
import livphoto.model.Users;
import livphoto.repository.LikeRepository;
import livphoto.repository.PostRepository;
import livphoto.repository.UserRepository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
public class LikeController {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public LikeController(
            LikeRepository likeRepository,
            PostRepository postRepository,
            UserRepository userRepository) {

        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/post/{postId}")
    public String likePost(@PathVariable Long postId) {

        String email = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Users user = userRepository.findByEmail(email);

        if (likeRepository.existsByUserIdAndPostId(user.getId(), postId)) {
            return "Ya diste like a este post 😅";
        }

        Post post = postRepository.findById(postId).orElseThrow();

        Like like = new Like();
        like.setUser(user);
        like.setPost(post);

        likeRepository.save(like);

        return "Like guardado ❤️";
    }

    @GetMapping("/count/{postId}")
    public long countLikesByPost(@PathVariable Long postId) {
        return likeRepository.countByPostId(postId);
    }
}