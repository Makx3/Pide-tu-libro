package GUIs;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Clases.*;

public class fmrPerfil extends JFrame {
    private JPanel jpPerfil;
    private JLabel labNombre;
    private JButton botMostrarReservas;
    private JLabel labPerfil;
    private JLabel labNombreCargado;
    private JLabel labApellido;
    private JLabel labApellidoCargado;
    private JLabel labEstadoCargado;
    private JLabel labUltimoLibroReservado;
    private JLabel labUltimoReservadoCargado;
    private JLabel labEstado;
    private JButton botMostrarMenu;

    private fmrLogin ventanaLogin;

    public fmrPerfil(fmrLogin ventanaLogin, Usuario usuarioLogeado) {
        this.ventanaLogin = ventanaLogin;
        initComponents();
    }

    private void initComponents() {
        setTitle("Perfil");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(jpPerfil);

        botMostrarMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fmrMenu ventanaMenu = new fmrMenu(ventanaLogin, null);
                ventanaMenu.setVisible(true);
                dispose(); // Cierra la ventana actual
            }
        });

    }
}
