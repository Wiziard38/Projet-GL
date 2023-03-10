package fr.ensimag.deca.context;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tree.AbstractExpr;
import fr.ensimag.deca.tree.ConvFloat;

import fr.ensimag.deca.tree.NotEquals;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

/**
 * Test for the NotEquals node using mockito, using @Mock and @Before
 * annotations.
 *
 * @author Mathis
 * @date 09/01/2023
 */
public class TestNotEquals {

    final Type INT = new IntType(null);
    final Type FLOAT = new FloatType(null);
    final Type BOOLEAN = new BooleanType(null);

    @Mock
    AbstractExpr intexpr1;
    @Mock
    AbstractExpr intexpr2;
    @Mock
    AbstractExpr floatexpr1;
    @Mock
    AbstractExpr floatexpr2;
    @Mock
    AbstractExpr booleanexpr1;
    @Mock
    AbstractExpr booleanexpr2;

    DecacCompiler compiler;

    @BeforeEach
    public void setup() throws ContextualError {
        // MockitoAnnotations.initMocks(this);
        MockitoAnnotations.openMocks(this);
        compiler = new DecacCompiler(null, null, false);
        when(intexpr1.verifyExpr(compiler, null, null)).thenReturn(INT);
        when(intexpr2.verifyExpr(compiler, null, null)).thenReturn(INT);
        when(floatexpr1.verifyExpr(compiler, null, null)).thenReturn(FLOAT);
        when(floatexpr2.verifyExpr(compiler, null, null)).thenReturn(FLOAT);
        when(booleanexpr1.verifyExpr(compiler, null, null)).thenReturn(BOOLEAN);
        when(booleanexpr2.verifyExpr(compiler, null, null)).thenReturn(BOOLEAN);
    }

    @Test
    public void testIntInt() throws ContextualError {
        NotEquals t = new NotEquals(intexpr1, intexpr2);
        // check the result
        assertTrue(t.verifyExpr(compiler, null, null).isBoolean());
        // check that the mocks have been called properly.
        verify(intexpr1, atLeastOnce()).verifyExpr(compiler, null, null);
        verify(intexpr2, atLeastOnce()).verifyExpr(compiler, null, null);
    }

    @Test
    public void testIntFloat() throws ContextualError {
        NotEquals t = new NotEquals(intexpr1, floatexpr1);
        // check the result
        assertTrue(t.verifyExpr(compiler, null, null).isBoolean());
        // ConvFloat should have been inserted on the right side
        assertTrue(t.getLeftOperand() instanceof ConvFloat);
        assertFalse(t.getRightOperand() instanceof ConvFloat);
        // check that the mocks have been called properly.
        verify(intexpr1, atLeastOnce()).verifyExpr(compiler, null, null);
        verify(floatexpr1, atLeastOnce()).verifyExpr(compiler, null, null);
    }

    @Test
    public void testFloatInt() throws ContextualError {
        NotEquals t = new NotEquals(floatexpr1, intexpr1);
        // check the result
        assertTrue(t.verifyExpr(compiler, null, null).isBoolean());
        // ConvFloat should have been inserted on the right side
        assertTrue(t.getRightOperand() instanceof ConvFloat);
        assertFalse(t.getLeftOperand() instanceof ConvFloat);
        // check that the mocks have been called properly.
        verify(intexpr1, atLeastOnce()).verifyExpr(compiler, null, null);
        verify(floatexpr1, atLeastOnce()).verifyExpr(compiler, null, null);
    }

    @Test
    public void testFloatFloat() throws ContextualError {
        NotEquals t = new NotEquals(floatexpr1, floatexpr2);
        // check the result
        assertTrue(t.verifyExpr(compiler, null, null).isBoolean());
        // check that the mocks have been called properly.
        verify(floatexpr1, atLeastOnce()).verifyExpr(compiler, null, null);
        verify(floatexpr2, atLeastOnce()).verifyExpr(compiler, null, null);
    }

    @Test
    public void testBoolBool() throws ContextualError {
        NotEquals t = new NotEquals(booleanexpr1, booleanexpr2);
        // check the result
        assertTrue(t.verifyExpr(compiler, null, null).isBoolean());
        // check that the mocks have been called properly.
        verify(booleanexpr1, atLeastOnce()).verifyExpr(compiler, null, null);
        verify(booleanexpr2, atLeastOnce()).verifyExpr(compiler, null, null);
    }
}