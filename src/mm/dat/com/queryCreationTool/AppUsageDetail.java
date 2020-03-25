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

public class AppUsageDetail {

	private static final String INSERT_INTO = "INSERT INTO";
	private static final String VALUES = "VALUES";
	private static final String STRAT_PARENTHESES = "(";
	private static final String END_PARENTHESES = ")";
	private static final String COMMA = ",";
	private static final String SPACE = " ";
	private static final String NEW_LINE = "\n";
	private static final String SEMI_COLON = ";";
	// AppUsageInfo
	private static final String APP_USAGE_DETAIL = "ASS.APP_USAGE_DETAIL";
	private static final String APP_USAGE_ID = "APP_USAGE_ID";
	private static final String START_USAGE_DATE_TIME = "START_USAGE_DATE_TIME";
	private static final String END_USAGE_DATE_TIME = "END_USAGE_DATE_TIME";

	public static void main(String[] args) {

		try {
			int endLoop = 200;
			Path path = Paths.get("C:\\Users\\25-00101\\Desktop\\AppUsageInfo_" + endLoop);
			if (!Files.exists(path)) {
				Files.createDirectories(path);
			}

			FileWriter seqWriter = new FileWriter(path + "\\AppUsageDetail_" + endLoop + ".sql");
			String seperator = COMMA;
			
			seqWriter.write(INSERT_INTO + SPACE + APP_USAGE_DETAIL + STRAT_PARENTHESES + APP_USAGE_ID + COMMA
					+ START_USAGE_DATE_TIME + COMMA + END_USAGE_DATE_TIME + END_PARENTHESES + VALUES);
			
			for (int i = 1; i <= endLoop; i++) {
				for (int j = 1; j <= 50; j++) {
					if (i == endLoop && j == 50) {
						seperator = SEMI_COLON;
					}
					
					
					seqWriter.write(NEW_LINE + STRAT_PARENTHESES + i + COMMA + "'2019-01-10'" + COMMA + "'2019-03-10'"
							+ END_PARENTHESES + seperator);

				}
			}
			seqWriter.close();
			System.out.println("Successfully Created AppUsageDetail File.");

		} catch (IOException e) {
			System.out.println("Error" + SPACE + e);
		}

	}

}
