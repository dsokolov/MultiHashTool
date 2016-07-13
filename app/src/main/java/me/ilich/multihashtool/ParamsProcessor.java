package me.ilich.multihashtool;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleks on 13.07.2016.
 */
public class ParamsProcessor {

    public OutputParams[] process(InputParams inputParams) {
        OutputParams vkOutputParams = new VkOutputParams(new Sha1HashGenerator().asPlain(inputParams.getAlias(),
                inputParams.getKeyStore(), inputParams.getStorePass()));
        OutputParams fbOutputParams = new FbOutputParams(new Sha1HashGenerator().asBase64(inputParams.getAlias(),
                inputParams.getKeyStore(), inputParams.getStorePass()));
        OutputParams[] outputParams = {vkOutputParams, fbOutputParams};
        return outputParams;
    }

    public void toHTML(OutputParams[] params) {
        List<String> answer = new ArrayList<>();
        answer.add("<!DOCTYPE html>");
        answer.add("<html>");
        answer.add("<head></head>");
        answer.add("<body>");
        answer.add("<p>For Vk: " + params[0].toString() + "</p>");
        answer.add("<p>For Fb: " + params[1].toString() + "</p>");
        answer.add("</body>");
        answer.add("</html>");
        try {
            PrintWriter fileHTML = new PrintWriter(new FileWriter("SHA1.html"));
            for (int i = 0; i < answer.size(); i++){
                fileHTML.println(answer.get(i));
            }
            fileHTML.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
