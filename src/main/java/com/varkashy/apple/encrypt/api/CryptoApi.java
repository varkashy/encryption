package com.varkashy.apple.encrypt.api;

import com.varkashy.apple.encrypt.api.util.FileUtil;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Entry point for Cryptography operations.
 * We define a symmeteric key using AES and store it on file system
 * For each run when the object is constructed, it tries to read from file/if it
 * exists, if the file exists, the same secret key is re-used , else a
 * new secret key is created and persisted in the file system.
 */
public class CryptoApi {
    static Cipher cipher;
    private final SecretKey secretKey;
    private static final String KEY_PATH = "src/main/resources/%s";
    private static final String KEY_FILE = "key.aes";

    /**
     * Constructor with logic to initialize secret key and cipher
     */
    public CryptoApi(){
        try {
            secretKey = initializeKeyIfNotSet();
            cipher = Cipher.getInstance("AES");
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize encryption flow");
        }
    }

    /**
     * Utility method that ensures the key exists. This is like an
     * ensureFind implementation - if the key doesn't exist it
     * creates a new one.
     * @return SecretKey to be used for encryption/decryption
     * @throws NoSuchAlgorithmException
     */
    private SecretKey initializeKeyIfNotSet() throws NoSuchAlgorithmException, IOException {
        byte[] encodedKey = FileUtil.readFromFile(String.format(KEY_PATH,KEY_FILE));
        if(encodedKey.length>0){
            return new SecretKeySpec(encodedKey,"AES");
        }
        else{
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            SecretKey secretKey = keyGenerator.generateKey();
            encodedKey = secretKey.getEncoded();
            FileUtil.writeToFile(encodedKey,String.format(KEY_PATH,KEY_FILE));
            return secretKey;
        }
    }

    /**
     * Encryption logic
     * @param plainText Text that needs to be encrypted
     * @return Encrypted String representation of value
     * @throws InvalidKeyException Encryption exception if secret key is not correct
     * @throws BadPaddingException Encryption exception if padding is off
     * @throws IllegalBlockSizeException Encryption exception if block size is less
     */
    public String encrypt(String plainText) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] plainTextByte = plainText.getBytes();
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedByte = cipher.doFinal(plainTextByte);
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(encryptedByte);
    }

    /**
     * Decryption logic, decrypts and returns plain text
     * @param encryptedText Cipher text that needs to be decrypted
     * @return Plain Text for encrypted value
     * @throws InvalidKeyException Encryption exception if secret key is not correct
     * @throws BadPaddingException Encryption exception if padding is off
     * @throws IllegalBlockSizeException Encryption exception if block size is less
     */
    public String decrypt(String encryptedText) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] encryptedTextByte = decoder.decode(encryptedText);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
        return new String(decryptedByte);
    }
}
