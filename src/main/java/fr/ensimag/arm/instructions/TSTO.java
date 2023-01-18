package fr.ensimag.arm.instructions;

import java.io.PrintStream;

import fr.ensimag.pseudocode.ImmediateInteger;
import fr.ensimag.pseudocode.InstructionArm;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class TSTO extends InstructionArm {
    private ImmediateInteger val;

    public TSTO(ImmediateInteger i) {
        val = i;
    }

    public TSTO(int i) {
        this(new ImmediateInteger(i));
    }

    @Override
    public void displayInstructions(PrintStream s) {
        // idk
    }
}
