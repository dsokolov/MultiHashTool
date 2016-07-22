package me.ilich.multihashtool;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleks on 15.07.2016.
 */
public class HTMLCreator {

    private List<String> answer;

    public void generateHTML(OutputParams[] params) {
        answer = new ArrayList<>();
        answer.add("<!DOCTYPE html>");
        answer.add("<html>");
        answer.add("<head></head>");
        answer.add("<body>");
        answer.add("<p><b>For Vk:" + generateForm(params[0]));
        answer.add("<p><b>For Fb:" + generateForm(params[1]));
        answer.add("</body>");
        answer.add("</html>");
    }

    private String generateForm(OutputParams param) {
        StringBuilder stringBuilder = new StringBuilder("</b><br><br>SHA1:<br>");
        stringBuilder.append(param.getSHA1());
        stringBuilder.append("<br><br>Package Name:<br>");
        stringBuilder.append(param.getPackageName());
        stringBuilder.append("<br><br>Class Name:<br>");
        stringBuilder.append(param.getClassName());
        stringBuilder.append("</p>");
        return stringBuilder.toString();
    }

    public void print(String fileName) {
        try {
            PrintWriter fileHTML = new PrintWriter(new FileWriter(fileName));
            for (int i = 0; i < answer.size(); i++) {
                fileHTML.println(answer.get(i));
            }
            fileHTML.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void print(File file) {
        try {
            PrintWriter fileHTML = new PrintWriter(new FileWriter(file));
            for (int i = 0; i < answer.size(); i++) {
                fileHTML.println(answer.get(i));
            }
            fileHTML.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
