package fr.ensimag.arm.instructions;

import java.io.PrintStream;

import fr.ensimag.pseudocode.InstructionArm;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class RTS extends InstructionArm {

    @Override
    public void displayInstructions(PrintStream s) {
        s.println("ldr R15, [[R10], #-1]");
        s.println("sub R13, R10, #-2");
        s.println("ldr R10, [[R10]]");
    }

}

// ici C(V(R)) ???