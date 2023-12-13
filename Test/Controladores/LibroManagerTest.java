package Controladores;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Clases.*;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class LibroManagerTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void buscarLibrosPorCriteriosExtendidos_valido() {
        // Cadena a buscar
        String cadenaBusqueda = "Anillos";

        // Act
        List<Libro> libros = LibroManager.buscarLibrosPorCriteriosExtendidos(cadenaBusqueda);

        // Assert
        assertNotNull(libros);
        assertFalse(libros.isEmpty());

        // Verificar que al menos un libro contenga la cadena de búsqueda en el nombre
        assertTrue(libros.stream().anyMatch(libro -> libro.getTituloLibro().contains(cadenaBusqueda)));
    }

    @Test
    void buscarLibrosPorCriteriosExtendidos_fallido() {
        // Cadena a buscar
        String cadenaBusqueda = "libro no existente";

        // Act
        List<Libro> libros = LibroManager.buscarLibrosPorCriteriosExtendidos(cadenaBusqueda);

        // Assert
        assertNotNull(libros);
        assertTrue(libros.isEmpty());

        // Verificar que al menos un libro contenga la cadena de búsqueda en el nombre
        assertFalse(libros.stream().anyMatch(libro -> libro.getTituloLibro().contains(cadenaBusqueda)));
    }

    @Test
    void obtenerTodosLibrosPassingTest() {
        List<Libro> libros = LibroManager.obtenerTodosLibros();

        // Verificar que la lista no sea nula
        assertNotNull(libros);

        // Verificar que la lista no esté vacía
        assertFalse(libros.isEmpty());

        // Verificar que la lista tenga exactamente 30 libros
        assertEquals(30, libros.size());
    }
}

