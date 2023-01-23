package fr.ensimag.arm.instructions;

import java.io.PrintStream;

import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.InstructionArm;

/**
 * SGT instruction pour arm
 * 
 * @author gl39
 */
public class SGT extends InstructionArm {

    private GPRegister op;

    public SGT(GPRegister op) {
        this.op = op;
    }

    @Override
    public void displayInstructions(PrintStream s) {
        s.println("movgt " + op + ", #1");
        s.println("movle " + op + ", #0");
    }

}