package Controladores;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Clases.Usuario;

/**
 * Corresponde a la clase encargada de manejar los datos de los usuarios.
 */
public class UsuarioManager {

    /**
     * Archivo .csv que almacena la lista de usuarios.
     */
    public static final String nombreArchivoUsuarios = "Usuarios.csv";


    /**
     * Verifica la autenticación de un usuario mediante el rut y la contraseña.
     * @param rut Rut del usuario.
     * @param contraseña  Contraseña del usuario.
     * @return Usuario autenticado si las credenciales son correctas; de lo contrario, retorna `null`.
     */
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

    /**
     * Actualiza la cantidad de libros reservados por un usuario en el sistema.
     * @param rut Rut del usuario.
     * @param nuevaCantidadReservados Nueva cantidad de libros reservados por el usuario.
     */
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
