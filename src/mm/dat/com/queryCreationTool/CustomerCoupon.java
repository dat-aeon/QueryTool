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

public class CustomerCoupon {

	private static final String INSERT_INTO = "INSERT INTO";
	private static final String VALUES = "VALUES";
	private static final String STRAT_PARENTHESES = "(";
	private static final String END_PARENTHESES = ")";
	private static final String COMMA = ",";
	private static final String SPACE = " ";
	private static final String SEMI_COLON = ";";
	private static final String NEW_LINE = "\n";
	// CustomerCoupon
	private static final String CUSTOMER_COUPON = "VCS.CUSTOMER_COUPON";
	private static final String COUPON_ID = "COUPON_ID";
	private static final String CUSTOMER_ID = "CUSTOMER_ID";
	private static final String STATUS = "STATUS";
	private static final String CREATED_BY = "CREATED_BY";

	public static void main(String[] args) {

		try {
			int endLoop = 100;
			Path path = Paths.get("C:\\Users\\25-00101\\Desktop\\CouponList_" + endLoop);
			if (!Files.exists(path)) {
				Files.createDirectories(path);
			}

			FileWriter seqWriter = new FileWriter(path + "\\CustomerCoupon_" + endLoop + ".sql");

			seqWriter.write(INSERT_INTO + SPACE + CUSTOMER_COUPON + STRAT_PARENTHESES + COUPON_ID + COMMA + CUSTOMER_ID
					+ COMMA + STATUS + COMMA + CREATED_BY + END_PARENTHESES + VALUES);

			for (int i = 1; i <= endLoop; i++) {

				seqWriter.write(NEW_LINE + STRAT_PARENTHESES + i + COMMA + i + COMMA + "'Valid'" + COMMA + "'admin'"
						+ END_PARENTHESES + (i != endLoop ? COMMA : SEMI_COLON));
			}
			seqWriter.close();
			System.out.println("Successfully Created Customer Coupon Info File.");

		} catch (IOException e) {
			System.out.println("Error" + SPACE + e);
		}

	}

}
