package Controladores;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LibroManager {

    public static final String nombreArchivoLibros = "Libros.csv";

    public static List<Object[]> obtenerTodosLibros() {
        List<Object[]> librosData = new ArrayList<>();

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
                    String tituloLibro = campos[1].trim();
                    String autorLibro = campos[2].trim();
                    boolean estadoLibro = Boolean.parseBoolean(campos[3].trim());
                    String isbnLibro = campos[4].trim();
                    String edicionLibro = campos[5].trim();
                    String generoLibro = campos[6].trim();

                    Object[] rowData = {idLibro, tituloLibro, autorLibro, estadoLibro, isbnLibro, edicionLibro, generoLibro};
                    librosData.add(rowData);
                }
            }
            lector.close();
        } catch (IOException e) {
            System.err.println("Hubo un error al leer el archivo: " + e.getMessage());
        }
        return librosData;
    }

    public static List<Object[]> buscarLibrosPorNombre(String nombre) {
        List<Object[]> librosData = new ArrayList<>();

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
                    String tituloLibro = campos[1].trim();
                    String autorLibro = campos[2].trim();
                    boolean estadoLibro = Boolean.parseBoolean(campos[3].trim());
                    String isbnLibro = campos[4].trim();
                    String edicionLibro = campos[5].trim();
                    String generoLibro = campos[6].trim();

                    if (!estadoLibro && tituloLibro.equalsIgnoreCase(nombre)) {
                        Object[] rowData = {idLibro, tituloLibro, autorLibro, estadoLibro, isbnLibro, edicionLibro, generoLibro};
                        librosData.add(rowData);
                    }
                }
            }
            lector.close();
        } catch (IOException e) {
            System.err.println("Hubo un error al leer el archivo: " + e.getMessage());
        }
        return librosData;
    }
}
