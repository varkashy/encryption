package com.varkashy.apple.encrypt.api;

import org.junit.jupiter.api.Test;

import javax.crypto.IllegalBlockSizeException;

import static org.junit.jupiter.api.Assertions.*;

class CryptoApiTest {

    final CryptoApi cryptoApi = new CryptoApi();
    private final String PLAIN_TEXT = "TestMe";

    @Test
    void testEncryptionDecryption(){
        try {
            final String cipherText = cryptoApi.encrypt(PLAIN_TEXT);
            assertNotEquals(cipherText,PLAIN_TEXT);
            assertEquals(PLAIN_TEXT,cryptoApi.decrypt(cipherText));
        } catch (Exception e) {
           fail();
        }
    }

    @Test
    public void testInvalidEncryption(){
        try {
            cryptoApi.decrypt(PLAIN_TEXT);
            fail();
        } catch (IllegalBlockSizeException e) {
            // Do Nothing
        }
        catch (Exception ex){
            fail();
        }
    }
}