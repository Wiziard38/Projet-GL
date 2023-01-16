package fr.ensimag.deca.context;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tree.AbstractExpr;

import fr.ensimag.deca.tree.Not;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

/**
 * Test for the Not node using mockito, using @Mock and @Before annotations.
 *
 * @author Mathis
 * @date 09/01/2023
 */
public class TestNot {

    final Type BOOLEAN = new BooleanType(null);
    final Type FLOAT = new FloatType(null);

    @Mock
    AbstractExpr boolexpr1;
    @Mock
    AbstractExpr floatexpr1;
    
    DecacCompiler compiler;
    
    @BeforeEach
    public void setup() throws ContextualError {
        //MockitoAnnotations.initMocks(this);
        MockitoAnnotations.openMocks(this);
        compiler = new DecacCompiler(null, null, false);
        when(boolexpr1.verifyExpr(compiler, null, null)).thenReturn(BOOLEAN);
        when(floatexpr1.verifyExpr(compiler, null, null)).thenReturn(FLOAT);
    }

    @Test
    public void testBool() throws ContextualError {
        Not t = new Not(boolexpr1);
        // check the result
        assertTrue(t.verifyExpr(compiler, null, null).isBoolean());
        // check that the mocks have been called properly.
        verify(boolexpr1).verifyExpr(compiler, null, null);
    }
}