import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Created by Kevin on 13-6-2016.
 */
public class genPublicandPrivateKey {

    public static void main(String[] args){
        generateKeys();
        System.out.println("Keys generated");
    }

    public static void generateKeys(){
        try{
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            //Het creeren van de public en de private key
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();
            writeKeys(publicKey, privateKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    public static void writeKeys(PublicKey publicKey, PrivateKey privateKey){
        ObjectOutputStream objectOutputStream;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("publicKey");
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            //Het schrijven van de public key
            objectOutputStream.writeObject(publicKey);
            //Het schrijven van de private key
            fileOutputStream = new FileOutputStream("privateKey");
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(privateKey);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
