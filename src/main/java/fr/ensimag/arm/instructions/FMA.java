package fr.ensimag.arm.instructions;

import java.io.PrintStream;

import fr.ensimag.pseudocode.DVal;
import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.InstructionArm;

/**
 * FMA instruction pour arm
 * 
 * @author gl39
 */
public class FMA extends InstructionArm {

    private DVal op1;
    private GPRegister op2;

    public FMA(DVal op1, GPRegister op2) {
        this.op1 = op1;
        this.op2 = op2;
    }

    @Override
    public void displayInstructions(PrintStream s) {
        s.println("mul " + op2 + ", " + op1 + ", " + op2);
        s.println("add " + op2 + ", " + fr.ensimag.pseudocode.Register.R1 + ", " + op2);
    }
}
