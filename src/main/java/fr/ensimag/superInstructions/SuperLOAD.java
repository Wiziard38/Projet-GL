package fr.ensimag.superInstructions;

import fr.ensimag.pseudocode.DVal;
import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.ImmediateInteger;
import fr.ensimag.pseudocode.Instruction;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class SuperLOAD {

    public static Instruction main(DVal op1, GPRegister op2, boolean arm) {
        if (arm) {
            return new fr.ensimag.arm.instructions.LDR(op1, op2);
        } else {
            return new fr.ensimag.ima.instructions.LOAD(op1, op2);
        }
    }

    public static Instruction main(int i, GPRegister r, boolean arm) {
        if (arm) {
            return new fr.ensimag.arm.instructions.mov(i, r);
        } else {
            return new fr.ensimag.ima.instructions.LOAD(new ImmediateInteger(i), r);
        }
    }

}