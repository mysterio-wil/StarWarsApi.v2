import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;

public class GeneradorDeArchivo {

    public void generaArchivoJson(Pelicula pelicula, String nombreArchivo) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(pelicula);

        try (FileWriter escritor = new FileWriter(nombreArchivo)) {
            escritor.write(json);
            System.out.println("Archivo JSON generado exitosamente: " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Ocurrió un error al escribir el archivo: " + e.getMessage());
        }
    }
}

