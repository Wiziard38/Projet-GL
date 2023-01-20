package fr.ensimag.arm.instructions;

import java.io.PrintStream;

import fr.ensimag.pseudocode.ImmediateString;
import fr.ensimag.pseudocode.InstructionArm;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class WSTR extends InstructionArm {

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
        s.println(".data");
        s.println();
        s.println("msg_" + op.stripped() + ":");
        s.println(".asciz " + op);
        s.println("len" + op.stripped() + " = . - msg_" + op.stripped());
        s.print("\n");
        s.println(".text");
    }

}
