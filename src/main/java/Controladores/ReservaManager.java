package Controladores;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReservaManager {

    public static final String nombreArchivoReservados = "Reservados.csv";

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

