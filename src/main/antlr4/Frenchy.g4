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
WORD : ('a'..'z'+);
VALUE : ('0'..'9'+);
WHITESPACE : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+ -> skip ;
/*
 * Parser Rules
 */
program: variable EOF;
variable: 'soit' WORD 'valant' VALUE;