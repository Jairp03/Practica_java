package livphoto.dto;

public class FeedPostDTO {

    private Long postId;
    private String contenido;
    private String userEmail;
    private long likes;
    private long comentarios;
    private boolean likedByUser;

    public FeedPostDTO(Long postId,
                       String contenido,
                       String userEmail,
                       long likes,
                       long comentarios,
                       boolean likedByUser) {

        this.postId = postId;
        this.contenido = contenido;
        this.userEmail = userEmail;
        this.likes = likes;
        this.comentarios = comentarios;
        this.likedByUser = likedByUser;
    }

    public Long getPostId() {
        return postId;
    }

    public String getContenido() {
        return contenido;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public long getLikes() {
        return likes;
    }

    public long getComentarios() {
        return comentarios;
    }

    public boolean isLikedByUser() {
        return likedByUser;
    }
}