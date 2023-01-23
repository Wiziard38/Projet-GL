package fr.ensimag.superInstructions;

import fr.ensimag.pseudocode.Instruction;
import fr.ensimag.pseudocode.Register;

/**
 * Class used to send the PUSH instruction depending on wether we compile in ARM
 * or IMA.
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class SuperPUSH {

    public static Instruction main(Register op1, boolean arm) {
        if (arm) {
            op1 = op1.convertToArmRegister();
            return new fr.ensimag.arm.instructions.PUSH(op1);
        } else {
            return new fr.ensimag.ima.instructions.PUSH(op1);
        }
    }
}
