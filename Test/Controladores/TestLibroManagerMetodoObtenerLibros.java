package Controladores;


import org.junit.jupiter.api.Test;

import Clases.*;


import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class TestLibroManagerMetodoObtenerLibros {


    @Test
    void obtenerTodosLibros() {
        List<Libro> libros = LibroManager.obtenerTodosLibros();

        assertNotNull(libros);
        assertEquals(30, libros.size());

        assertEquals("El Señor de los Anillos", libros.get(0).getTituloLibro());
        assertEquals("Cien años de soledad", libros.get(1).getTituloLibro());
        assertEquals("1984", libros.get(2).getTituloLibro());
    }
}
