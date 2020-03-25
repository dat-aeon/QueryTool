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

public class ShopCoupon {

	private static final String INSERT_INTO = "INSERT INTO";
	private static final String VALUES = "VALUES";
	private static final String STRAT_PARENTHESES = "(";
	private static final String END_PARENTHESES = ")";
	private static final String COMMA = ",";
	private static final String SPACE = " ";
	private static final String NEW_LINE = "\n";
	private static final String SEMI_COLON = ";";

	// Shop Info
	private static final String SHOP_COUPON = "VCS.SHOP_COUPON";
	private static final String BRANCH_ID = "BRANCH_ID";
	private static final String SHOP_ID = "SHOP_ID";
	private static final String COUPON_PASSWORD = "COUPON_PASSWORD";

	public static void main(String[] args) {

		try {
			int endLoop = 500;
			Path path = Paths.get("C:\\Users\\25-00101\\Desktop\\CouponList_" + endLoop);
			if (!Files.exists(path)) {
				Files.createDirectories(path);
			}

			FileWriter seqWriter = new FileWriter(path + "\\ShopCoupon_" + endLoop + ".sql");
			String seperator = COMMA;

			seqWriter.write(INSERT_INTO + SPACE + SHOP_COUPON + STRAT_PARENTHESES + BRANCH_ID + COMMA + SHOP_ID + COMMA
					+ COUPON_PASSWORD + END_PARENTHESES + VALUES);
			
			for (int i = 1; i <= endLoop; i++) {
				for (int j = 1; j <= 20; j++) {
					if (i == endLoop && j == 20) {
						seperator = SEMI_COLON;
					}

					seqWriter.write(NEW_LINE + STRAT_PARENTHESES + i + COMMA + i + COMMA + "'123'"
							+ END_PARENTHESES + seperator);
				}
			}
			seqWriter.close();

			System.out.println("Successfully Created ShopCoupon File.");

		} catch (IOException e) {
			System.out.println("Error" + SPACE + e);
		}

	}

}
