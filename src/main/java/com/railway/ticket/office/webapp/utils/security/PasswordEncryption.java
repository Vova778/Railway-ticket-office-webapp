package com.railway.ticket.office.webapp.utils.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class implements functionality of passwords encryption
 * using SHA-256
 */
public class PasswordEncryption {
    private static final Logger LOGGER = LogManager.getLogger(PasswordEncryption.class);
    private PasswordEncryption() {
    }

    public static String getEncrypted(String input) {
        if (input == null) {
            throw new IllegalArgumentException("PasswordEncryption: Can't encrypt null...");
        }
        return bytesToHex(encrypt(input.toCharArray()));
    }

    private static byte[] encrypt(char[] input) {
        byte[] bytes = StandardCharsets.UTF_8.encode(CharBuffer.wrap(input)).array();
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        return messageDigest.digest(bytes);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xff & aByte);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static boolean validate(String password, String userPassword) {

        if (password == null || userPassword == null) {
            throw new IllegalArgumentException("Can't validate null!");
        }
        LOGGER.info("[PasswordEncrypt]" +
                " password: [{}], user.password: [{}]", password, userPassword);
        return password.equals(userPassword);
    }


}
