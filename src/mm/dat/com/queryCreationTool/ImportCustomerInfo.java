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

public class ImportCustomerInfo {

	private static final String INSERT_INTO = "INSERT INTO";
	private static final String VALUES = "VALUES";
	private static final String STRAT_PARENTHESES = "(";
	private static final String END_PARENTHESES = ")";
	private static final String COMMA = ",";
	private static final String SPACE = " ";
	private static final String NEW_LINE = "\n";
	private static final String SEMI_COLON = ";";

	// ImportCustomer Info
	private static final String IMPORT_CUSTOMER_INFO = "VCS.IMPORT_CUSTOMER_INFO";
	private static final String CUSTOMER_NO = "CUSTOMER_NO";
	private static final String PHONE_NO = "PHONE_NO";
	private static final String MEMBERCARD_ID = "MEMBERCARD_ID";
	private static final String NAME = "NAME";
	private static final String DOB = "DOB";
	private static final String NRC_NO = "NRC_NO";
	private static final String SALARY = "SALARY";
	private static final String AGE = "AGE";
	private static final String COMPANY_NAME = "COMPANY_NAME";
	private static final String ADDRESS = "ADDRESS";
	private static final String GENDER = "GENDER";
	private static final String STATUS = "STATUS";

	public static void main(String[] args) {

		try {
			int endLoop = 1000;
			Path path = Paths.get("C:\\Users\\25-00101\\Desktop\\ImportCustomer_" + endLoop);

			if (!Files.exists(path)) {
				Files.createDirectories(path);
			}

			FileWriter seqWriter = new FileWriter(path + "\\ImportCustomerInfo_" + endLoop + ".sql");

			seqWriter.write(INSERT_INTO + SPACE + IMPORT_CUSTOMER_INFO + STRAT_PARENTHESES + CUSTOMER_NO + COMMA
					+ PHONE_NO + COMMA + MEMBERCARD_ID + COMMA + NAME + COMMA + DOB + COMMA + NRC_NO + COMMA + SALARY
					+ COMMA + AGE + COMMA + COMPANY_NAME + COMMA + ADDRESS + COMMA + GENDER + COMMA + STATUS
					+ END_PARENTHESES + VALUES);

			for (int i = 1; i <= endLoop; i++) {

				seqWriter.write(NEW_LINE + STRAT_PARENTHESES + "'100" + i + "'" + COMMA + "'0945082162" + i + "'"
						+ COMMA + "'1'" + COMMA + "'U Win Maung" + i + "'" + COMMA + "'1950-02-20'" + COMMA
						+ "'11/AmAhta(N)00407" + i + "'" + COMMA + "200000" + COMMA + "69" + COMMA + "'DAT'" + COMMA
						+ "'Hlaing Township,Yangon'" + COMMA + "1" + COMMA + "1" + END_PARENTHESES
						+ (i != endLoop ? COMMA : SEMI_COLON));

			}
			seqWriter.close();
			System.out.println("Successfully Created ImportCustomerInfo File.");

		} catch (IOException e) {
			System.out.println("Error" + SPACE + e);
		}

	}

}
