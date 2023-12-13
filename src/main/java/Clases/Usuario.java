package Clases;

/**
 * Corresponde a una representacion de un usuario en la gestión de la biblioteca.
 */
public class Usuario {
    private String rut;
    private String  contraseña;
    private String nombre;
    private String apellido;
    private boolean estado;
    private int cantidadReservados;

    /**
     * Constructor de la clase "Usuario".
     *
     * @param rut RUT del usuario.
     * @param contraseña Contraseña del usuario.
     * @param nombre Nombre del usuario.
     * @param apellido Apellido del usuario.
     * @param estado Estado del usuario (true si está activo, false si está moroso).
     * @param cantidadReservados Cantidad de libros reservados por el usuario.
     */
    public Usuario(String rut, String contraseña, String nombre, String apellido, boolean estado, int cantidadReservados) {
        this.rut = rut;
        this.contraseña = contraseña;
        this.nombre = nombre;
        this.apellido = apellido;
        this.estado = estado;
        this.cantidadReservados = cantidadReservados;
    }

    // Setters
    public void setRut(String rut) {
        this.rut = rut;
    }

    public void setContrasena(String contrasena) {
        this.contraseña = contrasena;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }


    public String getRut() { return rut; }

    public String getContraseña() { return rut; }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public boolean isEstado() {
        return estado;
    }

    public int getCantidadReservados() {
        return cantidadReservados;
    }

    public void setCantidadReservados(int cantidadReservados) {
        this.cantidadReservados = cantidadReservados;
    }

}