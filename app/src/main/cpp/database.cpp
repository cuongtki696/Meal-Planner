#include <jni.h>
#include <sqlite3.h>
#include <string.h>
#include <android/log.h>

#define LOG_TAG "SQLiteExample"
#define LOGI(...)  __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

extern "C" JNIEXPORT jlong JNICALL
Java_com_example_myapp_DatabaseHelper_nativeOpenDatabase(JNIEnv *env, jobject obj, jstring dbPath) {
    sqlite3 *db;
    const char *path = (*env)->GetStringUTFChars(env, dbPath, 0);

    if (sqlite3_open(path, &db) != SQLITE_OK) {
        LOGI("Cannot open database: %s", sqlite3_errmsg(db));
        return 0;
    }

    LOGI("Database opened successfully");
    return (jlong) db;
}

extern "C" JNIEXPORT void JNICALL
Java_com_example_myapp_DatabaseHelper_nativeCloseDatabase(JNIEnv *env, jobject obj, jlong dbHandle) {
    sqlite3 *db = (sqlite3 *) dbHandle;
    sqlite3_close(db);
    LOGI("Database closed");
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_myapp_DatabaseHelper_nativeExecuteQuery(JNIEnv *env, jobject obj, jlong dbHandle, jstring query) {
    sqlite3 *db = (sqlite3 *) dbHandle;
    const char *sql = (*env)->GetStringUTFChars(env, query, 0);
    char *errMsg = 0;

    if (sqlite3_exec(db, sql, 0, 0, &errMsg) != SQLITE_OK) {
        LOGI("SQL error: %s", errMsg);
        sqlite3_free(errMsg);
        return (*env)->NewStringUTF(env, "SQL error");
    }

    LOGI("Query executed successfully");
    return (*env)->NewStringUTF(env, "Query executed successfully");
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_myapp_DatabaseHelper_nativeCreateMealPlannerDB(JNIEnv *env, jobject obj, jlong dbHandle) {
    sqlite3 *db = (sqlite3 *) dbHandle;
    const char *sql = "CREATE TABLE IF NOT EXISTS recipes ("
                      "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                      "name TEXT NOT NULL, "
                      "category TEXT NOT NULL, "
                      "ingredients TEXT, "
                      "instructions TEXT);";

    char *errMsg = 0;
    if (sqlite3_exec(db, sql, 0, 0, &errMsg) != SQLITE_OK) {
        LOGI("SQL error: %s", errMsg);
        sqlite3_free(errMsg);
        return (*env)->NewStringUTF(env, "Failed to create table");
    }

    LOGI("Table created successfully");
    return (*env)->NewStringUTF(env, "Table created successfully");
}
