package com.satb.safekeeper;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.Console;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SafeKeeper {

    private static SecretKeySpec secretKey;
    private static byte[] key;
    private static final String ALGO = "AES/CBC/PKCS7Padding";
;   private static final String ivStr = "0123456789abcdef";
    private static void setKey(String myKey)
    {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static String encrypt(String strToEncrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance(ALGO);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(ivStr.getBytes(StandardCharsets.US_ASCII)));
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.US_ASCII)));
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    private static String decrypt(String strToDecrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance(ALGO);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(ivStr.getBytes(StandardCharsets.US_ASCII)));
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e)
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

    public static void main(String[] args)
    {
        Security.addProvider(new BouncyCastleProvider());
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