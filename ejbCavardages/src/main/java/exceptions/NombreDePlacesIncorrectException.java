package exceptions;

public class NombreDePlacesIncorrectException extends Exception {
    private static final String MESSAGE = "Vous ne pouvez pas proposer plus de places que votre véhicule posséde";

    public NombreDePlacesIncorrectException(){
        super(MESSAGE);
    }
}
