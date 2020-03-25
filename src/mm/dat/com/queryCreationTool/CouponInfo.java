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

public class CouponInfo {

	private static final String INSERT_INTO = "INSERT INTO";
	private static final String VALUES = "VALUES";
	private static final String STRAT_PARENTHESES = "(";
	private static final String END_PARENTHESES = ")";
	private static final String COMMA = ",";
	private static final String SPACE = " ";
	private static final String NEW_LINE = "\n";
	private static final String SEMI_COLON = ";";
	// Coupon Info
	private static final String COUPON_INFO = "VCS.COUPON_INFO";
	private static final String COUPON_CODE = "COUPON_CODE";
	private static final String COUPON_NAME = "COUPON_NAME";
	private static final String DESCRIPTION = "DESCRIPTION";
	private static final String COUPON_AMOUNT = "COUPON_AMOUNT";
	private static final String GOOD_PRICE = "GOODS_PRICE";
	private static final String START_TIME = "START_DATE";
	private static final String EXPIRE_TIME = "EXPIRE_DATE";
	private static final String DISCOUNT_UNIT = "DISCOUNT_UNIT";
	private static final String UNUSE_IMAGE_PATH = "UNUSE_IMAGE_PATH";
	private static final String USE_IMAGE_PATH = "USE_IMAGE_PATH";
	private static final String TOTAL_NO = "TOTAL_NO";
	private static final String COUPON_NO_PER_CUST = "COUPON_NO_PER_CUST";
	private static final String CREATED_BY = "CREATED_BY";

	public static void main(String[] args) {

		try {
			int endLoop = 10000;
			Path path = Paths.get("C:\\Users\\25-00101\\Desktop\\CouponList_" + endLoop);
			if (!Files.exists(path)) {
				Files.createDirectories(path);
			}

			FileWriter seqWriter = new FileWriter(path + "\\CouponInfo_" + endLoop + ".sql");

			seqWriter.write(INSERT_INTO + SPACE + COUPON_INFO + STRAT_PARENTHESES + COUPON_CODE + COMMA + COUPON_NAME
					+ COMMA + DESCRIPTION + COMMA + COUPON_AMOUNT + COMMA + GOOD_PRICE + COMMA + START_TIME + COMMA
					+ EXPIRE_TIME + COMMA + DISCOUNT_UNIT + COMMA + UNUSE_IMAGE_PATH + COMMA + USE_IMAGE_PATH + COMMA
					+ TOTAL_NO + COMMA + COUPON_NO_PER_CUST + COMMA + CREATED_BY + END_PARENTHESES + VALUES);

			for (int i = 1; i <= endLoop; i++) {

				seqWriter.write(NEW_LINE + STRAT_PARENTHESES + "'C00" + i + "'" + COMMA + "'Fried Chicken" + i + "'"
						+ COMMA + "'New Year Event" + i + "'" + COMMA + "100" + COMMA + "1500" + COMMA + "'2019-02-22'"
						+ COMMA + "'2019-03-22'" + COMMA + "'Kyats'" + COMMA
						+ "'C:\\Users\\25-00101\\Desktop\\CouponList_100'" + COMMA
						+ "'C:\\Users\\25-00101\\Desktop\\CouponList_100'" + COMMA + "50" + COMMA + "1" + COMMA + "'admin'" + END_PARENTHESES
						+ (i != endLoop ? COMMA : SEMI_COLON));

			}
			seqWriter.close();
			System.out.println("Successfully Created AppUsageInfo File.");

		} catch (IOException e) {
			System.out.println("Error" + SPACE + e);
		}

	}

}
