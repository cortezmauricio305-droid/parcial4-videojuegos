package sv.edu.ufg.parcial4.service;

import java.util.List;
import sv.edu.ufg.parcial4.model.Categoria;

public interface CategoriaService {

    List<Categoria> listar(String busqueda);

    Categoria buscarPorId(Long id);

    Categoria guardar(Categoria categoria);

    void eliminar(Long id);
}
