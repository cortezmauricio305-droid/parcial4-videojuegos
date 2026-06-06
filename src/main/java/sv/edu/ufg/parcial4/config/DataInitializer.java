package sv.edu.ufg.parcial4.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sv.edu.ufg.parcial4.model.Categoria;
import sv.edu.ufg.parcial4.model.Desarrolladora;
import sv.edu.ufg.parcial4.model.Plataforma;
import sv.edu.ufg.parcial4.model.Videojuego;
import sv.edu.ufg.parcial4.repository.CategoriaRepository;
import sv.edu.ufg.parcial4.repository.DesarrolladoraRepository;
import sv.edu.ufg.parcial4.repository.PlataformaRepository;
import sv.edu.ufg.parcial4.repository.VideojuegoRepository;

@Configuration
public class DataInitializer {

    @Bean
    ApplicationRunner cargarDatosIniciales(
        CategoriaRepository categoriaRepository,
        DesarrolladoraRepository desarrolladoraRepository,
        PlataformaRepository plataformaRepository,
        VideojuegoRepository videojuegoRepository
    ) {
        return args -> {
            if (videojuegoRepository.count() > 0) {
                return;
            }

            Categoria accion = categoria("Accion", "Juegos con combate, reflejos y misiones intensas.");
            Categoria aventura = categoria("Aventura", "Juegos enfocados en exploracion e historia.");
            Categoria estrategia = categoria("Estrategia", "Juegos de planificacion y toma de decisiones.");
            categoriaRepository.saveAll(List.of(accion, aventura, estrategia));

            Desarrolladora nintendo = desarrolladora("Nintendo", "Japon", "https://www.nintendo.com");
            Desarrolladora valve = desarrolladora("Valve", "Estados Unidos", "https://www.valvesoftware.com");
            Desarrolladora local = desarrolladora("Pixel Studio SV", "El Salvador", "https://pixelstudio.example");
            desarrolladoraRepository.saveAll(List.of(nintendo, valve, local));

            Plataforma switchPlataforma = plataforma("Nintendo Switch", "Nintendo", 2017);
            Plataforma pc = plataforma("PC", "Varios", null);
            Plataforma playstation = plataforma("PlayStation 5", "Sony", 2020);
            plataformaRepository.saveAll(List.of(switchPlataforma, pc, playstation));

            Videojuego zelda = videojuego(
                "Sky Kingdom",
                "Aventura de mundo abierto con exploracion y acertijos.",
                new BigDecimal("59.99"),
                18,
                LocalDate.of(2025, 9, 12),
                "E10+",
                aventura,
                nintendo,
                switchPlataforma
            );
            Videojuego portal = videojuego(
                "Portal Lab",
                "Juego de rompecabezas con mecanicas de portales.",
                new BigDecimal("19.99"),
                25,
                LocalDate.of(2024, 4, 5),
                "T",
                estrategia,
                valve,
                pc
            );
            Videojuego volcano = videojuego(
                "Volcano Battle",
                "Accion cooperativa con escenarios inspirados en volcanes.",
                new BigDecimal("34.50"),
                10,
                LocalDate.of(2026, 2, 20),
                "T",
                accion,
                local,
                pc,
                playstation
            );
            videojuegoRepository.saveAll(List.of(zelda, portal, volcano));
        };
    }

    private Categoria categoria(String nombre, String descripcion) {
        Categoria categoria = new Categoria();
        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion);
        categoria.setActivo(true);
        return categoria;
    }

    private Desarrolladora desarrolladora(String nombre, String pais, String sitioWeb) {
        Desarrolladora desarrolladora = new Desarrolladora();
        desarrolladora.setNombre(nombre);
        desarrolladora.setPais(pais);
        desarrolladora.setSitioWeb(sitioWeb);
        return desarrolladora;
    }

    private Plataforma plataforma(String nombre, String fabricante, Integer anioLanzamiento) {
        Plataforma plataforma = new Plataforma();
        plataforma.setNombre(nombre);
        plataforma.setFabricante(fabricante);
        plataforma.setAnioLanzamiento(anioLanzamiento);
        return plataforma;
    }

    private Videojuego videojuego(
        String titulo,
        String descripcion,
        BigDecimal precio,
        Integer stock,
        LocalDate fechaLanzamiento,
        String clasificacionEdad,
        Categoria categoria,
        Desarrolladora desarrolladora,
        Plataforma... plataformas
    ) {
        Videojuego videojuego = new Videojuego();
        videojuego.setTitulo(titulo);
        videojuego.setDescripcion(descripcion);
        videojuego.setPrecio(precio);
        videojuego.setStock(stock);
        videojuego.setFechaLanzamiento(fechaLanzamiento);
        videojuego.setClasificacionEdad(clasificacionEdad);
        videojuego.setActivo(true);
        videojuego.setCategoria(categoria);
        videojuego.setDesarrolladora(desarrolladora);
        videojuego.getPlataformas().addAll(List.of(plataformas));
        return videojuego;
    }
}
