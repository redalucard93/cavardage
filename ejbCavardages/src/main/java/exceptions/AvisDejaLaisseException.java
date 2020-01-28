package exceptions;

public class AvisDejaLaisseException extends Exception {
    private static final String MESSAGE = "Avis déjà laissé pour ce trajet";

    public AvisDejaLaisseException(){
        super(MESSAGE);
    }

}
