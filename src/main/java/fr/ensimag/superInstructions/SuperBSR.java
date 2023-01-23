package fr.ensimag.superInstructions;

import fr.ensimag.pseudocode.DVal;
import fr.ensimag.pseudocode.Instruction;
import fr.ensimag.pseudocode.Label;

/**
 * Class used to send the BSr instruction depending on wether we compile in ARM
 * or IMA.
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class SuperBSR {

    public static Instruction main(Label op, boolean arm) {
        if (arm) {
            return new fr.ensimag.arm.instructions.BSR(op);
        } else {
            return new fr.ensimag.ima.instructions.BSR(op);
        }
    }

    public static Instruction main(DVal operand, boolean arm) {
        if (arm) {
            return new fr.ensimag.arm.instructions.BSR(operand);
        } else {
            return new fr.ensimag.ima.instructions.BSR(operand);
        }
    }
}
