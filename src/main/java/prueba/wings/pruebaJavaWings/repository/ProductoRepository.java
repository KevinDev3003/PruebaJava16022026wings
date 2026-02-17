package prueba.wings.pruebaJavaWings.repository;

import prueba.wings.pruebaJavaWings.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {}