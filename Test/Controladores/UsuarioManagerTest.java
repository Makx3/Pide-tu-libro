package Controladores;

import Clases.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioManagerTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void verificarAutenticacionUsuarioValido() {
        // Caso de prueba con usuario y contraseña válidos
        Usuario usuario = UsuarioManager.verificarAutenticacion("209271338", "1234");
        assertNotNull(usuario);
        assertEquals("Alex", usuario.getNombre());
        assertEquals("Saez", usuario.getApellido());
        assertTrue(usuario.isEstado());
        assertEquals(0, usuario.getCantidadReservados());
    }

    @Test
    void verificarAutenticacionUsuarioInvalido() {
        // Caso de prueba con usuario y contraseña inválidos
        Usuario usuario = UsuarioManager.verificarAutenticacion("usuario_invalido", "contrasena_invalida");
        assertNull(usuario);
    }
}
