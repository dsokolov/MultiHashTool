package me.ilich.multihashtool;

import java.net.URL;

/**
 * Created by Aleks on 13.07.2016.
 */
public class InputParams {
    private String alias;
    private URL keyStore;
    private String storePass;

    InputParams(String alias, URL keyStore, String storePass) {
        this.alias = alias;
        this.keyStore = keyStore;
        this.storePass = storePass;
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
}
