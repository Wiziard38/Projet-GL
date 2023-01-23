package fr.ensimag.arm.instructions;

import fr.ensimag.pseudocode.BinaryInstructionRegToDVal;
import fr.ensimag.pseudocode.DVal;
import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.ImmediateInteger;

/**
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class CMP extends BinaryInstructionRegToDVal {

    public CMP(DVal op1, GPRegister op2) {
        super(op2, op1);
    }

    public CMP(int val, GPRegister op2) {
        this(new ImmediateInteger(val), op2);
    }

}