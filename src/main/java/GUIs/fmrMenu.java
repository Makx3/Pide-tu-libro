package GUIs;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import Controladores.*;
import java.util.ArrayList;
import Clases.*;

public class fmrMenu extends JFrame {
    private JButton reservarButton;
    private JList<Libro> Lista_libros;
    private JTextField texBuscarLibro;
    private JButton btnBuscar;
    private JLabel labPideTuLibro;
    private JButton botCerrarSesion;
    private JButton botMostrarPerfil;
    private JPanel jpMenu;
    private List<Libro> librosData;
    private fmrLogin ventanaLogin;
    private Usuario usuarioLogeado;

    public fmrMenu(fmrLogin ventanaLogin, Usuario usuarioLogeado) {
        this.ventanaLogin = ventanaLogin;
        this.usuarioLogeado = usuarioLogeado;
        this.librosData = new ArrayList<>();

        initComponents();

        cargarDatosEnLista();
    }

    private void initComponents() {
        setVisible(true);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setTitle("Menú");
        setContentPane(jpMenu);

        // Botón "Cerrar sesión":
        botCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ventanaLogin.setVisible(true);
            }
        });

        // Botón "Reservar":
        reservarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reservarLibro();
            }
        });

        // Botón "Buscar":
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatosEnLista();
            }
        });

        // Botón "Mostrar perfil":
        botMostrarPerfil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fmrPerfil ventanaPerfil = new fmrPerfil();
                ventanaPerfil.setVisible(true);
                dispose();
            }
        });

        cargarDatosEnLista();
    }

    private void reservarLibro() {
        int indiceSeleccionado = Lista_libros.getSelectedIndex();

        if (librosData != null && indiceSeleccionado >= 0 && indiceSeleccionado < librosData.size()) {
            Libro libro = librosData.get(indiceSeleccionado);

            if (ReservaManager.estaLibroReservado(libro.getIdLibro())) {
                JOptionPane.showMessageDialog(null, "Este libro ya está reservado.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Usuario usuarioLogeado = this.usuarioLogeado;

            int nuevaCantidadReservados = usuarioLogeado.getCantidadReservados() + 1;
            usuarioLogeado.setCantidadReservados(nuevaCantidadReservados);

            String entradaReservados = String.format("%s,%s,%s,%d,%s,%s,%s,%s,%s,%s",
                    usuarioLogeado.getRut(), usuarioLogeado.getNombre(), usuarioLogeado.getApellido(),
                    nuevaCantidadReservados, libro.getIdLibro(), libro.getTituloLibro(), libro.getAutorLibro(), true, libro.getIsbnLibro(), libro.getEdicionLibro());

            ReservaManager.agregarReservado(entradaReservados);
            UsuarioManager.actualizarCantidadReservados(usuarioLogeado.getRut(), nuevaCantidadReservados);

            libro.setEstadoLibro(true);

            DefaultListModel<Libro> modeloLista = (DefaultListModel<Libro>) Lista_libros.getModel();
            modeloLista.removeElementAt(indiceSeleccionado);
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione un libro para reservar.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void cargarDatosEnLista() {
        String filtroNombre = texBuscarLibro.getText().trim();

        if (!filtroNombre.isEmpty()) {
            librosData = LibroManager.buscarLibrosPorNombre(filtroNombre);
        } else {
            librosData = LibroManager.obtenerTodosLibros();
        }

        DefaultListModel<Libro> modeloLista = new DefaultListModel<>();

        if (librosData.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Libro no encontrado o nombre incorrecto.", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (Libro libro : librosData) {
                String idLibro = libro.getIdLibro();
                String tituloLibro = libro.getTituloLibro();
                String autorLibro = libro.getAutorLibro();
                boolean estadoLibro = libro.isEstadoLibro();
                String isbnLibro = libro.getIsbnLibro();
                String edicionLibro = libro.getEdicionLibro();

                if (!estadoLibro) {
                    String infoLibro = String.format("%s | %s | %s | %s | %s | %s",
                            idLibro, tituloLibro, autorLibro, estadoLibro ? "Reservado" : "Disponible", isbnLibro, edicionLibro);

                    modeloLista.addElement(libro);
                }
            }
        }

        Lista_libros.setModel(modeloLista);
    }
}
