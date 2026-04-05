package livphoto.model;

import jakarta.persistence.*;

@Entity
@Table(name = "follows")
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // usuario que sigue
    @ManyToOne
    @JoinColumn(name = "follower_id")
    private Users follower;

    // usuario seguido
    @ManyToOne
    @JoinColumn(name = "following_id")
    private Users following;

    public Follow() {}

    public Long getId() {
        return id;
    }

    public Users getFollower() {
        return follower;
    }

    public void setFollower(Users follower) {
        this.follower = follower;
    }

    public Users getFollowing() {
        return following;
    }

    public void setFollowing(Users following) {
        this.following = following;
    }
}