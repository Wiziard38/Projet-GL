package fr.ensimag.deca.context;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tree.AbstractExpr;
import fr.ensimag.deca.tree.AbstractIdentifier;
import fr.ensimag.deca.tree.AbstractInitialization;
import fr.ensimag.deca.tree.DeclVar;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

/**
 * Test for the DeclVar node using mockito, using @Mock and @Before annotations.
 *
 * @author Mathis
 * @date 09/01/2023
 */
public class TestDeclVar {

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
    @Mock
    AbstractIdentifier ident1;
    @Mock
    AbstractIdentifier ident2;
    @Mock
    AbstractInitialization init;

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
        DeclVar t = new DeclVar(ident1, ident2, init);
        // check the result
        // assertTrue(t.verifyExpr(compiler, null, null).isInt());
        // check that the mocks have been called properly.
        // verify(intexpr1).verifyExpr(compiler, null, null);
        // verify(intexpr2).verifyExpr(compiler, null, null);
    }
}