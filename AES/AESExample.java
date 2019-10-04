package AES;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileReader;
import java.io.IOException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AESExample {

    /**
     * @param (ALGORITHM, ecbAl, tripleAlg) - AES && ECB using one function to generate key
     *                                      for tripleDes using string io to generate key
     *
     *  State[row][col] = input [row + 4col] -> matrix[4][4]
     *
     *  Количество раундов варьируется в зависимости от длины ключа,
     *  соответственно 10 раундов для 128-битных ключей,
     *  12 раундов для 192-битных ключей
     *  14 раундов для 256-битных ключей.
     *
     */
    private static final String file = "file";
    private static final String ALGORITHM = "AES";
    private static final String ecbAl = "AES/ECB/PKCS5PADDING";
    private static final String tripleAlg = "DESede/ECB/PKCS5Padding";
    private static final String io = "FetsunAnnaVolodumurivna";
    private static int size = 256; //  128 || 192 || 256
    private static int maxKeyLen = 0;

    /**
     *
     * @return st - our message to decode/encrypt var24
     * @throws IOException
     *
     */
    public static String readFile(String doc) throws IOException {
        FileReader data = new FileReader(doc);
        Scanner sc = new Scanner(data);
        String st = "";
        while (sc.hasNextLine()){
            st += sc.nextLine();
        }
        return st;
    }

    /**
     * size = 256
     */
    private Key generateKey(){
        try {
            final KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
            keyGen.init(size); // 256

            final SecretKey key = keyGen.generateKey();
            //final SecretKeySpec myKey = new SecretKeySpec("FetsunAnnaVolodumurivna".getBytes(), "AES");
            //System.out.println("mykey: " + myKey);
            return key;
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return null;
    }

    private String enctypt (String algorithm, String Data, Key key) throws Exception {
        if (Data == null) {
            return "empty message";
        }
        Cipher cipher = Cipher.getInstance(algorithm);
        // получить/создать симметричный ключ шифрования
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = cipher.doFinal(Data.getBytes());
        String EncodedString = Base64.getMimeEncoder().encodeToString(encVal);
        return EncodedString;
    }

    private String decrypt (String algorithm, String encryptedData, Key key) throws Exception {
        if (encryptedData == null) {
            return "empty message";
        }
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = Base64.getMimeDecoder().decode(encryptedData);
        byte[] decValue = cipher.doFinal(decordedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }

    public static void main(String[] args) {
        AESExample aes = new AESExample();
        tripleDES tripleDes = new tripleDES();
        try{
            final Key key = aes.generateKey();
            final String message = readFile(file);

            String encdata = aes.enctypt(ALGORITHM, message, key);
            String decdata = aes.decrypt(ALGORITHM, encdata, key);

            String encECBdata = aes.enctypt(ecbAl, message, key);
            String decECBdata = aes.decrypt(ecbAl, encECBdata, key);

            String encTripleDES = tripleDes.encryptedTripleDes(tripleAlg, message, io);
            String decTripleDES = tripleDes.decryptedTripldeDes(tripleAlg, encTripleDES, io);

            maxKeyLen = Cipher.getMaxAllowedKeyLength(ALGORITHM);
            System.out.println("MaxAllowedKeyLength=[" + maxKeyLen + "].\n");

            System.out.println("\t [Encrypted Data using AES]: \n" + encdata + "\n");
            System.out.println("\t [Decrypted Data using AES]: \n" + decdata + "\n");
            System.out.println("\t [Encrypted Data using ECB]: \n" + encECBdata + "\n");
            System.out.println("\t [Decrypted Data using ECB]: \n" + decECBdata + "\n");
            System.out.println("\t [Encrypted Data using TripleDES]: \n" + encTripleDES + "\n");
            System.out.println("\t [Decrypted Data using TripleDES]: \n" + decTripleDES + "\n");
        } catch (Exception e) {
            Logger.getLogger(AESExample.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
