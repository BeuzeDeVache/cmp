/*  j0 -- a compiler for j0
 *
 *  constants representing tokens
 *  03.11.99, Matthias Zenger
 */
package j0;

/** definition of all token classes
 */
interface Tokens {

  int EOF = 0;
  int NUM = EOF + 1;
  int IDENT = NUM + 1;
  int EQ = IDENT + 1;
  int NEQ = EQ + 1;
  int LT = NEQ + 1;
  int GTEQ = LT + 1;
  int LTEQ = GTEQ + 1;
  int GT = LTEQ + 1;
  int LBRACE = GT + 1;
  int RBRACE = LBRACE + 1;
  int SEMI = RBRACE + 1;
  int LBRACKET = SEMI + 1;
  int RBRACKET = LBRACKET + 1;
  int ASSIGN = RBRACKET + 1;
  int LPAREN = ASSIGN + 1;
  int RPAREN = LPAREN + 1;
  int COMMA = RPAREN + 1;
  int OR = COMMA + 1;
  int AND = OR + 1;
  int PLUS = AND + 1;
  int MINUS = PLUS + 1;
  int TIMES = MINUS + 1;
  int DIV = TIMES + 1;
  int MOD = DIV + 1;
  int NOT = MOD + 1;
  int INT = NOT + 1;
  int BOOLEAN = INT + 1;
  int PROGRAM = BOOLEAN + 1;
  int WHILE = PROGRAM + 1;
  int RETURN = WHILE + 1;
  int IF = RETURN + 1;
  int ELSE = IF + 1;
  int VOID = ELSE + 1;
  int TRUE = VOID + 1;
  int FALSE = TRUE + 1;
}
