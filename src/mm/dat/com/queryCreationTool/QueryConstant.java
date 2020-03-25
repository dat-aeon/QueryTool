package mm.dat.com.queryCreationTool;

public final class QueryConstant {

	private static final String M_CUSTOMER_COLUMNS = "customercd," + "applicationtype," + "areacd," + "idcardtype," + "idcardno," + "idcardoldno," + "name," + "chinesename,"
			+ "title," + "othertitle," + "nickname," + "sex," + "birthday," + "marrystatus," + "race," + "maidenname," + "city," + "rt," + "rw," + "customertype," + "creditcardno,"
			+ "cardhold," + "cardtype," + "cardtypecontent," + "creditcardno1," + "creditcardno2," + "issurename1," + "issurename2," + "validuntilyear1," + "validuntilmonth1,"
			+ "validuntilyear2," + "validuntilmonth2," + "creditcardlimit," + "carno," + "carmonthmoney," + "graduateschool," + "educationlevel," + "nowzipcode," + "nowaddress,"
			+ "hometeldistrict," + "hometel," + "telowner," + "telownerrelate," + "liveyear," + "livemonth," + "mobileno," + "nowhousetype," + "nowhouseowner,"
			+ "nowhouseownerrelatetype," + "nowhouseownerrelate," + "nowhousemoney," + "liveperson," + "livepersonnum," + "homezipcode," + "hukouaddress," + "hukouteldistrict,"
			+ "hukoutel," + "email," + "corpname," + "employeenum," + "corpzipcode," + "corpaddress," + "corpcity," + "occupation," + "corpteldistrict," + "corptel,"
			+ "corpsubtel," + "corpregteldistrict," + "corpregtel," + "corptype," + "corpway," + "department," + "\"position\"," + "workyear," + "workmonth," + "restday,"
			+ "salary," + "securitycardno," + "payday," + "otherincome," + "otherincomesource," + "annualsalary," + "socialno," + "payday1," + "payday2," + "paymethod,"
			+ "bankname," + "accountnumber," + "corpcontacttime," + "homecontacttime," + "corpcontacttimestar," + "corpcontacttimeend," + "businesstype," + "business,"
			+ "employmentstype," + "communicationaddress," + "jointincomestatus," + "aeonaccount," + "expresscardflag," + "delflag," + "creperson," + "credate," + "cretime,"
			+ "creproid," + "updperson," + "upddate," + "updtime," + "updproid," + "updcnt," + "id_fullname," + "id_birthplace," + "id_birthdate," + "id_zipcode," + "id_address,"
			+ "id_rt," + "id_rw," + "id_kelurahan," + "id_kecamatan," + "transunionscore," + "perloanscore," + "area," + "subarea," + "corparea," + "corpsubarea," + "idcardno2,"
			+ "flatunitno," + "landmark," + "corplandmark," + "idcardtype2," + "corpflatunitno," + "nationality," + "birthplace," + "firstname," + "middlename," + "surname,"
			+ "bankbranch," + "accounttype," + "maidenfirstname," + "maidenmiddlename," + "maidensurname," + "sssgsis," + "tinno," + "creditcardlimit1," + "creditcardlimit2,"
			+ "creditcardtype1," + "creditcardtype2," + "natureofbusiness," + "corpfaxno," + "noofemployee," + "otherteldistrict," + "othertel," + "corpfaxregion,"
			+ "customerrelate," + "customerrelationship," + "nowstreet," + "corpstreet," + "employmentstatus," + "reg_hometeldistrict," + "regtel," + "corp_province," + "province,"
			+ "reg_postal_code," + "reg_province," + "reg_city," + "reg_address," + "monthly_rent_amt," + "nationality_others," + "no_of_person," + "drela_name," + "drela_pinyin,"
			+ "drela_title," + "drela_hometel," + "drela_hometeldistrict," + "drela_mobileno," + "drela_relation," + "drela_corpname," + "sameaddress," + "samenowaddress,"
			+ "sameregaddress," + "idtypeothers," + "ebill," + "hukoutype," + "aeoncard," + "creditcard," + "district," + "subdistrict," + "village," + "paymentchannel,"
			+ "corpdistrict," + "corpsubdistrict," + "corpvillage," + "departmenttype," + "givenname," + "familyname," + "creditcardbankname," + "cardname," + "dependants,"
			+ "relation," + "relationship," + "subvillage," + "city2," + "district2," + "village2," + "subvillage2," + "landmark2," + "permanentzipcode," + "permanentaddress,"
			+ "corpsubvillage," + "localname," + "localfamilyname," + "localmiddlename," + "localfirstname," + "town," + "state," + "mailboxno," + "corprt," + "corpprovince,"
			+ "corpmailboxno," + "fathername," + "vip," + "reg_rt," + "reg_village," + "reg_district," + "reg_mailboxno," + "reg_liveyear," + "reg_livemonth,"
			+ "reg_nowhouseownerrelatetype," + "reg_liveperson," + "reg_livepersonnum," + "reg_hometel," + "reg_town," + "reg_landmark," + "reg_state," + "corptown," + "corpstate,"
			+ "reg_zipcode," + "regteldistrict," + "memberid," + "familyb_no," + "familyb_place," + "familyb_date," + "prev_corpname," + "prev_corprt," + "prev_corpvillage,"
			+ "prev_corpdistrict," + "prev_corpprovince," + "prev_corpdept," + "prev_corpteldistrict," + "prev_corptelno," + "corpcurrentaddressno," + "streetroad,"
			+ "corpstreetroad," + "corpemail," + "totalincome," + "education," + "person," + "channel," + "nationalityothers," + "corpmobileno," + "prev_corpdepttype,"
			+ "natureofbusinesstype," + "issuedby," + "localcorpname," + "businessregno," + "workplace," + "capital," + "otherloan," + "otherloan_from," + "otherloan_balance,"
			+ "otherloan_purpose," + "otherloan_payment," + "otherloan_endpayment," + "positionothers," + "corpcd," + "corpindustry";

