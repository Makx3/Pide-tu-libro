package Clases;

/**
 * Corresponde a una representacion de un libro en la gestión de la biblioteca.
 */
public class Libro {
    private String idLibro;
    private String tituloLibro;
    private String autorLibro;
    private boolean estadoLibro;
    private String isbnLibro;
    private String edicionLibro;
    private String generoLibro;

    /**
     * Constructor de la clase "Libro".
     *
     * @param idLibro ID único del libro.
     * @param estadoLibro Estado de reserva del libro (reservado o disponible).
     * @param tituloLibro Título del libro.
     * @param autorLibro Autor del libro.
     * @param isbnLibro ISBN del libro.
     * @param edicionLibro Edición del libro.
     * @param generoLibro Género al que pertenece el libro.
     */
    public Libro(String idLibro, boolean estadoLibro ,String tituloLibro, String autorLibro, String isbnLibro, String edicionLibro, String generoLibro) {
        this.idLibro = idLibro;
        this.estadoLibro = estadoLibro;
        this.tituloLibro = tituloLibro;
        this.autorLibro = autorLibro;
        this.isbnLibro = isbnLibro;
        this.edicionLibro = edicionLibro;
        this.generoLibro = generoLibro;
    }

    public String getIdLibro() {
        return idLibro;
    }
    public String getTituloLibro() {
        return tituloLibro;
    }
    public String getAutorLibro() {
        return autorLibro;
    }
    public void setEstadoLibro(boolean estadoLibro) {
        this.estadoLibro = estadoLibro;
    }
    public String getIsbnLibro() {
        return isbnLibro;
    }
    public String getEdicionLibro() {
        return edicionLibro;
    }


    /**
     * Retorna una representación en cadena del libro.
     * @return String que representa el libro.
     */
    @Override
    public String toString() {
        String estado = estadoLibro ? "Reservado" : "Disponible";

        return String.format("%s | %s | %s | %s | %s | %s | %s",
                idLibro, estado ,tituloLibro, autorLibro, isbnLibro, edicionLibro, generoLibro);
    }

}
