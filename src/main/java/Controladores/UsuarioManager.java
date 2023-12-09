package Controladores;

import Clases.Usuario;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;


public class UsuarioManager {
    public static final String nombreArchivoUsuarios = "Usuarios.csv";

    public static Usuario verificarAutenticacion(String rut, String contraseña) {
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

                if (campos.length >= 6) {
                    String rutUsuario = campos[0].trim();
                    String contraseñaUsuario = campos[1].trim();
                    String nombreUsuario = campos[2].trim();
                    String apellidoUsuario = campos[3].trim();
                    boolean estadoUsuario = Boolean.parseBoolean(campos[4].trim());
                    int reservadosUsuario = Integer.parseInt(campos[5].trim());

                    if (rut.equals(rutUsuario) && contraseña.equals(contraseñaUsuario)) {
                        lector.close();
                        return new Usuario(rutUsuario, contraseñaUsuario, nombreUsuario, apellidoUsuario, estadoUsuario, reservadosUsuario);
                    }
                }
            }
            lector.close();
        } catch (IOException ex) {
            System.err.println("Error al leer el archivo de usuarios: " + ex.getMessage());
        }

        return null;
    }


    public static Usuario obtenerUsuarioPorRut(String rut) {
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

                if (campos.length >= 6) {
                    String rutUsuario = campos[0].trim();

                    if (rut.equals(rutUsuario)) {
                        String contraseña = campos[1].trim();
                        String nombre = campos[2].trim();
                        String apellido = campos[3].trim();
                        boolean estado = Boolean.parseBoolean(campos[4].trim());
                        int cantidadReservados = Integer.parseInt(campos[5].trim());

                        lector.close();
                        return new Usuario(rut, contraseña, nombre, apellido, estado, cantidadReservados);
                    }
                }
            }
            lector.close();
        } catch (IOException ex) {
            System.err.println("Error al leer el archivo de usuarios: " + ex.getMessage());
        }

        return null;
    }

    public static void actualizarCantidadReservados(String rut, int nuevaCantidadReservados) {
        try {
            File archivo = new File(nombreArchivoUsuarios);
            File archivoTemp = new File(nombreArchivoUsuarios + ".temp");

            BufferedReader lector = new BufferedReader(new FileReader(archivo));
            BufferedWriter escritor = new BufferedWriter(new FileWriter(archivoTemp));

            boolean primeraLinea = true;

            while (lector.ready()) {
                String linea = lector.readLine();

                if (primeraLinea) {
                    primeraLinea = false;
                    escritor.write(linea);
                } else {
                    String[] campos = linea.split(",");
                    if (campos.length >= 6) {
                        String rutUsuario = campos[0].trim();
                        String nuevaLinea = linea;

                        if (rut.equals(rutUsuario)) {
                            // Si el RUT coincide, actualiza la cantidad de reservados
                            nuevaLinea = String.format("%s,%s,%s,%s,%s,%d",
                                    campos[0], campos[1], campos[2], campos[3], campos[4], nuevaCantidadReservados);
                        }

                        escritor.write(nuevaLinea);
                    }
                    escritor.newLine();
                }
            }

            lector.close();
            escritor.close();

            // Elimina el archivo original y renombra el temporal
            archivo.delete();
            archivoTemp.renameTo(archivo);
        } catch (IOException e) {
            System.err.println("Hubo un error al actualizar la cantidad de reservados: " + e.getMessage());
        }
    }
}
