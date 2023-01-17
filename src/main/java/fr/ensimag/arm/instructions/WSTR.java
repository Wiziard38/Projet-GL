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
    private Label label;

    public WSTR(ImmediateString op) {
        this.op = op;
        label = new Label(op.toString());
    }

    public WSTR(String message) {
        this(new ImmediateString(message));
    }

    @Override
    public void displayInstructions(PrintStream s) {
        s.print("\t" + "mov r0, #1");
        s.print("\t" + "adr r1, msg");
        s.print("\t" + "mov r2, #len");
        s.print("\t" + "mov r7, #4");
        s.print("\t" + "svc #0");
        s.print("\n");
        s.print("\t" + "mov r0, #0");
        s.print("\t" + "mov r7, #1");
        s.print("\t" + "svc #0");
        s.print("\n");
        s.print(label + ":");
        s.print("\t" + ".ascii " + op);
        s.print("\t" + "len = . - msg");
    }

}
