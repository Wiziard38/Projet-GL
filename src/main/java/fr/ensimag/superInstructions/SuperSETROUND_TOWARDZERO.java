package fr.ensimag.superInstructions;

import fr.ensimag.pseudocode.Instruction;

/**
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class SuperSETROUND_TOWARDZERO {

    public static Instruction main(boolean arm) {
        if (arm) {
            return new fr.ensimag.arm.instructions.SETROUND_TOWARDZERO();
        } else {
            return new fr.ensimag.ima.instructions.SETROUND_TOWARDZERO();
        }
    }
}