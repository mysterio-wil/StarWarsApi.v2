public record Pelicula(String title, String director, String producer, String release_date) {

    @Override
    public String toString() {
        return "Título: " + title + "\nDirector: " + director + "\nProductor: " + producer + "\nFecha de lanzamiento: " + release_date;
    }
}
