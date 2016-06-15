package com.company;

import java.io.*;
import java.security.*;

public class encryptFile {

    static PrivateKey privateKey;
    static String input;

    public static void main(String[] args) {
        readInputs();
        signText(input, privateKey);
        System.out.println("Signed input with the private key");
    }

    private static void readInputs(){
        ObjectInputStream objectInputStream;
        try {
           objectInputStream = new ObjectInputStream(new FileInputStream("privateKey"));
            privateKey = (PrivateKey) objectInputStream.readObject();
            //Het lezen van de input file
            FileReader fileReader= new FileReader("INPUT.EXT");
            BufferedReader  reader = new BufferedReader(fileReader);
            //Text van de input file lezen
            input = reader.readLine();
            reader.close();
            fileReader.close();
            //Het signenen van de file met behulp van de private key

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
    private static void signText(String text, PrivateKey privateKey){
        try {
            //Het aanmaken van de signature
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initSign(privateKey);
            //Signature sign bytes om aan de file toe te voegen
            byte[] signatureBytes = signature.sign();
            File signedFile = new File("INPUT(SignedByLk).EXT");
            signedFile.createNewFile();
            //Schrijven van de file
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(signedFile));
            objectOutputStream.writeInt(signatureBytes.length);
            objectOutputStream.writeObject(signatureBytes);
            objectOutputStream.writeObject(text);
            objectOutputStream.close();

        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException | IOException e) {
            e.printStackTrace();
        }
    }
}
