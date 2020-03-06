package com.satb.safekeeper;


import java.io.Console;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SafeKeeper {

    private static final String ALGO = "AES/CBC/PKCS5PADDING";
;   private static final String ivStr = "0123456789abcdef";

    private static String encrypt(String value, String secret) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        IvParameterSpec iv = new IvParameterSpec(ivStr.getBytes("UTF-8"));
        SecretKeySpec skeySpec = new SecretKeySpec(secret.getBytes("UTF-8"), "AES");

        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

        byte[] encrypted = cipher.doFinal(value.getBytes());
        return new String(Base64.getEncoder().encode(encrypted));
    }

    private static String decrypt(String strToDecrypt, String secret) throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        IvParameterSpec iv = new IvParameterSpec(ivStr.getBytes("UTF-8"));
        SecretKeySpec skeySpec = new SecretKeySpec(secret.getBytes("UTF-8"), "AES");

        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        byte[] original = cipher.doFinal(Base64.getDecoder().decode(strToDecrypt));

        return new String(original);
    }

    public static void main(String[] args) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        Console console = System.console();
        System.out.print("Enter the secret key: ");
        char [] secret = console.readPassword();
        String secretKey = String.valueOf(secret);
        System.out.println("Your secret key is " + secretKey);

        System.out.println("Do you want to \n 1. Encrypt \n 2. Decrypt");
        System.out.print("Enter your choice: ");
        String option = console.readLine();
        System.out.println("You selected " + option);

        if(option.equals("1")){
            System.out.print("Enter the text: ");
            String plainText = console.readLine();
            String encryptedString = SafeKeeper.encrypt(plainText, secretKey) ;
            System.out.println(encryptedString);
        }else {
            System.out.print("Enter the encrypted text: ");
            String cipherText = console.readLine();
            String decryptedString = SafeKeeper.decrypt(cipherText, secretKey) ;
            System.out.println(decryptedString);
        }
    }
}