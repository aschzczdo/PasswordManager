package AES;

import user.User;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Encrypt {
    public String Encrypt(String encrypt){
        User user = new User();
        String password = user.getPassword();
        //final byte[] SALT = "YourStaticSaltHere".getBytes(StandardCharsets.UTF_8); -->Testing if method works
        try{
            SecretKeySpec secretKeySpec = SecretKey.createSecretKeySpec(password,SecretKey.generateSalt()); //Creating SecreteKeySpec object. IMPORTANT! STORE THE salt in DB
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec);//Initializing Cipher object with 2 params (Encrypt mode, SecretKey)
            //Doing encrypt to byte level
            byte [] chain  = encrypt.getBytes("UTF-8"); //Getting our Encrypt String as bytes
            byte[] encrypted = cipher.doFinal(chain); //Encrypting bytes
            //Transforming encrypted bytes to an Stringed (keeps encrypted but legible)
            String encryptedString = Base64.getEncoder().encodeToString(encrypted); //Getting bytes to Base64
            return encryptedString;
        }catch(Exception e){
            return null;
        }
    }
}
