package livphoto.controller;

import livphoto.model.Post;
import livphoto.model.Users;
import livphoto.repository.PostRepository;
import livphoto.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import livphoto.repository.LikeRepository;
import livphoto.repository.CommentsRepository;
import livphoto.dto.FeedPostDTO;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final CommentsRepository commentsRepository;

    public PostController(PostRepository postRepository,
                          UserRepository userRepository,
                          LikeRepository likeRepository,
                          CommentsRepository commentsRepository) {

        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
        this.commentsRepository = commentsRepository;
    }

    @PostMapping
    public Post createPost(@RequestBody Post post) {

        // 🔥 Obtener usuario del token
        String email = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        // 🔥 Asignar usuario al post
        post.setUserEmail(email);

        return postRepository.save(post);
    }
    @GetMapping
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @GetMapping("/user/{userId}")
    public List<Post> getPostsByUser(@PathVariable Long userId) {
        return postRepository.findByUserId(userId);
    }

    @GetMapping("/feed")
    public List<FeedPostDTO> getFeed() {

        String email = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Users user = userRepository.findByEmail(email);

        List<Post> posts = postRepository.findAllByOrderByFechaDesc();

        return posts.stream().map(post -> {

            long likes = likeRepository.countByPostId(post.getId());

            long comentarios = commentsRepository.countByPostId(post.getId());

            boolean likedByUser = likeRepository
                    .existsByUserIdAndPostId(user.getId(), post.getId());

            return new FeedPostDTO(
                    post.getId(),
                    post.getContenido(),
                    post.getUserEmail(),
                    likes,
                    comentarios,
                    likedByUser
            );

        }).toList();
    }

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("image") MultipartFile file) {

        try {

            String uploadDir = "uploads/";

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            Path filePath = Paths.get(uploadDir + fileName);

            Files.write(filePath, file.getBytes());

            return fileName;

        } catch (Exception e) {
            return "Error al subir imagen";
        }
    }
}