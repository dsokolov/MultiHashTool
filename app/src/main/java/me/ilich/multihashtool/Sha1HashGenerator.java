package me.ilich.multihashtool;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
