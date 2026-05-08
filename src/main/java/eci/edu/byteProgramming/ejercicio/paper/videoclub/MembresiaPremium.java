package eci.edu.byteProgramming.ejercicio.paper.videoclub;

public class MembresiaPremium implements Membresia {
    private static final double PORCENTAJE_DESCUENTO = 0.20;

    @Override
    public String getNombre() { return "Premium"; }

    @Override
    public double calcularTotal(double subtotal) {
        return subtotal * (1 - PORCENTAJE_DESCUENTO);
    }

    @Override
    public double calcularDescuento(double subtotal) {
        return subtotal * PORCENTAJE_DESCUENTO;
    }
}
