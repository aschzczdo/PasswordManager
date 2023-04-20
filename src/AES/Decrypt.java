package AES;

import user.User;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Decrypt {
    public static String decryptData(String data, SecretKeySpec secretKeySpec) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec); // Initializing Cipher object with 2 params (Decrypt mode, SecretKey)

            // Decoding the Base64-encoded string
            byte[] chain = Base64.getDecoder().decode(data);
            byte[] decrypted = cipher.doFinal(chain); // Decrypting bytes

            // Transforming decrypted bytes to a String
            String decryptedString = new String(decrypted, StandardCharsets.UTF_8);
            return decryptedString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
