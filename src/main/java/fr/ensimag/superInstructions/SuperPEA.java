package fr.ensimag.superInstructions;

import fr.ensimag.pseudocode.DAddr;
import fr.ensimag.pseudocode.Instruction;

/**
 * Class used to send the PEA instruction depending on wether we compile in ARM
 * or IMA.
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class SuperPEA {

    public static Instruction main(DAddr operand, boolean arm) {
        if (arm) {
            return new fr.ensimag.arm.instructions.PEA(operand);
        } else {
            return new fr.ensimag.ima.instructions.PEA(operand);
        }
    }
}
