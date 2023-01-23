package fr.ensimag.arm.instructions;

import java.io.PrintStream;

import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.InstructionArm;

/**
 * SHR instruction pour arm
 * 
 * @author gl39
 */
public class SHR extends InstructionArm {

    private GPRegister op;

    public SHR(GPRegister op1) {
        op = op1;
    }

    @Override
    public void displayInstructions(PrintStream s) {
        s.println("mv " + op + ", " + op + ", LSR #1");
    }
}
