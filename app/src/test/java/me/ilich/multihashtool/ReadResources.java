package me.ilich.multihashtool;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * Created by disokolov on 11.07.16.
 */
public class ReadResources {

    @Test
    public void read() {
        URL url = Sha1HashGeneratorTest.class.getResource("/file1.txt");
        InputStream is = null;
        try {
            is = url.openStream();
        } catch (IOException e) {
            assertFalse(e.getMessage(), true);
        }
        assertNotNull(is);
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            for (String s = reader.readLine(); s != null; s = reader.readLine()) {
                sb.append(s);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals("123\n456\n", sb.toString());
        try {
            is.close();
        } catch (IOException e) {
            assertFalse(e.getMessage(), true);
        }
    }

}
