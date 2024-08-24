package com.example.myapplication;

public class DatabaseHelper {
    static {
        System.loadLibrary("native-lib");
    }

    public native long nativeOpenDatabase(String dbPath);
    public native void nativeCloseDatabase(long dbHandle);
    public native String nativeExecuteQuery(long dbHandle, String query);
    public native String nativeCreateMealPlannerDB(long dbHandle);

    private long dbHandle;

    public void openDatabase(String dbPath) {
        dbHandle = nativeOpenDatabase(dbPath);
    }

    public void closeDatabase() {
        nativeCloseDatabase(dbHandle);
    }

    public String createMealPlannerDB() {
        return nativeCreateMealPlannerDB(dbHandle);
    }
}
