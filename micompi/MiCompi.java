package micompi;

import java.io.IOException;

public class MiCompi {
    nodo p;
    public static void main(String[] args) throws IOException {
        lexico lexico = new lexico();
        if (!lexico.errorEncontrado) {
            System.out.println("Analisis lexico terminado");
        }
    }
}