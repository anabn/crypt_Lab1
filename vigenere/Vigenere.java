package vigenere;

import java.io.*;
import java.util.Scanner;

public class Vigenere {
    private final static String key = "FETSUN";
    private final static String file = "vigFile";

    private static String readFile() throws IOException {
        FileReader data = new FileReader(file);
        Scanner sc = new Scanner(data);
        String st = "";
        while (sc.hasNextLine()){
            st += sc.nextLine().toUpperCase();
        }
        return st;
    }

    private static String cipherEncryption(String message, String key) {
        String encryptedText = "";
        for (int i = 0; i < message.length(); i++){
            if ((int)message.charAt(i) == 32){
                encryptedText += (char)32;
            } else if ((int)message.charAt(i) > 64 && (int)message.charAt(i) < 91) {
                int ch = (((message.charAt(i) - 'A') + (key.charAt(i % key.length()) - 'A')) % 26) + 'A';
                encryptedText += (char) ch;
//            } else if ((int)message.charAt(i) > 98 && (int)message.charAt(i) < 123) {
//                int ch = (((message.charAt(i) - 'a') + (key.charAt(i % key.length()) - 'a')) % 26) + 'a';
//                encryptedText += (char) ch;
            }
        }
        return encryptedText;
    }

    private static String cipherDecryption(String message) {
        String decryptedText = "";
        for (int i = 0; i < key.length(); i++) {
            if ((int)message.charAt(i) == 32) {
                decryptedText += (char) 32;
            } else {
                int ch = key.charAt(i) - 'A' - 1;
                ch = 'Z' - ch;
                decryptedText += (char) (ch);
            }
        }
        return cipherEncryption(message, decryptedText);
    }

    public static void main(String[] args) {
        try {
            String msg = readFile();
            System.out.println(msg);
            String enc = cipherEncryption(msg, key);
            String dec = cipherDecryption(enc);
            System.out.println(enc + "\n" + dec);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
