package fr.ensimag.pseudocode;

import java.io.PrintStream;

/**
 * Used to display nothing when IMA instruction can't be translated to ARM
 * instruction
 *
 * @author gl39
 * @date 01/01/2023
 */
public class InstructionVideArm extends InstructionArm {

    @Override
    public void displayInstructions(PrintStream s) {
        // nothing to print
    }

}
