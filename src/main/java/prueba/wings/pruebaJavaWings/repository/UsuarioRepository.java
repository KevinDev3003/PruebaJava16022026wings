package prueba.wings.pruebaJavaWings.repository;
import prueba.wings.pruebaJavaWings.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Optional<Usuario> findByIdUsuarioAndContrasena(String idUsuario, String contrasena);
}
