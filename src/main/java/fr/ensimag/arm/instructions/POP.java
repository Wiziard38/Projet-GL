package fr.ensimag.arm.instructions;

import java.io.PrintStream;

import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.InstructionArm;

/**
 * OP instruction pour arm
 * 
 * @author gl39
 */
public class POP extends InstructionArm {

    private GPRegister op;

    public POP(GPRegister op) {
        this.op = op;
    }

    @Override
    public void displayInstructions(PrintStream s) {
        s.print("POP {" + op + "}");
    }

}
