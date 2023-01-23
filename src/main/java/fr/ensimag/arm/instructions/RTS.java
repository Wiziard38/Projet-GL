package fr.ensimag.arm.instructions;

import java.io.PrintStream;

import fr.ensimag.pseudocode.InstructionArm;

/**
 * RTS instruction pour arm
 * 
 * @author gl39
 */
public class RTS extends InstructionArm {

    @Override
    public void displayInstructions(PrintStream s) {
        s.println("ldr R15, [[R10], #-4]"); // PC <- C[V[LB]-1]
        s.println("sub R13, R10, #8"); // SP <- V[LB]-2
        s.println("ldr R10, [R10]"); // LB <- C[V[LB]]
    }

}
