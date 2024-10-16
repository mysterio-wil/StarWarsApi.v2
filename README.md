# Aplicación de Consulta de Películas de Star Wars

Esta aplicación permite consultar información sobre las películas de Star Wars mediante la API pública **SWAPI**. El usuario puede elegir una película desde un menú interactivo, visualizar la información obtenida y generar un archivo `.json` con los detalles de la película.

## Tabla de Contenidos

1. [Descripción del Proyecto](#descripción-del-proyecto)
2. [Estructura del Proyecto](#estructura-del-proyecto)
    - [ConsultaPelicula](#consultapelicula)
    - [GeneradorDeArchivo](#generadordearchivo)
    - [Pelicula (Record)](#pelicula-record)
    - [Principal](#principal)
3. [Requerimientos](#requerimientos)
4. [Uso de la Aplicación](#uso-de-la-aplicación)
5. [Compilación y Ejecución](#compilación-y-ejecución)

---

## Descripción del Proyecto

La aplicación consulta las películas de Star Wars usando la API SWAPI. El usuario selecciona una película introduciendo su número (del 1 al 6), la aplicación hace la consulta a la API y devuelve la información correspondiente. Además, genera un archivo `.json` con los detalles de la película obtenidos.

## Estructura del Proyecto

El proyecto está estructurado en las siguientes clases:

### 1. ConsultaPelicula

La clase `ConsultaPelicula` se encarga de realizar las consultas HTTP a la API de SWAPI. Hace uso de la clase `HttpClient` para enviar la solicitud y de la clase `Gson` para deserializar la respuesta JSON en un objeto `Pelicula`.

#### Código:

```java
import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultaPelicula {

    public Pelicula buscaPelicula(int numeroDePelicula) {
        // Construir la URL de la película basada en el número ingresado por el usuario
        URI direccion = URI.create("https://swapi.py4e.com/api/films/" + numeroDePelicula + "/");

        // Crear cliente HTTP y preparar la solicitud
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(direccion)
                .build();

        try {
            // Enviar la solicitud y capturar la respuesta
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            // Verificar el estado de la respuesta
            if (response.statusCode() != 200) {
                System.out.println("Error: " + response.statusCode() + " al consultar la película.");
                return null;
            }

            // Deserializar el cuerpo de la respuesta JSON en un objeto Pelicula
            String json = response.body();
            return new Gson().fromJson(json, Pelicula.class);

        } catch (IOException | InterruptedException e) {
            System.out.println("Ocurrió un error durante la solicitud: " + e.getMessage());
            return null;
        }
    }
}
```
### 2. GeneradorDeArchivo

La clase `GeneradorDeArchivo` genera un archivo `.json` que contiene la información de la película obtenida de la API. Utiliza `Gson` configurado con `PrettyPrinting` para escribir el archivo en formato legible.

#### Código:

```java
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;

public class GeneradorDeArchivo {

    // Método que genera el archivo .json con los detalles de la película
    public void generaArchivoJson(Pelicula pelicula, String nombreArchivo) {
        // Configuración de Gson para escribir el JSON de manera bonita (Pretty Printing)
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        
        // Convertir el objeto Pelicula en formato JSON
        String json = gson.toJson(pelicula);

        // Escribir el JSON en un archivo
        try (FileWriter escritor = new FileWriter(nombreArchivo)) {
            escritor.write(json);
            System.out.println("Archivo JSON generado exitosamente: " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Ocurrió un error al escribir el archivo: " + e.getMessage());
        }
    }
}
```
## 3. Pelicula (Record)

La clase `Pelicula` está representada como un `record`. Este `record` encapsula los campos que representan los datos relevantes de la película, tales como el título, el director, el productor y la fecha de lanzamiento. Se utiliza para deserializar el JSON de la API SWAPI.

### Código:

```java
public record Pelicula(String title, String director, String producer, String release_date) {

    // Sobrescritura del método toString() para mostrar los datos de la película en un formato más claro
    @Override
    public String toString() {
        return "Título: " + title + "\nDirector: " + director + "\nProductor: " + producer + "\nFecha de lanzamiento: " + release_date;
    }
}
```
## 4. Principal

La clase `Principal` es el punto de entrada de la aplicación. Presenta un menú para que el usuario seleccione la película que desea consultar, llama a `ConsultaPelicula` para obtener los datos, muestra la información en la consola y luego genera un archivo `.json` con los datos de la película.

### Código:

```java
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        // Escáner para capturar la entrada del usuario
        Scanner scanner = new Scanner(System.in);
        ConsultaPelicula consulta = new ConsultaPelicula();
        GeneradorDeArchivo generador = new GeneradorDeArchivo();

        // Solicitar al usuario el número de la película a consultar
        System.out.println("Bienvenido al sistema de consulta de películas de Star Wars.");
        System.out.println("Ingrese el número de la película que desea buscar (1-6): ");
        int numeroDePelicula = scanner.nextInt();

        // Buscar los datos de la película correspondiente
        Pelicula pelicula = consulta.buscaPelicula(numeroDePelicula);

        // Si la película fue encontrada, mostrar los datos y generar el archivo JSON
        if (pelicula != null) {
            System.out.println("Datos de la película obtenidos: ");
            System.out.println(pelicula);

            // Generar el archivo JSON con los detalles de la película
            String nombreArchivo = "pelicula" + numeroDePelicula + ".json";
            generador.generaArchivoJson(pelicula, nombreArchivo);
        } else {
            System.out.println("No se pudo encontrar la película o hubo un error en la consulta.");
        }
    }
}
````
## Requerimientos
- Java 11 o superior.
- Conexión a internet para consultar la API SWAPI.
- API de SWAPI: La aplicación consulta la API pública de Star Wars en [SWAPI](https://swapi.py4e.com/api/films/).

## Uso de la Aplicación
1. El usuario ejecuta la aplicación y se le presenta un menú para elegir una película de Star Wars ingresando un número entre 1 y 6.
2. La aplicación consulta la API y obtiene los detalles de la película seleccionada.
3. Los detalles de la película se muestran en la consola.
4. Se genera un archivo `.json` con los datos de la película.