	private static final String T_CUSTOMER_COLUMNS = "sysappcd," + "infotype," + "idcardno";

	private static final String T_AGREEMENT = "agreementcd," + "agreementtype," + "sysappcd," + "agreementdate," + "agreementupdate," + "agreementreceiveddate," + "saledate,"
			+ "agreementstatus," + "chargeflag," + "customercd," + "peopleflag," + "peoplecd," + "relation1," + "relation2," + "agentcd," + "agentname," + "storeteldistrict,"
			+ "storetel," + "storepic," + "storecd," + "storename," + "campaigncd," + "productuseaddress1," + "productuseaddress2," + "addressee," + "deliveryaddress,"
			+ "contactteldistrict," + "contacttel," + "contactzipcode," + "memo," + "bankcd," + "account," + "accountowner," + "agentappcd," + "merchantstamp,"
			+ "aeonstaffdatereceived," + "comments," + "stampflag," + "delflag," + "creperson," + "credate," + "cretime," + "creproid," + "updperson," + "upddate," + "updtime,"
			+ "updproid," + "updcnt," + "agreementcanceldate," + "merchantstaff," + "repostatus," + "autopay_agencycd," + "autopay_accnt_name," + "autopay_accnt_no,"
			+ "autopay_bankcd," + "autopay_bank_br_code," + "disburse_accnt_name," + "disburse_accnt_no," + "disburse_bankcd," + "disburse_bank_br_code," + "app_cancel_date,"
			+ "autopay_accnt_type," + "agg_cancel_reason," + "return_prod_price," + "cancel_fee_rate," + "ebill," + "interest_start_date," + "interest_start_flag," + "mailto,"
			+ "psakflag," + "settlementstatus," + "canceltype," + "prev_agstatus," + "baddebtstat," + "repocriteria," + "writeoffflag," + "writeoffdate," + "pointflag,"
			+ "disbursementstatus," + "reasoncode," + "agreementdelinquencyreason";

	private static final String T_PAYSTAGESAGREEMENT = "agreementcd," + "agreementtype," + "producttotalmoney," + "approvalfee," + "approvalfeeifany," + "approvalfeetype,"
			+ "downpayment," + "cashback," + "financeprice," + "commissionrate," + "numberofinstalment," + "interestamount," + "hpprice," + "monthlypayment," + "initialpayment,"
			+ "periodpayment," + "period," + "hpmonthmoney," + "hpsecondday," + "paymentdate," + "hplastday," + "adjustmoney," + "paytype," + "nameoncard," + "accountbanktype,"
			+ "bankname," + "branchname," + "accountownedtype," + "accountowned," + "accountno," + "campaigntype," + "instalmenttype," + "hpfirstday," + "paymenttype,"
			+ "promotionvoucher," + "application_fee," + "application_fee_rate," + "admin_fee," + "admin_fee_rate," + "interest_rate," + "promotion_code," + "firstpaydate,"
			+ "lastpayment," + "premiumamount," + "annualeffectiverate," + "delflag," + "creperson," + "credate," + "cretime," + "creproid," + "updperson," + "upddate,"
			+ "updtime," + "updproid," + "updcnt," + "docstampamount," + "paymenttypenew," + "vatamount";

