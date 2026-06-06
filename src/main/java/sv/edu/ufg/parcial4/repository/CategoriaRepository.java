package sv.edu.ufg.parcial4.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import sv.edu.ufg.parcial4.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    List<Categoria> findAllByOrderByNombreAsc();

    List<Categoria> findByNombreContainingIgnoreCaseOrderByNombreAsc(String nombre);
}
