package Controladores;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LibroManager {

    // ATRIBUTOS
    public static final String nombreArchivoLibros = "Libros.csv";

    // MOSTRAR LIBROS CSV
    public static List<Object[]> listadoLibros(String filtroIsbn) {
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

                    if (filtroIsbn == null || isbnLibro.equals(filtroIsbn)) {
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

    public static List<Object[]> listadoLibrosDisponibles() {
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

                    if (!estadoLibro) {
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
