package GUIs;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import Controladores.*;
import Clases.*;

public class fmrMenu extends JFrame {
    private JButton reservarButton;
    private JList<String> Lista_libros;
    private JTextField texBuscarLibro;
    private JButton btnBuscar;
    private JLabel labPideTuLibro;
    private JButton botCerrarSesion;
    private JButton botMostrarPerfil;
    private JPanel jpMenu;
    private List<Object[]> librosData;
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

    private void cargarDatosEnLista() {

        String filtroNombre = texBuscarLibro.getText().trim();

        if (!filtroNombre.isEmpty()) {

            librosData = LibroManager.buscarLibrosPorNombre(filtroNombre);
        } else {

            librosData = LibroManager.obtenerTodosLibros();
        }

        DefaultListModel<String> modeloLista = new DefaultListModel<>();

        if (librosData.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Libro no encontrado o nombre incorrecto.", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (Object[] rowData : librosData) {
                String idLibro = (String) rowData[0];
                String tituloLibro = (String) rowData[1];
                String autorLibro = (String) rowData[2];
                boolean estadoLibro = (boolean) rowData[3];
                String isbnLibro = (String) rowData[4];
                String edicionLibro = (String) rowData[5];

                if (!estadoLibro) {
                    String infoLibro = String.format("%s | %s | %s | %s | %s | %s",
                            idLibro, tituloLibro, autorLibro, estadoLibro ? "Reservado" : "Disponible", isbnLibro, edicionLibro);

                    modeloLista.addElement(infoLibro);
                }
            }
        }

        Lista_libros.setModel(modeloLista);
    }

    private void reservarLibro() {
        int indiceSeleccionado = Lista_libros.getSelectedIndex();

        if (librosData != null && indiceSeleccionado >= 0 && indiceSeleccionado < librosData.size()) {
            Object[] rowData = librosData.get(indiceSeleccionado);
            String idLibro = (String) rowData[0];

            if (ReservaManager.estaLibroReservado(idLibro)) {
                JOptionPane.showMessageDialog(null, "Este libro ya está reservado.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Usuario usuarioLogeado = this.usuarioLogeado;

            int nuevaCantidadReservados = usuarioLogeado.getCantidadReservados() + 1;
            usuarioLogeado.setCantidadReservados(nuevaCantidadReservados);

            String entradaReservados = String.format("%s,%s,%s,%d,%s,%s,%s,%s,%s,%s",
                    usuarioLogeado.getRut(), usuarioLogeado.getNombre(), usuarioLogeado.getApellido(),
                    nuevaCantidadReservados, idLibro, rowData[1], rowData[2], rowData[3], rowData[4], rowData[5]);

            ReservaManager.agregarReservado(entradaReservados);
            UsuarioManager.actualizarCantidadReservados(usuarioLogeado.getRut(), nuevaCantidadReservados);

            rowData[3] = true;

            DefaultListModel<String> modeloLista = (DefaultListModel<String>) Lista_libros.getModel();
            modeloLista.removeElementAt(indiceSeleccionado);

            cargarDatosEnLista();
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione un libro para reservar.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

}
