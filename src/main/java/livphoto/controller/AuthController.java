package livphoto.controller;

import livphoto.config.JwtUtil;
import livphoto.model.Users;
import livphoto.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository,
                          BCryptPasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // REGISTRO
    @PostMapping("/register")
    public Users register(@RequestBody Users user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    // LOGIN
    @PostMapping("/login")
    public java.util.Map<String, String> login(@RequestBody Users user) {

        Users existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        if (!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        String token = jwtUtil.generateToken(existingUser.getEmail());

        return java.util.Map.of("token", token);
    }
}