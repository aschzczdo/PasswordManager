package AES;

import user.User;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Decrypt {
    public String Decrypt(String decrypt){
        User user = new User();
        String password = user.getPassword();
        //final byte[] SALT = "YourStaticSaltHere".getBytes(StandardCharsets.UTF_8);-->Testing if method works
        try{
            SecretKeySpec secretKeySpec = SecretKey.createSecretKeySpec(password,SecretKey.generateSalt()); //Creating SecreteKeySpec object. IMPORTANT! STORE THE salt in DB
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE,secretKeySpec);//Initializing Cipher object with 2 params (Encrypt mode, SecretKey)
            //Doing encrypt to byte level
            byte [] chain  =    Base64.getDecoder().decode(decrypt);
            byte[] decrypted = cipher.doFinal(chain); //Encrypting bytes
            //Transforming encrypted bytes to an Stringed (keeps encrypted but legible)
            String decryptedString = new String (decrypted); //Getting bytes to Base64
            return decryptedString;
        }catch(Exception e){
            return null;
        }
    }
}
