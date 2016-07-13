package me.ilich.multihashtool;

/**
 * Created by Aleks on 13.07.2016.
 */
public abstract class OutputParams {
    String SHA1;

    public String getSHA1() {
        return SHA1;
    }

    @Override
    public String toString() {
        return SHA1;
    }
}
