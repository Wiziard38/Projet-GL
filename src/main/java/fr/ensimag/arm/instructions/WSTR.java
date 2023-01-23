package fr.ensimag.arm.instructions;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.LinkedList;

import fr.ensimag.pseudocode.ImmediateString;
import fr.ensimag.pseudocode.InstructionArm;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class WSTR extends InstructionArm {

    private static boolean retourLigne = false;

    private HashSet<String> stringDico = new HashSet<String>();

    private ImmediateString op;

    public WSTR(ImmediateString op) {
        this.op = op;
    }

    public WSTR(String message) {
        this(new ImmediateString(message));
    }

    @Override
    public void displayInstructions(PrintStream s) {
        s.println("mov r0, #1");
        s.println("ldr r1, =msg_" + op.stripped());
        s.println("ldr r2, =len" + op.stripped());
        s.println("mov r7, #4");
        s.println("swi 0");
        s.println();
        if (op.stripped() == "retourLigne") {
            if (!retourLigne) {
                retourLigne = true;
            } else {
                return;
            }
        }

        System.out.println(1 + s.toString());
        // if (stringDico.contains(op.stripped())) {
        // return;
        // }

        s.println(".data");
        s.println();
        s.println("msg_" + op.stripped() + ":");
        s.println(".asciz " + op);
        s.println("len" + op.stripped() + " = . - msg_" + op.stripped());
        s.print("\n");
        s.println(".text");
        // s.flush(); // <-- peut etre essaye avec ca apres
        stringDico.add(op.stripped());
        System.out.println(2 + s.toString());

    }
}
