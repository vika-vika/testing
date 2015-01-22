package net.vannadis.testme.exception;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by viktoriala on 9/23/2014.
 */
public class TestMeException extends RuntimeException { // to be unchecked

        public TestMeException(String message) {
            super(message);
        }
}