	private static final String M_EXPRESSCARD = "expresscardno," + "customercd," + "expiredate," + "expresscardstatus," + "expresscardreason," + "contactcomment,"
			+ "expresscardtype," + "creditlimit," + "creperson," + "credate," + "cretime," + "creproid," + "updperson," + "upddate," + "updtime," + "updproid," + "updcnt,"
			+ "membercardreasonothers," + "reissueamount," + "reissueflag," + "embossdate";

	private static final String T_IMAGE = "imagecode," + "imagefilename," + "imagepath," + "faxno, " + "groupid," + "adjuststatus," + "imagesource," + "applicationtype,"
			+ "adjustdate," + "adjusttime," + "imagetype," + "datacd," + "regstatus," + "notype," + "comments," + "inputerid," + "delflag," + "creperson," + "credate," + "cretime,"
			+ "creproid," + "updperson," + "upddate," + "updtime," + "updproid," + "updcnt," + "adjustperson," + "branchcd," + "updgroupid," + "scantype," + "indexdate,"
			+ "loantype," + "appformno," + "idcardtype," + "idcardno";

	private static final String T_JUDGEMENT = "sysappcode," + "judgementcd," + "judgementresult," + "judgementresultreason," + "approvecd," + "comments," + "judgementmanid,"
			+ "judgementdate," + "indicationtype," + "indictationcomment," + "judgementstatisticflag," + "delflag," + "creperson," + "credate," + "cretime," + "creproid,"
			+ "updperson," + "upddate," + "updtime," + "updproid," + "updcnt," + "loan_approved_amt," + "cancel_remarks," + "cancelreason," + "rejectreason," + "creditscore,"
			+ "emailflag," + "smsflag";

	/*
	 * private static final String T_APPLICATION = "sysappcd," + "appcd," +
	 * "customercd," + "applicationtype," + "peopleflag," + "peoplecd," +
	 * "relation1, " + "relation2," + "appdate," + "agentcd, " + "agentname," +
	 * "storeteldistrict," + "storetel," + "storepic," + "storecd," +
	 * "storename," + "campaigncd," + "productuseaddress1," +
	 * "productuseaddress2," + "addressee," + "deliveryaddress," +
	 * "contactteldistrict," + "contacttel," + "contactzipcode," + "memo," +
	 * "bankcd," + "account," + "accountowner," + "agentappcd," + "appstatus," +
	 * "appverifystatus," + "judgementcd," + "judgementdate," + "approvecd," +
	 * "judgementnoticeflag," + "judgementstatisticflag," +
	 * "advancedsettlementflag," + "jointincomestatus," + "merchantstamp," +
	 * "aeonstaffdatereceived," + "comments," + "delflag," + "creperson," +
	 * "credate," + "cretime," + "creproid," + "updperson," + "upddate," +
	 * "updtime," + "updproid," + "updcnt," + "canceldate," + "merchantstaff," +
	 * "autopay_agencycd," + "autopay_accnt_name," + "autopay_accnt_no," +
	 * "autopay_bankcd," + "autopay_bank_br_code," + "disburse_agencycd," +
	 * "disburse_accnt_name," + "disburse_accnt_no," + "disburse_bankcd," +
	 * "disburse_bank_br_code," + "assessment_date," + "app_cancel_date," +
	 * "interest_date," + "application_branch," + "cancel_reason_code," +
	 * "bank_city," + "bank_province," + "app_delete_date," + "canceltime," +
	 * "cancelperson," + "bikjudgement," + "schemecd," + "canceltype," +
	 * "aeonstaffname," + "aeonstaffid," + "idproof," + "incomeproof," +
	 * "residenceproof," + "bankstatement," + "creditcardcopy," +
	 * "membershipcardcopy," + "others," + "othersinput," + "sanctionfilepath,"
	 * + "loantype," + "expresscardno";
	 */

