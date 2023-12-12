package GUIs;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Clases.Usuario;

/**
 * Corresponde a una interfaz gráfica de usuario y permite ver información del usuario actual.
 */
public class Perfil extends JFrame implements ActionListener{
    private JPanel jpPerfil;
    private JButton botMostrarReservas;
    private JLabel labNombreCargado;
    private JLabel labApellidoCargado;
    private JLabel labEstadoCargado;
    private JButton botMostrarMenu;
    private JLabel labPerfil;
    private JLabel labApellido;
    private JLabel labNombre;
    private JLabel labEstado;
    private Menu ventanaMenu;
    private Usuario usuarioLogeado;

    /**
     * Constructor. Inicializa componenentes.
     * @param ventanaMenu Referencia a la ventana Menú para controlar la navegación.
     * @param usuarioLogeado Objeto Usuario que representa al usuario autenticado.
     */
    public Perfil(Menu ventanaMenu, Usuario usuarioLogeado) {
        this.ventanaMenu = ventanaMenu;
        this.usuarioLogeado = usuarioLogeado;
        initComponents(usuarioLogeado);
    }

    /**
     * Inicializa componentes.
     * @param usuarioLogeado Objeto Usuario que representa al usuario autenticado.
     */
    private void initComponents(Usuario usuarioLogeado) {
        setTitle("Perfil");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(jpPerfil);

        botMostrarMenu.addActionListener(this);
        botMostrarReservas.addActionListener(this);

        labNombreCargado.setText(usuarioLogeado.getNombre());
        labApellidoCargado.setText(usuarioLogeado.getApellido());
        String estado = usuarioLogeado.isEstado() ? "Activo" : "Moroso";
        labEstadoCargado.setText(estado);
    }

    /**
     * Método. Gestor de eventos en la ventana.
     * @param event Activación de botones.
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == botMostrarReservas){
            Reservas ventanaReservas = new Reservas(Perfil.this, usuarioLogeado);
            ventanaReservas.setVisible(true);
            dispose();
        }if(event.getSource() == botMostrarMenu){
            if (ventanaMenu != null) {
                ventanaMenu.setVisible(true);
            }
            dispose();
        }
    }
}
