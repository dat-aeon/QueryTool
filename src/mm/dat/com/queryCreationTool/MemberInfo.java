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

public class MemberInfo {

	private static final String INSERT_INTO = "INSERT INTO";
	private static final String VALUES = "VALUES";
	private static final String STRAT_PARENTHESES = "(";
	private static final String END_PARENTHESES = ")";
	private static final String COMMA = ",";
	private static final String SPACE = " ";
	private static final String SEMI_COLON = ";";
	private static final String NEW_LINE = "\n";
	// Password Info
	private static final String MEMBER_INFO = "VCS.MEMBER_INFO";
	private static final String CUSTOMER_ID = "CUSTOMER_ID";
	private static final String MEMBER_TYPE_ID = "MEMBER_TYPE_ID";
	private static final String SCORE = "SCORE";
	private static final String CREATED_BY = "CREATED_BY";

	public static void main(String[] args) {

		try {
			int endLoop = 1000;
			Path path = Paths.get("C:\\Users\\25-00101\\Desktop\\CustomerImport_" + endLoop + "\\MemberInfo");
			if (!Files.exists(path)) {
				Files.createDirectories(path);
			}

			FileWriter seqWriter = new FileWriter(path + "\\MemberInfo_" + endLoop + ".sql");

			seqWriter.write(INSERT_INTO + SPACE + MEMBER_INFO + STRAT_PARENTHESES + CUSTOMER_ID + COMMA + MEMBER_TYPE_ID
					+ COMMA + SCORE + COMMA + CREATED_BY + END_PARENTHESES + VALUES);

			for (int i = 1; i <= endLoop; i++) {

				seqWriter.write(NEW_LINE + STRAT_PARENTHESES + i + COMMA + "1" + COMMA + "20" + COMMA + "'admin'"
						+ END_PARENTHESES + (i != endLoop ? COMMA : SEMI_COLON));
			}
			seqWriter.close();
			System.out.println("Successfully Created Member Info File.");

		} catch (IOException e) {
			System.out.println("Error" + SPACE + e);
		}

	}

}
