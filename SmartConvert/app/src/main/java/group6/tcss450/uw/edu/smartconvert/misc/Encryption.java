package group6.tcss450.uw.edu.smartconvert.misc;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by donal on 11/29/2017.
 */

public class Encryption {

    public Encryption() {}

    /**
     * Encodes the password using SHA-1 encryption.
     * @param thePass the password to be encrypted.
     * @return a String version of the byte array encryption or null if encoding failed.
     */
    public static String encodePass(final String thePass) {
        byte[] byteArray = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(thePass.getBytes());
            byteArray = md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if (byteArray != null) {
            return new String(byteArray);
        }

        return null;
    }
}
