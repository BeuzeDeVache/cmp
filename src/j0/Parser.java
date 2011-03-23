/*  A COMPILER FOR J0
 *
 *  constants representing tokens/token classes
 *  03.11.2K, Matthias Zenger
 */
package j0;

import java.io.*;

/**
 * Parser class
 */
class Parser extends Scanner {

	public Parser(InputStream in) {
		super(in);
		nextToken();
	}

	/**
	 * Accept a token or print an error, if another token was found
	 */
	void accept(int token) {
		if (this.token == token) {
			nextToken();
		} else {
			error(tokenClass(token) + " expected, but "
					+ tokenClass(this.token) + " found");
		}
	}

	/**
	 * ModuleDeclaration = "program" ident ";" { Declaration }
	 */
	void moduleDeclaration() {
		accept(PROGRAM);
		accept(IDENT);
		accept(SEMI);
		while (token != EOF)
			declaration();
		accept(EOF);
	}

	void declaration() {

		if (true) {

		}

	}

	void primitiveType() {
		if(this.token == BOOLEAN) accept(BOOLEAN);
		else accept(INT);
	}
	// A COMPLETER : Methodes restantes pour l'analyse syntaxique

}

/**
 * Test class for the parser
 */
class ParserTest implements Tokens {

	static public void main(String[] args) {

		try {

			// Check for arguments
			if (args.length == 0) {
				System.out.println("Usage: java j0.ParserTest <file>");
				System.exit(1);
			}

			Parser parser = new Parser(new FileInputStream(args[0]));
			parser.moduleDeclaration();
			parser.close();
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}
}
