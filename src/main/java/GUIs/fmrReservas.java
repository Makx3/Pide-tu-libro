package GUIs;

import Clases.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import Controladores.ReservaManager;

public class fmrReservas extends JFrame {
    private JPanel jpHistorialReservas;
    private JLabel labHistorialDeReservas;
    private JButton botMostrarPerfil;
    private JList<String> lista_libros_reservados;
    private DefaultListModel<String> modeloListaReservados;
    private fmrPerfil ventanaPerfil;
    private Usuario usuarioLogeado;

    public fmrReservas(fmrPerfil ventanaPerfil, Usuario usuarioLogeado) {
        this.ventanaPerfil = ventanaPerfil;
        this.usuarioLogeado = usuarioLogeado;
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


        modeloListaReservados = new DefaultListModel<>();
        lista_libros_reservados.setModel(modeloListaReservados);


        cargarReservasUsuario();

        Timer timer = new Timer(60000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarReservasUsuario();
            }
        });
        timer.start();
    }

    private void cargarReservasUsuario() {

        modeloListaReservados.clear();


        List<String> reservasUsuario = ReservaManager.obtenerReservasUsuario(usuarioLogeado.getRut());


        for (String reserva : reservasUsuario) {
            modeloListaReservados.addElement(reserva);
        }
    }
}
