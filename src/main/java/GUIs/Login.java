package GUIs;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import Clases.Usuario;
import Controladores.UsuarioManager;

/**
 * Corresponde a una interfaz gráfica de usuario  y permite el ingreso de a los usuarios registrados en la biblioteca.
 */
public class Login extends JFrame implements ActionListener {
    private JPanel jpLogin;
    private JTextField texRut;
    private JPasswordField texContraseña;
    private JButton botIniciarSesion;
    private JLabel labNombre;
    private JLabel labContraseña;
    private JLabel labTitulo;

    /**
     * Constructor. Inicializa componentes
     */
    public Login() {
        setVisible(true);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setTitle("Login");
        setContentPane(jpLogin);

        botIniciarSesion.addActionListener(this);
    }

    /**
     * Método. Limpia los campos de texto.
     */
    private void limpiarCampos() {
        texRut.setText("");
        texContraseña.setText("");
    }

    /**
     * Método. Gestor de eventos en la ventana.
     * @param event Activación de botones.
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == botIniciarSesion){
            String rut = texRut.getText();
            String contraseña = new String(texContraseña.getPassword());

            Usuario usuarioLogeado = UsuarioManager.verificarAutenticacion(rut, contraseña);

            if (usuarioLogeado != null) {
                limpiarCampos();
                Menu ventanaMenu = new Menu(Login.this, usuarioLogeado);
                ventanaMenu.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "RUT o contraseña incorrectos", "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
                limpiarCampos();
            }
        }
    }
}