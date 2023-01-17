package fr.ensimag.superInstructions;

import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.Instruction;

/**
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class SuperSLE {

    public static Instruction main(GPRegister op, boolean arm) {
        if (arm) {
            return new fr.ensimag.arm.instructions.SLE(op);
        } else {
            return new fr.ensimag.ima.instructions.SLE(op);
        }
    }
}