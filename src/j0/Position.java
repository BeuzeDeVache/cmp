/*  j0 -- a compiler for j0
 *
 *  position management
 *  01.12.99, Matthias Zenger
 */
package j0;

final class Position {

  /** undefined position
   */
  public static final int UNDEFINED = 0;
  /** first position in a source file
   */
  public static final int FIRST = (1 << 12) | 1;

  /** encode a line and column number into a single int
   */
  public static int encode(int line, int col) {
    return (line << 12) | (col & 0xfff);
  }

  /** get the line number out of an encoded position
   */
  public static int line(int pos) {
    return pos >>> 12;
  }

  /** return the column number of an encoded position
   */
  public static int column(int pos) {
    return pos & 0xfff;
  }
}
