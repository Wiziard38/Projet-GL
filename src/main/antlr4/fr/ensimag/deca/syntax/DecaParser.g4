parser grammar DecaParser;

options {
	// Default language but name it anyway
	language = Java;

	// Use a superclass to implement all helper methods, instance variables and overrides of ANTLR
	// default methods, such as error handling.
	superClass = AbstractDecaParser;

	// Use the vocabulary generated by the accompanying lexer. Maven knows how to work out the
	// relationship between the lexer and parser and will build the lexer before the parser. It will
	// also rebuild the parser if the lexer changes.
	tokenVocab = DecaLexer;
}

// which packages should be imported?
@header {
        import fr.ensimag.deca.tree.*;
        import java.io.PrintStream;
}

@members {
        @Override
        protected AbstractProgram parseProgram() {
        return prog().tree;
    }
}

prog
	returns[AbstractProgram tree]:
	list_classes main EOF {
                assert($list_classes.tree != null);
                assert($main.tree != null);
                $tree = new Program($list_classes.tree, $main.tree);
                setLocation($tree, $list_classes.start);
        };

main
	returns[AbstractMain tree]:
	/* epsilon */ {
                $tree = new EmptyMain();
        }
	| block {
                assert($block.decls != null);
                assert($block.insts != null);
                $tree = new Main($block.decls, $block.insts);
                setLocation($tree, $block.start);
        };

block
	returns[ListDeclVar decls, ListInst insts]:
	OBRACE list_decl list_inst CBRACE {
                assert($list_decl.tree != null);
                assert($list_inst.tree != null);
                $decls = $list_decl.tree;
                $insts = $list_inst.tree;
        };

list_decl
	returns[ListDeclVar tree]
	@init {
                $tree = new ListDeclVar();
        }: decl_var_set[$tree]*;

decl_var_set[ListDeclVar l]:
	type list_decl_var[$l,$type.tree] SEMI;

list_decl_var[ListDeclVar l, AbstractIdentifier t]:
	dv1 = decl_var[$t] {
                $l.add($dv1.tree);
        } (
		COMMA dv2 = decl_var[$t] { 
                $l.add($dv2.tree);              //ici
        }
	)*;

decl_var[AbstractIdentifier t]
	returns[AbstractDeclVar tree]
	@init {
                AbstractInitialization init = new NoInitialization();         //ici
        }:
	i = ident {
		/* condition: expression e must be a "LVALUE" */ 
                if (! ($e.tree instanceof AbstractLValue)) {
                        throw new InvalidLValue(this, $ctx);
                } 
        } (
		EQUALS e = expr {
                        init = new Initialization($e.tree);         //ici
        }
	)? {
                $tree = new DeclVar($t, $ident.tree, init);         //ici
        };

list_inst
	returns[ListInst tree]
	@init {
                $tree = new ListInst();         //ici
}: (
		inst {
                        $tree.add($inst.tree);          //ici
        }
	)*;

inst
	returns[AbstractInst tree]:
	e1 = expr SEMI {
                assert($e1.tree != null);
                $tree = $e1.tree;                   //ici
                setLocation($tree, $e1.start);          //ici
        }
	| SEMI {
                $tree = new NoOperation();       //ici
                setLocation($tree, $SEMI);          //ici
        }
	| PRINT OPARENT list_expr CPARENT SEMI {
                assert($list_expr.tree != null);
                $tree = new Print(false, $list_expr.tree);          //ici
                setLocation($tree, $PRINT);          //ici
        }
	| PRINTLN OPARENT list_expr CPARENT SEMI {
                assert($list_expr.tree != null);
                $tree = new Println(false, $list_expr.tree);        //ici
                setLocation($tree, $PRINTLN);          //ici
        }
	| PRINTX OPARENT list_expr CPARENT SEMI {
                assert($list_expr.tree != null);
                $tree = new Print(true, $list_expr.tree);          //ici
                setLocation($tree, $PRINTX);          //ici
        }
	| PRINTLNX OPARENT list_expr CPARENT SEMI {
                assert($list_expr.tree != null);
                $tree = new Println(true, $list_expr.tree);        //ici
                setLocation($tree, $PRINTLNX);          //ici             
        }
	| if_then_else {
                assert($if_then_else.tree != null);
                $tree = $if_then_else.tree;                     //ici
                setLocation($tree, $if_then_else.start);          //ici
        }
	| WHILE OPARENT condition = expr CPARENT OBRACE body = list_inst CBRACE {
                assert($condition.tree != null);
                assert($body.tree != null);
                $tree = new While($condition.tree, $body.tree);           //ici
                setLocation($tree, $WHILE);          //ici
        }
	| RETURN expr SEMI {
                assert($expr.tree != null);
                $tree = $expr.tree;                      //ici
                setLocation($tree, $RETURN);          //ici
        };

