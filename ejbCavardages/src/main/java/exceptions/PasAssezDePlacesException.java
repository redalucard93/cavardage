package exceptions;

public class PasAssezDePlacesException extends Exception {
    private static final String MESSAGE = "Il n'y a pas assez de places disponibles pour ce trajet";

    public PasAssezDePlacesException(){
        super(MESSAGE);
    }
}
