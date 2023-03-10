package fr.ensimag.arm.instructions;

import java.io.PrintStream;

import fr.ensimag.pseudocode.InstructionArm;

/**
 * Error instruction pour arm
 * 
 * @author gl39
 */
public class ERROR extends InstructionArm {

    @Override
    public void displayInstructions(PrintStream s) {
        s.println("mov r0, #1");
        s.println("mov r7, #1");
        s.println("svc #0");
    }

}
