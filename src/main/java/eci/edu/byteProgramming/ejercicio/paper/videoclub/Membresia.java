package eci.edu.byteProgramming.ejercicio.paper.videoclub;

public interface Membresia {
    String getNombre();
    double calcularTotal(double subtotal);
    double calcularDescuento(double subtotal);
}
