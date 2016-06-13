import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Created by Kevin on 13-6-2016.
 */
public class genPublicandPrivateKey {

    public static void main(String[] args){
        generateKeys();
        System.out.println("Key generated");
    }

    public static void generateKeys(){
        try{
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024);
            PublicKey publicKey = keyPairGenerator.generateKeyPair().getPublic();
            PrivateKey privateKey = keyPairGenerator.generateKeyPair().getPrivate();
            writeKeys(publicKey, privateKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    public static void writeKeys(PublicKey publicKey, PrivateKey privateKey){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("publicKey");
            fileOutputStream.write(publicKey.getEncoded());
            fileOutputStream.close();
            fileOutputStream = new FileOutputStream("privateKey");
            fileOutputStream.write(privateKey.getEncoded());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
