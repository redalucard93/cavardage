package exceptions;

public class PasDeVehiculePossedeException extends Exception {

    private static final String MESSAGE = "Vous devez précisez votre véhicule avant de proposer un trajet";

    public PasDeVehiculePossedeException(){
        super(MESSAGE);
    }
}

