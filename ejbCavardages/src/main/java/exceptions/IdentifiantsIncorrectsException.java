package exceptions;

/**
 * Created by alphalion27 on 01/11/17.
 */
public class IdentifiantsIncorrectsException extends Exception {

    private static final String MESSAGE = "Identifiants incorrects";

    public IdentifiantsIncorrectsException(){
        super(MESSAGE);
    }
}
