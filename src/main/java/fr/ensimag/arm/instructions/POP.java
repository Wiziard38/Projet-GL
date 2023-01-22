package fr.ensimag.arm.instructions;

import java.io.PrintStream;

import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.InstructionArm;

/**
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class POP extends InstructionArm {

    private GPRegister op;

    public POP(GPRegister op) {
        this.op = op;
    }

    @Override
    public void displayInstructions(PrintStream s) {
        s.print("POP [" + op + "]");
    }

}
