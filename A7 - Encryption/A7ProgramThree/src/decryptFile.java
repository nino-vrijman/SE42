import java.io.*;
import java.security.*;

/**
 * Created by Kevin on 13-6-2016.
 */
public class decryptFile {

    public static String fileData;
    public static void main(String[] args) {
        PublicKey publicKey = getPublicKey();
        boolean fileIsValid = fileIsValid(publicKey);
        if (fileIsValid) {
            System.out.println("The file has been decrypted and is valid");
            System.out.println("Filecontent: "+ fileData);
            createOriginalFile();
        }
        else {
            System.out.println("The has not been decrypted and is not valid");
        }
    }

    private static void createOriginalFile() {
        File originalFile = new File("INPUT.EXT");
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(originalFile));
            objectOutputStream.writeObject(fileData);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean fileIsValid(PublicKey publicKey) {
        ObjectInputStream objectInputStream;
        boolean fileIsValid = false;
        try{
            objectInputStream = new ObjectInputStream(new FileInputStream("INPUT(SignedByLk).EXT"));
            int sizeOfSignature = objectInputStream.readInt();
            byte[] signatureBytes = (byte[]) objectInputStream.readObject();
            String fileText = (String) objectInputStream.readObject();
            //verify the file by creating a signature with the public key
            Signature publicSig = Signature.getInstance("SHA1WithRSA");
            publicSig.initVerify(publicKey);
            publicSig.verify(signatureBytes);
            fileIsValid =  publicSig.verify(signatureBytes);
            if(fileIsValid){
                fileData = fileText;
            }
        } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }
        return fileIsValid;
    }

    private static PublicKey getPublicKey() {
        PublicKey returnKey = null;
        ObjectInputStream objectInputStream;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream("publicKey"));
            returnKey = (PublicKey) objectInputStream .readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return returnKey;
    }
}
