package backend.demo.jna.api;


import com.sun.jna.Library;
import com.sun.jna.Native;

public interface JNAApiInterface extends Library {
    JNAApiInterface INSTANCE = (JNAApiInterface) Native.load("dll/Test", JNAApiInterface.class);
    int sum(int x, int y);
}