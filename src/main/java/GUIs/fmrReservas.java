package GUIs;

import Clases.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class fmrReservas extends JFrame{
    private JPanel jpHistorialReservas;
    private JLabel labHistorialDeReservas;
    private JButton botMostrarPerfil;
    private fmrPerfil ventanaPerfil;

    public fmrReservas(fmrPerfil ventanaPerfil, Usuario usuarioLogeado) {
        this.ventanaPerfil = ventanaPerfil;
        initComponents(usuarioLogeado);
    }

    private void initComponents(Usuario usuarioLogeado) {
        setTitle("Historial de reservas");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(jpHistorialReservas);

        //>---Botón "Mostrar Perfil":
        botMostrarPerfil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ventanaPerfil != null) {
                    ventanaPerfil.setVisible(true);
                }
                dispose();
            }
        });
    }
}
