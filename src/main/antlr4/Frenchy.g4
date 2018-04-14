grammar Frenchy;

@lexer::header {
  package fr.arolla.lexer;
}

@parser::header {
  package fr.arolla.parser;
}

/*
 * Lexer Rules
 */
BOOLEAN    : 'vrai'|'faux';
WORD       : ('a'..'z'+);
VALUE      : ('0'..'9'+);
WHITESPACE : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+ -> skip ;
ADD        : '+';
/*
 * Parser Rules
 */
program    : (variable)+ EOF;
element    : VALUE|WORD;
statement  : BOOLEAN|element (ADD element)*;
variable   : 'soit' WORD 'valant' statement;