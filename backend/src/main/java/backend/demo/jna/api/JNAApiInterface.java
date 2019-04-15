package backend.demo.jna.api;


import com.sun.jna.Library;
import com.sun.jna.Native;

public interface JNAApiInterface extends Library {
    JNAApiInterface INSTANCE = (JNAApiInterface) Native.load("dll/MathLibrary", JNAApiInterface.class);
    int sum(int a, int b);
}