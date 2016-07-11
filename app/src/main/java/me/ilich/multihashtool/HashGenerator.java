package me.ilich.multihashtool;

import java.net.URL;

/**
 * Created by disokolov on 11.07.16.
 */
public abstract class HashGenerator {

    public String asPlain(String alias, URL keyStore) {
        return onGenerate();
    }

    public String asBase64(String alias, URL keyStore) {
        return onGenerate();
    }

    protected abstract String onGenerate();

}
