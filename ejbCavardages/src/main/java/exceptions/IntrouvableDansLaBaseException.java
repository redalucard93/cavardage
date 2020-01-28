package exceptions;

public class IntrouvableDansLaBaseException extends Exception {

    private static final String MESSAGE = "Un element est manquant dans la base de données";

    public IntrouvableDansLaBaseException(){
        super(MESSAGE);
    }
}
