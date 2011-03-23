/*  j0 -- a compiler for j0
 *
 *  simple scanner framework
 *  03.11.99, Matthias Zenger
 */
package j0;

import java.io.*;
import java.util.*;

/** the scanner class
 */
class Scanner implements Tokens {

  public Scanner(InputStream in) {
    this.in = in;
    this.buf = new StringBuffer();
    this.keywords = new HashMap();
    keywords.put("int", new Integer(INT));
    keywords.put("program", new Integer(PROGRAM));
    keywords.put("while", new Integer(WHILE));
    keywords.put("return", new Integer(RETURN));
    keywords.put("if", new Integer(IF));
    keywords.put("else", new Integer(ELSE));
    keywords.put("void", new Integer(VOID));
    keywords.put("true", new Integer(TRUE));
    keywords.put("false", new Integer(FALSE));
    keywords.put("boolean", new Integer(BOOLEAN));
    nextCh();
  }
  /** the current token class
   */
  public int token;
  /** the token representation as a string
   */
  public String chars;
  /** the current token's position
   */
  public int pos;
  /** the input string
   */
  private InputStream in;
  /** the current lookahead character
   */
  private char ch;
  /** the line and the column of the current lookahead character
   */
  private int line = 1;
  private int col = 0;
  private char oldch;
  /** the EOF character
   */
  private final char EOF_CH = (char) -1;
  /** an internal buffer for assembling string representations
   */
  private StringBuffer buf;
  /** an internal table, keeps the tokenclasses for the keywords
   */
  private HashMap keywords;

  /** this method scans the next token
   */
  void nextToken() {
    loop:
    while (true) {
      while (ch <= ' ')
        nextCh();

      pos = Position.encode(line, col);
      switch (ch) {

        case EOF_CH:
          token = EOF;
          break;

        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
          buf.setLength(0);
          buf.append(ch);
          nextCh();
          while (ch >= '0' && ch <= '9') {
            buf.append(ch);
            nextCh();
          }
          token = NUM;
          chars = buf.toString();
          break;

        case '{':
          nextCh();
          token = LBRACE;
          break;

        case '}':
          nextCh();
          token = RBRACE;
          break;

        case ';':
          nextCh();
          token = SEMI;
          break;

        case '[':
          nextCh();
          token = LBRACKET;
          break;

        case ']':
          nextCh();
          token = RBRACKET;
          break;

        case '(':
          nextCh();
          token = LPAREN;
          break;

        case ')':
          nextCh();
          token = RPAREN;
          break;

        case ',':
          nextCh();
          token = COMMA;
          break;

        case '|':
          nextCh();
          token = OR;
          break;

        case '&':
          nextCh();
          token = AND;
          break;

        case '+':
          nextCh();
          token = PLUS;
          break;

        case '-':
          nextCh();
          token = MINUS;
          break;

        case '*':
          nextCh();
          token = TIMES;
          break;

        case '%':
          nextCh();
          token = MOD;
          break;

        case '<':
          nextCh();
          if (ch == '=') {
            nextCh();
            token = LTEQ;
          } else {
            token = LT;
          }
          break;

        case '>':
          nextCh();
          if (ch == '=') {
            nextCh();
            token = GTEQ;
          } else {
            token = GT;
          }
          break;

        case '=':
          nextCh();
          if (ch == '=') {
            nextCh();
            token = EQ;
          } else {
            token = ASSIGN;
          }
          break;

        case '!':
          nextCh();
          if (ch == '=') {
            nextCh();
            token = NEQ;
          } else {
            token = NOT;
          }
          break;

        case '/':
          nextCh();
          if (ch == '*') {
            do {
              nextCh();
              while (ch == '*') {
                nextCh();
                if (ch == '/') {
                  nextCh();
                  continue loop;
                }
              }
            } while (ch != EOF_CH);
            error("EOF in comment");
          } else if (ch == '/') {
            nextCh();
            while (ch != '\n' && ch != '\r' && ch != EOF_CH) {
              nextCh();
            }
            if (ch == EOF_CH) {
              error("EOF in comment");
              token = EOF;
            } else {
              nextCh();
              continue loop;
            }
          } else {
            token = DIV;
          }
          break;

        default:
          buf.setLength(0);
          buf.append(ch);
          if (!((ch >= 'a' && ch <= 'z')
                  || (ch >= 'A' && ch <= 'Z')
                  || (ch == '_'))) {
            error("illegal character: " + ch);
          }

          nextCh();

          while ((ch >= 'a' && ch <= 'z')
                  || (ch >= 'A' && ch <= 'Z')
                  || (ch >= '0' && ch <= '9')
                  || (ch == '_')) {

            buf.append(ch);
            nextCh();

          }

          Object o;

          if ((o = keywords.get(chars = buf.toString())) == null)
            token = IDENT;
          else
            token = ((Integer) o).intValue();
 
          break;

      }

      break;

    }
  }

