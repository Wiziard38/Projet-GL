
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class test {
    
    public static void main(String[] args) {
        FileOutputStream destFile;
        try {
            destFile = new FileOutputStream("test.S");
            PrintStream printFile = new PrintStream(destFile);
            printFile.print(".text\n");
            printFile.print("\t.global _start\n");
            printFile.print("_start:\n");
            printFile.print("\tmov r0, #1\n");
            printFile.print("\tadr r1, msg\n");
            printFile.print("\tmov r2, #len\n");
            printFile.print("\tmov r7, #4\n");
            printFile.print("\tsvc #0\n");
            printFile.print("\n");
            printFile.print("\tmov r0, #0\n");
            printFile.print("\tmov r7, #1\n");
            printFile.print("\tsvc #0\n");
            printFile.print("msg:\n");
            printFile.print("\t.ascii \"Bonjour le monde\\n\"\n");
            printFile.print("\tlen = . - msg\n");
            printFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
    }
}
