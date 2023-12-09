package Controladores;

import java.io.*;

public class csvReservados {

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
}