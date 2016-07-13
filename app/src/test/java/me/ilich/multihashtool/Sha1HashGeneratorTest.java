package me.ilich.multihashtool;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * Created by disokolov on 08.07.16.
 */
public class Sha1HashGeneratorTest {

    HashGenerator hashGenerator;

    @Before
    public void setUp() {
        hashGenerator = new Sha1HashGenerator();
    }

    @Test
    public void empty() {
        try {
            hashGenerator.asPlain(null, null, null);
            assertTrue(false);
        } catch (NullPointerException e) {
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void emptyStorepass() {
        try {
            hashGenerator.asPlain("androiddebugkey", Sha1HashGenerator.class.getResource("/debug.keystore"), null);
            assertTrue(false);
        } catch (NullPointerException e) {
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void emptyAlias() {
        try {
            hashGenerator.asPlain(null, Sha1HashGeneratorTest.class.getResource("/debug.keystore"), "android");
            assertTrue(false);
        } catch (NullPointerException e) {
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void emptyJeystore() {
        try {
            hashGenerator.asPlain("androiddebugkey", null, "android");
            assertTrue(false);
        } catch (NullPointerException e) {
        } catch (Exception e) {
            assertTrue(false);
        }
    }


    @Test
    public void debugKeystore() {
        try {
            String s = hashGenerator.asBase64("androiddebugkey", Sha1HashGeneratorTest.class.getResource("/debug.keystore"), "android");
            assertEquals("dhB0h1VlObkPVGYXW5jRhxYALLg=", s);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void debugKeystoreBase64() {
        try {
            String s = hashGenerator.asPlain("androiddebugkey", Sha1HashGeneratorTest.class.getResource("/debug.keystore"), "android");
            assertEquals("76107487556539B90F5466175B98D18716002CB8", s);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

}
