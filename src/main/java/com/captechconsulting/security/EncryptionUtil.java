package com.captechconsulting.security;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;


public class EncryptionUtil {

    public static final String ENCODING_CHARSET = "UTF-8";
    public static final String DES_ENCRYPTION_ALGORITHM = "DES";

    private boolean encryptionEnabled;

    public EncryptionUtil() {
        this.encryptionEnabled = true;
    }

    public EncryptionUtil(boolean encryptionEnabled) {
        this.encryptionEnabled = encryptionEnabled;
    }

    private Cipher getEncryptionCipher(final String value) throws GeneralSecurityException {
        final Cipher encryptionCipher;
        final KeySpec keySpec = new DESKeySpec(value.getBytes());
        final SecretKey key = SecretKeyFactory.getInstance(DES_ENCRYPTION_ALGORITHM).generateSecret(keySpec);
        encryptionCipher = Cipher.getInstance(key.getAlgorithm());
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key);
        return encryptionCipher;
    }

    private Cipher getDecryptionCipher(final String value) throws GeneralSecurityException {
        KeySpec keySpec = new DESKeySpec(value.getBytes());
        SecretKey key = SecretKeyFactory.getInstance(DES_ENCRYPTION_ALGORITHM).generateSecret(keySpec);
        Cipher decryptionCipher = Cipher.getInstance(key.getAlgorithm());
        decryptionCipher.init(Cipher.DECRYPT_MODE, key);
        return decryptionCipher;
    }

    public String decrypt(final String encryptedValue, final String password) throws IOException, GeneralSecurityException {
        if (this.encryptionEnabled) {
            byte[] decodedValue = new sun.misc.BASE64Decoder().decodeBuffer(encryptedValue);
            byte[] unencryptedBytes = this.getDecryptionCipher(password).doFinal(decodedValue);
            return new String(unencryptedBytes, ENCODING_CHARSET);
        } else {
            return encryptedValue;
        }
    }

    public String encrypt(final String plainValue, final String password) throws IOException, GeneralSecurityException {
        if (this.encryptionEnabled) {
            final byte[] encryptedBytes = this.getEncryptionCipher(password).doFinal(plainValue.getBytes(ENCODING_CHARSET));
            final String base64WithNewlines = new sun.misc.BASE64Encoder().encode(encryptedBytes);
            return base64WithNewlines.replace(System.getProperty("line.separator"), "");
        } else {
            return plainValue;
        }
    }

    public boolean encryptionEnabled() {
        return this.encryptionEnabled;
    }

    public void encryptionEnabled(final boolean isEncryptionEnabled) {
        this.encryptionEnabled = isEncryptionEnabled;
    }

}