package fr.ensimag.arm.instructions;

import java.io.PrintStream;

import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.InstructionArm;

/**
 * SEQ instruction pour arm
 * 
 * @author gl39
 */
public class SEQ extends InstructionArm {

    private GPRegister op;

    public SEQ(GPRegister op) {
        this.op = op;
    }

    @Override
    public void displayInstructions(PrintStream s) {
        s.println("moveq " + op + ", #1");
        s.println("movne " + op + ", #0");
    }

}