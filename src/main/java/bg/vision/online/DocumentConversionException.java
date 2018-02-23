package bg.vision.online;

/**
 *
 */
final class DocumentConversionException extends RuntimeException {

    public DocumentConversionException(String message, Exception ex) {
        super(message, ex);
    }
}
