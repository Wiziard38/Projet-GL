lexer grammar DecaLexer;

options {
   language=Java;
   // Tell ANTLR to make the generated lexer class extend the
   // the named class, which is where any supporting code and
   // variables will be placed.
   superClass = AbstractDecaLexer;
}

@members {
}

// Deca lexer rules.

// Symboles speciaux
OBRACE : '{';
CBRACE : '}';
OPARENT : '(';
CPARENT : ')';
SEMI : ';';
COMMA : ',';
EQUALS : '=';
OR : '||';
AND : '&&';
NEQ : '!=';
EQEQ : '==';
LEQ : '<=';
GEQ : '>=';
GT : '>';
LT : '<';
PLUS : '+' ;
MINUS : '-' ;
TIMES : '*' ;
SLASH : '/';
PERCENT : '%';
EXCLAM : '!';
DOT : '.';

// Mots reserves
ASM : 'asm';
CLASS : 'class';
EXTENDS : 'extends';
ELSE : 'else';
FALSE : 'false';
IF : 'if';
INSTANCEOF : 'instanceof';
NEW : 'new';
NULL : 'null';
READINT : 'readInt';
READFLOAT : 'readFloat';
PRINT : 'print';
PRINTLN : 'println';
PRINTLNX : 'printlnx';
PRINTX : 'printx';
PROTECTED : 'protected';
RETURN : 'return';
THIS : 'this';
TRUE : 'true';
WHILE : 'while';

// Identifier
fragment DIGIT : '0' .. '9';
fragment LETTER : ('a' .. 'z' | 'A' .. 'Z');
IDENT : (LETTER | '$' | '_') (LETTER | DIGIT | '$' | '_')*;


// Literaux entiers
fragment POSITIVE_DIGIT : '1' .. '9';
INT : POSITIVE_DIGIT DIGIT* | '0';

// Literaux flottants
fragment NUM : DIGIT+;
fragment SIGN : (PLUS | MINUS)?;
fragment EXP : ('E' | 'e') SIGN NUM;
fragment DEC : NUM DOT NUM;
fragment FLOATDEC : (DEC | DEC EXP) ('F' | 'f')?;
fragment DIGITHEX : (DIGIT | 'A' .. 'F' | 'a' .. 'f');
fragment NUMHEX : DIGITHEX+;
fragment FLOATHEX : ('0x' | '0X') NUMHEX DOT NUMHEX ('P' | 'p') SIGN NUM ('F' | 'f')?;
FLOAT : FLOATDEC | FLOATHEX;

// Separateurs
WS : (' ' | '\n' | '\r' | '\t') {skip();} ;

// Strings
STRING : '"' (LETTER | WS | '\\"' | '\\\\')* '"' {skip();} ;
EOL : '\r'? '\n' | '\r' {skip();} ;
MULTI_LINE_STRING : '"' (LETTER | WS | '\\"' | '\\\\' | EOL)* '"' {skip();} ;

// Commentaires
COMMENT : '//' ~('\n'|'\r')* '\r'? '\n' {skip();} 
        | '/*' .*? '*/' {skip();} 
        ;

// Includes
FILENAME : (LETTER | DIGIT | DOT | '-' | '_')+;
INCLUDE : '#include' (' ')* '"' FILENAME '"';
