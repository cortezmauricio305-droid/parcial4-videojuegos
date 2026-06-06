package sv.edu.ufg.parcial4.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import sv.edu.ufg.parcial4.model.Videojuego;

public interface VideojuegoRepository extends JpaRepository<Videojuego, Long> {

    @EntityGraph(attributePaths = {"categoria", "desarrolladora", "plataformas"})
    List<Videojuego> findAllByOrderByTituloAsc();

    @EntityGraph(attributePaths = {"categoria", "desarrolladora", "plataformas"})
    List<Videojuego> findByTituloContainingIgnoreCaseOrderByTituloAsc(String titulo);

    @EntityGraph(attributePaths = {"categoria", "desarrolladora", "plataformas"})
    Optional<Videojuego> findWithRelationsById(Long id);
}
