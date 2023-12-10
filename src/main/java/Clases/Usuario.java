package Clases;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;


public class Usuario {
    private String rut;
    private String contraseña;
    private String nombre;
    private String apellido;
    private boolean estado;
    private int cantidadReservados;
    private String ultimoLibroReservado;

    private String ultimoLibroReservadoId;


    public Usuario(String rut, String contraseña, String nombre, String apellido, boolean estado, int cantidadReservados) {
        this.rut = rut;
        this.contraseña = contraseña;
        this.nombre = nombre;
        this.apellido = apellido;
        this.estado = estado;
        this.cantidadReservados = cantidadReservados;
    }



    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getCantidadReservados() {
        return cantidadReservados;
    }

    public void setCantidadReservados(int cantidadReservados) {
        this.cantidadReservados = cantidadReservados;
    }

    public String getUltimoLibroReservado() {
        return ultimoLibroReservado;
    }

    public void setUltimoLibroReservado(String ultimoLibroReservado) {
        this.ultimoLibroReservado = ultimoLibroReservado;
    }
    public String getUltimoLibroReservadoId() {
        return ultimoLibroReservadoId;
    }

    public void setUltimoLibroReservadoId(String ultimoLibroReservadoId) {
        this.ultimoLibroReservadoId = ultimoLibroReservadoId;
    }

    // Método para cargar el último libro reservado desde el CSV de Reservados
    public void cargarUltimoLibroReservadoDesdeCSV(String rutUsuario) {
        String rutaReservadosCSV = "C:\\Users\\Usuario\\IdeaProjects\\Pide-tu-libro3\\Reservados.csv";

        try (CSVReader reader = new CSVReader(new FileReader(rutaReservadosCSV))) {
            List<String[]> reservadosData = reader.readAll();

            for (String[] reserva : reservadosData) {
                String rutReserva = reserva[0];

                if (rutReserva.equals(rutUsuario)) {
                    // Encontramos una reserva del usuario, actualizamos el último libro reservado
                    this.ultimoLibroReservado = reserva[5];  // Suponiendo que el título está en la posición 5
                    break;
                }
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }
}

