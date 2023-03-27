package AES;

import user.User;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Encrypt {
    public static String encryptPassword(String password, SecretKeySpec secretKeySpec) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] passwordBytes = password.getBytes("UTF-8");
            byte[] encryptedBytes = cipher.doFinal(passwordBytes);
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting password", e);
        }
    }
}
