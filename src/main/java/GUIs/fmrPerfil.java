package GUIs;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class fmrPerfil extends JFrame {
    private JPanel jpPerfil;
    private JLabel labInformacion;
    private JButton botCerrar;
    private JLabel labNombre;
    private JButton botMostrarReservas;

    public fmrPerfil() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Perfil");
        setSize(600, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jpPerfil = new JPanel();
        labInformacion = new JLabel("Aquí va la información del perfil");
        botCerrar = new JButton("Cerrar Perfil");

        jpPerfil.add(labInformacion);
        jpPerfil.add(botCerrar);

        add(jpPerfil);

        botCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new fmrPerfil().setVisible(true);
            }
        });
    }
}
