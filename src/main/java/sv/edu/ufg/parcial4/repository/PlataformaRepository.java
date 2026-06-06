package sv.edu.ufg.parcial4.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import sv.edu.ufg.parcial4.model.Plataforma;

public interface PlataformaRepository extends JpaRepository<Plataforma, Long> {

    List<Plataforma> findAllByOrderByNombreAsc();
}
