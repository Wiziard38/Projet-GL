package fr.ensimag.superInstructions;

import fr.ensimag.pseudocode.Instruction;
import fr.ensimag.pseudocode.Label;

/**
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class SuperBLE {

    public static Instruction main(Label op, boolean arm) {
        if (arm) {
            return new fr.ensimag.arm.instructions.BLE(op);
        } else {
            return new fr.ensimag.ima.instructions.BLE(op);
        }
    }
}