if_then_else
	returns[IfThenElse tree]
	@init {
                // ListInst else_branch = new ListInst();        //ici
                // ListInst elsif_else_branch = new ListInst();     //ici
                // Tree last_else;                                 //ici
}:
	if1 = IF OPARENT condition = expr CPARENT OBRACE li_if = list_inst CBRACE {
                // assert($condition.tree != null);                //ici
                // assert($li_if.tree != null);                    //ici
                // $tree = new IfThenElse($condition.tree, li_if.tree, else_branch);          //ici
                // last_else = else_branch;                        //ici
        } (
		ELSE elsif = IF OPARENT elsif_cond = expr CPARENT OBRACE elsif_li = list_inst CBRACE {
                        // assert($elsif_cond.tree != null);               //d'ici
                        // assert($elsif_li.tree != null);
                        // last_else.add(new IfThenElse($elsif_cond.tree, $elsif_li.tree, elsif_else_branch));
                        // last_else = elsif_else_branch;          //a la
        }
	)* (
		ELSE OBRACE li_else = list_inst CBRACE {
        }
	)?;

list_expr
	returns[ListExpr tree]
	@init {
                $tree = new ListExpr();         //ici
        }: (
		e1 = expr {
                        $tree.add($e1.tree);    //ici
        } (
			COMMA e2 = expr {
                                $tree.add($e2.tree);    //ici
                        }
		)*
	)?;

expr
	returns[AbstractExpr tree]:
	assign_expr {
                assert($assign_expr.tree != null);
                $tree = $assign_expr.tree;               //ici
        };

assign_expr
	returns[AbstractExpr tree]:
	e = or_expr (
		{
		/* condition: expression e must be a "LVALUE" */
                if (! ($e.tree instanceof AbstractLValue)) {
                        throw new InvalidLValue(this, $ctx);
                }
        } EQUALS e2 = assign_expr {
                assert($e.tree != null);
                assert($e2.tree != null);
                $tree = new Assign((AbstractLValue) $e.tree, $e2.tree);          //ici
        }
		| /* epsilon */ {
                assert($e.tree != null);
                $tree = $e.tree;                //ici
        }
	);

or_expr
	returns[AbstractExpr tree]:
	e = and_expr {
                assert($e.tree != null);
                $tree = $e.tree;                //ici
        }
	| e1 = or_expr OR e2 = and_expr {
                assert($e1.tree != null);
                assert($e2.tree != null);
                $tree = new Or($e1.tree, $e2.tree);             //ici
       };

and_expr
	returns[AbstractExpr tree]:
	e = eq_neq_expr {
                assert($e.tree != null);
                $tree = $e.tree;                //ici
        }
	| e1 = and_expr AND e2 = eq_neq_expr {
                assert($e1.tree != null);                         
                assert($e2.tree != null);
                $tree = new And($e1.tree, $e2.tree);            //ici
        };

