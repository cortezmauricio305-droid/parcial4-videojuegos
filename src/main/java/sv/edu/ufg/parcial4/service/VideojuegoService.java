package sv.edu.ufg.parcial4.service;

import java.util.Collection;
import java.util.List;
import sv.edu.ufg.parcial4.model.Videojuego;

public interface VideojuegoService {

    List<Videojuego> listar(String busqueda);

    Videojuego buscarPorId(Long id);

    Videojuego guardar(Videojuego videojuego, Long categoriaId, Long desarrolladoraId, Collection<Long> plataformaIds);

    void eliminar(Long id);

    long contar();
}
