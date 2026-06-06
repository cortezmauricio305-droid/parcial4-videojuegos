package sv.edu.ufg.parcial4.service.impl;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import sv.edu.ufg.parcial4.model.Categoria;
import sv.edu.ufg.parcial4.model.Desarrolladora;
import sv.edu.ufg.parcial4.model.Plataforma;
import sv.edu.ufg.parcial4.model.Videojuego;
import sv.edu.ufg.parcial4.repository.CategoriaRepository;
import sv.edu.ufg.parcial4.repository.DesarrolladoraRepository;
import sv.edu.ufg.parcial4.repository.PlataformaRepository;
import sv.edu.ufg.parcial4.repository.VideojuegoRepository;
import sv.edu.ufg.parcial4.service.VideojuegoService;

@Service
public class VideojuegoServiceImpl implements VideojuegoService {

    private final VideojuegoRepository videojuegoRepository;
    private final CategoriaRepository categoriaRepository;
    private final DesarrolladoraRepository desarrolladoraRepository;
    private final PlataformaRepository plataformaRepository;

    public VideojuegoServiceImpl(
        VideojuegoRepository videojuegoRepository,
        CategoriaRepository categoriaRepository,
        DesarrolladoraRepository desarrolladoraRepository,
        PlataformaRepository plataformaRepository
    ) {
        this.videojuegoRepository = videojuegoRepository;
        this.categoriaRepository = categoriaRepository;
        this.desarrolladoraRepository = desarrolladoraRepository;
        this.plataformaRepository = plataformaRepository;
    }

    @Override
    public List<Videojuego> listar(String busqueda) {
        if (StringUtils.hasText(busqueda)) {
            return videojuegoRepository.findByTituloContainingIgnoreCaseOrderByTituloAsc(busqueda.trim());
        }
        return videojuegoRepository.findAllByOrderByTituloAsc();
    }

    @Override
    public Videojuego buscarPorId(Long id) {
        return videojuegoRepository.findWithRelationsById(id)
            .orElseThrow(() -> new IllegalArgumentException("Videojuego no encontrado"));
    }

    @Override
    @Transactional
    public Videojuego guardar(
        Videojuego videojuego,
        Long categoriaId,
        Long desarrolladoraId,
        Collection<Long> plataformaIds
    ) {
        Categoria categoria = categoriaRepository.findById(categoriaId)
            .orElseThrow(() -> new IllegalArgumentException("Categoria no encontrada"));
        Desarrolladora desarrolladora = desarrolladoraRepository.findById(desarrolladoraId)
            .orElseThrow(() -> new IllegalArgumentException("Desarrolladora no encontrada"));
        Set<Plataforma> plataformas = new LinkedHashSet<>();
        if (!CollectionUtils.isEmpty(plataformaIds)) {
            plataformas.addAll(plataformaRepository.findAllById(plataformaIds));
        }

        videojuego.setCategoria(categoria);
        videojuego.setDesarrolladora(desarrolladora);
        videojuego.setPlataformas(plataformas);
        return videojuegoRepository.save(videojuego);
    }

    @Override
    public void eliminar(Long id) {
        videojuegoRepository.deleteById(id);
    }

    @Override
    public long contar() {
        return videojuegoRepository.count();
    }
}
