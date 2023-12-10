package Controladores;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import Clases.Usuario;

public class UsuarioManager {
    public static final String nombreArchivoUsuarios = "Usuarios.csv";

    public static Usuario verificarAutenticacion(String rut, String contraseña) {
        try (BufferedReader lector = new BufferedReader(new FileReader(nombreArchivoUsuarios))) {
            String linea;

            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");

                if (campos.length >= 3 && campos[0].trim().equals(rut) && campos[1].trim().equals(contraseña)) {
                    return new Usuario(campos[0].trim(), campos[1].trim(), campos[2].trim(),
                            campos[3].trim(), Boolean.parseBoolean(campos[4].trim()),
                            Integer.parseInt(campos[5].trim()));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al verificar autenticación: " + e.getMessage());
        }

        return null;
    }

    public static void actualizarEstadoUsuario(String rut, boolean nuevoEstado) {
        BufferedReader lector = null;
        BufferedWriter escritor = null;
        String archivoTemporal = "UsuariosTemp.csv";

        try {
            lector = new BufferedReader(new FileReader(nombreArchivoUsuarios));
            escritor = new BufferedWriter(new FileWriter(archivoTemporal, true));

            String linea;
            boolean primeraLinea = true;

            while ((linea = lector.readLine()) != null) {
                if (primeraLinea) {
                    escritor.write(linea);
                    escritor.newLine();
                    primeraLinea = false;
                    continue;
                }

                String[] campos = linea.split(",");

                if (campos.length >= 6 && campos[0].trim().equals(rut)) {
                    campos[4] = String.valueOf(nuevoEstado);
                }

                escritor.write(String.join(",", campos));
                escritor.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al actualizar el estado del usuario: " + e.getMessage());
        } finally {
            try {
                if (lector != null) {
                    lector.close();
                }
                if (escritor != null) {
                    escritor.close();
                }
                // Eliminar el archivo temporal
                Files.delete(Paths.get(archivoTemporal));
            } catch (IOException ex) {
                System.err.println("Error al cerrar el lector/escritor: " + ex.getMessage());
            }
        }
    }

    public static boolean verificarMorosidad(String rut) {
        BufferedReader lector = null;

        try {
            lector = new BufferedReader(new FileReader(ReservaManager.nombreArchivoReservados));
            String linea;

            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");

                if (campos.length >= 7 && campos[0].trim().equals(rut)) {
                    String fechaReservaStr = campos[6].trim();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate fechaReserva = LocalDate.parse(fechaReservaStr, formatter);

                    LocalDate fechaActual = LocalDate.now();

                    long diasDiferencia = ChronoUnit.DAYS.between(fechaReserva, fechaActual);

                    return diasDiferencia > 7;
                }
            }
        } catch (IOException e) {
            System.err.println("Error al verificar morosidad: " + e.getMessage());
        } finally {
            try {
                if (lector != null) {
                    lector.close();
                }
            } catch (IOException ex) {
                System.err.println("Error al cerrar el lector: " + ex.getMessage());
            }
        }

        return false;
    }

    public static void actualizarCantidadReservados(String rut, int nuevaCantidadReservados) {
        BufferedReader lector = null;
        BufferedWriter escritor = null;

        try {
            lector = new BufferedReader(new FileReader(nombreArchivoUsuarios));
            List<String> lineas = new ArrayList<>();

            String linea;
            boolean primeraLinea = true;

            while ((linea = lector.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    lineas.add(linea);
                    continue;
                }

                String[] campos = linea.split(",");
                if (campos.length >= 6 && campos[0].equals(rut)) {
                    campos[5] = String.valueOf(nuevaCantidadReservados);
                    lineas.add(String.join(",", campos));
                } else {
                    lineas.add(linea);
                }
            }

            escritor = new BufferedWriter(new FileWriter(nombreArchivoUsuarios));
            for (String nuevaLinea : lineas) {
                escritor.write(nuevaLinea);
                escritor.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al actualizar la cantidad de reservados: " + e.getMessage());
        } finally {
            try {
                if (lector != null) {
                    lector.close();
                }
                if (escritor != null) {
                    escritor.close();
                }
            } catch (IOException ex) {
                System.err.println("Error al cerrar el lector/escritor: " + ex.getMessage());
            }
        }
    }
}
