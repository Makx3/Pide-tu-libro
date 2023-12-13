package GUIs;

import Clases.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import Controladores.ReservaManager;

/**
 * Corresponde a una interfaz gráfica de usuario y permite ver los libros reservados por el usuario actual.
 */
public class Reservas extends JFrame implements ActionListener {
    private JPanel jpHistorialReservas;
    private JButton botMostrarPerfil;
    private JList<String> lista_libros_reservados;
    private JLabel labHistorialDeReservas;
    private DefaultListModel<String> modeloListaReservados;
    private Perfil ventanaPerfil;
    private Usuario usuarioLogeado;
    private Timer timer;

    /**
     * Constructor. Inicializa componentes.
     * @param ventanaPerfil Referencia a la ventana Perfil para controlar la navegación.
     * @param usuarioLogeado Objeto Usuario que representa al usuario autenticado.
     */
    public Reservas(Perfil ventanaPerfil, Usuario usuarioLogeado) {
        this.ventanaPerfil = ventanaPerfil;
        this.usuarioLogeado = usuarioLogeado;
        initComponents(usuarioLogeado);
    }

    /**
     * Inicializa componentes.
     * @param usuarioLogeado Objeto Usuario que representa al usuario autenticado.
     */
    private void initComponents(Usuario usuarioLogeado) {
        setTitle("Historial de reservas");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(jpHistorialReservas);

        botMostrarPerfil.addActionListener(this);

        modeloListaReservados = new DefaultListModel<>();
        lista_libros_reservados.setModel(modeloListaReservados);

        cargarReservasUsuario();

         this.timer = new Timer(60000, this);
         timer.start();
    }

    /**
     * Método. Actualiza en tiempo los libros reservados por el usuario actual.
     */
    private void cargarReservasUsuario() {
        modeloListaReservados.clear();
        List<String> reservasUsuario = ReservaManager.obtenerReservasUsuario(usuarioLogeado.getRut());
        for (String reserva : reservasUsuario) {
            modeloListaReservados.addElement(reserva);
        }
    }

    /**
     * Método. Gestor de eventos en la ventana.
     * @param event Activación de botones.
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == botMostrarPerfil ){
            if (ventanaPerfil != null) {
                ventanaPerfil.setVisible(true);
            }
            dispose();
        }if(event.getSource() == timer){
            cargarReservasUsuario();
        }
    }
}
