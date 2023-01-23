package fr.ensimag.arm.instructions;

import java.io.PrintStream;

import fr.ensimag.pseudocode.DVal;
import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.InstructionArm;

/**
 * REM instruction pour arm, pas implémenté
 * 
 * @author gl39
 */
public class REM extends InstructionArm {

    private DVal op1;
    private GPRegister op2;

    public REM(DVal op1, GPRegister op2) {
        this.op1 = op1;
        this.op2 = op2;
    }

    @Override
    public void displayInstructions(PrintStream s) {
    }

}