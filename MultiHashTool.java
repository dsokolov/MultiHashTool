
import sun.misc.BASE64Encoder;
import java.io.*;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Алексей on 06.07.2016.
 */
public class MultiHashTool {

    public static void main(String[] args) throws Exception{
        String debugKey = args[0];
        String keyStore = args[1];
        hashGeneration(debugKey, keyStore);
    }

    public static void hashGeneration(String debugKey, String keyStore) throws Exception{

        String[] s = getHash(debugKey, keyStore);
        String crt = new String("crt.crt");
        try {
            sun.security.tools.keytool.Main.main(s);
        } catch (Exception ex) {
            //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        String ShaFromFile = getShaFromFile(crt);
        System.out.println("For VK: " + ShaFromFile);

        String Base64FromHex = getBase64FromHEX(ShaFromFile);
        System.out.println("For FB: " + Base64FromHex);
    }

    private static String getBase64FromHEX(String input) {
        byte barr[] = new byte[input.length()/2];
        int bcnt = 0;
        for (int i = 0; i < input.length(); i += 2) {
            char c1 = input.charAt(i);
            char c2 = input.charAt(i + 1);
            int i1 = intFromChar(c1);
            int i2 = intFromChar(c2);
            barr[bcnt] = 0;
            barr[bcnt] |= (byte) ((i1 & 0x0F) << 4);
            barr[bcnt] |= (byte) (i2 & 0x0F);
            bcnt++;
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(barr);
    }
    private static int intFromChar(char c) {
        char[] carr = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        char clower = Character.toLowerCase(c);
        for (int i = 0; i < carr.length; i++) {
            if (clower == carr[i]) {
                return i;
            }
        }
        return 0;
    }

    private static String getShaFromString(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA-1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
    private static String getShaFromFile(String f) throws NoSuchAlgorithmException, IOException {
        int BUFFER_SIZE = 64 * 1024;
        BufferedInputStream file = new BufferedInputStream(new FileInputStream(f));
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        DigestInputStream in = new DigestInputStream(file, md);
        int j;
        byte[] buffer = new byte[BUFFER_SIZE];
        do {
            j = in.read(buffer, 0, BUFFER_SIZE);
        } while (j == BUFFER_SIZE);
        md = in.getMessageDigest();
        in.close();
        byte[] result = md.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    private static String[] getHash(String debugKey, String keyStore){
        String[] s = new String[7];
        s[0] = "-exportcert";
        s[1] = "-alias";
        s[2] = debugKey;
        s[3] = "-keystore";
        s[4] = keyStore;
        s[5] = "-file";
        s[6] = "crt.crt";

        return s;
    }
}
