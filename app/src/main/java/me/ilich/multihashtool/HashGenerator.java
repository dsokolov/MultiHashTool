package me.ilich.multihashtool;

import sun.misc.BASE64Encoder;

import java.net.URL;

/**
 * Created by disokolov on 11.07.16.
 */
public abstract class HashGenerator {

    public final String asPlain(String alias, URL keyStore, String storePass) {
        if (alias == null){
            throw new NullPointerException("alias");
        }
        if (keyStore == null){
            throw new NullPointerException("keyStore");
        }
        if (storePass == null){
            throw new NullPointerException("storePass");
        }
        return onGenerate(alias, keyStore, storePass).toUpperCase();
    }

    public final String asBase64(String alias, URL keyStore, String storePass) {
        if (alias == null){
            throw new NullPointerException("alias");
        }
        if (keyStore == null){
            throw new NullPointerException("keyStore");
        }
        if (storePass == null){
            throw new NullPointerException("storePass");
        }
        return getBase64FromHEX(onGenerate(alias, keyStore, storePass));
    }

    private static String getBase64FromHEX(String input) {
        byte barr[] = new byte[input.length()/2];
        int bcnt = 0;
        for (int i = 0; i < input.length(); i += 2) {
            char c1 = input.charAt(i);
            char c2 = input.charAt(i + 1);
            int i1 = getIntFromChar(c1);
            int i2 = getIntFromChar(c2);
            barr[bcnt] = 0;
            barr[bcnt] |= (byte) ((i1 & 0x0F) << 4);
            barr[bcnt] |= (byte) (i2 & 0x0F);
            bcnt++;
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(barr);
    }

    private static int getIntFromChar(char c) {
        char[] carr = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        char clower = Character.toLowerCase(c);
        for (int i = 0; i < carr.length; i++) {
            if (clower == carr[i]) {
                return i;
            }
        }
        return 0;
    }

    protected abstract String onGenerate(String alias, URL keyStore, String storePass);

}
