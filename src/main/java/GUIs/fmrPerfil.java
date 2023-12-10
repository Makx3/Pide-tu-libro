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
    private JLabel labUltimoLibroReservado;
    private JLabel labUltimoReservadoCargado;
    private JLabel labEstado;
    private JButton botMostrarMenu;
    private fmrMenu ventanaMenu;

    public fmrPerfil(fmrMenu ventanaMenu, Usuario usuarioLogeado) {
        this.ventanaMenu = ventanaMenu;
        initComponents(usuarioLogeado);
    }

    private void initComponents(Usuario usuarioLogeado) {
        setTitle("Perfil");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(jpPerfil);

        botMostrarMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ventanaMenu != null) {
                    ventanaMenu.setVisible(true);
                }
                dispose();
            }
        });

        labNombreCargado.setText(usuarioLogeado.getNombre());
        labApellidoCargado.setText(usuarioLogeado.getApellido());
        String estado = usuarioLogeado.isEstado() ? "Activo" : "Inactivo";
        labEstadoCargado.setText(estado);
        labUltimoReservadoCargado.setText(usuarioLogeado.getUltimoLibroReservadoId());
    }
}
