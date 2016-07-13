package me.ilich.multihashtool;

import java.net.URL;

/**
 * Created by disokolov on 11.07.16.
 */
public abstract class HashGenerator {

    public String asPlain(String alias, URL keyStore, String storePass) {
        if (alias == null){
            throw new NullPointerException("alias");
        }
        if (keyStore == null){
            throw new NullPointerException("keyStore");
        }
        if (storePass == null){
            throw new NullPointerException("storePass");
        }
        return onGenerate(alias, keyStore, storePass);
    }

    public String asBase64(String alias, URL keyStore, String storePass) {
        if (alias == null){
            throw new NullPointerException("alias");
        }
        if (keyStore == null){
            throw new NullPointerException("keyStore");
        }
        if (storePass == null){
            throw new NullPointerException("storePass");
        }
        return onGenerate(alias, keyStore, storePass);
    }

    protected abstract String onGenerate(String alias, URL keyStore, String storePass);

}
