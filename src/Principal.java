import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ConsultaPelicula consulta = new ConsultaPelicula();
        GeneradorDeArchivo generador = new GeneradorDeArchivo();

        System.out.println("Bienvenido al sistema de consulta de películas de Star Wars.");
        System.out.println("Ingrese el número de la película que desea buscar (1-6): ");
        int numeroDePelicula = scanner.nextInt();

        Pelicula pelicula = consulta.buscaPelicula(numeroDePelicula);

        if (pelicula != null) {
            System.out.println("Datos de la película obtenidos: ");
            System.out.println(pelicula);

            // Generar el archivo JSON
            String nombreArchivo = "pelicula" + numeroDePelicula + ".json";
            generador.generaArchivoJson(pelicula, nombreArchivo);
        } else {
            System.out.println("No se pudo encontrar la película o hubo un error en la consulta.");
        }
    }
}
