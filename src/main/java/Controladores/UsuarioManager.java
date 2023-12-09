package Controladores;

import java.io.*;

public class UsuarioManager {
    public static final String nombreArchivoUsuarios = "Usuarios.csv";

    public static boolean verificarAutenticacion(String rut, String contraseña) {
        try {
            BufferedReader lector = new BufferedReader(new FileReader(nombreArchivoUsuarios));
            String linea;

            boolean primeraLinea = true;

            while ((linea = lector.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                String[] campos = linea.split(",");

                if (campos.length >= 2) {
                    String rutUsuario = campos[0].trim();
                    String contraseñaUsuario = campos[1].trim();

                    if (rut.equals(rutUsuario) && contraseña.equals(contraseñaUsuario)) {

                        lector.close();
                        return true;
                    }
                }
            }
            lector.close();
        } catch (IOException ex) {
            System.err.println("Error al leer el archivo de usuarios: " + ex.getMessage());
        }

        return false;
    }
}