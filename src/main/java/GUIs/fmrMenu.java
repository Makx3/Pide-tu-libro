package GUIs;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import Clases.Usuario;
import Controladores.*;

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

        initComponents();

        cargarDatosEnLista();
    }

    private void initComponents() {
        setVisible(true);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setTitle("Menú");
        setContentPane(jpMenu);

        botCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ventanaLogin.setVisible(true);
            }
        });

        reservarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reservarLibro();
            }
        });

        cargarDatosEnLista();
    }

    private void cargarDatosEnLista() {
        librosData = csvLibros.listadoLibrosDisponibles();
        DefaultListModel<String> modeloLista = new DefaultListModel<>();

        for (Object[] rowData : librosData) {
            String idLibro = (String) rowData[0];
            String tituloLibro = (String) rowData[1];
            String autorLibro = (String) rowData[2];
            boolean estadoLibro = (boolean) rowData[3];
            String isbnLibro = (String) rowData[4];
            String edicionLibro = (String) rowData[5];

            String infoLibro = String.format("%s | %s | %s | %s | %s | %s",
                    idLibro, tituloLibro, autorLibro, estadoLibro ? "Reservado" : "Disponible", isbnLibro, edicionLibro);

            modeloLista.addElement(infoLibro);
        }

        Lista_libros.setModel(modeloLista);
    }

    private void reservarLibro() {
        int indiceSeleccionado = Lista_libros.getSelectedIndex();

        if (indiceSeleccionado != -1) {
            Object[] rowData = librosData.get(indiceSeleccionado);
            String idLibro = (String) rowData[0];


            if (csvReservados.estaLibroReservado(idLibro)) {
                JOptionPane.showMessageDialog(null, "Este libro ya está reservado.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }


            int nuevaCantidadReservados = usuarioLogeado.getCantidadReservados() + 1;
            usuarioLogeado.setCantidadReservados(nuevaCantidadReservados);


            String entradaReservados = String.format("%s,%s,%s,%d,%s,%s,%s,%s,%s,%s",
                    usuarioLogeado.getRut(), usuarioLogeado.getNombre(), usuarioLogeado.getApellido(),
                    nuevaCantidadReservados, idLibro, rowData[1], rowData[2], rowData[3], rowData[4], rowData[5]);

            csvReservados.agregarReservado(entradaReservados);
            UsuarioManager.actualizarCantidadReservados(usuarioLogeado.getRut(), nuevaCantidadReservados);

            cargarDatosEnLista();
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione un libro para reservar.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

}
