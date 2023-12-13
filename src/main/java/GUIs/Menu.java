package GUIs;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import Controladores.*;
import Clases.*;

/**
 * Corresponde a una interfaz gráfica de usuario y permite buscar y reservar libros.
 */
public class Menu extends JFrame implements ActionListener{
    private JButton botReservarLibro;
    private JList<Libro> Lista_libros;
    private JTextField texBuscarLibro;
    private JButton botBuscarLibro;
    private JLabel labPideTuLibro;
    private JButton botCerrarSesion;
    private JButton botMostrarPerfil;
    private JPanel jpMenu;
    private List<Libro> librosData;
    private Login ventanaLogin;
    private Usuario usuarioLogeado;

    /**
     * Constructor. Inicializa componentes.
     * @param ventanaLogin Referencia a la ventana Login para controlar la navegación.
     * @param usuarioLogeado Objeto Usuario que representa al usuario autenticado.
     */
    public Menu(Login ventanaLogin, Usuario usuarioLogeado) {
        this.ventanaLogin = ventanaLogin;
        this.usuarioLogeado = usuarioLogeado;
        this.librosData = new ArrayList<>();

        initComponents();

        cargarDatosEnLista();
    }

    /**
     * Inicializa componentes
     */
    private void initComponents() {
        setVisible(true);
        setSize(1100, 500);
        setLocationRelativeTo(null);
        setTitle("Menú");
        setContentPane(jpMenu);

        botCerrarSesion.addActionListener(this);

        botReservarLibro.addActionListener(this);

        botBuscarLibro.addActionListener(this);

        botMostrarPerfil.addActionListener(this);

        cargarDatosEnLista();
    }

    /**
     * Método para reservar un libro de la lista
     */
    private void reservarLibro() {
        int indiceSeleccionado = Lista_libros.getSelectedIndex();

        if (usuarioLogeado != null && usuarioLogeado.isEstado()) {
            if (indiceSeleccionado >= 0 && indiceSeleccionado < librosData.size()) {
                Libro libro = librosData.get(indiceSeleccionado);
                String idLibro = libro.getIdLibro();

                if (ReservaManager.estaLibroReservado(idLibro)) {
                    JOptionPane.showMessageDialog(null, "Este libro ya está reservado.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Verificar si el usuario ha alcanzado el límite de reservas
                if (usuarioLogeado.getCantidadReservados() >= 3) {
                    JOptionPane.showMessageDialog(null, "Ya has alcanzado el límite de reservas (3 libros).", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int nuevaCantidadReservados = usuarioLogeado.getCantidadReservados() + 1;
                usuarioLogeado.setCantidadReservados(nuevaCantidadReservados);

                // Construir la entrada para agregar a Reservados
                String entradaReservados = String.format("%s,%s,%s,%d,%s,%s,%s,%s,%s,%s",
                        usuarioLogeado.getRut(), usuarioLogeado.getNombre(), usuarioLogeado.getApellido(),
                        nuevaCantidadReservados, idLibro, libro.getTituloLibro(), libro.getAutorLibro(), true, libro.getIsbnLibro(), libro.getEdicionLibro());

                // Agregar la reserva con la fecha
                ReservaManager.agregarFecha(entradaReservados);

                UsuarioManager.actualizarCantidadReservados(usuarioLogeado.getRut(), nuevaCantidadReservados);

                // Actualizar estado del libro en el archivo CSV
                LibroManager.actualizarEstadoLibro(idLibro, true);

                libro.setEstadoLibro(true);

                DefaultListModel<Libro> modeloLista = (DefaultListModel<Libro>) Lista_libros.getModel();
                modeloLista.removeElement(libro);

                cargarDatosEnLista();

                JOptionPane.showMessageDialog(null, "Libro reservado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, seleccione un libro para reservar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No puedes reservar libros. Por favor, devuelve el/los libros pendientes.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Método para cargar datos en la lista según el criterio de busqueda
     */
    private void cargarDatosEnLista() {
        String filtroNombre = texBuscarLibro.getText().trim();

        if (!filtroNombre.isEmpty()) {
            librosData = LibroManager.buscarLibrosPorCriteriosExtendidos(filtroNombre);
        } else {
            librosData = LibroManager.obtenerTodosLibros();
        }

        DefaultListModel<Libro> modeloLista = new DefaultListModel<>();

        if (librosData.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Libro no encontrado o criterio incorrecto.", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (Libro rowData : librosData) {
                modeloLista.addElement(rowData);
            }
        }

        Lista_libros.setModel(modeloLista);
    }

    /**
     * Obtener la referencia a la ventana Login
     * @return La referencia de la ventana Login
     */
    public Login getVentanaLogin() {
        return this.ventanaLogin;
    }

    /**
     * Método. Gestor de eventos en la ventana.
     * @param event Activación de botones.
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == botCerrarSesion){
            dispose();
            if (Menu.this.getVentanaLogin() != null) {
                Menu.this.getVentanaLogin().setVisible(true);
            }
        }if (event.getSource() == botBuscarLibro){
            cargarDatosEnLista();
        }if(event.getSource() == botReservarLibro){
            reservarLibro();
        }if(event.getSource() == botMostrarPerfil){
            Perfil ventanaPerfil = new Perfil(Menu.this, usuarioLogeado);
            ventanaPerfil.setVisible(true);
            dispose();
        }
    }
}