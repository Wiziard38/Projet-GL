package fr.ensimag.arm.instructions;

import java.io.PrintStream;

import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.InstructionArm;

/**
 * SLE instruction pour arm
 * 
 * @author gl39
 */
public class SLE extends InstructionArm {

    private GPRegister op;

    public SLE(GPRegister op) {
        this.op = op;
    }

    @Override
    public void displayInstructions(PrintStream s) {
        s.println("movle " + op + ", #1");
        s.println("movgt " + op + ", #0");
    }

}