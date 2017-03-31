package bll.EmBaseCompact;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import dal.EmBaseCompact.EmBaseCompact_AddDal;
import dal.EmCommercialInsurance.CI_InsurantClaim_ListDal;
import Model.CI_InsurantClaimModel;
import Model.EmBaseCompactModel;

public class EmBaseCompact_AddBll {
	private Integer y;
	private Integer y1;
	private Integer y2;
	private Integer y3;
	private Integer y5;
	private EmBaseCompactModel reg;
	private EmBaseCompact_AddDal data;
	private EmBaseCompactModel reg2;
	private EmBaseCompact_AddDal data2;
	private EmBaseCompactModel reg3;
	private EmBaseCompact_AddDal data3;
	private EmBaseCompactModel reg4;
	private EmBaseCompact_AddDal data4;
	private EmBaseCompactModel reg5;
	private EmBaseCompact_AddDal data5;

	// Date类型转换String
	public String DatetoSting(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String Date = sdf.format(d);
		return Date;
	}

	public void add_emcompact(String cid, String gid, String embase1,
			String embase2, String embase3, String embase5, String embase6,
			String embase8, String embase9, String embase10, String embase11,
			String embase12, String embase13, String embase14, String embase15,
			String embase16, String embase17, String embase18, String embase19,
			String embase20, String embase21) {

		reg = new EmBaseCompactModel(gid, cid, embase1, embase2, embase3,
				embase5, embase6, embase8, embase9, embase10, embase11,
				embase12, embase13, embase14, embase15, embase16, embase17,
				embase18, embase19, embase20, embase21, "", "0", "新签", "", "");
	}

	public void ren_emcompact(String cid, String gid, String embase1,
			String embase2, String embase3, String embase5, String embase6,
			String embase8, String embase9, String embase10, String embase11,
			String embase12, String embase13, String embase14, String embase15,
			String embase16, String embase17, String embase18, String embase19,
			String embase20, String embase21) {

		reg = new EmBaseCompactModel(gid, cid, embase1, embase2, embase3,
				embase5, embase6, embase8, embase9, embase10, embase11,
				embase12, embase13, embase14, embase15, embase16, embase17,
				embase18, embase19, embase20, embase21, "", "0", "续签", "", "");
	}

	public void end_emcompact(String gid, String embase1, String url,
			String username) {

		reg5 = new EmBaseCompactModel(gid, "", embase1, url, username, "", "",
				"", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
				"0", "终止", "", "");

	}

	// 签回劳动合同
	public void sign_emcompact(int ebcc_id, String embase1) {

		reg2 = new EmBaseCompactModel(ebcc_id, embase1);
	}

	public void filing_emcompact(int ebcc_id, String embase1) {

		reg3 = new EmBaseCompactModel(ebcc_id, embase1);
	}

	public int Dochek() {
		data = new EmBaseCompact_AddDal();

		int y = 0;
		try {
			y = data.add_emcompact(reg);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public int Dochek2() {
		data2 = new EmBaseCompact_AddDal();

		int y1 = 0;
		try {
			y1 = data2.sign_emcompact(reg2);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return y1;
	}

	public void setY1(Integer y1) {
		this.y1 = y1;
	}

	public int Dochek3() {
		data3 = new EmBaseCompact_AddDal();

		int y2 = 0;
		try {
			y2 = data3.filing_emcompact(reg3);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return y2;
	}

	public void setY2(Integer y2) {
		this.y2 = y2;
	}

	public int Dochek4() {
		data4 = new EmBaseCompact_AddDal();

		int y3 = 0;
		try {
			y3 = data4.ren_emcompact(reg4);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return y2;
	}

	public void setY3(Integer y3) {
		this.y3 = y3;
	}

	public int Dochek5() {
		data5 = new EmBaseCompact_AddDal();

		int y5 = 0;
		try {
			y5 = data5.end_emcompact(reg5);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return y5;
	}

	public void setY5(Integer y5) {
		this.y5 = y5;
	}

	// 查询该员工是否已有社保信息
	public boolean check_ciin(int gid) {
		EmBaseCompact_AddDal dal = new EmBaseCompact_AddDal();
		return dal.check_ciin(gid);
	}

	public boolean ch_xadd(int gid) {
		EmBaseCompact_AddDal dal = new EmBaseCompact_AddDal();
		return dal.ch_xadd(gid);
	}

	// 获取备注
	public String[] remark_add(String ebcc_id, String content) {
		int result = 0;
		String[] message = new String[5];
		EmBaseCompact_AddDal dal = new EmBaseCompact_AddDal();
		result = dal.getremark_add(ebcc_id, content);
		if (result != 0) {
			message[0] = "1";
			message[1] = "备注成功!";
			message[2] = String.valueOf(result);
			message[3] = "claim_remark";
			message[4] = "备注";
		} else {
			message[0] = "0";
			message[1] = "备注失败!";
		}
		return message;
	}

	// 获取备注
	public List<CI_InsurantClaimModel> embasecompact_remarklist(String eccl_id)
			throws SQLException {
		EmBaseCompact_AddDal dal = new EmBaseCompact_AddDal();
		List<CI_InsurantClaimModel> list = dal.embasecompact_remarklist(eccl_id);
		return list;
	}
}
