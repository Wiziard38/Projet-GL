package fr.ensimag.arm.instructions;

import java.io.PrintStream;

import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.InstructionArm;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class SHL extends InstructionArm {

    private GPRegister op;

    public SHL(GPRegister op1) {
        op = op1;
    }

    @Override
    public void displayInstructions(PrintStream s) {
        s.println("mv " + op + ", " + op + ", LSL #1");
    }
}
