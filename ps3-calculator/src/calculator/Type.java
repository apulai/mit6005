package calculator;

/*
 * TODO define your symbols and groups from problem 1 here
 * TERMINALS
   +, -, *, / , (, ), in, pt, [0-9]+

 */

/**
 * Token type.
TYPES
in		    num in
pt		    num pt
num         [0-9]+ | [0-9]+'.'[0-9]*  
op_plusminus: +,-
op_proddiv:   *,/
op_parent:    (,)
op_equal:     =

GRAMMAR:
expr :== (tag | tag + tag | tag - tag )
tag :== (prod | prod * prod | prod / prod )
prod :== ( in | pt | num | ( exp ) )
in :== num in
pt :== num pt
num  :== ( part | part.part )
part :== [0-9]+

 *
 */
enum Type { PLUS, MINUS, MUL, DIV, OPENP, CLOSEP, NUM, IN, PT, EQUAL
	// TODO define for problem 1
}