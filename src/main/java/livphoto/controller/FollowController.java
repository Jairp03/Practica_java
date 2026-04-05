package livphoto.controller;

import livphoto.model.Follow;
import livphoto.model.Users;
import livphoto.repository.FollowRepository;
import livphoto.repository.UserRepository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follow")
public class FollowController {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public FollowController(FollowRepository followRepository,
                            UserRepository userRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/{userId}")
    public String followUser(@PathVariable Long userId) {

        String email = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Users follower = userRepository.findByEmail(email);

        Users following = userRepository.findById(userId).orElseThrow();

        if (followRepository.existsByFollowerIdAndFollowingId(follower.getId(), userId)) {
            return "Ya sigues a este usuario";
        }

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowing(following);

        followRepository.save(follow);

        return "Ahora sigues a este usuario";
    }
    @GetMapping("/followers/{userId}")
    public long countFollowers(@PathVariable Long userId) {
        return followRepository.countByFollowingId(userId);
    }
}