package GUIs;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import Controladores.*;
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
                if (fmrMenu.this.getVentanaLogin() != null) {
                    fmrMenu.this.getVentanaLogin().setVisible(true);
                }
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
                fmrPerfil ventanaPerfil = new fmrPerfil(fmrMenu.this, usuarioLogeado);
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
            String idLibro = libro.getIdLibro();

            if (ReservaManager.estaLibroReservado(idLibro)) {
                JOptionPane.showMessageDialog(null, "Este libro ya está reservado.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Usuario usuarioLogeado = this.usuarioLogeado;

            // Verificar si el usuario ha alcanzado el límite de reservas
            if (usuarioLogeado.getCantidadReservados() >= 3) {
                JOptionPane.showMessageDialog(null, "Ya has alcanzado el límite de reservas (3 libros).", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int nuevaCantidadReservados = usuarioLogeado.getCantidadReservados() + 1;
            usuarioLogeado.setCantidadReservados(nuevaCantidadReservados);

            String entradaReservados = String.format("%s,%s,%s,%d,%s,%s,%s,%s,%s,%s",
                    usuarioLogeado.getRut(), usuarioLogeado.getNombre(), usuarioLogeado.getApellido(),
                    nuevaCantidadReservados, idLibro, libro.getTituloLibro(), libro.getAutorLibro(), true, libro.getIsbnLibro(), libro.getEdicionLibro());

            ReservaManager.agregarReservado(entradaReservados);
            UsuarioManager.actualizarCantidadReservados(usuarioLogeado.getRut(), nuevaCantidadReservados);

            // Actualizar estado del libro en el archivo CSV
            LibroManager.actualizarEstadoLibro(idLibro, true);

            libro.setEstadoLibro(true);

            // Actualizar el último libro reservado en el objeto Usuario
            usuarioLogeado.setUltimoLibroReservado(libro.getTituloLibro());

            DefaultListModel<Libro> modeloLista = (DefaultListModel<Libro>) Lista_libros.getModel();
            modeloLista.removeElement(libro);

            cargarDatosEnLista();

            JOptionPane.showMessageDialog(null, "Libro reservado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione un libro para reservar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void cargarDatosEnLista() {
        String filtroNombre = texBuscarLibro.getText().trim();

        if (!filtroNombre.isEmpty()) {
            librosData = LibroManager.buscarLibrosPorCriterios(filtroNombre);
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
    public fmrLogin getVentanaLogin() {
        return ventanaLogin;
    }
}

