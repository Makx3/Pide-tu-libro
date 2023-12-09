package Controladores;

import java.io.*;

public class csvUsuarios {

    public static final String nombreArchivoUsuarios = "Usuarios.csv";


    public static void actualizarCantidadReservados(String rut, int nuevaCantidadReservados) {
        try {
            File archivoUsuarios = new File(nombreArchivoUsuarios);
            File archivoTemp = new File(nombreArchivoUsuarios + ".temp");

            BufferedReader lector = new BufferedReader(new FileReader(archivoUsuarios));
            BufferedWriter escritor = new BufferedWriter(new FileWriter(archivoTemp));

            String linea;
            boolean primeraLinea = true;

            while ((linea = lector.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                } else {
                    String[] campos = linea.split(",");
                    if (campos.length >= 7) {
                        String rutUsuario = campos[0].trim();
                        if (rut.equals(rutUsuario)) {
                            campos[5] = Integer.toString(nuevaCantidadReservados); // El campo 6 es la cantidad de reservados
                        }
                    }

                    escritor.write(String.join(",", campos));
                    escritor.newLine();
                }
            }

            lector.close();
            escritor.close();

            archivoUsuarios.delete();
            archivoTemp.renameTo(archivoUsuarios);
        } catch (IOException e) {
            System.err.println("Error al actualizar la cantidad de reservados en Usuarios.csv: " + e.getMessage());
        }
    }

}