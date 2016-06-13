package com.company;

import java.io.*;
import java.security.PrivateKey;

public class Main {

    public static void main(String[] args) {

    }
    public static void readPrivateKey(){
        ObjectInputStream objectInputStream;
        try {
           objectInputStream = new ObjectInputStream(new FileInputStream("praviteKey"));
            PrivateKey privateKey = (PrivateKey) objectInputStream.readObject();

            FileReader fileReader= new FileReader("INPUT.EXT");
            BufferedReader  reader = new BufferedReader(fileReader);
            String text = reader.readLine();
            reader.close();
            fileReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
