package fr.ensimag.superInstructions;

import fr.ensimag.pseudocode.DAddr;
import fr.ensimag.pseudocode.Instruction;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class SuperPEA {

    public static Instruction main(DAddr operand, boolean arm) {
        if (arm) {
            return new fr.ensimag.arm.instructions.PEA(operand);
        } else {
            return new fr.ensimag.ima.instructions.PEA(operand);
        }
    }
}
