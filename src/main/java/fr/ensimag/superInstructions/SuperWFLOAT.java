package fr.ensimag.superInstructions;

import fr.ensimag.pseudocode.Instruction;

/**
 * Class used to send the WFLOAT instruction depending on wether we compile in
 * ARM
 * or IMA.
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class SuperWFLOAT {

    public static Instruction main(boolean arm) {
        if (arm) {
            return new fr.ensimag.arm.instructions.WFLOAT();
        } else {
            return new fr.ensimag.ima.instructions.WFLOAT();
        }
    }
}