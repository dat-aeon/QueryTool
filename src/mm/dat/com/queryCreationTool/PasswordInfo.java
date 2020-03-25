/**************************************************************************
 * $Date : 2019/02/21$
 * $Author : Aung Ko Lin$
 * $Rev : $
 *************************************************************************/
package mm.dat.com.queryCreationTool;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PasswordInfo {

	private static final String INSERT_INTO = "INSERT INTO";
	private static final String VALUES = "VALUES";
	private static final String STRAT_PARENTHESES = "(";
	private static final String END_PARENTHESES = ")";
	private static final String COMMA = ",";
	private static final String SPACE = " ";
	private static final String SEMI_COLON = ";";
	private static final String NEW_LINE = "\n";
	// Password Info
	private static final String PASSWORD_INFO = "VCS.PASSWORD_INFO";
	private static final String USER_ID = "USER_ID";
	private static final String USER_TYPE_ID = "USER_TYPE_ID";
	private static final String PASSWORD = "PASSWORD";
	private static final String CREATED_BY = "CREATED_BY";

	public static void main(String[] args) {

		try {
			int endLoop = 1000;
			Path path = Paths.get("C:\\Users\\25-00101\\Desktop\\CustomerImport_" + endLoop);
			if (!Files.exists(path)) {
				Files.createDirectories(path);
			}

			FileWriter seqWriter = new FileWriter(path + "\\PasswordInfo_" + endLoop + ".sql");

			seqWriter.write(INSERT_INTO + SPACE + PASSWORD_INFO + STRAT_PARENTHESES + USER_ID + COMMA + USER_TYPE_ID
					+ COMMA + PASSWORD + COMMA + CREATED_BY + END_PARENTHESES + VALUES);

			for (int i = 1; i <= endLoop; i++) {

				seqWriter.write(NEW_LINE + STRAT_PARENTHESES + "1" + COMMA + "1" + COMMA + "'12345'" + COMMA + "'admin'"
						+ END_PARENTHESES + (i != endLoop ? COMMA : SEMI_COLON));
			}
			seqWriter.close();
			System.out.println("Successfully Created Password Info File.");

		} catch (IOException e) {
			System.out.println("Error" + SPACE + e);
		}

	}

}
