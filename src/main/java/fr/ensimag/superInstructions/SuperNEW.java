package fr.ensimag.superInstructions;

import fr.ensimag.pseudocode.DVal;
import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.Instruction;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class SuperNEW {

    public static Instruction main(DVal size, GPRegister destination, boolean arm) {
        if (arm) {
            destination = destination.convertToArmRegister();
            return new fr.ensimag.arm.instructions.NEW(size, destination);
        } else {
            return new fr.ensimag.ima.instructions.NEW(size, destination);
        }
    }

    public static Instruction main(int size, GPRegister op2, boolean arm) {
        if (arm) {
            op2 = op2.convertToArmRegister();
            return new fr.ensimag.arm.instructions.NEW(size, op2);
        } else {
            return new fr.ensimag.ima.instructions.NEW(size, op2);
        }
    }
}
