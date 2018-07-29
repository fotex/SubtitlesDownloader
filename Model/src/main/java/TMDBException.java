public class TMDBException extends Exception {

    public TMDBException(String message) {
        super(message);
    }

    public TMDBException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
