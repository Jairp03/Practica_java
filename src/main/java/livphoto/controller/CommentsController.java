package livphoto.controller;

import livphoto.model.Comments;
import livphoto.repository.CommentsRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentsController {

    private final CommentsRepository commentsRepository;

    public CommentsController(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    @PostMapping
    public Comments createComment(@RequestBody Comments comments) {
        return commentsRepository.save(comments);
    }

    @GetMapping
    public List<Comments> getAllComments() {
        return commentsRepository.findAll();
    }

    @GetMapping("/post/{postId}")
    public List<Comments> getCommentsByPost(@PathVariable Long postId) {
        return commentsRepository.findByPostId(postId);
    }
}