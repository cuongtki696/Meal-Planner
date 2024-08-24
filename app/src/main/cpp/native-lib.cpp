#include <jni.h>
#include <string>
#include <ctime>

// Function to get current date
extern "C" JNIEXPORT jstring JNICALL
Java_com_example_myapplication_MainActivity_getCurrentDate(JNIEnv *env, jobject thiz) {
    // Get the current time
    std::time_t t = std::time(nullptr);
    char date[100];
    std::strftime(date, sizeof(date), "%Y-%m-%d", std::localtime(&t));

    // Return the date as a jstring
    return env->NewStringUTF(date);
}
