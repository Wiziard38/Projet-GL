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
        // s.println("mov R15, [[[R11], #-1]]");
        // s.println("sub R13, R11, #-2");
        // s.println("mov R11, [[R11]]");
    }

}

// ici C(V(R)) ???