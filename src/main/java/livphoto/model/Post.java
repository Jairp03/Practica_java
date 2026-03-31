package livphoto.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contenido;

    private String imagenUrl;

    private LocalDateTime fecha;

    private String userEmail;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    public Post() {
        this.fecha = LocalDateTime.now();
    }

    public Post(String contenido, String imagenUrl, Users user, String userEmail) {
        this.contenido = contenido;
        this.imagenUrl = imagenUrl;
        this.user = user;
        this.fecha = LocalDateTime.now();
        this.userEmail = userEmail;
    }

    public Long getId() {
        return id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}