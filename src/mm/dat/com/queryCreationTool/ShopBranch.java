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

public class ShopBranch {

	private static final String INSERT_INTO = "INSERT INTO";
	private static final String VALUES = "VALUES";
	private static final String STRAT_PARENTHESES = "(";
	private static final String END_PARENTHESES = ")";
	private static final String COMMA = ",";
	private static final String SPACE = " ";
	private static final String SEMI_COLON = ";";
	private static final String NEW_LINE = "\n";
	// ShopBranch
	private static final String SHOP_BRANCH = "VCS.SHOP_BRANCH";
	private static final String SHOP_ID = "SHOP_ID";
	private static final String BRANCH_NAME = "BRANCH_NAME";
	private static final String BRANCH_CODE = "BRANCH_CODE";
	private static final String ADDRESS = "ADDRESS";
	private static final String CREATED_BY = "CREATED_BY";

	public static void main(String[] args) {

		try {
			int endLoop = 2000;
			Path path = Paths.get("C:\\Users\\25-00101\\Desktop\\CouponList_" + endLoop);
			if (!Files.exists(path)) {
				Files.createDirectories(path);
			}

			FileWriter seqWriter = new FileWriter(path + "\\ShopBranch_" + endLoop + ".sql");
			String seperator = COMMA;
			seqWriter.write(INSERT_INTO + SPACE + SHOP_BRANCH + STRAT_PARENTHESES + SHOP_ID + COMMA + BRANCH_NAME
					+ COMMA + BRANCH_CODE + COMMA + ADDRESS + COMMA + CREATED_BY + END_PARENTHESES + VALUES);
			int branchCode = 1;
			for (int i = 1; i <= endLoop; i++) {
				
				for (int j = 1; j <= 5; j++) {
					
					if (i == endLoop && j == 5) {
						seperator = SEMI_COLON;
					}
					seqWriter.write(NEW_LINE + STRAT_PARENTHESES + i + COMMA + "'MICT Branch" + i + "'" + COMMA + "'BB"
							+ branchCode + "'" + COMMA + "'Hlaing Township,Yangon'" + COMMA + "'admin'" + END_PARENTHESES
							+ seperator);
					branchCode = branchCode + 1;
				}
			}
			seqWriter.close();
			System.out.println("Successfully Created ShopBranch File.");

		} catch (IOException e) {
			System.out.println("Error" + SPACE + e);
		}

	}

}
