package Controladores;

import java.io.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
public class ReservaManager {

    public static final String nombreArchivoReservados = "Reservados.csv";

    public static void agregarReservado(String entradaReservados) {
        try {
            BufferedWriter escritor = new BufferedWriter(new FileWriter(nombreArchivoReservados, true));
            escritor.write(entradaReservados);
            escritor.newLine();
            escritor.close();
        } catch (IOException e) {
            System.out.println("Error al agregar reservado: " + e.getMessage());
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

    public static String obtenerUltimoLibroReservadoPorUsuario(String rut) {
        try {
            BufferedReader lector = new BufferedReader(new FileReader(nombreArchivoReservados));
            List<String> reservasPorUsuario = new ArrayList<>();

            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] camposReserva = linea.split(",");
                if (camposReserva.length >= 4 && camposReserva[0].trim().equals(rut)) {
                    reservasPorUsuario.add(linea);
                }
            }

            lector.close();

            if (!reservasPorUsuario.isEmpty()) {
                // Ordena las reservas por el número de reserva
                Collections.sort(reservasPorUsuario, Comparator.comparingInt(reserva -> Integer.parseInt(reserva.split(",")[3].trim())));
                String ultimaReserva = reservasPorUsuario.get(reservasPorUsuario.size() - 1);

                // Devuelve el título del libro de la última reserva
                String[] camposUltimaReserva = ultimaReserva.split(",");
                if (camposUltimaReserva.length >= 6) {
                    return camposUltimaReserva[5].trim();
                }
            }
        } catch (IOException | NumberFormatException ex) {
            System.err.println("Error al obtener el último libro reservado por usuario: " + ex.getMessage());
        }

        return null;
    }
}
