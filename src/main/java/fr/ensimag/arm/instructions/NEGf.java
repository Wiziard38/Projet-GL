package fr.ensimag.arm.instructions;

import java.io.PrintStream;

import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.InstructionArm;

public class NEGf extends InstructionArm {

    private GPRegister op1;
    private GPRegister op2;

    public NEGf(GPRegister op1, GPRegister op2) {
        this.op1 = op1;
        this.op2 = op2;
    }

    @Override
    public void displayInstructions(PrintStream s) {
        s.println("vneg.f32 " + op2 + ", " + op1 + ", " + op2);
    }

}
