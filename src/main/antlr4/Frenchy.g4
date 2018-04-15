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
// Pour ne pas être sensible aux espaces
WHITESPACE : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+ -> skip ;
ADD        : '+';
EQUALS     : 'égale';
/*
 * Parser Rules
 */
program              : (variableDefinition)+ EOF;
element              : VALUE|BOOLEAN|WORD;
operation            : EQUALS|ADD;
statement            : element (operation element)*;
variableDefinition   : 'soit' WORD 'valant' statement;
