package me.ilich.multihashtool;

/**
 * Created by Aleks on 13.07.2016.
 */
public abstract class OutputParams {
    private final String SHA1;
    private final String packageName;
    private final String className;

    OutputParams(String SHA1, String packageName, String className) {
        this.SHA1 = SHA1;
        this.packageName = packageName;
        this.className = className;
    }

    public String getSHA1() {
        return SHA1;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(getSHA1());
        stringBuilder.append(" " + getPackageName());
        stringBuilder.append(" " + getClassName());
        return stringBuilder.toString();
    }
}
