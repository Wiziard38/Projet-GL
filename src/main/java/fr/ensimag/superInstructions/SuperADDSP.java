package fr.ensimag.superInstructions;

import fr.ensimag.pseudocode.ImmediateInteger;
import fr.ensimag.pseudocode.Instruction;

/**
 * Class used to send the ADDSP instruction depending on wether we compile in
 * ARM
 * or IMA.
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class SuperADDSP {

    public static Instruction main(ImmediateInteger operand, boolean arm) {
        if (arm) {
            return new fr.ensimag.arm.instructions.ADD(operand, fr.ensimag.pseudocode.Register.SP);
        } else {
            return new fr.ensimag.ima.instructions.ADDSP(operand);
        }
    }
}