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

public class AppUsageInfo {

	private static final String INSERT_INTO = "INSERT INTO";
	private static final String VALUES = "VALUES";
	private static final String STRAT_PARENTHESES = "(";
	private static final String END_PARENTHESES = ")";
	private static final String COMMA = ",";
	private static final String SPACE = " ";
	private static final String SEMI_COLON = ";";
	private static final String NEW_LINE = "\n";
	// AppUsageInfo
	private static final String APP_USAGE_INFO = "VCS.APP_USAGE_INFO";
	private static final String CUSTOMER_ID = "CUSTOMER_ID";
	private static final String REGISTRATION_DATE_TIME = "REGISTRATION_DATE_TIME";
	private static final String PHONE_MODEL = "PHONE_MODEL";
	private static final String MANUFACTURE = "MANUFACTURE";
	private static final String SDK = "SDK";
	private static final String OS_TYPE = "OS_TYPE";
	private static final String OS_VERSION = "OS_VERSION";
	private static final String CREATED_BY = "CREATED_BY";

	public static void main(String[] args) {

		try {
			int endLoop = 10001;
			Path path = Paths.get("C:\\Users\\25-00101\\Desktop\\AppUsageInfo_" + endLoop);
			if (!Files.exists(path)) {
				Files.createDirectories(path);
			}

			FileWriter seqWriter = new FileWriter(path + "\\AppUsageInfo_" + endLoop + ".sql");

			seqWriter.write(INSERT_INTO + SPACE + APP_USAGE_INFO + STRAT_PARENTHESES + CUSTOMER_ID + COMMA
					+ REGISTRATION_DATE_TIME + COMMA + PHONE_MODEL + COMMA + MANUFACTURE + COMMA + SDK + COMMA + OS_TYPE
					+ COMMA + OS_VERSION + COMMA + CREATED_BY + END_PARENTHESES + VALUES);

			for (int i = 7001; i <= endLoop; i++) {
				seqWriter.write(NEW_LINE + STRAT_PARENTHESES + i + COMMA + "'2019-02-24'" + COMMA + "'M-0PDG119'"
						+ COMMA + "'TEST_MANUFACTURE'" + COMMA + "'4.2'" + COMMA + "'Android'" + COMMA + "'8.0'" + COMMA
						+ "'admin'" + END_PARENTHESES + (i != endLoop ? COMMA : SEMI_COLON));

			}
			seqWriter.close();
			System.out.println("Successfully Created AppUsageInfo File.");

		} catch (IOException e) {
			System.out.println("Error" + SPACE + e);
		}

	}

}
