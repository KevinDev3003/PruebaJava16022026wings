package prueba.wings.pruebaJavaWings.controller;

import prueba.wings.pruebaJavaWings.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
public class AuthController {
    private final UsuarioRepository usuarioRepository;

    public AuthController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping({"/", "/login"})
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String usuario,
                          @RequestParam String contrasena,
                          HttpSession session,
                          Model model) {

        return usuarioRepository.findByIdUsuarioAndContrasena(usuario, contrasena)
                .map(u -> {
                    session.setAttribute("user", u.getIdUsuario());
                    return "redirect:/productos";
                })
                .orElseGet(() -> {
                    model.addAttribute("error", "Usuario o contraseña incorrectos");
                    return "login";
                });
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}

