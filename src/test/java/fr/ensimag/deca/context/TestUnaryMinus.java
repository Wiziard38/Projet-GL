package fr.ensimag.deca.context;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tree.AbstractExpr;

import fr.ensimag.deca.tree.UnaryMinus;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

/**
 * Test for the UnaryMinus node using mockito, using @Mock and @Before
 * annotations.
 *
 * @author Mathis
 * @date 09/01/2023
 */
public class TestUnaryMinus {

    final Type INT = new IntType(null);
    final Type FLOAT = new FloatType(null);

    @Mock
    AbstractExpr intexpr1;
    @Mock
    AbstractExpr floatexpr1;

    DecacCompiler compiler;

    @BeforeEach
    public void setup() throws ContextualError {
        // MockitoAnnotations.initMocks(this);
        MockitoAnnotations.openMocks(this);
        compiler = new DecacCompiler(null, null, false);
        when(intexpr1.verifyExpr(compiler, null, null)).thenReturn(INT);
        when(floatexpr1.verifyExpr(compiler, null, null)).thenReturn(FLOAT);
    }

    @Test
    public void testInt() throws ContextualError {
        UnaryMinus t = new UnaryMinus(intexpr1);
        // check the result
        assertTrue(t.verifyExpr(compiler, null, null).isInt());
        // check that the mocks have been called properly.
        verify(intexpr1).verifyExpr(compiler, null, null);
    }

    @Test
    public void testFloat() throws ContextualError {
        UnaryMinus t = new UnaryMinus(floatexpr1);
        // check the result
        assertTrue(t.verifyExpr(compiler, null, null).isFloat());
        // check that the mocks have been called properly.
        verify(floatexpr1).verifyExpr(compiler, null, null);
    }
}