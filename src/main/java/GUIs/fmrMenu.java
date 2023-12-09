package GUIs;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
    private JTable tabLibros;
    private fmrLogin ventanaLogin;

    public fmrMenu(fmrLogin ventanaLogin) {
        this.ventanaLogin = ventanaLogin;  // Guardar la referencia a la ventana de login

        setVisible(true);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setTitle("Menú");
        setContentPane(jpMenu);

        // Crear la tabla con un modelo por defecto
        DefaultTableModel modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Título");
        modeloTabla.addColumn("Autor");
        modeloTabla.addColumn("ISBN");
        modeloTabla.addColumn("Estado");

        // Crear la tabla con el modelo
        tabLibros = new JTable(modeloTabla);

        // Crear el JScrollPane y agregar la tabla a él
        scrollPane = new JScrollPane(tabLibros);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // Botón "Cerrar Sesión":
        botCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cerrar la ventana de menú
                dispose();

                // Abrir nuevamente la ventana de inicio de sesión
                ventanaLogin.setVisible(true);
            }
        });

        // Botón "Buscar":
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Llamar a la función para cargar los datos en la tabla
                cargarDatosEnTabla();
            }
        });

        // Botón "Mostrar perfil"
        botMostrarPerfil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fmrPerfil ventanaPerfil = new fmrPerfil();
                ventanaPerfil.setVisible(true);
                dispose();
            }
        });
    }

    // Método para cargar datos en la tabla
    private void cargarDatosEnTabla() {
        // Obtener la lista de libros (puedes adaptarlo para obtener revistas si es necesario)
        List<Object[]> librosData = csvLibros.listadoLibros(null);

        // Obtener el modelo de la tabla
        DefaultTableModel modeloTabla = (DefaultTableModel) tabLibros.getModel();

        // Limpiar la tabla antes de agregar nuevos datos
        modeloTabla.setRowCount(0);

        // Agregar los datos de la lista a la tabla
        for (Object[] rowData : librosData) {
            modeloTabla.addRow(rowData);
        }
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
