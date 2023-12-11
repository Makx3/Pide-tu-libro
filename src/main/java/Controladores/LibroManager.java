package Controladores;

import Clases.Libro;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;


public class LibroManager {

    public static final String nombreArchivoLibros = "Libros.csv";

    public static List<Libro> obtenerTodosLibros() {
        List<Libro> librosData = new ArrayList<>();

        try {
            BufferedReader lector = new BufferedReader(new FileReader(nombreArchivoLibros));
            String linea;

            boolean primeraLinea = true;

            while ((linea = lector.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                String[] campos = linea.split(",");

                if (campos.length >= 7) {
                    String idLibro = campos[0].trim();
                    boolean estadoLibro = Boolean.parseBoolean(campos[1].trim());
                    String tituloLibro = campos[2].trim();
                    String autorLibro = campos[3].trim();
                    String isbnLibro = campos[4].trim();
                    String edicionLibro = campos[5].trim();
                    String generoLibro = campos[6].trim();

                    Libro libro = new Libro(idLibro, estadoLibro ,tituloLibro, autorLibro, isbnLibro, edicionLibro, generoLibro);
                    librosData.add(libro);
                }
            }
            lector.close();
        } catch (IOException e) {
            System.err.println("Hubo un error al leer el archivo: " + e.getMessage());
        }
        return librosData;
    }

    public static List<Libro> buscarLibrosPorCriteriosExtendidos(String filtro) {
        List<Libro> librosFiltrados = new ArrayList<>();

        try {
            BufferedReader lector = new BufferedReader(new FileReader(nombreArchivoLibros));
            String linea;

            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");

                if (campos.length >= 7) {
                    String idLibro = campos[0].trim();
                    String estadoStr = campos[1].trim(); // Estado en formato de cadena ("true" o "false")
                    String titulo = campos[2].trim();
                    String autor = campos[3].trim();
                    String isbn = campos[4].trim();
                    String edicion = campos[5].trim();
                    String genero = campos[6].trim();

                    // Verifica si algún campo coincide con el filtro
                    if (titulo.contains(filtro) || autor.contains(filtro) || isbn.contains(filtro) || genero.contains(filtro)) {
                        boolean estado = Boolean.parseBoolean(estadoStr);
                        Libro libro = new Libro(idLibro, estado, titulo, autor, isbn, edicion, genero);
                        librosFiltrados.add(libro);
                    }
                }
            }

            lector.close();
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de libros: " + e.getMessage());
        }

        return librosFiltrados;
    }


    public static void actualizarEstadoLibro(String idLibro, boolean nuevoEstado) {
        List<String> lineas = new ArrayList<>();

        try (BufferedReader lector = new BufferedReader(new FileReader(nombreArchivoLibros))) {
            String linea;

            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos.length >= 7 && campos[0].trim().equals(idLibro)) {
                    campos[1] = String.valueOf(nuevoEstado);
                }
                lineas.add(String.join(",", campos));
            }
        } catch (IOException e) {
            System.err.println("Hubo un error al leer el archivo: " + e.getMessage());
        }

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(nombreArchivoLibros))) {
            for (String linea : lineas) {
                escritor.write(linea);
                escritor.newLine();
            }
        } catch (IOException e) {
            System.err.println("Hubo un error al escribir en el archivo: " + e.getMessage());
        }
    }
}
