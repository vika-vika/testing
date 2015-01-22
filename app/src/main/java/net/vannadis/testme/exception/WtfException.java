package net.vannadis.testme.exception;

/**
 * Created by viktoriala on 9/24/2014.
 */
public class WtfException extends RuntimeException { // to be unchecked

    public WtfException(String message) {
        //to lazy to do smth with it
        super(message);
    }
}
