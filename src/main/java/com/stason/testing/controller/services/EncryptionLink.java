package com.stason.testing.controller.services;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
/**
 * It is an encryption class, which hashes links
 * @author Stanislav Hlova
 * @version 1.0
 */
public class EncryptionLink {
    private static final int ITERATIONS = 600;
    private static final int KEY_LENGTH = 512;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA512";

    private EncryptionLink() {
    }

    /**
     * Hashes login with a given salt
     * @param login a user's login
     * @param salt a salt
     * @return return hashed login
     */
    public static String hash(String login, String salt) {
        char[] chars = login.toCharArray();
        byte[] bytes = salt.getBytes();

        PBEKeySpec spec = new PBEKeySpec(chars, bytes, ITERATIONS, KEY_LENGTH);
        Arrays.fill(chars, Character.MIN_VALUE);

        try {
            SecretKeyFactory fac = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] secureLink = fac.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(secureLink).replace("\\+","A");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        } finally {
            spec.clearPassword();
        }


        return null;
    }
    /**
     * Generates a salt for hashing
     * @return return a salt
     */
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt.toString();
    }
}

