package fr.ensimag.pseudocode;

import java.io.PrintStream;

public abstract class InstructionArm extends Instruction {

    @Override
    void displayOperands(PrintStream s) {
        // TODO Auto-generated method stub

    }

    abstract void displayInstructions(PrintStream s);

    @Override
    void display(PrintStream s) {
        this.displayInstructions(s);
    }

}
