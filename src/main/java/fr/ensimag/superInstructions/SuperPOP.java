package fr.ensimag.superInstructions;

import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.Instruction;

/**
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class SuperPOP {

    public static Instruction main(GPRegister op, boolean arm) {
        if (arm) {
            op = op.convertToArmRegister();
            return new fr.ensimag.arm.instructions.POP(op);
        } else {
            return new fr.ensimag.ima.instructions.POP(op);
        }
    }
}
