package me.ilich.multihashtool;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Aleks on 13.07.2016.
 */
public class InputParams {
    private String alias;
    private URL keyStore;
    private String storePass;
    private String packageName;
    private String className;

    InputParams(String alias, URL keyStore, String storePass, String packageName, String className) {
        this.alias = alias;
        this.keyStore = keyStore;
        this.storePass = storePass;
        this.packageName = packageName;
        this.className = className;
    }

    public String getAlias() {
        return this.alias;
    }

    public URL getKeyStore() {
        return this.keyStore;
    }

    public String getStorePass() {
        return this.storePass;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public String getClassName() {
        return this.className;
    }

    public static InputParams create(JsonObject jsonObject) {
        InputParams inputParams = null;
        JsonElement packageName = jsonObject.get("packageName");
        JsonElement keyStore = jsonObject.get("keyStore");
        JsonElement className = jsonObject.get("className");
        JsonElement storePass = jsonObject.get("storePass");
        JsonElement alias = jsonObject.get("alias");
        try {
            inputParams = new InputParams(alias.getAsString(), new URL("file:///" + keyStore.getAsString()), storePass.getAsString(), packageName.getAsString(), className.getAsString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return inputParams;
    }

    public static InputParams readFromFile(String filename) {

        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader("appconfig.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder json = new StringBuilder();
        String str;
        try {
            while ((str = in.readLine()) != null) {
                json.append(str);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonParser parser = new JsonParser();
        JsonElement jsonTree = parser.parse(json.toString());
        JsonObject jsonObject = null;
        if (jsonTree.isJsonObject()) {
            jsonObject = jsonTree.getAsJsonObject();
        }

        return create(jsonObject);
    }
}
