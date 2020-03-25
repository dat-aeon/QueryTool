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

public class ShopInfo {

	private static final String INSERT_INTO = "INSERT INTO";
	private static final String VALUES = "VALUES";
	private static final String STRAT_PARENTHESES = "(";
	private static final String END_PARENTHESES = ")";
	private static final String COMMA = ",";
	private static final String SPACE = " ";
	private static final String SEMI_COLON = ";";
	private static final String NEW_LINE = "\n";
	// Shop Info
	private static final String SHOP_INFO = "VCS.SHOP_INFO";
	private static final String SHOP_NAME = "SHOP_NAME";
	private static final String SHOP_CODE = "SHOP_CODE";
	private static final String ADDRESS = "ADDRESS";
	private static final String CREATED_BY = "CREATED_BY";

	public static void main(String[] args) {

		try {
			int endLoop = 100;
			Path path = Paths.get("C:\\Users\\25-00101\\Desktop\\CouponList_" + endLoop);
			if (!Files.exists(path)) {
				Files.createDirectories(path);
			}

			FileWriter seqWriter = new FileWriter(path + "\\ShopInfo_" + endLoop + ".sql");
			seqWriter.write(INSERT_INTO + SPACE + SHOP_INFO + STRAT_PARENTHESES + SHOP_NAME + COMMA + SHOP_CODE + COMMA
					+ ADDRESS + COMMA + CREATED_BY + END_PARENTHESES + VALUES);

			for (int i = 1; i <= endLoop; i++) {

				seqWriter.write(NEW_LINE + STRAT_PARENTHESES + "'KFC" + i + "'" + COMMA + "'S00" + i + "'" + COMMA
						+ "'Hlaing Township,Yangon'" + COMMA + "'admin'" + END_PARENTHESES
						+ (i != endLoop ? COMMA : SEMI_COLON));
			}
			seqWriter.close();
			System.out.println("Successfully Created ShopInfo File.");

		} catch (IOException e) {
			System.out.println("Error" + SPACE + e);
		}

	}

}
