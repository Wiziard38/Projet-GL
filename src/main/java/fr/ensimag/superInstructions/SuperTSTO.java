package fr.ensimag.superInstructions;

import fr.ensimag.pseudocode.ImmediateInteger;
import fr.ensimag.pseudocode.Instruction;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class SuperTSTO {

    public static Instruction main(ImmediateInteger operand, boolean arm) {
        if (arm) {
            return new fr.ensimag.arm.instructions.TSTO(operand);
        } else {
            return new fr.ensimag.ima.instructions.TSTO(operand);
        }
    }

    public static Instruction main(int operand, boolean arm) {
        if (arm) {
            return new fr.ensimag.arm.instructions.TSTO(operand);
        } else {
            return new fr.ensimag.ima.instructions.TSTO(operand);
        }
    }
}
