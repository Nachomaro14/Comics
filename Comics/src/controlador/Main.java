package controlador;
import vista.interfaz;

public class Main {
    
    public static void main(String[] args) {
        new controlador( new interfaz() ).iniciar() ;
    }
}