package GUIs;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Clases.Usuario;
import Controladores.UsuarioManager;

public class fmrLogin extends JFrame {
    private JPanel jpLogin;
    private JTextField texRut;
    private JPasswordField texContraseña;
    private JButton botIniciarSesion;
    private JLabel labNombre;
    private JLabel labContraseña;
    private JLabel labTitulo;

    public fmrLogin() {
        setVisible(true);
        setSize(600, 700);
        setLocationRelativeTo(null);
        setTitle("Login");
        setContentPane(jpLogin);

        botIniciarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rut = texRut.getText();
                String contraseña = new String(texContraseña.getPassword());

                Usuario usuarioLogeado = UsuarioManager.verificarAutenticacion(rut, contraseña);

                if (usuarioLogeado != null) {
                    limpiarCampos(); // Limpia los campos antes de abrir la ventana del menú
                    fmrMenu ventanaMenu = new fmrMenu(fmrLogin.this, usuarioLogeado);
                    ventanaMenu.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "RUT o contraseña incorrectos", "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
                    limpiarCampos();
                }
            }
        });
    }

    private void limpiarCampos() {
        texRut.setText("");
        texContraseña.setText("");
    }
}
