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

    public void generateHTML(OutputParams[] params){
        answer = new ArrayList<>();
        answer.add("<!DOCTYPE html>");
        answer.add("<html>");
        answer.add("<head></head>");
        answer.add("<body>");
        answer.add("<p>For Vk: " + params[0].toString() + "</p>");
        answer.add("<p>For Fb: " + params[1].toString() + "</p>");
        answer.add("</body>");
        answer.add("</html>");
    }

    public void print(String fileName) {
        try {
            PrintWriter fileHTML = new PrintWriter(new FileWriter(fileName));
            for (int i = 0; i < answer.size(); i++){
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
            for (int i = 0; i < answer.size(); i++){
                fileHTML.println(answer.get(i));
            }
            fileHTML.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
