package fr.ensimag.superInstructions;

import fr.ensimag.pseudocode.ImmediateString;
import fr.ensimag.pseudocode.Instruction;

/**
 * Class used to send the WSTR instruction depending on wether we compile in ARM
 * or IMA.
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class SuperWSTR {

    public static Instruction main(ImmediateString operand, boolean arm) {
        if (arm) {
            return new fr.ensimag.arm.instructions.WSTR(operand);
        } else {
            return new fr.ensimag.ima.instructions.WSTR(operand);
        }
    }

    public static Instruction main(String operand, boolean arm) {
        if (arm) {
            return new fr.ensimag.arm.instructions.WSTR(operand);
        } else {
            return new fr.ensimag.ima.instructions.WSTR(operand);
        }
    }

}