eq_neq_expr
	returns[AbstractExpr tree]:
	e = inequality_expr {
                assert($e.tree != null);
                $tree = $e.tree;                //ici
        }
	| e1 = eq_neq_expr EQEQ e2 = inequality_expr {
                assert($e1.tree != null);
                assert($e2.tree != null);
                $tree = new Equals($e1.tree, $e2.tree);         //ici
        }
	| e1 = eq_neq_expr NEQ e2 = inequality_expr {
                assert($e1.tree != null);
                assert($e2.tree != null);
                $tree = new NotEquals($e1.tree, $e2.tree);         //ici
        };

inequality_expr
	returns[AbstractExpr tree]:
	e = sum_expr {
                assert($e.tree != null);
                $tree = $e.tree;                //ici
        }
	| e1 = inequality_expr LEQ e2 = sum_expr {
                assert($e1.tree != null);
                assert($e2.tree != null);
                $tree = new LowerOrEqual($e1.tree, $e2.tree);         //ici
        }
	| e1 = inequality_expr GEQ e2 = sum_expr {
                assert($e1.tree != null);
                assert($e2.tree != null);
                $tree = new GreaterOrEqual($e1.tree, $e2.tree);         //ici
        }
	| e1 = inequality_expr GT e2 = sum_expr {
                assert($e1.tree != null);
                assert($e2.tree != null);
                $tree = new Greater($e1.tree, $e2.tree);         //ici
        }
	| e1 = inequality_expr LT e2 = sum_expr {
                assert($e1.tree != null);
                assert($e2.tree != null);
                $tree = new Lower($e1.tree, $e2.tree);         //ici
        }
	| e1 = inequality_expr INSTANCEOF type {
                assert($e1.tree != null);
                assert($type.tree != null);
                // $tree = new Instanceof($e1.tree, $e2.tree);         //ici pas supporté
        };

sum_expr
	returns[AbstractExpr tree]:
	e = mult_expr {
                assert($e.tree != null);
                $tree = $e.tree;                //ici
        }
	| e1 = sum_expr PLUS e2 = mult_expr {
                assert($e1.tree != null);
                assert($e2.tree != null);
                $tree = new Plus($e1.tree, $e2.tree);         //ici
        }
	| e1 = sum_expr MINUS e2 = mult_expr {
                assert($e1.tree != null);
                assert($e2.tree != null);
                $tree = new Minus($e1.tree, $e2.tree);         //ici
        };

mult_expr
	returns[AbstractExpr tree]:
	e = unary_expr {
                assert($e.tree != null);
                $tree = $e.tree;                //ici
        }
	| e1 = mult_expr TIMES e2 = unary_expr {
                assert($e1.tree != null);                                         
                assert($e2.tree != null);
                $tree = new Multiply($e1.tree, $e2.tree);         //ici
        }
	| e1 = mult_expr SLASH e2 = unary_expr {
                assert($e1.tree != null);                                         
                assert($e2.tree != null);
                $tree = new Divide($e1.tree, $e2.tree);         //ici
        }
	| e1 = mult_expr PERCENT e2 = unary_expr {
                assert($e1.tree != null);                                                                          
                assert($e2.tree != null);
                $tree = new Modulo($e1.tree, $e2.tree);         //ici
        };

unary_expr
	returns[AbstractExpr tree]:
	op = MINUS e = unary_expr {
                assert($e.tree != null);
                $tree = new UnaryMinus($e.tree);                //ici
        }
	| op = EXCLAM e = unary_expr {
                assert($e.tree != null);
                $tree = new Not($e.tree);                //ici
        }
	| select_expr {
                assert($select_expr.tree != null);
                $tree = $select_expr.tree;                //ici
        };

select_expr
	returns[AbstractExpr tree]:
	e = primary_expr {
                assert($e.tree != null);
                $tree = $e.tree;                //ici
        }
	| e1 = select_expr DOT i = ident {
                assert($e1.tree != null);
                assert($i.tree != null);
                $tree = $e.tree;                //ici pas supporté
        } (
		o = OPARENT args = list_expr CPARENT {
                // we matched "e1.i(args)"
                assert($args.tree != null);
                // $tree = $args.tree;             //ici ??
        }
		| /* epsilon */ {
                // we matched "e.i"
        }
	);

