package fr.ensimag.arm.instructions;

import fr.ensimag.pseudocode.Register;

import java.io.PrintStream;

import fr.ensimag.pseudocode.InstructionArm;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class PUSH extends InstructionArm {

    private Register op;

    public PUSH(Register op) {
        this.op = op;
    }

    @Override
    public void displayInstructions(PrintStream s) {
        s.print("PUSH [" + op + "]");
    }
}
