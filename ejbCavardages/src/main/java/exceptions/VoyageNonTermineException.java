package exceptions;

public class VoyageNonTermineException extends Exception {

    private static final String MESSAGE = "Le voyage n'est pas encore terminé";
    public VoyageNonTermineException(){
        super(MESSAGE);
    }
}
