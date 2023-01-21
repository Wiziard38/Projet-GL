package fr.ensimag.superInstructions;

import fr.ensimag.pseudocode.DAddr;
import fr.ensimag.pseudocode.Instruction;
import fr.ensimag.pseudocode.Register;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class SuperSTORE {

    public static Instruction main(Register op1, DAddr op2, boolean arm) {
        if (arm) {
            return new fr.ensimag.arm.instructions.STR(op1, op2);
        } else {
            return new fr.ensimag.ima.instructions.STORE(op1, op2);
        }
    }
}