package es.princip.getp.batch;

public class BatchInsertionException extends RuntimeException {

    public BatchInsertionException(final String message) {
        super(message);
    }

    public BatchInsertionException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
