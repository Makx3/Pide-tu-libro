package Controladores;

import Clases.Usuario;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TestUsuarioManagerMetodoverificarAtutenticacion {

    private static final String NOMBRE_ARCHIVO_TEMPORAL = "UsuariosTemporal.csv";

    @Test
    void verificarAutenticacion() {
        // Datos de prueba
        String datosUsuarios = "209271338,1234,Alex,Saez,true,0\n" +
                "216756398,2345,Aron,Saez,true,0\n" +
                "213575726,Firu123,Yoandri,Villarroel,true,0\n" +
                "1,1,1,1,true,3\n" +
                "2,2,2,2,true,0";

        try {
            // Escribir datos de prueba en el archivo temporal
            escribirArchivoTemporal(NOMBRE_ARCHIVO_TEMPORAL, datosUsuarios);

            // Llamar al método que quieres probar
            Usuario usuarioLogeado = UsuarioManager.verificarAutenticacion("209271338", "1234");

            // Verificar el resultado esperado
            assertNotNull(usuarioLogeado);
            assertEquals("Alex", usuarioLogeado.getNombre());

        } catch (IOException e) {
            e.printStackTrace();
            fail("Error durante la preparación de la prueba.");
        }
    }

    private void escribirArchivoTemporal(String nombreArchivo, String contenido) throws IOException {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(nombreArchivo))) {
            escritor.write(contenido);
        }
    }
}