primary_expr
	returns[AbstractExpr tree]:
	ident {
                assert($ident.tree != null);
                $tree = $ident.tree;            //ici
        }
	| m = ident OPARENT args = list_expr CPARENT {
                assert($args.tree != null);
                assert($m.tree != null);
                                                                //ici pas fait
        }
	| OPARENT expr CPARENT {
                assert($expr.tree != null);
                $tree = $expr.tree;             //ici
        }
	| READINT OPARENT CPARENT {
                $tree = new ReadInt();          //ici
        }
	| READFLOAT OPARENT CPARENT {
                $tree = new ReadFloat();                //ici
        }
	| NEW ident OPARENT CPARENT {
                assert($ident.tree != null);
                $tree = $ident.tree;            //ici
        }
	| cast = OPARENT type CPARENT OPARENT expr CPARENT {
                assert($type.tree != null);
                assert($expr.tree != null);                     //ici pas supporté
        }
	| literal {
                assert($literal.tree != null);
                $tree = $literal.tree;      //ici
        };

type
	returns[AbstractIdentifier tree]:
	ident {
                assert($ident.tree != null);
                $tree = $ident.tree;        //ici
        };

literal
	returns[AbstractExpr tree]:
	INT {
                $tree = new IntLiteral(Integer.parseInt($INT.text));        //ici
                setLocation($tree, $INT);          //ici
        }
	| fd = FLOAT {
                $tree = new FloatLiteral(Float.parseFloat($fd.text));        //ici
                setLocation($tree, $fd);          //ici
        }
	| STRING {
                $tree = new StringLiteral($STRING.text);        //ici
                setLocation($tree, $STRING);          //ici
        }
	| TRUE {
                $tree = new BooleanLiteral(true);        //ici
                setLocation($tree, $TRUE);          //ici
        }
	| FALSE {
                $tree = new BooleanLiteral(false);        //ici
                setLocation($tree, $FALSE);          //ici
        }
	| THIS {                                        //ici pas supporté
        }
	| NULL {                                        //ici pas supporté
        };

ident
	returns[AbstractIdentifier tree]:
	IDENT {
                //symbolTable???.create($IDENT.text);          //ici pas sur
                //$tree = new Identifier(symbol);         //ici
        };

/****     Class related rules     ****/

list_classes
	returns[ListDeclClass tree]
	@init {
        $tree = new ListDeclClass();
    }:
	(
		c1 = class_decl {
        }
	)*;

class_decl:
	CLASS name = ident superclass = class_extension OBRACE class_body CBRACE {
        };

class_extension
	returns[AbstractIdentifier tree]:
	EXTENDS ident {
        }
	| /* epsilon */ {
        };

class_body: (
		m = decl_method {
        }
		| decl_field_set
	)*;

decl_field_set: v = visibility t = type list_decl_field SEMI;

visibility:
	/* epsilon */ {
        }
	| PROTECTED {
        };

list_decl_field: dv1 = decl_field (COMMA dv2 = decl_field)*;

decl_field:
	i = ident {
        } (
		EQUALS e = expr {
        }
	)? {
        };

decl_method
	@init {
}:
	type ident OPARENT params = list_params CPARENT (
		block {
        }
		| ASM OPARENT code = multi_line_string CPARENT SEMI {
        }
	) {
        };

list_params: (
		p1 = param {
        } (
			COMMA p2 = param {
        }
		)*
	)?;

multi_line_string
	returns[String text, Location location]:
	s = STRING {
                $text = $s.text;
                $location = tokenLocation($s);
        }
	| s = MULTI_LINE_STRING {
                $text = $s.text;
                $location = tokenLocation($s);
        };

param:
	type ident {
        };
