package dal.EmCommercialInsurance;

import java.sql.CallableStatement;

import Conn.dbconn;

public class CI_InsurantUpload_Dal {
	private static dbconn conn = new dbconn();

	// 插入商保审核的文件
	public int uploadCIOK(String sheetname, String filename, String username,
			String fileAllname) {
		int result;

		String sql = "insert into EmComInsuranceUpdata (eciu_name,eciu_idcard,eciu_qfee, eciu_sex, eciu_castsort, eciu_fact_date,eciu_state) select [投保人],[身份证号码],[费用],[性别],[投保类型],[投保时间],0 from opendatasource ('Microsoft.ACE.OLEDB.12.0','data source="
				+ fileAllname
				+ ";Extended properties=excel 5.0;')...["
				+ sheetname + "$] where [投保人] is not null";
		try {
			System.out.println(sql);
			conn.execQuery(sql);
			result = 0;

			// 判断上传数据
			CallableStatement c = conn
					.getcall("EmComInsuranceAutUpload_P_zzq(?,?)");
			c.setString(1, "1");
			c.registerOutParameter(2, java.sql.Types.INTEGER);
			c.execute();

		} catch (Exception e) {
			e.printStackTrace();
			result = 1;
		}

		return result;
	}

	// 插入商保理赔明细文件
	public int uploadCIclaimOK(String sheetname, String filename, String username,
			String fileAllname,String email_state) {
		int result;
		String sql = "insert into EmCommercialClaimUpdateNew (eccn_tid,eccn_host,eccn_pid,eccn_name,eccn_pdate,eccn_jz_content,eccn_aut_money,eccn_money,eccn_jp_money,eccn_jp_content,eccn_castsort,eccn_pf_bl,eccn_email) select [批次],[就诊医院],cast([发票号] as decimal(18)),[被保险人],[发票日期],[就诊原因],[审核金额],[赔付金额],[拒延赔费用],[拒延赔原因],[投保类型],[赔付比例],[邮箱地址] from opendatasource ('Microsoft.ACE.OLEDB.12.0','data source="
				+ fileAllname
				+ ";Extended properties=excel 5.0;')...["
				+ sheetname + "$] where [被保险人] is not null";
		try {
			System.out.println(sql);
			conn.execQuery(sql);
			result = 0;

			// 判断上传数据
			CallableStatement c = conn
					.getcall("EmComInsuranceClaimAutUpload_P_zzq(?,?,?)");
			c.setString(1, "1");
			c.setString(2, email_state);
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();

		} catch (Exception e) {
			e.printStackTrace();
			result = 1;
		}

		return result;
	}
}
