package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    PasswordField tfPassword;
    @FXML
    TextArea taMessage;
    @FXML
    Button btnEncrypt;
    @FXML
    Button btnDecrypt;
    //De file die gebruikt wordt om het gebeuren op te slaan
    File encryptedFile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnDecrypt.setOnMouseClicked(this::btnDecryptAction);
        btnEncrypt.setOnMouseClicked(this::btnEncryptAction);
        encryptedFile  = new File("EncryptedMessage");
    }

    private void btnDecryptAction(MouseEvent mouseEvent) {
        if(!tfPassword.getText().isEmpty() && tfPassword.getText() != null){
            decryptMessage(tfPassword.getText().getBytes());
        }
    }

    private void decryptMessage(byte[] passwordBytes) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream( new FileInputStream(encryptedFile));
            byte[] salt = (byte[])objectInputStream.readObject();
            byte[] password = (byte[]) objectInputStream.readObject();
            //Het salten van het ingevoerde wachtwoord
            byte[] passwordWithSalt = combineByteArray(salt, passwordBytes);
            //Hashen van dit geheel
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(passwordWithSalt);
            byte[] hashedPassword = messageDigest.digest();
            //Als dit wachtwoord overeen komt met het wachtwoord wat opgeslagen is dan is het wachtwoord correct ingevult
            if(Arrays.equals(hashedPassword,password)){
                String message = new String (new BASE64Decoder().decodeBuffer((String)objectInputStream.readObject()));
                taMessage.setText(message);
            }
            else {
                Alert failed = new Alert(Alert.AlertType.WARNING);
                failed.setContentText("Wrong password");
                failed.show();
            }
        } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void btnEncryptAction(MouseEvent mouseEvent) {
        String message = taMessage.getText();
        if(! tfPassword.getText().isEmpty()&&  tfPassword.getText() != null&&!message.isEmpty()&&message != null){
            byte[] password = tfPassword.getText().getBytes();
            encryptMessage(password, message);
        }
        taMessage.setText("");
    }

    private void encryptMessage(byte[] password, String message) {
        //Encode van de message
        BASE64Encoder encoder = new BASE64Encoder();
        String encryptedMessage = encoder.encode(message.getBytes());
        //Aanmaken van de random salt
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[32];
        secureRandom.nextBytes(salt);
        //Toevoegen van de salt aan het password
        byte[] passwordWithSalt = combineByteArray(salt,password);
        //Hashen van het hele gebeuren
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(passwordWithSalt);
            byte[] hashedPassword = messageDigest.digest();
            //Writen naar de file
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(encryptedFile));
            objectOutputStream.writeObject(salt);
            objectOutputStream.writeObject(hashedPassword);
            objectOutputStream.writeObject(encryptedMessage);
            //Laten zien dat het gelukt is om de file aan te maken
            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setContentText("File Created");
            success.show();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        finally {
            Arrays.fill(password, (byte) 0);
        }
    }


    /**
     * This message will comebine arrayOne with arrayTwo
     * @param arrayOne
     * @param arrayTwo
     * @return is a combined array
     */
    private byte[] combineByteArray(byte[]arrayOne, byte[] arrayTwo){
        byte[] combined = new byte[arrayOne.length + arrayTwo.length];
        for (int i = 0; i < combined.length; ++i)
        {
            combined[i] = i < arrayOne.length ? arrayOne[i] : arrayTwo[i - arrayOne.length];
        }
        return combined;
    }
}
