package GUIs;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import Controladores.csvLibros;

public class fmrMenu extends JFrame {
    private JButton btnBuscar;
    private JPanel jpMenu;
    private JButton botCerrarSesion;
    private JScrollPane scrollPane;
    private JTextField texBuscarLibro;
    private JLabel labPideTuLibro;
    private JButton botMostrarPerfil;
    private JList<String> Lista_libros;
    private fmrLogin ventanaLogin;

    public fmrMenu(fmrLogin ventanaLogin) {
        this.ventanaLogin = ventanaLogin;

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

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatosEnLista();
            }
        });

        botMostrarPerfil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fmrPerfil ventanaPerfil = new fmrPerfil();
                ventanaPerfil.setVisible(true);
                dispose();
            }
        });
    }

    private void cargarDatosEnLista() {
        List<Object[]> librosData = csvLibros.listadoLibros(null);

        DefaultListModel<String> modeloLista = new DefaultListModel<>();

        // encabezado de la lista
        modeloLista.addElement("ID | Título | Autor | Estado | ISBN | Edición");

        // Agregar información a la lista
        for (Object[] rowData : librosData) {
            String idLibro = (String) rowData[0];
            String tituloLibro = (String) rowData[1];
            String autorLibro = (String) rowData[2];
            boolean estadoLibro = (boolean) rowData[3];
            String isbnLibro = (String) rowData[4];
            String edicionLibro = (String) rowData[5];

            String infoLibro = String.format("%s | %s | %s | %s | %s | %s",
                    idLibro, tituloLibro, autorLibro, estadoLibro, isbnLibro, edicionLibro);

            modeloLista.addElement(infoLibro);
        }
        Lista_libros.setModel(modeloLista);
    }

    private void createUIComponents() {
    }
}
