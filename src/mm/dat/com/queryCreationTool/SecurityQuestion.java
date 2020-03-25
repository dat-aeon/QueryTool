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

public class SecurityQuestion {

	private static final String INSERT_INTO = "INSERT INTO";
	private static final String VALUES = "VALUES";
	private static final String STRAT_PARENTHESES = "(";
	private static final String END_PARENTHESES = ")";
	private static final String COMMA = ",";
	private static final String SPACE = " ";
	private static final String SEMI_COLON = ";";
	private static final String NEW_LINE = "\n";
	// Security Question
	private static final String SECURITY_QUESTION = "VCS.SECURITY_QUESTION";
	private static final String QUESTION_MYAN = "QUESTION_MYAN";
	private static final String QUESTION_ENG = "QUESTION_ENG";
	private static final String CREATED_BY = "CREATED_BY";

	public static void main(String[] args) {

		try {
			int endLoop = 50;
			Path path = Paths.get("C:\\Users\\25-00101\\Desktop\\SecurityQuestion_" + endLoop);
			if (!Files.exists(path)) {
				Files.createDirectories(path);
			}

			FileWriter seqWriter = new FileWriter(path + "\\SecurityQuestion_" + endLoop + ".sql");

			seqWriter.write(INSERT_INTO + SPACE + SECURITY_QUESTION + STRAT_PARENTHESES + QUESTION_MYAN + COMMA
					+ QUESTION_ENG + COMMA + CREATED_BY + END_PARENTHESES + VALUES);

			for (int i = 1; i <= endLoop; i++) {

				seqWriter.write(NEW_LINE + STRAT_PARENTHESES + "'သင္ျကိုက္နွစ္သက္ေသာ အသီးက ဘာလဲ�?�" + i + "'" + COMMA
						+ "'What is your favourites fruit?" + i + "'" + COMMA + "'admin'" + END_PARENTHESES
						+ (i != endLoop ? COMMA : SEMI_COLON));
			}
			seqWriter.close();
			System.out.println("Successfully Created SecurityQuestion File.");

		} catch (IOException e) {
			System.out.println("Error" + SPACE + e);
		}

	}

}
