package fr.ensimag.superInstructions;

import fr.ensimag.pseudocode.Instruction;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class SuperWNL {

    public static Instruction main(boolean arm) {
        if (arm) {
            return new fr.ensimag.arm.instructions.WNL();
        } else {
            return new fr.ensimag.ima.instructions.WNL();
        }
    }
}