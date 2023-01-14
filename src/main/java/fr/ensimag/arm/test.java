package fr.ensimag.arm;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class test {
    
    public static void main(String[] args) throws FileNotFoundException {
        FileOutputStream destFile = new FileOutputStream("test");
        PrintStream printFile = new PrintStream(destFile);
        printFile.print("oui");
    }
}
