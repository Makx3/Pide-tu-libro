package Controladores;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ReservaManager {

    public static final String nombreArchivoReservados = "Reservados.csv";

    public static void agregarReservado(String entradaReservados) {
        BufferedWriter escritor = null;

        try {
            escritor = new BufferedWriter(new FileWriter(nombreArchivoReservados, true));

            // Agregar la fecha de reserva con formato día-mes-año
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String fechaReservaStr = LocalDate.now().format(formatter);

            entradaReservados += "," + fechaReservaStr;

            escritor.write(entradaReservados);
            escritor.newLine();
        } catch (IOException e) {
            System.err.println("Error al agregar reservado: " + e.getMessage());
        } finally {
            try {
                if (escritor != null) {
                    escritor.close();
                }
            } catch (IOException ex) {
                System.err.println("Error al cerrar el escritor: " + ex.getMessage());
            }
        }

        // Verificar morosidad después de agregar una reserva
        String rutUsuario = obtenerRutDesdeEntradaReservados(entradaReservados);
        verificarMoroso(rutUsuario);
    }

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

    private static String obtenerRutDesdeEntradaReservados(String entradaReservados) {
        // Implementa la lógica para obtener el rut desde la entrada de reservados
        // Aquí, se asume que el rut está en la primera posición antes de la coma
        String[] campos = entradaReservados.split(",");
        return campos.length > 0 ? campos[0].trim() : null;
    }

    public static void verificarMoroso(String rut) {
        try {
            BufferedReader lector = new BufferedReader(new FileReader(nombreArchivoReservados));
            String linea;

            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos.length >= 6 && campos[0].trim().equals(rut)) {
                    LocalDate fechaReserva = LocalDate.parse(campos[5].trim(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    LocalDate fechaActual = LocalDate.now();

                    // Verificar si ha pasado una semana
                    if (fechaActual.minusDays(7).isAfter(fechaReserva)) {
                        // Actualizar el estado del usuario a "Moroso"
                        UsuarioManager.actualizarEstadoUsuario(rut, false);
                        System.out.println("Usuario " + rut + " ha pasado a estado Moroso.");
                    }
                }
            }

            lector.close();
        } catch (IOException | NumberFormatException ex) {
            System.err.println("Error al verificar si el usuario está moroso: " + ex.getMessage());
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

