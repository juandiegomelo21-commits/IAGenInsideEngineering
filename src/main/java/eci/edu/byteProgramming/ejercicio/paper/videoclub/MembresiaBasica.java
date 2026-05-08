package eci.edu.byteProgramming.ejercicio.paper.videoclub;

public class MembresiaBasica implements Membresia {

    @Override
    public String getNombre() { return "Basica"; }

    @Override
    public double calcularTotal(double subtotal) { return subtotal; }

    @Override
    public double calcularDescuento(double subtotal) { return 0; }
}
