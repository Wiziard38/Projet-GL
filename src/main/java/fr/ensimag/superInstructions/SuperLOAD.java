package fr.ensimag.superInstructions;

import javax.swing.plaf.synth.Region;

import fr.ensimag.pseudocode.DAddr;
import fr.ensimag.pseudocode.DVal;
import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.ImmediateFloat;
import fr.ensimag.pseudocode.ImmediateInteger;
import fr.ensimag.pseudocode.Instruction;

/**
 * Class used to send the LOAD instruction depending on wether we compile in ARM
 * or IMA.
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class SuperLOAD {

    public static Instruction main(DAddr op1, GPRegister op2, boolean arm) {
        if (arm) {
            op2 = op2.convertToArmRegister();
            return new fr.ensimag.arm.instructions.LDR(op1, op2);
        } else {
            return new fr.ensimag.ima.instructions.LOAD(op1, op2);
        }
    }

    public static Instruction main(DVal op1, GPRegister op2, boolean arm) {
        if (arm) {
            op2 = op2.convertToArmRegister();
            return new fr.ensimag.arm.instructions.mov(op1, op2);
        } else {
            return new fr.ensimag.ima.instructions.LOAD(op1, op2);
        }
    }

    public static Instruction main(int i, GPRegister r, boolean arm) {
        if (arm) {
            r = r.convertToArmRegister();
            return new fr.ensimag.arm.instructions.mov(i, r);
        } else {
            return new fr.ensimag.ima.instructions.LOAD(new ImmediateInteger(i), r);
        }
    }

    public static Instruction main(float f, GPRegister r, boolean arm) {
        if (arm) {
            r = r.convertToArmRegister();
            return new fr.ensimag.arm.instructions.vmov(f, r);
        } else {
            return new fr.ensimag.ima.instructions.LOAD(new ImmediateFloat(f), r);
        }
    }

}