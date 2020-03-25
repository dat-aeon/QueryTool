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

public class CustomerInfo {

	private static final String INSERT_INTO = "INSERT INTO";
	private static final String VALUES = "VALUES";
	private static final String STRAT_PARENTHESES = "(";
	private static final String END_PARENTHESES = ")";
	private static final String COMMA = ",";
	private static final String SPACE = " ";
	private static final String SEMI_COLON = ";";
	private static final String NEW_LINE = "\n";

	// Customer Info
	private static final String CUSTOMER_INFO = "VCS.CUSTOMER_INFO";
	private static final String CUSTOMER_NO = "CUSTOMER_NO";
	private static final String PHONE_NO = "PHONE_NO";
	private static final String CUSTOMER_TYPE_ID = "CUSTOMER_TYPE_ID";
	private static final String USER_TYPE_ID = "USER_TYPE_ID";
	private static final String NAME = "NAME";
	private static final String DOB = "DOB";
	private static final String NRC_NO = "NRC_NO";
	private static final String JOIN_DATE = "JOIN_DATE";
	private static final String SALARY = "SALARY";
	private static final String COMPANY_NAME = "COMPANY_NAME";
	private static final String ADDRESS = "ADDRESS";
	private static final String GENDER = "GENDER";
	private static final String CREATED_BY = "CREATED_BY";

	public static void main(String[] args) {

		try {
			int endLoop = 300000;
			Path path = Paths.get("C:\\Users\\25-00101\\Desktop\\CustomerImport_" + endLoop);

			if (!Files.exists(path)) {
				Files.createDirectories(path);
			}

			FileWriter seqWriter = new FileWriter(path + "\\CustomerInfo_" + endLoop + ".sql");

			seqWriter.write(INSERT_INTO + SPACE + CUSTOMER_INFO + STRAT_PARENTHESES + CUSTOMER_NO + COMMA + PHONE_NO
					+ COMMA + CUSTOMER_TYPE_ID + COMMA + USER_TYPE_ID + COMMA + NAME + COMMA + DOB + COMMA + NRC_NO
					+ COMMA + JOIN_DATE + COMMA + SALARY + COMMA + COMPANY_NAME + COMMA + ADDRESS + COMMA + GENDER
					+ COMMA + CREATED_BY + END_PARENTHESES + VALUES);

			for (int i = 1; i <= endLoop; i++) {

				seqWriter.write(NEW_LINE + STRAT_PARENTHESES + "'100" + i + "'" + COMMA + "'0945082162" + i + "'"
						+ COMMA + "1" + COMMA + "1" + COMMA + "'U Win Maung" + i + "'" + COMMA + "'1950/02/20'" + COMMA
						+ "'11/AmAhta(N)00407" + i + "'" + COMMA + "'2019/02/22'" + COMMA + "200000" + COMMA + "'DAT'"
						+ COMMA + "'Hlaing Township , Yangon'" + COMMA + "1" + COMMA + "'admin'" + END_PARENTHESES
						+ (i != endLoop ? COMMA : SEMI_COLON));
			}
			seqWriter.close();
			System.out.println("Successfully Created CustomerInfo File.");

		} catch (IOException e) {
			System.out.println("Error" + SPACE + e);
		}

	}

}
