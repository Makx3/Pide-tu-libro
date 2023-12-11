package Clases;

public class Libro {
    private String idLibro;
    private String tituloLibro;
    private String autorLibro;
    private boolean estadoLibro;
    private String isbnLibro;
    private String edicionLibro;
    private String generoLibro;

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

    public void setIdLibro(String idLibro) {
        this.idLibro = idLibro;
    }

    public String getTituloLibro() {
        return tituloLibro;
    }

    public void setTituloLibro(String tituloLibro) {
        this.tituloLibro = tituloLibro;
    }

    public String getAutorLibro() {
        return autorLibro;
    }

    public void setAutorLibro(String autorLibro) {
        this.autorLibro = autorLibro;
    }

    public boolean isEstadoLibro() {
        return estadoLibro;
    }

    public void setEstadoLibro(boolean estadoLibro) {
        this.estadoLibro = estadoLibro;
    }

    public String getIsbnLibro() {
        return isbnLibro;
    }

    public void setIsbnLibro(String isbnLibro) {
        this.isbnLibro = isbnLibro;
    }

    public String getEdicionLibro() {
        return edicionLibro;
    }

    public void setEdicionLibro(String edicionLibro) {
        this.edicionLibro = edicionLibro;
    }

    public String getGeneroLibro() {
        return generoLibro;
    }

    public void setGeneroLibro(String generoLibro) {
        this.generoLibro = generoLibro;
    }

    @Override
    public String toString() {
        String estado = estadoLibro ? "Reservado" : "Disponible";

        return String.format("%s | %s | %s | %s | %s | %s | %s",
                idLibro, estado ,tituloLibro, autorLibro, isbnLibro, edicionLibro, generoLibro);
    }

}
