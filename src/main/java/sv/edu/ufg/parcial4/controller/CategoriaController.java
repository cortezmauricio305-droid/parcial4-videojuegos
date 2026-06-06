package sv.edu.ufg.parcial4.controller;

import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
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
import sv.edu.ufg.parcial4.model.Categoria;
import sv.edu.ufg.parcial4.service.CategoriaService;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) String q, Model model) {
        model.addAttribute("categorias", categoriaService.listar(q));
        model.addAttribute("q", q);
        return "categorias/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("categoria", new Categoria());
        model.addAttribute("tituloFormulario", "Nueva categoria");
        return "categorias/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(
        @Valid @ModelAttribute("categoria") Categoria categoria,
        BindingResult result,
        Model model,
        RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            model.addAttribute("tituloFormulario", categoria.getId() == null ? "Nueva categoria" : "Editar categoria");
            return "categorias/formulario";
        }
        categoriaService.guardar(categoria);
        redirectAttributes.addFlashAttribute("exito", "Categoria guardada correctamente.");
        return "redirect:/categorias";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("categoria", categoriaService.buscarPorId(id));
        model.addAttribute("tituloFormulario", "Editar categoria");
        return "categorias/formulario";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            categoriaService.eliminar(id);
            redirectAttributes.addFlashAttribute("exito", "Categoria eliminada correctamente.");
        } catch (DataIntegrityViolationException ex) {
            redirectAttributes.addFlashAttribute("error", "No se pudo eliminar la categoria porque tiene datos relacionados.");
        }
        return "redirect:/categorias";
    }
}
