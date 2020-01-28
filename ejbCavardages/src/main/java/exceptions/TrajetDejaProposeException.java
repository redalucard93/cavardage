package exceptions;

public class TrajetDejaProposeException extends Exception {
    private static final String MESSAGE = "Vous ne pouvez pas reserver votre propre trajet";

    public TrajetDejaProposeException() {
        super(MESSAGE);
    }

}