  /** read next character and store it in ch
   */
  final void nextCh() {
    try {
      if (ch == '\n') {
        line++;
        col = 1;
      } else if (ch != EOF_CH) {
        col++;
      }
      ch = (char) in.read();
      oldch = ((oldch == '\r') && (ch == '\n')) ? (char) in.read() : ch;
      ch = (oldch == '\r') ? '\n' : oldch;
    } catch (IOException e) {
      error(e.toString());
      ch = EOF_CH;
    }
  }

  /** give out an error message and terminate with an Error exception
   */
  void error(String message) {
    Report.error(Position.encode(line, pos), message);
  }

  /** close the file input stream
   */
  void close() throws IOException {
    in.close();
  }

  /** string representation of the current token
   */
  public String representation() {
    return tokenClass(token)
            + (((token == NUM) || (token == IDENT)) ? "(" + chars + ")" : "");
  }

  /** return string for the given token class
   */
  public static String tokenClass(int token) {
    switch (token) {
      case EOF:
        return "<eof>";
      case NUM:
        return "number";
      case IDENT:
        return "identifier";
      case LT:
        return "<";
      case LTEQ:
        return "<=";
      case GT:
        return ">";
      case GTEQ:
        return ">=";
      case EQ:
        return "==";
      case NEQ:
        return "!=";
      case LBRACE:
        return "{";
      case RBRACE:
        return "}";
      case SEMI:
        return ";";
      case LBRACKET:
        return "[";
      case RBRACKET:
        return "]";
      case ASSIGN:
        return "=";
      case LPAREN:
        return "(";
      case RPAREN:
        return ")";
      case COMMA:
        return ",";
      case OR:
        return "|";
      case AND:
        return "&";
      case PLUS:
        return "+";
      case MINUS:
        return "-";
      case TIMES:
        return "*";
      case DIV:
        return "/";
      case MOD:
        return "%";
      case NOT:
        return "!";
      case INT:
        return "int";
      case BOOLEAN:
        return "boolean";
      case PROGRAM:
        return "program";
      case WHILE:
        return "while";
      case RETURN:
        return "return";
      case IF:
        return "if";
      case ELSE:
        return "else";
      case VOID:
        return "void";
      case TRUE:
        return "true";
      case FALSE:
        return "false";
      default:
        return "<unknown>";
    }
  }
}

/** small scanner test class
 */
class ScannerTest implements Tokens {

  static public void main(String[] args) {

    if (args.length < 1) {
      System.err.println("[EE] Argument missing");
      System.exit(1);
    }

    try {
      Scanner s = new Scanner(new FileInputStream(args[0]));
      s.nextToken();
      while (s.token != EOF) {
        System.out.println(s.representation());
        s.nextToken();
      }
      s.close();
    } catch (IOException e) {
      System.out.println(e.toString());
    }
  }
}
