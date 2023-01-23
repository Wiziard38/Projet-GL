package fr.ensimag.superInstructions;

import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.Instruction;

/**
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class SuperSLT {

    public static Instruction main(GPRegister op, boolean arm) {
        if (arm) {
            op = op.convertToArmRegister();
            return new fr.ensimag.arm.instructions.SLT(op);
        } else {
            return new fr.ensimag.ima.instructions.SLT(op);
        }
    }
}
