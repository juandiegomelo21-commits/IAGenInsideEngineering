package eci.edu.byteProgramming.ejercicio.paper.videoclub;

import java.util.Scanner;

public class VideoClubApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        new Videoclub().ejecutar(scanner);
        scanner.close();
    }
}
