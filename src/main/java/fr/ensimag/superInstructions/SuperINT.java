package fr.ensimag.superInstructions;

import fr.ensimag.pseudocode.DVal;
import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.Instruction;

/**
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class SuperINT {

    public static Instruction main(DVal op1, GPRegister op2, boolean arm) {
        if (arm) {
            return new fr.ensimag.arm.instructions.INT(op1, op2);
        } else {
            return new fr.ensimag.ima.instructions.INT(op1, op2);
        }
    }
}