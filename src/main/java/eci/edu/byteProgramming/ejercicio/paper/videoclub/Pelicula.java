package eci.edu.byteProgramming.ejercicio.paper.videoclub;

public abstract class Pelicula {
    private final String titulo;
    private final double precio;
    private final boolean disponible;

    public Pelicula(String titulo, double precio, boolean disponible) {
        this.titulo = titulo;
        this.precio = precio;
        this.disponible = disponible;
    }

    public abstract String getTipo();

    public String getTitulo() { return titulo; }
    public double getPrecio() { return precio; }
    public boolean isDisponible() { return disponible; }
}
