package sv.edu.ufg.parcial4.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sv.edu.ufg.parcial4.model.Categoria;
import sv.edu.ufg.parcial4.repository.CategoriaRepository;
import sv.edu.ufg.parcial4.service.CategoriaService;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public List<Categoria> listar(String busqueda) {
        if (StringUtils.hasText(busqueda)) {
            return categoriaRepository.findByNombreContainingIgnoreCaseOrderByNombreAsc(busqueda.trim());
        }
        return categoriaRepository.findAllByOrderByNombreAsc();
    }

    @Override
    public Categoria buscarPorId(Long id) {
        return categoriaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Categoria no encontrada"));
    }

    @Override
    public Categoria guardar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public void eliminar(Long id) {
        categoriaRepository.deleteById(id);
    }
}
