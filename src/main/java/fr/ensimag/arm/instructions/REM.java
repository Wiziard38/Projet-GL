package fr.ensimag.arm.instructions;

import java.io.PrintStream;

import fr.ensimag.pseudocode.DVal;
import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.InstructionArm;

/**
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class REM extends InstructionArm {

    private DVal op1;
    private GPRegister op2;

    public REM(DVal op1, GPRegister op2) {
        this.op1 = op1;
        this.op2 = op2;
    }

    @Override
    public void displayInstructions(PrintStream s) {
        // s.println("sdiv " + registreTMP + ", " + op1 + ", " + op2);
        // s.println("mul " + registreTMP + ", " + op1 + ", " + registreTMP);
        // s.println("sub " + op2 + ", " + op2 + ", " + registreTMP);
    }

}

// RM / Dval -> R?
// R? * DVAl -> R?
// RM - R? -> Rm