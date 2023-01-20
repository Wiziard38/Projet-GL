package fr.ensimag.deca.context;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tree.AbstractExpr;

import fr.ensimag.deca.tree.And;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

/**
 * Test for the And node using mockito, using @Mock and @Before annotations.
 *
 * @author Mathis
 * @date 09/01/2023
 */
public class TestAnd {

    final Type BOOLEAN = new BooleanType(null);

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
        when(booleanexpr1.verifyExpr(compiler, null, null)).thenReturn(BOOLEAN);
        when(booleanexpr2.verifyExpr(compiler, null, null)).thenReturn(BOOLEAN);
    }

    @Test
    public void testBool() throws ContextualError {
        And t = new And(booleanexpr1, booleanexpr2);
        // check the result
        assertTrue(t.verifyExpr(compiler, null, null).isBoolean());
        // check that the mocks have been called properly.
        verify(booleanexpr1).verifyExpr(compiler, null, null);
        verify(booleanexpr2).verifyExpr(compiler, null, null);
    }
}