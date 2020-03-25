/**************************************************************************
 * $Date : 2019/02/21$
 * $Author : Aung Ko Lin$
 * $Rev : $
 *************************************************************************/
package mm.dat.com.queryCreationTool;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Random;
import java.util.stream.Collectors;

public class MainApp {

	private static final String SCHEMA_NAME = "public";

	private static int numberOfRecords = 50;
	private static int recordsPerEachFile = 20;
	private static int startCustomerNo = 1;
	private static boolean forDirectInsert = true;

	private static LinkedList<String> tCustomerValueList = new LinkedList<>();

	// JDBC driver name and database URL
	// static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:postgresql://10.1.9.69:5432/vcs_export";
	// Database credentials
	static final String USER = "vcs";
	static final String PASS = "vcs";

	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		try {

			MainApp app = new MainApp();

			int agCount = 0;
			int imageCount = 0;
			int totalImageCount = 0;
			int fileNo = 1;
			String postFix = ((numberOfRecords > recordsPerEachFile) ? "(1)" : "") + ".sql";

			Path path = null;
			FileWriter customerWriter = null;
			FileWriter agreementWriter = null;
			FileWriter payStageWriter = null;
			FileWriter expressCardWriter = null;
			FileWriter imageWriter = null;
			FileWriter judgementWriter = null;
			FileWriter applicationWriter = null;
			FileWriter tPaymentWriter = null;

			if (forDirectInsert) {

				conn = DriverManager.getConnection(DB_URL, USER, PASS);
				conn.setAutoCommit(false);
				stmt = conn.createStatement();

			} else {

				path = Paths.get("C:\\Users\\25-00141\\Desktop\\QueryPrepare\\Rows_" + numberOfRecords);
				if (!Files.exists(path)) {
					Files.createDirectories(path);
				}
				customerWriter = null;
				agreementWriter = null;
				payStageWriter = null;
				expressCardWriter = null;
				imageWriter = null;
				judgementWriter = null;
				applicationWriter = null;
				tPaymentWriter = null;
				customerWriter = new FileWriter(path + "\\MCustomer_" + numberOfRecords + postFix);
				agreementWriter = new FileWriter(path + "\\TAgreementCard_" + numberOfRecords + postFix);
				payStageWriter = new FileWriter(path + "\\TPayStage_" + numberOfRecords + postFix);
				expressCardWriter = new FileWriter(path + "\\MExpressCard_" + numberOfRecords + postFix);
				imageWriter = new FileWriter(path + "\\TImage_" + numberOfRecords + postFix);
				judgementWriter = new FileWriter(path + "\\TJudgement_" + numberOfRecords + postFix);
				applicationWriter = new FileWriter(path + "\\TApplication_" + numberOfRecords + postFix);
				tPaymentWriter = new FileWriter(path + "\\TPayment_" + numberOfRecords + postFix);

			}

			for (int rowNum = startCustomerNo; rowNum <= numberOfRecords + startCustomerNo - 1; rowNum++) {

				System.out.print("Current Record : " + rowNum);
				if (!forDirectInsert) {
					if (numberOfRecords > recordsPerEachFile) {
						if ((rowNum - startCustomerNo) % recordsPerEachFile == 0) {
							if (fileNo > 1) {

								customerWriter.close();
								agreementWriter.close();
								payStageWriter.close();
								expressCardWriter.close();
								imageWriter.close();
								judgementWriter.close();
								applicationWriter.close();
								tPaymentWriter.close();

								postFix = "(" + fileNo + ").sql";

								customerWriter = new FileWriter(path + "\\MCustomer_" + numberOfRecords + postFix);
								agreementWriter = new FileWriter(path + "\\TAgreementCard_" + numberOfRecords + postFix);
								payStageWriter = new FileWriter(path + "\\TPayStage_" + numberOfRecords + postFix);
								expressCardWriter = new FileWriter(path + "\\MExpressCard_" + numberOfRecords + postFix);
								imageWriter = new FileWriter(path + "\\TImage_" + numberOfRecords + postFix);
								judgementWriter = new FileWriter(path + "\\TJudgement_" + numberOfRecords + postFix);
								applicationWriter = new FileWriter(path + "\\TApplication_" + numberOfRecords + postFix);
								tPaymentWriter = new FileWriter(path + "\\TPayment_" + numberOfRecords + postFix);
							}

							fileNo += 1;
						}
					}
				}

				String customerInsertQuery = QueryConstant.getMCustomerInsertIntoValuesString(SCHEMA_NAME) + app.getMCustomerValues(rowNum);

				String tCustomerInsertQuery = QueryConstant.getTCustomerInsertIntoValuesString(SCHEMA_NAME) + "(" + tCustomerValueList.stream().collect(Collectors.joining(","))
						+ ");\r\n";

				String expressCardInsertQuery = QueryConstant.getMExpressCardInsertIntoValuesString(SCHEMA_NAME) + app.getExpressCardValues(rowNum);

				String tPaymentInsertQuery = QueryConstant.getTPaymentInsertIntoValuesString(SCHEMA_NAME) + app.getTPaymentValues(rowNum);

				if (forDirectInsert) {
					stmt.executeUpdate(customerInsertQuery);
					stmt.executeUpdate(tCustomerInsertQuery);
					stmt.execute(expressCardInsertQuery);
					stmt.execute(tPaymentInsertQuery);

				} else {
					customerWriter.write(customerInsertQuery);

					expressCardWriter.write(expressCardInsertQuery);

					tPaymentWriter.write(tPaymentInsertQuery);

				}

				agCount = Integer.parseInt(digitStrBtw(0, 9));

				System.out.print("\t Aggrement: " + agCount);

				for (int count = 0; count < agCount; count++) {
					String agreementInsertQuery = QueryConstant.getTAgreementInsertIntoValuesString(SCHEMA_NAME) + app.getTAgreementValues(rowNum, count);
					String agreePaysInsertQuery = QueryConstant.getTPayStagesAgreementInsertIntoValuesString(SCHEMA_NAME) + app.getTPayStagesAgreementValue(rowNum, count);
					String JudgementInsertQuery = QueryConstant.getTJudgementInsertIntoValuesString(SCHEMA_NAME) + app.getJudgementValues(rowNum, count);
					String ApplicationInsertQuery = QueryConstant.getTApplicationInsertIntoValuesString(SCHEMA_NAME) + app.getApplicationValues(rowNum, count);

					if (forDirectInsert) {
						stmt.executeUpdate(agreementInsertQuery);
						stmt.execute(agreePaysInsertQuery);
						stmt.execute(ApplicationInsertQuery);
						stmt.execute(JudgementInsertQuery);

					} else {
						agreementWriter.write(agreementInsertQuery);
						payStageWriter.write(agreePaysInsertQuery);
						judgementWriter.write(JudgementInsertQuery);
						applicationWriter.write(ApplicationInsertQuery);
					}

					imageCount = Integer.parseInt(digitStrBtw(3, 9));

					totalImageCount += imageCount;

					for (int i = 0; i < imageCount; i++) {
						String imageInsertQuery = QueryConstant.getTImageInsertIntoValuesString(SCHEMA_NAME) + app.getImageValues(rowNum, count, i);

						if (forDirectInsert) {
							stmt.execute(imageInsertQuery);
						} else {
							imageWriter.write(imageInsertQuery);
						}

					}

				}

				if (agCount == 0) {
					System.out.print("\t Application & Judgement Count: " + 1);
					String JudgementInsertQuery = QueryConstant.getTJudgementInsertIntoValuesString(SCHEMA_NAME) + app.getJudgementValues(rowNum, 1);
					String ApplicationInsertQuery = QueryConstant.getTApplicationInsertIntoValuesString(SCHEMA_NAME) + app.getApplicationValues(rowNum, 1);

					if (forDirectInsert) {
						stmt.execute(ApplicationInsertQuery);
						stmt.execute(JudgementInsertQuery);

					} else {
						judgementWriter.write(JudgementInsertQuery);
						applicationWriter.write(ApplicationInsertQuery);
					}

					imageCount = Integer.parseInt(digitStrBtw(3, 9));

					totalImageCount += imageCount;

					for (int i = 0; i < imageCount; i++) {
						String imageInsertQuery = QueryConstant.getTImageInsertIntoValuesString(SCHEMA_NAME) + app.getImageValues(rowNum, 1, i);

						if (forDirectInsert) {
							stmt.execute(imageInsertQuery);
						} else {
							imageWriter.write(imageInsertQuery);
						}

					}
				} else {
					System.out.print("\t Application & Judgement Count: " + agCount);
				}

				System.out.println("\t Image Count : " + totalImageCount);
				totalImageCount = 0;
				if (forDirectInsert) {
					conn.commit();
				}

			}

			if (!forDirectInsert) {
				customerWriter.close();
				agreementWriter.close();
				payStageWriter.close();
				expressCardWriter.close();
				imageWriter.close();
				judgementWriter.close();
				applicationWriter.close();
				tPaymentWriter.close();
			}

			System.out.println("Files Successfully Created.");

		} catch (IOException | SQLException e) {
			System.err.println(e);
		} finally {
			if (forDirectInsert) {
				try {
					if (stmt != null)
						conn.close();
				} catch (SQLException se) {
				} // do nothing
				try {
					if (conn != null)
						conn.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}

	}

	private String quote(Object obj) {
		return "'" + obj.toString() + "'";
	}

	private String getDataCd(int rowNum, int count) {

		return rowNum + "-" + count;
	}

	private String getJudgementCd(int rowNum, int count) {
		BigDecimal number = new BigDecimal("10000");
		number = number.add(new BigDecimal(rowNum * 10));
		// return
		// String.valueOf(number).replaceFirst("(\\d{3})(\\d{1})(\\d{8})(\\d)",
		// "$1-$2-$3-$4");
		return String.valueOf(number).replaceFirst("(\\d{2})(\\d{3})(\\d{1})", "$1-$2-$3");
	}

	private String getImageCd(int rowNum, int count, int i) {
		BigDecimal number = new BigDecimal("10");
		number = number.add(new BigDecimal(rowNum * 10));
		number = number.add(new BigDecimal(count));
		number = number.add(new BigDecimal(i * 10));

		if (i == 0)
			i += createRandomIntBetween(1, 100) + createRandomIntBetween(5, 10) + createRandomIntBetween(1, 5);

		// create instance of Random class
		Random rand = new Random();

		// Generate random integers in range 0 to 999
		int rand_int1 = rand.nextInt(100);
		int rand_int2 = rand.nextInt(50);
		int rand_int3 = rand.nextInt(100);

		return rowNum + "-" + i + "-" + count + "-" + number + rand_int1 + rand_int2 + rand_int3;
	}

	private String getAppFormNo(int rowNum, int count) {
		BigDecimal number = new BigDecimal("10");
		number = number.add(new BigDecimal(rowNum * 10));
		number = number.add(new BigDecimal(count));

		return "A-" + rowNum + "-" + count;
	}

	private String getCustomerCd(int rowNum) {
		BigDecimal number = new BigDecimal("10000000000000");
		number = number.add(new BigDecimal(rowNum * 1000));
		// return
		// String.valueOf(number).replaceFirst("(\\d{3})(\\d{1})(\\d{8})(\\d)",
		// "$1-$2-$3-$4");
		return String.valueOf(number).replaceFirst("(\\d{4})(\\d{9})(\\d{1})", "$1-$2-$3");
	}

	private String getAgreementCd(int rowNum, int count) {
		BigDecimal number = new BigDecimal("1000000000000000");
		number = number.add(new BigDecimal(rowNum * 1000));
		number = number.add(new BigDecimal(count));
		// return
		// String.valueOf(number).replaceFirst("(\\d{4})(\\d{7})(\\d{3})",
		// "$1-$2-$3");
		return String.valueOf(number).replaceFirst("(\\d{4})(\\d{1})(\\d{10})(\\d{1})", "$1-$2-$3-$4");
	}

	private String getAgreementNumber(int rowNum, int count) {

		return rowNum + "-1200-" + count;
	}

	private String getSysAppCode(int rowNum, int count) {
		BigDecimal number = new BigDecimal("10000");
		number = number.add(new BigDecimal(rowNum * 10));
		number = number.add(new BigDecimal(count));
		// return
		// String.valueOf(number).replaceFirst("(\\d{4})(\\d{7})(\\d{3})",
		// "$1-$2-$3");
		return String.valueOf(number).replaceFirst("(\\d{1})(\\d{3})(\\d{1})", "$1-$2-$3");
	}

	private String getPaymentCode(int rowNum) {
		BigDecimal number = new BigDecimal("10000");
		number = number.add(new BigDecimal(rowNum * 10));
		// return
		// String.valueOf(number).replaceFirst("(\\d{4})(\\d{7})(\\d{3})",
		// "$1-$2-$3");
		return String.valueOf(number).replaceFirst("(\\d{1})(\\d{3})(\\d{1})", "$1-$2-$3");
	}

	private String getMobileNo(int rowNum) {
		return "09" + String.format("%09d", rowNum);
	}

	private String getGenderString() {
		return String.valueOf(createRandomIntBetween(1, 2));
	}

	private static int createRandomIntBetween(int start, int end) {
		return start + (int) Math.round(Math.random() * (end - start));
	}

	private static String digitStrUpTo(int length) {

		String start = "1";
		String end = "";
		for (int i = 1; i <= length; i++) {
			if (i < length) {
				start += "0";
			}
			end += "9";
		}

		return digitStrBtw(Integer.parseInt(start), Integer.parseInt(end));
	}

	private static String digitStrBtw(int start, int end) {
		return String.valueOf(start + (int) Math.round(Math.random() * (end - start)));
	}

	private static String getDate() {
		int day = createRandomIntBetween(1, 28);
		int month = createRandomIntBetween(1, 12);
		int year = createRandomIntBetween(1900, 2019);

		return year + String.format("%02d", month) + String.format("%02d", day);
	}

	private static String getTime() {
		int hh = createRandomIntBetween(0, 23);
		int mm = createRandomIntBetween(0, 59);
		int ss = createRandomIntBetween(0, 59);

		return String.format("%02d", hh) + String.format("%02d", mm) + String.format("%02d", ss);
	}

	private String getTAgreementValues(int rowNum, int count) {

		LinkedList<String> valueList = new LinkedList<>();

		// agreementcd character varying(20),
		valueList.add(quote(getAgreementCd(rowNum, count)));

		// agreementtype numeric(19,0),
		valueList.add(digitStrUpTo(9));

		// sysappcd character varying(12)
		valueList.add(quote(getSysAppCode(rowNum, count)));

		// agreementdate character varying(10)
		valueList.add(quote(getDate()));

		// agreementupdate character varying(10)
		valueList.add(quote("AUP-" + rowNum));

		// agreementreceiveddate character varying(10)
		valueList.add(quote(getDate()));

		// saledate character varying(10)
		valueList.add(quote(getDate()));

		// agreementstatus numeric(5,0),
		valueList.add(digitStrBtw(1, 7));

		// chargeflag numeric(19,0),
		valueList.add(digitStrUpTo(1));

		// customercd character varying(16)
		valueList.add(quote(getCustomerCd(rowNum)));

		// peopleflag numeric(19,0),
		valueList.add(digitStrUpTo(9));

		// peoplecd character varying(16)
		valueList.add(quote("peoplecd-" + rowNum));

		// relation1 numeric(10,0),
		valueList.add(digitStrUpTo(1));

		// relation2 character varying(20)
		valueList.add(quote("relation2-" + rowNum));

		// agentcd character varying(12)
		valueList.add(quote("agt-" + rowNum));

		// agentname character varying(50)
		valueList.add(quote("relation2-" + rowNum));

		// storeteldistrict character varying(6)
		valueList.add(quote(rowNum));

		// storetel character varying(10)
		valueList.add(quote(digitStrUpTo(9)));

		// storepic character varying(50)
		valueList.add(quote("storepic-" + rowNum));

		// storecd character varying(18)
		valueList.add(quote("storecd-" + rowNum));

		// storename character varying(50)
		valueList.add(quote("storecd-" + rowNum));

		// campaigncd character varying(12)
		valueList.add(quote("CPCD-" + rowNum));

		// productuseaddress1 numeric(10,0),
		valueList.add(String.valueOf(rowNum));

		// productuseaddress2 character varying(200)
		valueList.add(quote("productuseaddress2-" + rowNum));

		// addressee character varying(50)
		valueList.add(quote("productuseaddress2-" + rowNum));

		// deliveryaddress character varying(200)
		valueList.add(quote("productuseaddress2-" + rowNum));

		// contactteldistrict character varying(6)
		valueList.add(quote(rowNum));

		// contacttel character varying(10)
		valueList.add(quote("ct-" + rowNum));

		// contactzipcode character varying(10)
		valueList.add(quote("czc-" + rowNum));

		// memo character varying(100)
		valueList.add(quote("memo-" + rowNum));

		// bankcd numeric(10,0),
		valueList.add(digitStrUpTo(9));

		// account character varying(25)
		valueList.add(quote("account-" + rowNum));

		// accountowner character varying(20)
		valueList.add(quote("accountowner-" + rowNum));

		// agentappcd character varying(16)
		valueList.add(quote("appcd-" + rowNum));

		// merchantstamp character varying(50)
		valueList.add(quote("merchantstamp-" + rowNum));

		// aeonstaffdatereceived character varying(10)
		valueList.add(quote("ASD-" + rowNum));

		// comments character varying(100)
		valueList.add(quote("comments-" + rowNum));

		// stampflag numeric(19,0),
		valueList.add(digitStrUpTo(9));

		// delflag numeric(19,0),
		valueList.add(digitStrBtw(0, 1));

		// creperson character varying(10)
		valueList.add(quote("crp-" + rowNum));

		// credate character varying(10)
		valueList.add(quote(getDate()));

		// cretime character varying(8)
		valueList.add(quote(getTime()));

		// creproid character varying(9)
		valueList.add(quote("cp-" + rowNum));

		// updperson character varying(10)
		valueList.add(quote("up-" + rowNum));

		// upddate character varying(10)
		valueList.add(quote(getDate()));

		// updtime character varying(8)
		valueList.add(quote(getTime()));

		// updproid character varying(9)
		valueList.add(quote("up-" + rowNum));

		// updcnt numeric(19,0),
		valueList.add(digitStrUpTo(5));

		// agreementcanceldate character varying(10)
		valueList.add(quote(getDate()));

		// merchantstaff character varying(50)
		valueList.add(quote("merchantstaff-" + rowNum));

		// repostatus numeric(5,0),
		valueList.add(digitStrUpTo(5));

		// autopay_agencycd numeric(5,0),
		valueList.add(digitStrUpTo(5));

		// autopay_accnt_name character varying(50)
		valueList.add(quote("autopay_accnt_name-" + rowNum));

		// autopay_accnt_no character varying(50)
		valueList.add(quote("autopay_accnt_no-" + rowNum));

		// autopay_bankcd numeric(10,0),
		valueList.add(digitStrUpTo(9));

		// autopay_bank_br_code numeric(5,0),
		valueList.add(digitStrUpTo(5));

		// disburse_accnt_name character varying(50)
		valueList.add(quote("disburse_accnt_name-" + rowNum));

		// disburse_accnt_no character varying(50)
		valueList.add(quote("disburse_accnt_no-" + rowNum));

		// disburse_bankcd numeric(10,0),
		valueList.add(digitStrUpTo(9));

		// disburse_bank_br_code numeric(5,0),
		valueList.add(digitStrUpTo(5));

		// app_cancel_date character varying(10)
		valueList.add(quote(getDate()));

		// autopay_accnt_type numeric(19,0),
		valueList.add(digitStrUpTo(9));

		// agg_cancel_reason numeric(5,0),
		valueList.add(digitStrUpTo(5));

		// return_prod_price numeric(19,0),
		valueList.add(digitStrUpTo(9));

		// cancel_fee_rate numeric(19,0),
		valueList.add(digitStrUpTo(9));

		// ebill numeric(5,0),
		valueList.add(digitStrUpTo(5));

		// interest_start_date character varying(10)
		valueList.add(quote(getDate()));

		// interest_start_flag numeric(19,0)
		valueList.add(digitStrUpTo(9));

		// mailto numeric(5,0)
		valueList.add(digitStrUpTo(5));

		// psakflag numeric(19,0)
		valueList.add(digitStrUpTo(9));

		// settlementstatus character varying(2)
		valueList.add(quote(digitStrUpTo(2)));

		// canceltype numeric(19,0),
		valueList.add(digitStrUpTo(9));

		// prev_agstatus numeric(5,0),
		valueList.add(digitStrUpTo(5));

		// baddebtstat character varying(3)
		valueList.add(quote(digitStrUpTo(3)));

		// repocriteria character varying(2)
		valueList.add(quote(digitStrUpTo(2)));

		// writeoffflag numeric(19,0),
		valueList.add(digitStrUpTo(9));

		// writeoffdate character varying(10)
		valueList.add(quote(getDate()));

		// pointflag numeric(19,0),
		valueList.add(digitStrUpTo(9));

		// disbursementstatus character varying(2)
		valueList.add(quote(digitStrUpTo(2)));

		// reasoncode numeric(2,0),
		valueList.add(digitStrUpTo(2));

		// agreementdelinquencyreason character varying(100)
		valueList.add(quote("agreementdelinquencyreason-" + rowNum));

		return "(" + valueList.stream().collect(Collectors.joining(",")) + ");\r\n";
	}

	private String getTPayStagesAgreementValue(int rowNum, int count) {

		LinkedList<String> valueList = new LinkedList<>();

		// agreementcd character varying(20)
		valueList.add(quote(getAgreementCd(rowNum, count)));

		// agreementtype integer
		valueList.add(digitStrUpTo(9));

		// producttotalmoney numeric(19,0),
		valueList.add(digitStrUpTo(9));

		// approvalfee numeric(19,0),
		valueList.add(digitStrUpTo(9));

		// approvalfeeifany numeric(19,0),
		valueList.add(digitStrUpTo(9));

		// approvalfeetype integer,
		valueList.add(digitStrUpTo(1));

		// downpayment numeric(19,0),
		valueList.add(digitStrUpTo(9));

		// cashback numeric(19,0),
		valueList.add(digitStrUpTo(9));

		// financeprice numeric(19,0),
		valueList.add(String.valueOf(rowNum + 10000));

		// commissionrate numeric(10,0),
		valueList.add(digitStrUpTo(9));

		// numberofinstalment numeric(5,0),
		valueList.add(digitStrUpTo(1));

		// interestamount numeric(19,0),
		valueList.add(digitStrUpTo(9));

		// hpprice numeric(19,0),
		valueList.add(digitStrUpTo(9));

		// monthlypayment numeric(19,0),
		valueList.add(digitStrUpTo(9));

		// initialpayment numeric(19,0),
		valueList.add(digitStrUpTo(9));

		// periodpayment numeric(19,0),
		valueList.add(digitStrUpTo(9));

		// period numeric(5,0),
		valueList.add(digitStrUpTo(5));

		// hpmonthmoney numeric(19,0),
		valueList.add(digitStrUpTo(9));

		// hpsecondday numeric(19,0),
		valueList.add(digitStrUpTo(9));

		// paymentdate character varying(10)
		valueList.add(digitStrUpTo(9));

		// hplastday bigint,
		valueList.add(digitStrUpTo(9));

		// adjustmoney numeric(19,0),
		valueList.add(digitStrUpTo(9));

		// paytype integer,
		valueList.add(digitStrUpTo(9));

		// nameoncard character varying(50)
		valueList.add(quote("nameoncard-" + rowNum));

		// accountbanktype integer,
		valueList.add(digitStrUpTo(9));

		// bankname character varying(50)
		valueList.add(quote("bankname-" + rowNum));

		// branchname character varying(50)
		valueList.add(quote("branchname-" + rowNum));

		// accountownedtype integer,
		valueList.add(digitStrUpTo(9));

		// accountowned character varying(50)
		valueList.add(quote("accountowned-" + rowNum));

		// accountno character varying(50)
		valueList.add(quote("accountno-" + rowNum));

		// campaigntype integer,
		valueList.add(digitStrUpTo(9));

		// instalmenttype integer,
		valueList.add(digitStrUpTo(9));

		// hpfirstday numeric(19,0),
		valueList.add(digitStrUpTo(9));

		// paymenttype integer,
		valueList.add(digitStrUpTo(9));

		// promotionvoucher numeric(19,0),
		valueList.add(digitStrUpTo(9));

		// application_fee numeric(19,0),
		valueList.add(digitStrUpTo(9));

		// application_fee_rate numeric(5,0),
		valueList.add(digitStrUpTo(5));

		// admin_fee numeric(19,0),
		valueList.add(digitStrUpTo(9));

		// admin_fee_rate numeric(5,0),
		valueList.add(digitStrUpTo(5));

		// interest_rate numeric(5,0),
		valueList.add(digitStrUpTo(5));

		// promotion_code character varying(10)
		valueList.add(quote("PC-" + rowNum));

		// firstpaydate character varying(10)
		valueList.add(quote(getDate()));

		// lastpayment numeric(19,0),
		valueList.add(digitStrUpTo(9));

		// premiumamount numeric(19,0),
		valueList.add(digitStrUpTo(9));

		// annualeffectiverate numeric(19,0),
		valueList.add(digitStrUpTo(9));

		// delflag integer,
		valueList.add(digitStrUpTo(9));

		// creperson character varying(10)
		valueList.add(quote("CP-" + rowNum));

		// credate character varying(10)
		valueList.add(quote(getDate()));

		// cretime character varying(8)
		valueList.add(quote(getTime()));

		// creproid character varying(9)
		valueList.add(quote("CO-" + rowNum));

		// updperson character varying(10)
		valueList.add(quote("UP-" + rowNum));

		// upddate character varying(10)
		valueList.add(quote(getDate()));

		// updtime character varying(8)
		valueList.add(quote(getTime()));

		// updproid character varying(9)
		valueList.add(quote("UO-" + rowNum));

		// updcnt numeric(19,0),
		valueList.add(digitStrUpTo(9));

		// docstampamount numeric(19,0),
		valueList.add(digitStrUpTo(9));

		// paymenttypenew numeric(5,0),
		valueList.add(digitStrUpTo(5));

		// vatamount numeric(19,2),
		valueList.add(digitStrUpTo(9));

		return "(" + valueList.stream().collect(Collectors.joining(",")) + ");\r\n";
	}

	private String getExpressCardValues(int rowNum) {

		LinkedList<String> valueList = new LinkedList<>();

		// expresscardno character varying(20)
		valueList.add(quote(rowNum));

		// customercd character varying(16)
		valueList.add(quote(getCustomerCd(rowNum)));

		// expiredate character varying(10)
		valueList.add(quote(getDate()));

		// expresscardstatus numeric(5,0)
		valueList.add(digitStrBtw(0, 1));

		// expresscardreason numeric(5,0)
		valueList.add(digitStrUpTo(5));

		// contactcomment character varying(200)
		valueList.add(quote("contactcomment-" + rowNum));

		// expresscardtype numeric(19,0)
		valueList.add(digitStrUpTo(9));

		// creditlimit numeric(19,0)
		valueList.add(digitStrUpTo(9));

		// creperson character varying(10)
		valueList.add(quote("CP-" + rowNum));

		// credate character varying(10)
		valueList.add(quote(getDate()));

		// cretime character varying(8)
		valueList.add(quote(getTime()));

		// creproid character varying(9)
		valueList.add(quote("CO-" + rowNum));

		// updperson character varying(10)
		valueList.add(quote("UP-" + rowNum));

		// upddate character varying(10)
		valueList.add(quote(getDate()));

		// updtime character varying(8)
		valueList.add(quote(getTime()));

		// updproid character varying(9)
		valueList.add(quote("UO-" + rowNum));

		// updcnt numeric(19,0),
		valueList.add(digitStrUpTo(9));

		// membercardreasonothers character varying(20)
		valueList.add(quote("MCRO-" + rowNum));

		// reissueamount numeric(19,0)
		valueList.add(digitStrUpTo(9));

		// reissueflag numeric(19,0)
		valueList.add(digitStrUpTo(9));

		// embossdate character varying(8)
		valueList.add(quote(getDate()));

		return "(" + valueList.stream().collect(Collectors.joining(",")) + ");\r\n";
	}

	private String getMCustomerValues(int rowNum) {

		LinkedList<String> valueList = new LinkedList<>();
		tCustomerValueList = new LinkedList<>();
		String temp = "";

		// customercd character varying(16) COLLATE pg_catalog."default" NOT
		// NULL,
		valueList.add(quote(getCustomerCd(rowNum)));

		// applicationtype numeric(19,0),
		valueList.add(digitStrBtw(1, 5));

		// areacd character varying(12) COLLATE pg_catalog."default",
		valueList.add(quote(createRandomIntBetween(1, 100)));

		// sysappcd character varying(12) for t_customer
		tCustomerValueList.add(quote(createRandomIntBetween(1, 100)));

		// idcardtype numeric(19,0),
		valueList.add("1");

		// infotype numeric(19,0) for t_customer
		tCustomerValueList.add("1");

		// idcardno character varying(30) COLLATE pg_catalog."default",
		// idcardoldno character varying(30) COLLATE pg_catalog."default",
		temp = quote(NRCEnum.getNRCCode(rowNum));
		valueList.add(temp);
		valueList.add(temp);

		// idcardno character varying(30) for t_customer
		tCustomerValueList.add(temp);

		// name character varying(200) COLLATE pg_catalog."default",
		valueList.add(quote("CustomerName-" + rowNum));

		// chinesename character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("CustomerName-CN-" + rowNum));

		// title numeric(19,0),
		valueList.add(String.valueOf(rowNum));

		// othertitle character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("OtherTitle-" + rowNum));

		// nickname character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("NickName-" + rowNum));

		// sex numeric(19,0),
		valueList.add(getGenderString());

		// birthday numeric(19,0),
		valueList.add(getDate());

		// marrystatus numeric(5,0),
		valueList.add(digitStrBtw(1, 5));

		// race numeric(19,0),
		valueList.add(digitStrBtw(1, 10));

		// maidenname character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("Maiden Name-" + rowNum));

		// city character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("City-" + rowNum));

		// rt character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("RT-" + rowNum));

		// rw character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("RW-" + rowNum));

		// customertype numeric(19,0),
		valueList.add(digitStrBtw(1, 5));

		// creditcardno character varying(25) COLLATE pg_catalog."default",
		valueList.add(quote("Credit Card No-" + rowNum));

		// cardhold numeric(19,0),
		valueList.add(digitStrBtw(1, 5));

		// cardtype numeric(19,0),
		valueList.add(digitStrBtw(1, 5));

		// cardtypecontent character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("Card Type Content-" + rowNum));

		// creditcardno1 character varying(16) COLLATE pg_catalog."default",
		valueList.add(quote("CNo1- " + rowNum));

		// creditcardno2 character varying(16) COLLATE pg_catalog."default",
		valueList.add(quote("CNo2- " + rowNum));

		// issurename1 character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("Issure Name One-" + rowNum));

		// issurename2 character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("Issure Name Two-" + rowNum));

		// validuntilyear1 numeric(5,0),
		valueList.add(digitStrBtw(1000, 9999));

		// validuntilmonth1 numeric(5,0),
		valueList.add(digitStrBtw(1, 12));

		// validuntilyear2 numeric(5,0),
		valueList.add(digitStrBtw(1000, 9999));

		// validuntilmonth2 numeric(5,0),
		valueList.add(digitStrBtw(1, 12));

		// creditcardlimit numeric(19,0),
		valueList.add(digitStrBtw(1000000, 99999999));

		// carno character varying(20) COLLATE pg_catalog."default",
		valueList.add(quote("Car No-" + rowNum));

		// carmonthmoney numeric(19,0),
		valueList.add(digitStrBtw(1000000, 99999999));

		// graduateschool character varying(40) COLLATE pg_catalog."default",
		valueList.add(quote("Graduate School-" + rowNum));

		// educationlevel numeric(19,0),
		valueList.add(digitStrBtw(1, 15));

		// nowzipcode character varying(10) COLLATE pg_catalog."default",
		valueList.add(quote(rowNum));

		// nowaddress character varying(200) COLLATE pg_catalog."default",
		valueList.add(quote("Now Address-" + rowNum));

		// hometeldistrict character varying(6) COLLATE pg_catalog."default",
		valueList.add(quote("HTD-" + digitStrBtw(1, 99)));

		// hometel character varying(10) COLLATE pg_catalog."default",
		valueList.add(quote("HT-" + rowNum));

		// telowner character varying(20) COLLATE pg_catalog."default",
		valueList.add(quote("Tel Owner-" + rowNum));

		// telownerrelate character varying(20) COLLATE pg_catalog."default",
		valueList.add(quote("Tel Owner R- " + rowNum));

		// liveyear numeric(5,0),
		valueList.add(digitStrBtw(1, 60));

		// livemonth numeric(5,0),
		valueList.add(digitStrBtw(1, 12));

		// mobileno character varying(16) COLLATE pg_catalog."default",
		valueList.add(quote(getMobileNo(rowNum)));

		// nowhousetype numeric(19,0),
		valueList.add(digitStrBtw(1, 10));

		// nowhouseowner character varying(20) COLLATE pg_catalog."default",
		valueList.add(quote("NowHouseOwner-" + rowNum));

		// nowhouseownerrelatetype numeric(19,0),
		valueList.add(digitStrBtw(1, 10));

		// nowhouseownerrelate character varying(20) COLLATE
		// pg_catalog."default",
		valueList.add(quote("NowHouseR-" + rowNum));

		// nowhousemoney numeric(19,0),
		valueList.add(digitStrBtw(1000000, 999999999));

		// liveperson numeric(5,0),
		valueList.add(digitStrBtw(1, 100));

		// livepersonnum numeric(5,0),
		valueList.add(digitStrBtw(1, 100));

		// homezipcode character varying(10) COLLATE pg_catalog."default",
		valueList.add(quote("HZ" + rowNum));

		// hukouaddress character varying(200) COLLATE pg_catalog."default",
		valueList.add(quote("hukouaddress" + rowNum));

		// hukouteldistrict character varying(6) COLLATE pg_catalog."default",
		valueList.add(quote(digitStrBtw(1, 999999)));

		// hukoutel character varying(16) COLLATE pg_catalog."default",
		valueList.add(quote("hukoutel" + rowNum));

		// email character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("email" + rowNum));

		// corpname character varying(60) COLLATE pg_catalog."default",
		valueList.add(quote("Company Name-" + rowNum));

		// employeenum numeric(19,0),
		valueList.add(digitStrBtw(1, 100));

		// corpzipcode character varying(10) COLLATE pg_catalog."default",
		valueList.add(quote("CZ" + rowNum));

		// corpaddress character varying(200) COLLATE pg_catalog."default",
		valueList.add(quote("Company Address-" + rowNum));

		// corpcity character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("Company City-" + rowNum));

		// occupation character varying(20) COLLATE pg_catalog."default",
		valueList.add(quote("OCUP-" + rowNum));

		// corpteldistrict character varying(6) COLLATE pg_catalog."default",
		valueList.add(quote(digitStrBtw(1, 999999)));

		// corptel character varying(10) COLLATE pg_catalog."default",
		valueList.add(quote("CT" + rowNum));

		// corpsubtel character varying(6) COLLATE pg_catalog."default",
		valueList.add(quote(rowNum));

		// corpregteldistrict character varying(12) COLLATE
		// pg_catalog."default",
		valueList.add(quote("CTD" + rowNum));

		// corpregtel character varying(12) COLLATE pg_catalog."default",
		valueList.add(quote("CRT" + rowNum));

		// corptype numeric(19,0),
		valueList.add(quote(digitStrBtw(1, 20)));

		// corpway numeric(5,0),
		valueList.add(quote(digitStrBtw(1, 100)));

		// department character varying(20) COLLATE pg_catalog."default",
		valueList.add(quote("Department-" + rowNum));

		// "position" character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("Position-" + rowNum));

		// workyear numeric(10,0),
		valueList.add(digitStrBtw(1, 100));

		// workmonth numeric(10,0),
		valueList.add(digitStrBtw(1, 100));

		// restday character varying(10) COLLATE pg_catalog."default",
		valueList.add(quote("RD-" + rowNum));

		// salary numeric(19,0),
		valueList.add(digitStrBtw(1000000, 99999999));

		// securitycardno character varying(20) COLLATE pg_catalog."default",
		valueList.add(quote("SCN-" + rowNum));

		// payday numeric(5,0),
		valueList.add(digitStrBtw(25, 30));

		// otherincome numeric(19,0),
		valueList.add(digitStrBtw(1, 10));

		// otherincomesource character varying(30) COLLATE pg_catalog."default",
		valueList.add(quote("OherIncomeSource-" + rowNum));

		// annualsalary numeric(19,0),
		valueList.add(digitStrBtw(1000000, 99999999));

		// socialno character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("SocialNo-" + rowNum));

		// payday1 numeric(5,0),
		valueList.add(digitStrBtw(25, 30));

		// payday2 numeric(5,0),
		valueList.add(digitStrBtw(25, 30));

		// paymethod numeric(5,0),
		valueList.add(digitStrBtw(1, 10));

		// bankname character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("BankName-" + rowNum));

		// accountnumber character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("BankAccNo-" + rowNum));

		// corpcontacttime character varying(20) COLLATE pg_catalog."default",
		valueList.add(quote("CCT-" + rowNum));

		// homecontacttime character varying(20) COLLATE pg_catalog."default",
		valueList.add(quote("HCT-" + rowNum));

		// corpcontacttimestar numeric(5,0),
		valueList.add(digitStrBtw(1, 12));

		// corpcontacttimeend numeric(5,0),
		valueList.add(digitStrBtw(1, 12));

		// businesstype numeric(19,0),
		valueList.add(digitStrBtw(1, 90));

		// business character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("Bussiness-" + rowNum));

		// employmentstype numeric(19,0),
		valueList.add(digitStrBtw(1, 90));

		// communicationaddress numeric(5,0),
		valueList.add(digitStrBtw(1, 90));

		// jointincomestatus numeric(5,0),
		valueList.add(digitStrBtw(1, 90));

		// aeonaccount character varying(15) COLLATE pg_catalog."default",
		valueList.add(quote("AeonAcc-" + rowNum));

		// expresscardflag character varying(1) COLLATE pg_catalog."default",
		valueList.add(quote(digitStrBtw(1, 9)));

		// delflag numeric(19,0),
		valueList.add(digitStrBtw(0, 1));

		// creperson character varying(10) COLLATE pg_catalog."default",
		valueList.add(quote("CP-" + rowNum));

		// credate character varying(10) COLLATE pg_catalog."default",
		valueList.add(quote(getDate()));

		// cretime character varying(8) COLLATE pg_catalog."default",
		valueList.add(quote(getTime()));

		// creproid character varying(9) COLLATE pg_catalog."default",
		valueList.add(quote("CR-" + rowNum));

		// updperson character varying(10) COLLATE pg_catalog."default",
		valueList.add(quote("UP-" + rowNum));

		// upddate character varying(10) COLLATE pg_catalog."default",
		valueList.add(quote(getDate()));

		// updtime character varying(8) COLLATE pg_catalog."default",
		valueList.add(quote(getTime()));

		// updproid character varying(9) COLLATE pg_catalog."default",
		valueList.add(quote("UR-" + rowNum));

		// updcnt numeric(19,0),
		valueList.add(digitStrBtw(1, 99999));

		// id_fullname character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("id_fullname-" + rowNum));

		// id_birthplace character varying(200) COLLATE pg_catalog."default",
		valueList.add(quote("id_birthplace-" + rowNum));

		// id_birthdate character varying(10) COLLATE pg_catalog."default",
		valueList.add(quote(digitStrBtw(19999999, 99999999)));

		// id_zipcode character varying(10) COLLATE pg_catalog."default",
		valueList.add(quote(digitStrBtw(1, 999999)));

		// id_address character varying(200) COLLATE pg_catalog."default",
		valueList.add(quote("id_address-" + rowNum));

		// id_rt character varying(3) COLLATE pg_catalog."default",
		valueList.add(quote(digitStrBtw(1, 999)));

		// id_rw character varying(3) COLLATE pg_catalog."default",
		valueList.add(quote(digitStrBtw(1, 999)));

		// id_kelurahan character varying(200) COLLATE pg_catalog."default",
		valueList.add(quote("id_kelurahan-" + rowNum));

		// id_kecamatan character varying(200) COLLATE pg_catalog."default",
		valueList.add(quote("id_kecamatan-" + rowNum));

		// transunionscore numeric(10,0),
		valueList.add(quote(digitStrBtw(1, 9999999)));

		// perloanscore numeric(10,0),
		valueList.add(quote(digitStrBtw(1, 9999999)));

		// area character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("area-" + rowNum));

		// subarea character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("subarea-" + rowNum));

		// corparea character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("corparea-" + rowNum));

		// corpsubarea character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("corpsubarea-" + rowNum));

		// idcardno2 character varying(30) COLLATE pg_catalog."default",
		valueList.add(quote("idcardno2-" + rowNum));

		// flatunitno character varying(6) COLLATE pg_catalog."default",
		valueList.add(digitStrBtw(1, 999999));

		// landmark character varying(200) COLLATE pg_catalog."default",
		valueList.add(quote("landmark-" + rowNum));

		// corplandmark character varying(200) COLLATE pg_catalog."default",
		valueList.add(quote("corplandmark-" + rowNum));

		// idcardtype2 numeric(5,0),
		valueList.add(digitStrBtw(1, 90));

		// corpflatunitno character varying(6) COLLATE pg_catalog."default",
		valueList.add(digitStrBtw(1, 999999));

		// nationality character varying(15) COLLATE pg_catalog."default",
		valueList.add(quote("Nal-" + rowNum));

		// birthplace character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("birthplace-" + rowNum));

		// firstname character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("firstname-" + rowNum));

		// middlename character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("middlename-" + rowNum));

		// surname character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("surname-" + rowNum));

		// bankbranch character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("bankbranch-" + rowNum));

		// accounttype numeric(19,0),
		valueList.add(digitStrBtw(1, 90));

		// maidenfirstname character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("maidenfirstname-" + rowNum));

		// maidenmiddlename character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("maidenmiddlename-" + rowNum));

		// maidensurname character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("maidensurname-" + rowNum));

		// sssgsis character varying(30) COLLATE pg_catalog."default",
		valueList.add(quote("sssgsis-" + rowNum));

		// tinno character varying(30) COLLATE pg_catalog."default",
		valueList.add(quote("tinno-" + rowNum));

		// creditcardlimit1 numeric(19,0),
		valueList.add(digitStrBtw(1, 99999999));

		// creditcardlimit2 numeric(19,0),
		valueList.add(digitStrBtw(1, 99999999));

		// creditcardtype1 character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("creditcardtype1-" + rowNum));

		// creditcardtype2 character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("creditcardtype2-" + rowNum));

		// natureofbusiness character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("natureofbusiness-" + rowNum));

		// corpfaxno character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("corpfaxno-" + rowNum));

		// noofemployee numeric(19,0),
		valueList.add(digitStrBtw(1, 99999999));

		// otherteldistrict numeric(19,0),
		valueList.add(digitStrBtw(1, 99999999));

		// othertel numeric(19,0),
		valueList.add(digitStrBtw(1, 99999999));

		// corpfaxregion character varying(6) COLLATE pg_catalog."default",
		valueList.add(quote("CFR" + digitStrBtw(1, 999)));

		// customerrelate numeric(5,0),
		valueList.add(digitStrBtw(1, 99999));

		// customerrelationship character varying(30) COLLATE
		// pg_catalog."default",
		valueList.add(quote("customerrelationship-" + rowNum));

		// nowstreet character varying(100) COLLATE pg_catalog."default",
		valueList.add(quote("nowstreet-" + rowNum));

		// corpstreet character varying(100) COLLATE pg_catalog."default",
		valueList.add(quote("corpstreet-" + rowNum));

		// employmentstatus character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("employmentstatus-" + rowNum));

		// reg_hometeldistrict character varying(6) COLLATE
		// pg_catalog."default",
		valueList.add(quote("RHTD" + digitStrBtw(1, 99)));

		// regtel character varying(10) COLLATE pg_catalog."default",
		valueList.add(quote("RT" + digitStrBtw(1, 999999)));

		// corp_province character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("corp_province-" + rowNum));

		// province character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("province-" + rowNum));

		// reg_postal_code character varying(10) COLLATE pg_catalog."default",
		valueList.add(quote("RPC-" + rowNum));

		// reg_province character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("reg_province-" + rowNum));

		// reg_city character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("reg_city-" + rowNum));

		// reg_address character varying(200) COLLATE pg_catalog."default",
		valueList.add(quote("reg_address-" + rowNum));

		// monthly_rent_amt numeric(19,0),
		valueList.add(digitStrBtw(1, 999999999));

		// nationality_others character varying(50) COLLATE
		// pg_catalog."default",
		valueList.add(quote("nationality_others-" + rowNum));

		// no_of_person numeric(5,0),
		valueList.add(digitStrBtw(1, 99999));

		// drela_name character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("drela_name-" + rowNum));

		// drela_pinyin character varying(30) COLLATE pg_catalog."default",
		valueList.add(quote("drela_pinyin-" + rowNum));

		// drela_title numeric(19,0),
		valueList.add(digitStrBtw(1, 99999999));

		// drela_hometel character varying(10) COLLATE pg_catalog."default",
		valueList.add(quote("DHT-" + rowNum));

		// drela_hometeldistrict character varying(6) COLLATE
		// pg_catalog."default",
		valueList.add(quote(digitStrBtw(1, 99999)));

		// drela_mobileno character varying(16) COLLATE pg_catalog."default",
		valueList.add(quote("DMN-" + rowNum));

		// drela_relation numeric(5,0),
		valueList.add(digitStrBtw(1, 99999));

		// drela_corpname character varying(52) COLLATE pg_catalog."default",
		valueList.add(quote("drela_corpname-" + rowNum));

		// sameaddress numeric(5,0),
		valueList.add(digitStrBtw(1, 99999));

		// samenowaddress numeric(5,0),
		valueList.add(digitStrBtw(1, 99999));

		// sameregaddress numeric(5,0),
		valueList.add(digitStrBtw(1, 99999));

		// idtypeothers character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("idtypeothers-" + rowNum));

		// ebill numeric(5,0),
		valueList.add(digitStrBtw(1, 99999));

		// hukoutype numeric(19,0),
		valueList.add(digitStrBtw(1, 99999));

		// aeoncard numeric(5,0),
		valueList.add(digitStrBtw(1, 99999));

		// creditcard numeric(5,0),
		valueList.add(digitStrBtw(1, 99999));

		// district character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("district-" + rowNum));

		// subdistrict character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("subdistrict-" + rowNum));

		// village character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("village-" + rowNum));

		// paymentchannel numeric(5,0),
		valueList.add(digitStrBtw(1, 99999));

		// corpdistrict character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("corpdistrict-" + rowNum));

		// corpsubdistrict character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("corpsubdistrict-" + rowNum));

		// corpvillage character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("corpvillage-" + rowNum));

		// departmenttype numeric(5,0),
		valueList.add(digitStrBtw(1, 99999));

		// givenname character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("givenname-" + rowNum));

		// familyname character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("familyname-" + rowNum));

		// creditcardbankname character varying(50) COLLATE
		// pg_catalog."default",
		valueList.add(quote("creditcardbankname-" + rowNum));

		// cardname character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("cardname-" + rowNum));

		// dependants numeric(5,0),
		valueList.add(digitStrBtw(1, 99999));

		// relation numeric(5,0),
		valueList.add(digitStrBtw(1, 99999));

		// relationship character varying(30) COLLATE pg_catalog."default",
		valueList.add(quote("relationship-" + rowNum));

		// subvillage character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("subvillage-" + rowNum));

		// city2 character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("city2-" + rowNum));

		// district2 character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("district2-" + rowNum));

		// village2 character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("village2-" + rowNum));

		// subvillage2 character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("subvillage2-" + rowNum));

		// landmark2 character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("landmark2-" + rowNum));

		// permanentzipcode character varying(10) COLLATE pg_catalog."default",
		valueList.add(quote("PZC-" + rowNum));

		// permanentaddress character varying(200) COLLATE pg_catalog."default",
		valueList.add(quote("permanentaddress-" + rowNum));

		// corpsubvillage character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("corpsubvillage-" + rowNum));

		// localname character varying(200) COLLATE pg_catalog."default",
		valueList.add(quote("localname-" + rowNum));

		// localfamilyname character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("localfamilyname-" + rowNum));

		// localmiddlename character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("localmiddlename-" + rowNum));

		// localfirstname character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("localfirstname-" + rowNum));

		// town character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("town-" + rowNum));

		// state character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("state-" + rowNum));

		// mailboxno character varying(4) COLLATE pg_catalog."default",
		valueList.add(quote(digitStrUpTo(4)));

		// corprt character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("corprt-" + rowNum));

		// corpprovince character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("corpprovince-" + rowNum));

		// corpmailboxno character varying(4) COLLATE pg_catalog."default",
		valueList.add(quote(digitStrBtw(1, 9999)));

		// fathername character varying(100) COLLATE pg_catalog."default",
		valueList.add(quote("fathername-" + rowNum));

		// vip numeric(1,0),
		valueList.add(digitStrBtw(1, 9));

		// reg_rt character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("reg_rt-" + rowNum));

		// reg_village character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("reg_village-" + rowNum));

		// reg_district character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("reg_district-" + rowNum));

		// reg_mailboxno character varying(4) COLLATE pg_catalog."default",
		valueList.add(quote(digitStrBtw(1, 9999)));

		// reg_liveyear numeric(5,0),
		valueList.add(digitStrBtw(1, 99999));

		// reg_livemonth numeric(5,0),
		valueList.add(digitStrBtw(1, 99999));

		// reg_nowhouseownerrelatetype numeric(19,0),
		valueList.add(digitStrBtw(1, 99999));

		// reg_liveperson numeric(5,0),
		valueList.add(digitStrBtw(1, 99999));

		// reg_livepersonnum numeric(5,0),
		valueList.add(digitStrBtw(1, 99999));

		// reg_hometel character varying(10) COLLATE pg_catalog."default",
		valueList.add(quote("RHT-" + rowNum));

		// reg_town character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("reg_town-" + rowNum));

		// reg_landmark character varying(200) COLLATE pg_catalog."default",
		valueList.add(quote("reg_landmark-" + rowNum));

		// reg_state character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("reg_state-" + rowNum));

		// corptown character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("corptown-" + rowNum));

		// corpstate character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("corpstate-" + rowNum));

		// reg_zipcode character varying(10) COLLATE pg_catalog."default",
		valueList.add(quote("RZC-" + rowNum));

		// regteldistrict character varying(6) COLLATE pg_catalog."default",
		valueList.add(quote(digitStrBtw(1, 99999)));

		// memberid character varying(20) COLLATE pg_catalog."default",
		valueList.add(quote(rowNum));

		// familyb_no character varying(20) COLLATE pg_catalog."default",
		valueList.add(quote("familyb_no-" + rowNum));

		// familyb_place character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("familyb_place-" + rowNum));

		// familyb_date character varying(10) COLLATE pg_catalog."default",
		valueList.add(quote("FBD-" + rowNum));

		// prev_corpname character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("prev_corpname-" + rowNum));

		// prev_corprt character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("prev_corprt-" + rowNum));

		// prev_corpvillage character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("prev_corpvillage-" + rowNum));

		// prev_corpdistrict character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("prev_corpdistrict-" + rowNum));

		// prev_corpprovince character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("prev_corpprovince-" + rowNum));

		// prev_corpdept character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("prev_corpdept-" + rowNum));

		// prev_corpteldistrict character varying(5) COLLATE
		// pg_catalog."default",
		valueList.add(quote(digitStrBtw(1, 99999)));

		// prev_corptelno character varying(10) COLLATE pg_catalog."default",
		valueList.add(quote("PCT-" + rowNum));

		// corpcurrentaddressno character varying(60) COLLATE
		// pg_catalog."default",
		valueList.add(quote("corpcurrentaddressno-" + rowNum));

		// streetroad character varying(60) COLLATE pg_catalog."default",
		valueList.add(quote("streetroad-" + rowNum));

		// corpstreetroad character varying(60) COLLATE pg_catalog."default",
		valueList.add(quote("corpstreetroad-" + rowNum));

		// corpemail character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("corpemail-" + rowNum));

		// totalincome numeric(19,0),
		valueList.add(digitStrBtw(1, 99999));

		// education character varying(30) COLLATE pg_catalog."default",
		valueList.add(quote("education-" + rowNum));

		// person character varying(30) COLLATE pg_catalog."default",
		valueList.add(quote("person-" + rowNum));

		// channel character varying(30) COLLATE pg_catalog."default",
		valueList.add(quote("channel-" + rowNum));

		// nationalityothers character varying(30) COLLATE pg_catalog."default",
		valueList.add(quote("nationalityothers-" + rowNum));

		// corpmobileno character varying(16) COLLATE pg_catalog."default",
		valueList.add(quote("CMN-" + rowNum));

		// prev_corpdepttype numeric(5,0),
		valueList.add(digitStrBtw(1, 99999));

		// natureofbusinesstype numeric(5,0),
		valueList.add(digitStrBtw(1, 99999));

		// issuedby character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("issuedby-" + rowNum));

		// localcorpname character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("localcorpname-" + rowNum));

		// businessregno character varying(20) COLLATE pg_catalog."default",
		valueList.add(quote("businessregno-" + rowNum));

		// workplace character varying(30) COLLATE pg_catalog."default",
		valueList.add(quote("workplace-" + rowNum));

		// capital numeric(10,0),
		valueList.add(digitStrBtw(1, 99999));

		// otherloan numeric(10,0),
		valueList.add(digitStrBtw(1, 99999));

		// otherloan_from character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("otherloan_from-" + rowNum));

		// otherloan_balance numeric(19,0),
		valueList.add(digitStrBtw(1, 99999));

		// otherloan_purpose character varying(50) COLLATE pg_catalog."default",
		valueList.add(quote("otherloan_purpose-" + rowNum));

		// otherloan_payment numeric(19,0),
		valueList.add(digitStrBtw(1, 99999));

		// otherloan_endpayment character varying(10) COLLATE
		// pg_catalog."default",
		valueList.add(quote("OLE-" + rowNum));

		// positionothers character varying(30) COLLATE pg_catalog."default",
		valueList.add(quote("positionothers-" + rowNum));

		// corpcd character varying(16) COLLATE pg_catalog."default",
		valueList.add(quote("corpcd-" + rowNum));

		// corpindustry character varying(19) COLLATE pg_catalog."default",
		valueList.add(quote("corpindustry-" + rowNum));

		return "(" + valueList.stream().collect(Collectors.joining(",")) + ");\r\n";
	}

	private String getImageValues(int rowNum, int count, int i) {

		LinkedList<String> valueList = new LinkedList<>();

		String temp = "";

		// imagecode character varying(25)
		valueList.add(quote(getImageCd(rowNum, count, i)));

		// imagefilename character varying(100)
		valueList.add(quote("mgmgimage" + rowNum + ".jpg"));

		// imagepath character varying(250)
		valueList.add(quote("/opt/tomcat/HirePurchaseFiles/uploads/201905/minkhant904ammin" + rowNum + ".jpg"));

		// faxno character varying(50)
		valueList.add(quote(rowNum));

		// groupid character varying(20)
		valueList.add(quote(rowNum));

		// adjuststatus numeric(5,0)
		valueList.add(digitStrBtw(1, 90));

		// imagesource numeric(5,0)
		valueList.add(digitStrBtw(1, 90));

		// applicationtype numeric(19,0)
		valueList.add(digitStrBtw(1, 90));

		// adjustdate character varying(10)
		valueList.add(quote(getDate()));

		// adjusttime character varying(8)
		valueList.add(quote(getTime()));

		// imagetype numeric(5,0)
		valueList.add(digitStrBtw(1, 90));

		// datacd character varying(20)
		valueList.add(quote(getDataCd(rowNum, count)));

		// regstatus numeric(5,0)
		valueList.add(digitStrBtw(1, 90));

		// notype numeric(19,0)
		valueList.add(digitStrBtw(1, 90));

		// comments character varying(250)
		valueList.add(quote("comments-" + rowNum));

		// inputerid character varying(12)
		valueList.add(quote("ID-" + rowNum));

		// delflag numeric(19,0)
		valueList.add(digitStrBtw(0, 1));

		// creperson character varying(10)
		valueList.add(quote("cP-" + rowNum));

		// credate character varying(10)
		valueList.add(quote(getDate()));

		// cretime character varying(8)
		valueList.add(quote(getTime()));

		// creproid character varying(9)
		valueList.add(quote("SCR" + rowNum));

		// updperson character varying(10)
		valueList.add(quote("uP-" + rowNum));

		// upddate character varying(10)
		valueList.add(quote(getDate()));

		// updtime character varying(8)
		valueList.add(quote(getTime()));

		// updproid character varying(9)
		valueList.add(quote("SCR" + rowNum));

		// updcnt numeric(19,0)
		valueList.add(digitStrBtw(1, 10));

		// adjustperson character varying(50)
		valueList.add(quote("AP-" + rowNum));

		// branchcd numeric(5,0)
		valueList.add(quote(rowNum));

		// updgroupid character varying(19)
		valueList.add(quote(rowNum));

		// scantype numeric(19,0)
		valueList.add(digitStrBtw(1, 9));

		// indexdate character varying(10)
		valueList.add(quote(getDate()));

		// loantype character varying(10)
		valueList.add(digitStrBtw(1, 4));

		// appformno character varying(10)
		valueList.add(quote(getAppFormNo(rowNum, count)));

		// idcardtype numeric(19,0)
		valueList.add("1");

		// idcardno character varying(30)
		temp = quote(NRCEnum.getNRCCode(rowNum));
		valueList.add(temp);

		return "(" + valueList.stream().collect(Collectors.joining(",")) + ");\r\n";
	}

	private String getJudgementValues(int rowNum, int count) {

		LinkedList<String> valueList = new LinkedList<>();

		// sysappcode character varying(12)
		valueList.add(quote(getSysAppCode(rowNum, count)));

		// judgementcd character varying(12)
		valueList.add(quote(getJudgementCd(rowNum, count)));

		// judgementresult numeric(5,0)
		valueList.add(digitStrBtw(1, 9));

		// judgementresultreason numeric(5,0)
		valueList.add(digitStrBtw(1, 9));

		// approvecd character varying(20)
		valueList.add(quote(rowNum));

		// comments character varying(100)
		valueList.add(quote("commemt-" + rowNum));

		// judgementmanid character varying(12)
		valueList.add(quote("j" + rowNum));

		// judgementdate character varying(10)
		valueList.add(quote(getDate()));

		// indicationtype character varying(3)
		valueList.add(digitStrBtw(1, 100));

		// indictationcomment character varying(100)
		valueList.add(quote("i-" + rowNum));

		// judgementstatisticflag numeric(19,0)
		valueList.add(digitStrBtw(0, 1));

		// delflag numeric(19,0)
		valueList.add(digitStrBtw(0, 1));

		// creperson character varying(10)
		valueList.add(quote("CP-" + rowNum));

		// credate character varying(10)
		valueList.add(quote(getDate()));

		// cretime character varying(8)
		valueList.add(quote(getTime()));

		// creproid character varying(9)
		valueList.add(quote("SRC-" + rowNum));

		// updperson character varying(10)
		valueList.add(quote("UP-" + rowNum));

		// upddate character varying(10)
		valueList.add(quote(getDate()));

		// updtime character varying(8)
		valueList.add(quote(getTime()));

		// updproid character varying(9)
		valueList.add(quote("SCR" + rowNum));

		// updcnt numeric(19,0)
		valueList.add(digitStrBtw(1, 10));

		// loan_approved_amt numeric(19,0)
		valueList.add(digitStrBtw(100000, 200000));

		// cancel_remarks character varying(50)
		valueList.add(quote("cancel_remarks" + rowNum));

		// cancelreason character varying(200)
		valueList.add(quote("cancel_reason" + rowNum));

		// rejectreason character varying(200)
		valueList.add(quote("reject_reason" + rowNum));

		// creditscore numeric(5,2)
		valueList.add(digitStrBtw(10, 100));

		// emailflag numeric(19,0)
		valueList.add(digitStrBtw(0, 50));

		// smsflag numeric(19,0)
		valueList.add(digitStrBtw(0, 10));

		return "(" + valueList.stream().collect(Collectors.joining(",")) + ");\r\n";
	}

	private String getApplicationValues(int rowNum, int count) {

		LinkedList<String> valueList = new LinkedList<>();

		// sysappcode character varying(12)
		valueList.add(quote(getSysAppCode(rowNum, count)));

		// appcd character varying(16)
		valueList.add(quote(getDataCd(rowNum, count)));

		// customercd character varying(16)
		valueList.add(quote(getCustomerCd(rowNum)));

		// applicationtype numeric(19,0)
		valueList.add(digitStrBtw(1, 20));

		// peopleflag numeric(19,0)
		valueList.add(digitStrBtw(1, 20));

		// peoplecd character varying(18)
		valueList.add(quote(rowNum));

		// relation1 numeric(10,0)
		valueList.add(digitStrBtw(1, 20));

		// relation2 character varying(20)
		valueList.add(quote("relation" + rowNum));

		// appdate character varying(10)
		valueList.add(quote(getDate()));

		// agentcd character varying(18)
		valueList.add(quote(rowNum));

		// agentname character varying(50)
		valueList.add(quote("agentname" + rowNum));

		// storeteldistrict character varying(6)
		valueList.add(quote("s" + rowNum));

		// storetel character varying(10)
		valueList.add(quote("ST" + rowNum));

		// storepic character varying(50)
		valueList.add(quote("storepic" + rowNum));

		// storecd character varying(18)
		valueList.add(quote(rowNum));

		// storename character varying(50)
		valueList.add(quote("storename" + rowNum));

		// campaigncd character varying(18)
		valueList.add(quote(rowNum));

		// productuseaddress1 numeric(10,0)
		valueList.add(digitStrBtw(1, 20));

		// productuseaddress2 character varying(200)
		valueList.add(quote("productuseaddress2" + rowNum));

		// addressee character varying(50)
		valueList.add(quote("addressee" + rowNum));

		// deliveryaddress character varying(200)
		valueList.add(quote("deliveryaddress" + rowNum));

		// contactteldistrict character varying(6)
		valueList.add(quote("c" + rowNum));

		// contacttel character varying(10)
		valueList.add(quote("CT" + rowNum));

		// contactzipcode character varying(10)
		valueList.add(quote("CZP" + rowNum));

		// memo character varying(100)
		valueList.add(quote("memo" + rowNum));

		// bankcd numeric(10,0)
		valueList.add(quote(rowNum));

		// account character varying(25)
		valueList.add(quote("a" + rowNum));

		// accountowner character varying(20)
		valueList.add(quote("a" + rowNum));

		// agentappcd character varying(16)
		valueList.add(quote("a" + rowNum));

		// appstatus numeric(5,0)
		valueList.add(digitStrBtw(1, 20));

		// appverifystatus numeric(5,0)
		valueList.add(digitStrBtw(1, 20));

		// judgementcd character varying(18)
		valueList.add(quote("j" + rowNum));

		// judgementdate character varying(10)
		valueList.add(quote(getDate()));

		// approvecd character varying(20)
		valueList.add(quote(rowNum));

		// judgementnoticeflag numeric(19,0)
		valueList.add(digitStrBtw(1, 1));

		// judgementstatisticflag numeric(19,0)
		valueList.add(digitStrBtw(1, 1));

		// dvancedsettlementflag numeric(19,0)
		valueList.add(digitStrBtw(1, 1));

		// jointincomestatus numeric(5,0)
		valueList.add(digitStrBtw(1, 1));

		// merchantstamp character varying(50)
		valueList.add(quote("merchantstamp" + rowNum));

		// aeonstaffdatereceived character varying(10)
		valueList.add(quote("AR-" + rowNum));

		// comments character varying(100)
		valueList.add(quote("comments" + rowNum));

		// delflag numeric(19,0)
		valueList.add(digitStrBtw(0, 1));

		// creperson character varying(10)
		valueList.add(quote("CP" + rowNum));

		// credate character varying(10)
		valueList.add(quote(getDate()));

		// cretime character varying(8)
		valueList.add(quote(getTime()));

		// creproid character varying(9)
		valueList.add(quote("SCR" + rowNum));

		// updperson character varying(10)
		valueList.add(quote("UP-" + rowNum));

		// upddate character varying(10)
		valueList.add(quote(getDate()));

		// updtime character varying(8)
		valueList.add(quote(getTime()));

		// updproid character varying(9)
		valueList.add(quote("SCR" + rowNum));

		// updcnt numeric(19,0)
		valueList.add(digitStrBtw(1, 10));

		// canceldate character varying(10)
		valueList.add(quote(getDate()));

		// merchantstaff character varying(50)
		valueList.add(quote("merchantstaff" + rowNum));

		// autopay_agencycd numeric(5,0)
		valueList.add(quote(rowNum));

		// autopay_accnt_name character varying(50)
		valueList.add(quote("autopay_accnt_name" + rowNum));

		// autopay_accnt_no character varying(50)
		valueList.add(quote("autopay_accnt_no" + rowNum));

		// autopay_bankcd numeric(10,0)
		valueList.add(digitStrBtw(1, 20));

		// autopay_bank_br_code numeric(5,0)
		valueList.add(digitStrBtw(1, 20));

		// disburse_agencycd numeric(5,0)
		valueList.add(quote(rowNum));

		// disburse_accnt_name character varying(50)
		valueList.add(quote("disburse_accnt_name" + rowNum));

		// disburse_accnt_no character varying(50)
		valueList.add(quote("disburse_accnt_no" + rowNum));

		// disburse_bankcd numeric(10,0)
		valueList.add(quote(rowNum));

		// disburse_bank_br_code numeric(5,0)
		valueList.add(quote(rowNum));

		// assessment_date character varying(10)
		valueList.add(quote(getDate()));

		// app_cancel_date character varying(10)
		valueList.add(quote(getDate()));

		// interest_date character varying(10)
		valueList.add(quote(getDate()));

		// application_branch character varying(50)
		valueList.add(quote("application_branch" + rowNum));

		// cancel_reason_code numeric(5,0)
		valueList.add(quote(rowNum));

		// bank_city character varying(50)
		valueList.add(quote("bank_city" + rowNum));

		// bank_province character varying(50)
		valueList.add(quote("bank_province" + rowNum));

		// app_delete_date character varying(10)
		valueList.add(quote(getDate()));

		// canceltime character varying(8)
		valueList.add(quote(getTime()));

		// cancelperson character varying(10)
		valueList.add(quote("CP" + rowNum));

		// bikjudgement character varying(2)
		valueList.add(digitStrBtw(1, 20));

		// schemecd character varying(9)
		valueList.add(quote(rowNum));

		// canceltype numeric(19,0)
		valueList.add(digitStrBtw(1, 20));

		// aeonstaffname character varying(100)
		valueList.add(quote("aeonstaffname" + rowNum));

		// aeonstaffid character varying(20)
		valueList.add(quote("a" + rowNum));

		// idproof numeric(5,0)
		valueList.add(digitStrBtw(1, 20));

		// incomeproof numeric(5,0)
		valueList.add(digitStrBtw(1, 20));

		// residenceproof numeric(5,0)
		valueList.add(digitStrBtw(1, 20));

		// bankstatement numeric(5,0)
		valueList.add(digitStrBtw(1, 20));

		// creditcardcopy numeric(5,0)
		valueList.add(digitStrBtw(1, 20));

		// membershipcardcopy numeric(5,0)
		valueList.add(digitStrBtw(1, 20));

		// others numeric(5,0)
		valueList.add(digitStrBtw(1, 20));

		// othersinput character varying(50)
		valueList.add(quote("othersinput" + rowNum));

		// sanctionfilepath character varying(255)
		valueList.add(quote("sanctionfilepath" + rowNum));

		// loantype character varying(10)
		valueList.add(quote("SFP" + rowNum));

		// expresscardno character varying(20)
		valueList.add(quote("SFP" + rowNum));

		return "(" + valueList.stream().collect(Collectors.joining(",")) + ");\r\n";
	}

	private String getTPaymentValues(int rowNum) {

		LinkedList<String> valueList = new LinkedList<>();

		// paymentcd character varying(14)
		valueList.add(quote(getPaymentCode(rowNum)));

		// agreementcd character varying(20)
		valueList.add(quote(getAgreementCd(rowNum, 3)));

		// paymentdate character varying(10)
		valueList.add(quote(getDate()));

		// receipttype numeric(5,0)
		valueList.add(digitStrBtw(1, 9));

		// paymentclass numeric(19,0)
		valueList.add(digitStrUpTo(9));

		// payment numeric(19,0)
		valueList.add(digitStrUpTo(9));

		// otherpayment numeric(19,0)
		valueList.add(digitStrUpTo(9));

		// coupon numeric(19,0)
		valueList.add(digitStrUpTo(9));

		// managementcd character varying(20)
		valueList.add(digitStrBtw(1, 10));

		// collectorcd character varying(10)
		valueList.add(digitStrBtw(1, 5));

		// receiptno character varying(20)
		valueList.add(digitStrBtw(1, 10));

		// bankcd numeric(10,0),
		valueList.add(digitStrUpTo(9));

		// receiptflag numeric(19,0),
		valueList.add(digitStrUpTo(9));

		// temppaymentdate character varying(10)
		valueList.add(quote(getDate()));

		// earlypayflag numeric(19,0),
		valueList.add(digitStrUpTo(9));

		// monthbatflag numeric(19,0),
		valueList.add(digitStrUpTo(9));

		// delflag numeric(19,0),
		valueList.add(digitStrUpTo(9));

		// branchcd character varying(12)
		valueList.add(digitStrBtw(1, 100));

		// countercd character varying(15)
		valueList.add(digitStrBtw(1, 100));

		// stampflag numeric(19,0),
		valueList.add(digitStrUpTo(9));

		// reason numeric(5,0)
		valueList.add(digitStrUpTo(2));

		// creperson character varying(10)
		valueList.add(quote("CP-" + rowNum));

		// credate character varying(10)
		valueList.add(quote(getDate()));

		// cretime character varying(8)
		valueList.add(quote(getTime()));

		// creproid character varying(9)
		valueList.add(quote("SRC-" + rowNum));

		// updperson character varying(10)
		valueList.add(quote("UP-" + rowNum));

		// upddate character varying(10)
		valueList.add(quote(getDate()));

		// updtime character varying(8)
		valueList.add(quote(getTime()));

		// updproid character varying(9)
		valueList.add(quote("SCR" + rowNum));

		// updcnt numeric(19,0)
		valueList.add(digitStrBtw(1, 10));

		// paymenttype numeric(19,0)
		valueList.add(digitStrBtw(1, 10));

		// bankacctcd character varying(23)
		valueList.add(quote("r-" + rowNum));

		// collectorfee numeric(19,0)
		valueList.add(digitStrBtw(1, 10));

		// preceiptno character varying(20)
		valueList.add(digitStrBtw(0, 20));

		// transaction_no character varying(18)
		valueList.add(digitStrBtw(0, 10));

		// approvalcd character varying(20)
		valueList.add(digitStrBtw(1, 10));

		// approvalnum numeric(5,0)
		valueList.add(digitStrBtw(1, 9));

		// approvalstatus numeric(5,0)
		valueList.add(digitStrBtw(1, 9));

		// bankdelflag numeric(19,0)
		valueList.add(digitStrBtw(1, 10));

		// deladjpaymentcd character varying(14)
		valueList.add(digitStrBtw(0, 14));

		// causal numeric(5,0)
		valueList.add(digitStrBtw(1, 9));

		// cancelreason numeric(5,0)
		valueList.add(digitStrBtw(1, 9));

		// remark character varying(200)
		valueList.add(quote("p-" + rowNum));

		// adjdescription character varying(200)
		valueList.add(quote("a-" + rowNum));

		// reqdescription character varying(200)
		valueList.add(quote("r-" + rowNum));

		// waiveearlysettleflag numeric(19,0)
		valueList.add(digitStrBtw(1, 10));

		// earlysettlementamt numeric(19,0)
		valueList.add(digitStrBtw(1, 10));

		// adjustmentamt numeric(19,0)
		valueList.add(digitStrBtw(1, 10));

		// deleteremark character varying(200)
		valueList.add(quote("d-" + rowNum));

		// compulsarysaving character varying(19)
		valueList.add(quote("c-" + rowNum));

		return "(" + valueList.stream().collect(Collectors.joining(",")) + ");\r\n";
	}

}
