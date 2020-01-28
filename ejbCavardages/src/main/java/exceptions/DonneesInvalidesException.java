package exceptions;

/**
 * Created by alphalion27 on 01/11/17.
 */
public class DonneesInvalidesException extends Exception {

    private static final String MESSAGE = "Données invalides";

    public DonneesInvalidesException(){
        super(MESSAGE);
    }


}
