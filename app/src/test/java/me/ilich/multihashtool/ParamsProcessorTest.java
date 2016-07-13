package me.ilich.multihashtool;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Aleks on 13.07.2016.
 */
public class ParamsProcessorTest {

    ParamsProcessor paramsProcessor;

    @Before
    public void setUp() {
        paramsProcessor = new ParamsProcessor();
    }


    @Test
    public void Sha1forVk() {
        try {
            OutputParams[] outputParams = paramsProcessor.process(new InputParams("androiddebugkey", Sha1HashGeneratorTest.class.getResource("/debug.keystore"), "android"));
            assertEquals("76107487556539B90F5466175B98D18716002CB8", outputParams[0].toString());
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    public void Sha1forFb() {
        try {
            OutputParams[] outputParams = paramsProcessor.process(new InputParams("androiddebugkey", Sha1HashGeneratorTest.class.getResource("/debug.keystore"), "android"));
            assertEquals("dhB0h1VlObkPVGYXW5jRhxYALLg=", outputParams[1].toString());
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    public void createHTML() {
        try {
            OutputParams[] outputParams = paramsProcessor.process(new InputParams("androiddebugkey", Sha1HashGeneratorTest.class.getResource("/debug.keystore"), "android"));
            paramsProcessor.toHTML(outputParams);
            assertEquals("76107487556539B90F5466175B98D18716002CB8", outputParams[0].toString());
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }
}
