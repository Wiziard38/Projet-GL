package fr.ensimag.superInstructions;

import fr.ensimag.pseudocode.Instruction;

/**
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class SuperSETROUND_DOWNWARD {

    public static Instruction main(boolean arm) {
        if (arm) {
            return new fr.ensimag.arm.instructions.SETROUND_DOWNWARD();
        } else {
            return new fr.ensimag.ima.instructions.SETROUND_DOWNWARD();
        }
    }
}