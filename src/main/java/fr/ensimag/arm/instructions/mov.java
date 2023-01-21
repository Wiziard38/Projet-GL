package fr.ensimag.arm.instructions;

import java.io.PrintStream;

import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.InstructionArm;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class mov extends InstructionArm {

    private int val;
    private GPRegister reg;

    public mov(int i, GPRegister r) {
        val = i;
        reg = r;
    }

    @Override
    public void displayInstructions(PrintStream s) {
        s.print("mov " + reg + ", #" + val);
    }

}
