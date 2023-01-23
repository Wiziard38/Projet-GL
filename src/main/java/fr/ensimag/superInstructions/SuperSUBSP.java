package fr.ensimag.superInstructions;

import fr.ensimag.pseudocode.ImmediateInteger;
import fr.ensimag.pseudocode.Instruction;

/**
 * Class used to send the SUBSP instruction depending on wether we compile in
 * ARM
 * or IMA.
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class SuperSUBSP {

    public static Instruction main(ImmediateInteger operand, boolean arm) {
        if (arm) {
            return new fr.ensimag.arm.instructions.SUB(operand, fr.ensimag.pseudocode.Register.SP);
        } else {
            return new fr.ensimag.ima.instructions.SUBSP(operand);
        }
    }

    public static Instruction main(int i, boolean arm) {
        if (arm) {
            return new fr.ensimag.arm.instructions.SUBSP(i);
        } else {
            return new fr.ensimag.ima.instructions.SUBSP(i);
        }
    }
}