package fr.ensimag.superInstructions;

import fr.ensimag.pseudocode.Instruction;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class SuperRINT {

    public static Instruction main(boolean arm) {
        if (arm) {
            return new fr.ensimag.arm.instructions.RINT();
        } else {
            return new fr.ensimag.ima.instructions.RINT();
        }
    }
}