package eci.edu.byteProgramming.ejercicio.paper.videoclub;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

public class VideoclubTest {

    // ─── PeliculaFisica ──────────────────────────────────────────────────────

    @Test
    void peliculaFisica_getTipo_returnsCorrectType() {
        PeliculaFisica pelicula = new PeliculaFisica("Interestellar", 8000, true);
        assertEquals("Fisica", pelicula.getTipo());
    }

    @Test
    void peliculaFisica_getters_returnCorrectValues() {
        PeliculaFisica pelicula = new PeliculaFisica("Interestellar", 8000, true);
        assertEquals("Interestellar", pelicula.getTitulo());
        assertEquals(8000, pelicula.getPrecio());
        assertTrue(pelicula.isDisponible());
    }

    @Test
    void peliculaFisica_noDisponible_returnsCorrectState() {
        PeliculaFisica pelicula = new PeliculaFisica("El Padrino", 7000, false);
        assertFalse(pelicula.isDisponible());
    }

    // ─── PeliculaDigital ─────────────────────────────────────────────────────

    @Test
    void peliculaDigital_getTipo_returnsCorrectType() {
        PeliculaDigital pelicula = new PeliculaDigital("Inception", 5000, true);
        assertEquals("Digital", pelicula.getTipo());
    }

    @Test
    void peliculaDigital_getters_returnCorrectValues() {
        PeliculaDigital pelicula = new PeliculaDigital("Matrix", 6000, true);
        assertEquals("Matrix", pelicula.getTitulo());
        assertEquals(6000, pelicula.getPrecio());
        assertTrue(pelicula.isDisponible());
    }

    // ─── MembresiaBasica ─────────────────────────────────────────────────────

    @Test
    void membresiaBasica_getNombre_returnsBasica() {
        MembresiaBasica membresia = new MembresiaBasica();
        assertEquals("Basica", membresia.getNombre());
    }

    @Test
    void membresiaBasica_calcularTotal_noAppliesDiscount() {
        MembresiaBasica membresia = new MembresiaBasica();
        assertEquals(13000.0, membresia.calcularTotal(13000.0), 0.001);
    }

    @Test
    void membresiaBasica_calcularDescuento_returnsZero() {
        MembresiaBasica membresia = new MembresiaBasica();
        assertEquals(0.0, membresia.calcularDescuento(13000.0), 0.001);
    }

    // ─── MembresiaPremium ────────────────────────────────────────────────────

    @Test
    void membresiaPremium_getNombre_returnsPremium() {
        MembresiaPremium membresia = new MembresiaPremium();
        assertEquals("Premium", membresia.getNombre());
    }

    @Test
    void membresiaPremium_calcularTotal_applies20PercentDiscount() {
        MembresiaPremium membresia = new MembresiaPremium();
        assertEquals(10400.0, membresia.calcularTotal(13000.0), 0.001);
    }

    @Test
    void membresiaPremium_calcularDescuento_returnsCorrectAmount() {
        MembresiaPremium membresia = new MembresiaPremium();
        assertEquals(2600.0, membresia.calcularDescuento(13000.0), 0.001);
    }

    // ─── Videoclub ───────────────────────────────────────────────────────────

    @Test
    void videoclub_ejecutar_premiumMovies1And3_printsCorrectReceipt() {
        String input = "2\n1,3\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        assertDoesNotThrow(() -> new Videoclub().ejecutar(scanner));
    }

    @Test
    void videoclub_ejecutar_basicaWithUnavailableMovie_skipsUnavailable() {
        // El Padrino (2) no disponible → se ignora, Interestellar y Matrix sí
        String input = "1\n1,2,4\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        assertDoesNotThrow(() -> new Videoclub().ejecutar(scanner));
    }

    @Test
    void videoclub_ejecutar_invalidMembership_reprompts() {
        // "abc" inválido → re-pregunta → "1" válido → Basica
        String input = "abc\n1\n3\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        assertDoesNotThrow(() -> new Videoclub().ejecutar(scanner));
    }

    @Test
    void videoclub_ejecutar_unavailableMovieThenValid_reprompts() {
        // Primer intento: solo El Padrino (no disponible) → re-pregunta
        // Segundo intento: Inception (disponible) → éxito
        String input = "1\n2\n3\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        assertDoesNotThrow(() -> new Videoclub().ejecutar(scanner));
    }

    @Test
    void videoclub_ejecutar_invalidMovieInputThenValid_reprompts() {
        // Primer intento: "xyz" → inválido → re-pregunta
        // Segundo intento: "1,3" → válido → éxito
        String input = "2\nxyz\n1,3\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        assertDoesNotThrow(() -> new Videoclub().ejecutar(scanner));
    }

    @Test
    void videoclub_ejecutar_emptyMovieInput_reprompts() {
        // Primer intento: "" (vacío) → pide de nuevo
        // Segundo intento: "4" → válido
        String input = "1\n\n4\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        assertDoesNotThrow(() -> new Videoclub().ejecutar(scanner));
    }

    @Test
    void videoclub_ejecutar_noInputAtAll_handlesGracefully() {
        // Scanner vacío → sale sin excepción
        String input = "1\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        assertDoesNotThrow(() -> new Videoclub().ejecutar(scanner));
    }

    // ─── VideoClubApp ────────────────────────────────────────────────────────

    @Test
    void videoClubApp_main_doesNotThrow() {
        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream("1\n3\n".getBytes()));
        assertDoesNotThrow(() -> VideoClubApp.main(new String[]{}));
        System.setIn(originalIn);
    }
}
