package fr.ensimag.arm.instructions;

import java.io.PrintStream;

import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.InstructionArm;

/**
 * LOAD instruction pour floatant immediat arm
 * 
 * @author gl39
 */
public class vmov extends InstructionArm {

    private float val;
    private GPRegister reg;

    public vmov(float f, GPRegister r) {
        val = f;
        reg = r;
    }

    @Override
    public void displayInstructions(PrintStream s) {
        s.println("vmov.F32 " + reg + ", " + val);
    }

}
