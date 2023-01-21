package fr.ensimag.arm.instructions;

import java.io.PrintStream;

import fr.ensimag.pseudocode.InstructionArm;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class RINT extends InstructionArm {

    @Override
    public void displayInstructions(PrintStream s) {
        s.println("mov r7, #3");
        s.println("mov r0, #0");

    }

}
