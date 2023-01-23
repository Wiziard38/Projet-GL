package fr.ensimag.arm.instructions;

import java.io.PrintStream;

import fr.ensimag.pseudocode.DVal;
import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.InstructionArm;

/**
 * LOAD instruction pour arm, avec immediat ou registre
 * 
 * @author gl39
 */
public class mov extends InstructionArm {

    private int val;
    private GPRegister reg;
    private DVal op = null;

    public mov(int i, GPRegister r) {
        val = i;
        reg = r;
    }

    public mov(DVal op1, GPRegister op2) {
        this.op = op1;
        reg = op2;
    }

    @Override
    public void displayInstructions(PrintStream s) {
        if (op == null) {
            s.print("mov " + reg + ", #" + val);
        } else {
            s.print("mov " + reg + ", " + op);
        }
    }

}
