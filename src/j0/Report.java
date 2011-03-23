/*  A COMPILER FOR J0
 *
 *  a simple class for giving out error messages
 *  03.11.2K, Matthias Zenger
 */
package j0;

import java.util.HashMap;

final class Report {

  static HashMap errorLog = new HashMap();

  static void error(int pos, String message) {
    Integer position = new Integer(pos);
    if (errorLog.get(position) == null) {
      System.out.println(Position.line(pos) + ":"
              + Position.column(pos) + ": " + message);
      errorLog.put(position, message);
    }
  }
}
