package eci.edu.byteProgramming.ejercicio.paper.videoclub;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Videoclub {
    private final List<Pelicula> catalogo;
    private final NumberFormat formatoPrecio;

    public Videoclub() {
        this.catalogo = inicializarCatalogo();
        this.formatoPrecio = NumberFormat.getNumberInstance(new Locale("es", "CO"));
        this.formatoPrecio.setMaximumFractionDigits(0);
    }

    private List<Pelicula> inicializarCatalogo() {
        List<Pelicula> lista = new ArrayList<>();
        lista.add(new PeliculaFisica("Interestellar", 8000, true));
        lista.add(new PeliculaFisica("El Padrino", 7000, false));
        lista.add(new PeliculaDigital("Inception", 5000, true));
        lista.add(new PeliculaDigital("Matrix", 6000, true));
        return lista;
    }

    public void ejecutar(Scanner scanner) {
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║   VIDEOCLUB DE DON MARIO     ║");
        System.out.println("╚══════════════════════════════╝");
        System.out.println();

        mostrarCatalogo();

        Membresia membresia = seleccionarMembresia(scanner);
        List<Pelicula> seleccionadas = seleccionarPeliculas(scanner);

        if (seleccionadas.isEmpty()) {
            System.out.println("No se seleccionaron peliculas disponibles.");
            return;
        }

        imprimirRecibo(seleccionadas, membresia);
    }

    private void mostrarCatalogo() {
        System.out.println("Peliculas disponibles:");
        System.out.println("--------------------------------------------------");
        for (int i = 0; i < catalogo.size(); i++) {
            Pelicula p = catalogo.get(i);
            String estado = p.isDisponible() ? "Disponible" : "No disponible";
            System.out.printf("%d. [%s] %s - $%s - %s%n",
                    i + 1,
                    p.getTipo(),
                    p.getTitulo(),
                    formatoPrecio.format(p.getPrecio()),
                    estado);
        }
        System.out.println("--------------------------------------------------");
        System.out.println();
    }

    private Membresia seleccionarMembresia(Scanner scanner) {
        System.out.println("Tipo de membresia:");
        System.out.println("  1. Basica  (precio normal)");
        System.out.println("  2. Premium (20% de descuento)");
        System.out.print("Seleccione (1/2): ");

        String entrada = scanner.nextLine().trim();
        System.out.println();
        return entrada.equals("2") ? new MembresiaPremium() : new MembresiaBasica();
    }

    private List<Pelicula> seleccionarPeliculas(Scanner scanner) {
        System.out.print("Seleccione peliculas (numeros separados por coma): ");
        String entrada = scanner.nextLine().trim();
        System.out.println();

        List<Pelicula> seleccionadas = new ArrayList<>();
        for (String parte : entrada.split(",")) {
            try {
                int indice = Integer.parseInt(parte.trim()) - 1;
                if (indice < 0 || indice >= catalogo.size()) {
                    System.out.println("Numero invalido: " + (indice + 1) + " (ignorado)");
                    continue;
                }
                Pelicula pelicula = catalogo.get(indice);
                if (!pelicula.isDisponible()) {
                    System.out.println("\"" + pelicula.getTitulo() + "\" no esta disponible (ignorada)");
                    continue;
                }
                seleccionadas.add(pelicula);
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida: \"" + parte.trim() + "\" (ignorada)");
            }
        }
        return seleccionadas;
    }

    private void imprimirRecibo(List<Pelicula> peliculas, Membresia membresia) {
        double subtotal = peliculas.stream().mapToDouble(Pelicula::getPrecio).sum();
        double descuento = membresia.calcularDescuento(subtotal);
        double total = membresia.calcularTotal(subtotal);

        System.out.println("--- RECIBO DE ALQUILER ---");
        System.out.println("Cliente: " + membresia.getNombre());
        System.out.println("Peliculas:");
        for (Pelicula p : peliculas) {
            System.out.printf(" - %s (%s) - $%s%n",
                    p.getTitulo(),
                    p.getTipo(),
                    formatoPrecio.format(p.getPrecio()));
        }
        System.out.println("Subtotal: $" + formatoPrecio.format(subtotal));
        if (descuento > 0) {
            System.out.println("Descuento (20%): $" + formatoPrecio.format(descuento));
        }
        System.out.println("Total a pagar: $" + formatoPrecio.format(total));
        System.out.println("--------------------------");
        System.out.println("¡Disfrute su pelicula!");
    }
}
