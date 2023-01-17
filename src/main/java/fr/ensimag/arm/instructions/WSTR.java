package fr.ensimag.arm.instructions;

import java.io.PrintStream;

import fr.ensimag.pseudocode.ImmediateString;
import fr.ensimag.pseudocode.InstructionArm;
import fr.ensimag.pseudocode.Label;

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
        s.println("adr r1, msg_" + op.stripped());
        s.println("mov r2, #len");
        s.println("mov r7, #4");
        s.println("svc #0");
        s.print("\n");
        s.println("mov r0, #0");
        s.println("mov r7, #1");
        s.println("svc #0");
        s.print("\n");
        s.println("msg_" + op.stripped() + ":");
        s.println(".ascii " + op);
        s.println("len = . - msg_" + op.stripped());
    }

}
