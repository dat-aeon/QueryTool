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

public class CustomerSecurityQuestion {

	private static final String INSERT_INTO = "INSERT INTO";
	private static final String VALUES = "VALUES";
	private static final String STRAT_PARENTHESES = "(";
	private static final String END_PARENTHESES = ")";
	private static final String COMMA = ",";
	private static final String SPACE = " ";
	private static final String SEMI_COLON = ";";
	private static final String NEW_LINE = "\n";
	// CustomerSecurityQuestion
	private static final String CUST_SEC_QUESTION = "VCS.CUST_SEC_QUESTION";
	private static final String SEC_QUES_ID = "SEC_QUES_ID";
	private static final String CUSTOMER_ID = "CUSTOMER_ID";
	private static final String ANSWER = "ANSWER";
	private static final String CREATED_BY = "CREATED_BY";

	public static void main(String[] args) {

		try {
			int endLoop = 50;
			Path path = Paths.get("C:\\Users\\25-00101\\Desktop\\CustomerImport_" + endLoop);
			if (!Files.exists(path)) {
				Files.createDirectories(path);
			}

			FileWriter seqWriter = new FileWriter(path + "\\CustomerSecurityQuestion_" + endLoop + ".sql");
		
			seqWriter.write(INSERT_INTO + SPACE + CUST_SEC_QUESTION + STRAT_PARENTHESES + SEC_QUES_ID + COMMA
							+ CUSTOMER_ID + COMMA + ANSWER + COMMA + CREATED_BY + END_PARENTHESES + VALUES); 
			
			for (int i = 1; i <= endLoop; i++) {

				seqWriter.write(NEW_LINE + STRAT_PARENTHESES + i + COMMA + i + COMMA
						+ "'AEON S-Loan Scheme အား လိုအပ္ေသာ စာ�?ြက္စာ�?မ္းမ်ား�?ွင့္ အ�?ူ အီ�?ြန္�?? ေ�?်းေငြေလ�?�ွာက္ထားရာ ေနရာမ်ားမွ �?ဆင့္ ေလ�?�ွာက္ထား�?ိုင္ ပါသည္�?� အေသးစိ�?္ သိရွိလို ပါက ဤ ေနရာ�?ြင္ click �?ွိပ္ပါ�?�"
						+ i + "'" + COMMA + "'admin'" + END_PARENTHESES + (i != endLoop ? COMMA : SEMI_COLON));
			}
			seqWriter.close();
			System.out.println("Successfully Created CustomerSecurityQuestion File.");

		} catch (IOException e) {
			System.out.println("Error" + SPACE + e);
		}

	}

}
