grammar Frenchy;

@lexer::header {
}

@parser::header {
}

/*
 * Lexer Rules
 */
BOOLEAN    : 'vrai'|'faux';
WORD       : ('a'..'z'+);
VALUE      : ('0'..'9'+);
// Pour ne pas être sensible aux espaces
WHITESPACE : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+ -> skip ;
ADD        : '+';
EQUALS     : 'égale';
/*
 * Parser Rules
 */
program              : (variableDefinition|ifStatement)+ EOF;
element              : VALUE|BOOLEAN|WORD;
operation            : EQUALS|ADD;
ifStatement          : 'si' condition 'alors' statementThen 'sinon' statementElse;
statementThen        : statement;
statementElse        : statement;
statement            : ifStatement|element (operation element)*;
condition            : BOOLEAN|element EQUALS element;
variableDefinition   : 'soit' WORD 'valant' statement;
