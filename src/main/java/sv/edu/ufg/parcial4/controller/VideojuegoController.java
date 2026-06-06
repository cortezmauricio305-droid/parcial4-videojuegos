package sv.edu.ufg.parcial4.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sv.edu.ufg.parcial4.model.Videojuego;
import sv.edu.ufg.parcial4.repository.CategoriaRepository;
import sv.edu.ufg.parcial4.repository.DesarrolladoraRepository;
import sv.edu.ufg.parcial4.repository.PlataformaRepository;
import sv.edu.ufg.parcial4.service.VideojuegoService;

@Controller
@RequestMapping("/videojuegos")
public class VideojuegoController {

    private final VideojuegoService videojuegoService;
    private final CategoriaRepository categoriaRepository;
    private final DesarrolladoraRepository desarrolladoraRepository;
    private final PlataformaRepository plataformaRepository;

    public VideojuegoController(
        VideojuegoService videojuegoService,
        CategoriaRepository categoriaRepository,
        DesarrolladoraRepository desarrolladoraRepository,
        PlataformaRepository plataformaRepository
    ) {
        this.videojuegoService = videojuegoService;
        this.categoriaRepository = categoriaRepository;
        this.desarrolladoraRepository = desarrolladoraRepository;
        this.plataformaRepository = plataformaRepository;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) String q, Model model) {
        model.addAttribute("videojuegos", videojuegoService.listar(q));
        model.addAttribute("q", q);
        return "videojuegos/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        prepararFormulario(model, new Videojuego(), "Nuevo videojuego");
        model.addAttribute("plataformaIdsSeleccionadas", List.of());
        return "videojuegos/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(
        @Valid @ModelAttribute("videojuego") Videojuego videojuego,
        BindingResult result,
        @RequestParam Long categoriaId,
        @RequestParam Long desarrolladoraId,
        @RequestParam(required = false) List<Long> plataformaIds,
        Model model,
        RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            prepararFormulario(model, videojuego, videojuego.getId() == null ? "Nuevo videojuego" : "Editar videojuego");
            model.addAttribute("categoriaIdSeleccionada", categoriaId);
            model.addAttribute("desarrolladoraIdSeleccionada", desarrolladoraId);
            model.addAttribute("plataformaIdsSeleccionadas", plataformaIds == null ? List.of() : plataformaIds);
            return "videojuegos/formulario";
        }
        videojuegoService.guardar(videojuego, categoriaId, desarrolladoraId, plataformaIds);
        redirectAttributes.addFlashAttribute("exito", "Videojuego guardado correctamente.");
        return "redirect:/videojuegos";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Videojuego videojuego = videojuegoService.buscarPorId(id);
        prepararFormulario(model, videojuego, "Editar videojuego");
        model.addAttribute("categoriaIdSeleccionada", videojuego.getCategoria().getId());
        model.addAttribute("desarrolladoraIdSeleccionada", videojuego.getDesarrolladora().getId());
        model.addAttribute(
            "plataformaIdsSeleccionadas",
            videojuego.getPlataformas().stream().map(plataforma -> plataforma.getId()).toList()
        );
        return "videojuegos/formulario";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        model.addAttribute("videojuego", videojuegoService.buscarPorId(id));
        return "videojuegos/detalle";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        videojuegoService.eliminar(id);
        redirectAttributes.addFlashAttribute("exito", "Videojuego eliminado correctamente.");
        return "redirect:/videojuegos";
    }

    private void prepararFormulario(Model model, Videojuego videojuego, String tituloFormulario) {
        model.addAttribute("videojuego", videojuego);
        model.addAttribute("tituloFormulario", tituloFormulario);
        model.addAttribute("categorias", categoriaRepository.findAllByOrderByNombreAsc());
        model.addAttribute("desarrolladoras", desarrolladoraRepository.findAllByOrderByNombreAsc());
        model.addAttribute("plataformas", plataformaRepository.findAllByOrderByNombreAsc());
    }
}
