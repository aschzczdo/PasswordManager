package AES;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

public class SecurePwdStorage {
    private static KeyStore keyStore;

    static {
        try {
            keyStore = KeyStore.getInstance("JCEKS");
            keyStore.load(null, null); // Create an empty KeyStore
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
            throw new RuntimeException("Error initializing KeyStore", e);
        }
    }

    public static void storePassword(String password) {
        try {
            SecretKey secretKey = new SecretKeySpec(password.getBytes(), "RAW");
            KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(secretKey);
            KeyStore.PasswordProtection passwordProtection = new KeyStore.PasswordProtection("password-alias".toCharArray());
            keyStore.setEntry("password-alias", secretKeyEntry, passwordProtection);
        } catch (KeyStoreException e) {
            throw new RuntimeException("Error storing password in KeyStore", e);
        }
    }

    public static String retrievePassword() {
        try {
            KeyStore.PasswordProtection passwordProtection = new KeyStore.PasswordProtection("password-alias".toCharArray());
            KeyStore.SecretKeyEntry secretKeyEntry = (KeyStore.SecretKeyEntry) keyStore.getEntry("password-alias", passwordProtection);
            if (secretKeyEntry == null) {
                return null;
            }
            SecretKey secretKey = secretKeyEntry.getSecretKey();
            return new String(secretKey.getEncoded());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableEntryException e) {
            throw new RuntimeException("Error retrieving password from KeyStore", e);
        }
    }
}

