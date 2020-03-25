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

public class CustomerAgreementList {

	private static final String INSERT_INTO = "INSERT INTO";
	private static final String VALUES = "VALUES";
	private static final String STRAT_PARENTHESES = "(";
	private static final String END_PARENTHESES = ")";
	private static final String COMMA = ",";
	private static final String SPACE = " ";
	private static final String NEW_LINE = "\n";
	private static final String SEMI_COLON = ";";
	// CustomerAgreementList
	private static final String CUST_AGREEMENT_LIST = "VCS.CUST_AGREEMENT_LIST";
	private static final String IMPORT_CUSTOMER_ID = "IMPORT_CUSTOMER_ID";
	private static final String AGREEMENT_NO = "AGREEMENT_NO";
	private static final String AGREEMENT_STATUS = "AGREEMENT_STATUS";

	public static void main(String[] args) {

		try {
			int endLoop = 1000;
			Path path = Paths.get("C:\\Users\\25-00101\\Desktop\\ImportCustomer_" + endLoop);
			if (!Files.exists(path)) {
				Files.createDirectories(path);
			}

			FileWriter seqWriter = new FileWriter(path + "\\CustomerAgreementList_" + endLoop + ".sql");

			seqWriter.write(INSERT_INTO + SPACE + CUST_AGREEMENT_LIST + STRAT_PARENTHESES + IMPORT_CUSTOMER_ID + COMMA
					+ AGREEMENT_NO + COMMA + AGREEMENT_STATUS + END_PARENTHESES + VALUES);

			for (int i = 1; i <= endLoop; i++) {

				seqWriter.write(NEW_LINE + STRAT_PARENTHESES + "'100" + i + "'" + COMMA + "'Agree'" + COMMA + i
						+ END_PARENTHESES + (i != endLoop ? COMMA : SEMI_COLON));

			}
			seqWriter.close();
			System.out.println("Successfully Created CustomerAgreementList File.");

		} catch (IOException e) {
			System.out.println("Error" + SPACE + e);
		}

	}

}
