package caesar;

public class Main {
    private static int N = 61;
    private static int n = 24;
    private final static int c = 15;
    private static String surname = "FETSUN";
    private static String name = "ANNA";
    private static String secondName = "VOLODUMURIVNA";

    public static String encrypt(String msg, int key){
        String encMsg = "";
        for (int i = 0; i < msg.length(); i++) {
            if ((int) msg.charAt(i) == 32) {
                encMsg += (char) 32;
                // casting int to char - space
            } else if ((int) msg.charAt(i) + key > 90 && (int) msg.charAt(i) < 96) {
                int temp = ((int) msg.charAt(i) + key) - 90;
                encMsg += (char) (64 + temp);
            } else {
                encMsg += (char) ((int) msg.charAt(i) + key);
            }
        }
        return encMsg;
    }

    public static String decrypt(String msg, int key){
        String decrypMsg="";
        for (int i = 0; i < msg.length(); i++) {
            if ((int) msg.charAt(i) == 32) {
                decrypMsg += (char) 32;
            } else if ((msg.charAt(i) - key) < 65) {
                int temp = ((int) msg.charAt(i) - key) + 26;
                decrypMsg += (char) temp;
            } else {
                decrypMsg += (char) ((int) msg.charAt(i) - key);
            }

        }
        return decrypMsg;
    }
    public static void main(String[] args) {
        int key = (N + n + c) % 26;
        String msg = surname + " " + name + " " + secondName;
        String encMsg = encrypt(msg, key);
        String decMsg = decrypt(encMsg, key);
        System.out.println("Key: [ " + key + " ] \nEncrypted message: [ "
                + encMsg +" ]\nDecryption message: [ " + decMsg +" ]");
    }
}
