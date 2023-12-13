package Controladores;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Corresponde a la clase encargada de manejar los datos de las reservas de libros.
 */
public class ReservaManager {

    /**
     * Archivo .csv que almacena la lista de libros.
     */
    public static final String nombreArchivoReservados = "Reservados.csv";

    /**
     * Agrega la fecha de reserva y la guarda en el archivo.
     * @param entradaReservados Fecha en la que se reserva un libro.
     */
    public static void agregarFecha(String entradaReservados) {
        BufferedWriter escritor = null;

        try {
            escritor = new BufferedWriter(new FileWriter(nombreArchivoReservados, true));

            // Agregar la fecha de reserva
            LocalDate fechaReserva = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String fechaReservaStr = fechaReserva.format(formatter);

            escritor.write(entradaReservados + "," + fechaReservaStr);
            escritor.newLine();
        } catch (IOException e) {
            System.err.println("Error al agregar fecha de reserva: " + e.getMessage());
        } finally {
            try {
                if (escritor != null) {
                    escritor.close();
                }
            } catch (IOException ex) {
                System.err.println("Error al cerrar el escritor: " + ex.getMessage());
            }
        }
    }

    /**
     * Verifica si un libro se encuentra reservado.
     * @param idLibro Identificador único del libro.
     * @return "true" si el libro está reservado, "false" si no lo está.
     */
    public static boolean estaLibroReservado(String idLibro) {
        try {
            BufferedReader lector = new BufferedReader(new FileReader(nombreArchivoReservados));
            String linea;

            boolean primeraLinea = true;

            while ((linea = lector.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                String[] campos = linea.split(",");

                if (campos.length >= 5) {
                    String idLibroReservado = campos[4].trim();

                    // Se compara el id del libro con el id del libro reservado
                    if (idLibro.equals(idLibroReservado)) {
                        lector.close();
                        return true;
                    }
                }
            }
            lector.close();
        } catch (IOException e) {
            System.err.println("Hubo un error al leer el archivo de reservados: " + e.getMessage());
        }

        return false;
    }

    /**
     * Obtiene el historial de reservas de un usuario específico.
     * @param rutUsuario Rut del usuario del cual se desea obtener el historial de reservas.
     * @return Lista de Strings que representan la información de las reservas del usuario.
     */
    public static List<String> obtenerReservasUsuario(String rutUsuario) {
        List<String> reservasUsuario = new ArrayList<>();

        try {
            BufferedReader lector = new BufferedReader(new FileReader(nombreArchivoReservados));
            String linea;

            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos.length >= 1 && campos[0].trim().equals(rutUsuario)) {

                    // Obtener solo el título, autor, ISBN y fecha de la reserva
                    String titulo = campos[5].trim();
                    String autor = campos[6].trim();
                    String isbn = campos[8].trim();
                    String fechaReserva = campos[10].trim();

                    // Incluye en la lista solo lo necesario
                    String informacionLibro = String.format("%s - %s (%s) - Reservado el: %s", titulo, autor, isbn, fechaReserva);
                    reservasUsuario.add(informacionLibro);
                }
            }

            lector.close();
        } catch (IOException ex) {
            System.err.println("Error al obtener reservas del usuario: " + ex.getMessage());
        }

        return reservasUsuario;
    }
}

