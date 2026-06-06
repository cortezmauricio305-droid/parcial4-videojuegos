package sv.edu.ufg.parcial4.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import sv.edu.ufg.parcial4.model.Desarrolladora;

public interface DesarrolladoraRepository extends JpaRepository<Desarrolladora, Long> {

    List<Desarrolladora> findAllByOrderByNombreAsc();
}
