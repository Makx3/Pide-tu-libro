package GUIs;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Clases.Usuario;

public class fmrPerfil extends JFrame {
    private JPanel jpPerfil;
    private JLabel labNombre;
    private JButton botMostrarReservas;
    private JLabel labPerfil;
    private JLabel labNombreCargado;
    private JLabel labApellido;
    private JLabel labApellidoCargado;
    private JLabel labEstadoCargado;
    private JLabel labEstado;
    private JButton botMostrarMenu;
    private fmrMenu ventanaMenu;
    private fmrMenu ventanaReservas;

    public fmrPerfil(fmrMenu ventanaMenu, Usuario usuarioLogeado) {
        this.ventanaMenu = ventanaMenu;
        this.ventanaReservas = ventanaReservas;
        initComponents(usuarioLogeado);
    }

    private void initComponents(Usuario usuarioLogeado) {
        setTitle("Perfil");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(jpPerfil);

        //>---Botón "Mostrar Menú":
        botMostrarMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ventanaMenu != null) {
                    ventanaMenu.setVisible(true);
                }
                dispose();
            }
        });

        //>---Botón "Mostrar Reservas":
        botMostrarReservas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fmrReservas ventanaReservas = new fmrReservas(fmrPerfil.this, usuarioLogeado);
                ventanaReservas.setVisible(true);
                dispose();
            }
        });

        labNombreCargado.setText(usuarioLogeado.getNombre());
        labApellidoCargado.setText(usuarioLogeado.getApellido());
        String estado = usuarioLogeado.isEstado() ? "Activo" : "Moroso";
        labEstadoCargado.setText(estado);
    }
}