	private static final String T_PAYMENT = "paymentcd," + "agreementcd," + "paymentdate," + "receipttype," + "paymentclass," + "payment," + "otherpayment," + "coupon,"
			+ "managementcd," + "collectorcd," + "receiptno," + "bankcd," + "receiptflag," + "temppaymentdate," + "earlypayflag," + "monthbatflag," + "delflag," + "branchcd,"
			+ "countercd," + "stampflag," + "reason," + "creperson," + "credate," + "cretime," + "creproid," + "updperson," + "upddate," + "updtime," + "updproid," + "updcnt,"
			+ "paymenttype," + "bankacctcd," + "collectorfee," + "preceiptno," + "transaction_no," + "approvalcd," + "approvalnum," + "approvalstatus," + "bankdelflag,"
			+ "deladjpaymentcd," + "causal," + "cancelreason," + "remark," + "adjdescription," + "reqdescription," + "waiveearlysettleflag," + "earlysettlementamt,"
			+ "adjustmentamt," + "deleteremark," + "compulsarysaving";

	private static final String T_APPLICATION = "sysappcd, appcd, customercd, applicationtype, peopleflag, peoplecd, relation1, relation2, appdate, agentcd, agentname, storeteldistrict, storetel, storepic, storecd, storename, campaigncd, productuseaddress1, productuseaddress2, addressee, deliveryaddress, contactteldistrict, contacttel, contactzipcode, memo, bankcd, account, accountowner, agentappcd, appstatus, appverifystatus, judgementcd, judgementdate, approvecd, judgementnoticeflag, judgementstatisticflag, advancedsettlementflag, jointincomestatus, merchantstamp, aeonstaffdatereceived, comments, delflag, creperson, credate, cretime, creproid, updperson, upddate, updtime, updproid, updcnt, canceldate, merchantstaff, autopay_agencycd, autopay_accnt_name, autopay_accnt_no, autopay_bankcd, autopay_bank_br_code, disburse_agencycd, disburse_accnt_name, disburse_accnt_no, disburse_bankcd, disburse_bank_br_code, assessment_date, app_cancel_date, interest_date, application_branch, cancel_reason_code, bank_city, bank_province, app_delete_date, canceltime, cancelperson, bikjudgement, schemecd, canceltype, aeonstaffname, aeonstaffid, idproof, incomeproof, residenceproof, bankstatement, creditcardcopy, membershipcardcopy, others, othersinput, sanctionfilepath, loantype, expresscardno";

	public static String getTCustomerInsertIntoValuesString(String schemaName) {
		if (!schemaName.equals(""))
			schemaName += ".";

		return "INSERT INTO " + schemaName + "t_customer (" + T_CUSTOMER_COLUMNS + ") VALUES ";
	}

	public static String getMCustomerInsertIntoValuesString(String schemaName) {
		if (!schemaName.equals(""))
			schemaName += ".";

		return "INSERT INTO " + schemaName + "m_customer (" + M_CUSTOMER_COLUMNS + ") VALUES ";
	}

	public static String getTAgreementInsertIntoValuesString(String schemaName) {
		if (!schemaName.equals(""))
			schemaName += ".";

		return "INSERT INTO " + schemaName + "t_agreement (" + T_AGREEMENT + ") VALUES ";
	}

	public static String getTPayStagesAgreementInsertIntoValuesString(String schemaName) {
		if (!schemaName.equals(""))
			schemaName += ".";

		return "INSERT INTO " + schemaName + "t_paystagesagreement (" + T_PAYSTAGESAGREEMENT + ") VALUES ";
	}

	public static String getMExpressCardInsertIntoValuesString(String schemaName) {
		if (!schemaName.equals(""))
			schemaName += ".";

		return "INSERT INTO " + schemaName + "m_expresscard (" + M_EXPRESSCARD + ") VALUES ";
	}

	public static String getTImageInsertIntoValuesString(String schemaName) {
		if (!schemaName.equals(""))
			schemaName += ".";

		return "INSERT INTO " + schemaName + "t_image (" + T_IMAGE + ") VALUES ";
	}

	public static String getTJudgementInsertIntoValuesString(String schemaName) {
		if (!schemaName.equals(""))
			schemaName += ".";

		return "INSERT INTO " + schemaName + "t_judgement (" + T_JUDGEMENT + ") VALUES ";
	}

	public static String getTApplicationInsertIntoValuesString(String schemaName) {
		if (!schemaName.equals(""))
			schemaName += ".";

		return "INSERT INTO " + schemaName + "t_application (" + T_APPLICATION + ") VALUES ";
	}

	public static String getTPaymentInsertIntoValuesString(String schemaName) {
		if (!schemaName.equals(""))
			schemaName += ".";

		return "INSERT INTO " + schemaName + "t_payment (" + T_PAYMENT + ") VALUES ";
	}

}
