package sv.edu.ufg.parcial4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sv.edu.ufg.parcial4.repository.CategoriaRepository;
import sv.edu.ufg.parcial4.repository.DesarrolladoraRepository;
import sv.edu.ufg.parcial4.repository.PlataformaRepository;
import sv.edu.ufg.parcial4.service.VideojuegoService;

@Controller
public class InicioController {

    private final VideojuegoService videojuegoService;
    private final CategoriaRepository categoriaRepository;
    private final PlataformaRepository plataformaRepository;
    private final DesarrolladoraRepository desarrolladoraRepository;

    public InicioController(
        VideojuegoService videojuegoService,
        CategoriaRepository categoriaRepository,
        PlataformaRepository plataformaRepository,
        DesarrolladoraRepository desarrolladoraRepository
    ) {
        this.videojuegoService = videojuegoService;
        this.categoriaRepository = categoriaRepository;
        this.plataformaRepository = plataformaRepository;
        this.desarrolladoraRepository = desarrolladoraRepository;
    }

    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("totalVideojuegos", videojuegoService.contar());
        model.addAttribute("totalCategorias", categoriaRepository.count());
        model.addAttribute("totalPlataformas", plataformaRepository.count());
        model.addAttribute("totalDesarrolladoras", desarrolladoraRepository.count());
        return "index";
    }
}
