package fr.ensimag.arm.instructions;

import java.io.PrintStream;

import fr.ensimag.pseudocode.DVal;
import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.InstructionArm;

/**
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class INT extends InstructionArm {

    private DVal op1;
    private GPRegister op2;

    public INT(DVal op1, GPRegister op2) {
        this.op1 = op1;
        this.op2 = op2;
    }

    @Override
    public void displayInstructions(PrintStream s) {
        // les jsp sont le registre pour le floatant
        // s.println("vcvt..f32 s32 " + jsp + ", " + jsp);
        // s.println("vmov " + op2 + ", " + jsp);
    }

}
