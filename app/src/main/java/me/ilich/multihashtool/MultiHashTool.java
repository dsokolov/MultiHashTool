package me.ilich.multihashtool;

/**
 * Created by Алексей on 06.07.2016.
 */
public class MultiHashTool {

    public static void main(String[] args) throws Exception {

        ParamsProcessor paramsProcessor = new ParamsProcessor();
        HTMLCreator htmlCreator = new HTMLCreator();

        OutputParams[] outputParams = paramsProcessor.process(InputParams.readFromFile(args[0]));
        htmlCreator.generateHTML(outputParams);
        htmlCreator.print("SHA1.html");

    }

}