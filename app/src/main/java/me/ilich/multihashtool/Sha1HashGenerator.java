package me.ilich.multihashtool;

import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by disokolov on 11.07.16.
 */
public class Sha1HashGenerator extends HashGenerator {

    @Override
    protected String onGenerate(String alias, URL keyStore, String storePass) {

        Path tmpDir = null;
        Path tmpDebugKeyStore = null;
        Path tmpCert = null;

        InputStream inStream = null;
        try {
            inStream = keyStore.openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //String myPath = keyStore.getPath().substring(1, keyStore.getPath().length());
        //Path srcDir = FileSystems.getDefault().getPath(myPath);

        try {
            tmpDir = Files.createTempDirectory("tmp");
            tmpDebugKeyStore = Files.createTempFile(tmpDir, "tmp_file", ".keystore");
            Files.copy(inStream, tmpDebugKeyStore, StandardCopyOption.REPLACE_EXISTING);
            tmpCert = Files.createTempFile(tmpDir, "crt", ".crt");
            } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder s = new StringBuilder();
        s.append("-exportcert -alias ");
        s.append(alias);
        s.append(" -keystore ");
        s.append(tmpDebugKeyStore.toAbsolutePath());
        s.append(" -file ");
        s.append(tmpCert.toAbsolutePath());
        s.append(" -storepass ");
        s.append(storePass);

        try {
            sun.security.tools.keytool.Main.main(s.toString().split(" "));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        String shaFromFile = null;

        try {
            shaFromFile = getShaFromFile(tmpCert.toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Files.deleteIfExists(tmpCert);
            Files.deleteIfExists(tmpDebugKeyStore);
            Files.deleteIfExists(tmpDir);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return shaFromFile;
    }

    @Override
    public String asPlain(String alias, URL keyStore, String storePass) {
        if (alias == null){
            throw new NullPointerException("alias");
        }
        if (keyStore == null){
            throw new NullPointerException("keyStore");
        }
        if (storePass == null){
            throw new NullPointerException("storePass");
        }
        return onGenerate(alias, keyStore, storePass).toUpperCase();
    }
    @Override
    public String asBase64(String alias, URL keyStore, String storePass) {
        if (alias == null){
            throw new NullPointerException("alias");
        }
        if (keyStore == null){
            throw new NullPointerException("keyStore");
        }
        if (storePass == null){
            throw new NullPointerException("storePass");
        }
        String shaFromFile = onGenerate(alias, keyStore, storePass);
        return getBase64FromHEX(shaFromFile);
    }


    private static String getBase64FromHEX(String input) {
        byte barr[] = new byte[input.length()/2];
        int bcnt = 0;
        for (int i = 0; i < input.length(); i += 2) {
            char c1 = input.charAt(i);
            char c2 = input.charAt(i + 1);
            int i1 = getIntFromChar(c1);
            int i2 = getIntFromChar(c2);
            barr[bcnt] = 0;
            barr[bcnt] |= (byte) ((i1 & 0x0F) << 4);
            barr[bcnt] |= (byte) (i2 & 0x0F);
            bcnt++;
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(barr);
    }

    private static int getIntFromChar(char c) {
        char[] carr = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        char clower = Character.toLowerCase(c);
        for (int i = 0; i < carr.length; i++) {
            if (clower == carr[i]) {
                return i;
            }
        }
        return 0;
    }

    private static String getShaFromFile(String f) throws NoSuchAlgorithmException, IOException {
        final int BUFFER_SIZE = 64 * 1024;
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
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

}
