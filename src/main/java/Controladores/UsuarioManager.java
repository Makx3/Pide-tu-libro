package Controladores;

import Clases.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioManager {
    public static final String nombreArchivoUsuarios = "Usuarios.csv";
    public static Usuario verificarAutenticacion(String rut, String contraseña) {
        try {
            BufferedReader lector = new BufferedReader(new FileReader(nombreArchivoUsuarios));
            String linea;

            // Ignora la primera línea (títulos)
            lector.readLine();

            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");

                if (campos.length >= 6) {
                    String rutUsuario = campos[0].trim();
                    String contraseñaUsuario = campos[1].trim();
                    String nombreUsuario = campos[2].trim();
                    String apellidoUsuario = campos[3].trim();
                    boolean estadoUsuario = Boolean.parseBoolean(campos[4].trim());
                    int reservadosUsuario = Integer.parseInt(campos[5].trim());

                    if (rut.equals(rutUsuario) && contraseña.equals(contraseñaUsuario)) {
                        Usuario usuario = new Usuario(rutUsuario, contraseñaUsuario, nombreUsuario, apellidoUsuario, estadoUsuario, reservadosUsuario);
                        usuario.setUltimoLibroReservadoId(ReservaManager.obtenerUltimoLibroReservadoPorUsuario(rutUsuario));
                        lector.close();
                        return usuario;
                    }
                }
            }

            lector.close();
        } catch (IOException | NumberFormatException ex) {
            System.err.println("Error al leer el archivo de usuarios: " + ex.getMessage());
        }

        return null;
    }
    public static void actualizarCantidadReservados(String rut, int nuevaCantidadReservados) {
        try {
            BufferedReader lector = new BufferedReader(new FileReader(nombreArchivoUsuarios));
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
                    // Encuentra la línea del usuario y actualiza la cantidad de reservados
                    campos[5] = String.valueOf(nuevaCantidadReservados);
                    lineas.add(String.join(",", campos));
                } else {
                    lineas.add(linea);
                }
            }
            lector.close();

            // Vuelve a escribir todas las líneas en el archivo
            BufferedWriter escritor = new BufferedWriter(new FileWriter(nombreArchivoUsuarios));
            for (String nuevaLinea : lineas) {
                escritor.write(nuevaLinea);
                escritor.newLine();
            }
            escritor.close();
        } catch (IOException e) {
            System.err.println("Error al actualizar la cantidad de reservados: " + e.getMessage());
        }
    }
}

