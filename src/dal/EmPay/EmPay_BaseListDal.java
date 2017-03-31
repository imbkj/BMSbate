package dal.EmPay;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.EmPay_BaseListModel;

public class EmPay_BaseListDal {
	// 获取员工列表数据
	public List<EmPay_BaseListModel> getEmPay_Baselist(String str1)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmPay_BaseListModel> list = new ArrayList<EmPay_BaseListModel>();
		String sqlstr = "select efba_emba_name,a.cid,efba_coba_shortname,cast(emsp_ss_fee as varchar(50)) ss_fee,cast(emsp_fee as varchar(50)) fee,cast(emsp_fee-emsp_ss_fee as varchar(50)) emsp_dis_fee,a.gid,a.ownmonth,emsp_type,emsp_id from EmShouldPay a left join EmFinanceBase b on b.gid=a.gid and b.ownmonth=a.ownmonth left join EmShouldPayAut d on d.espa_paynum=a.EmSp_paynum where espa_id="
				+ str1;
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {

				EmPay_BaseListModel model = new EmPay_BaseListModel();
				model.setCid(rs.getString("cid"));
				model.setCoba_company(rs.getString("efba_coba_shortname"));
				model.setEspa_ss_fee(rs.getString("ss_fee"));
				model.setEspa_sf_fee(rs.getString("fee"));
				model.setEspa_dis_fee(rs.getString("emsp_dis_fee"));
				model.setGid(rs.getString("gid"));
				model.setOwnmonth(rs.getString("ownmonth"));
				model.setEmba_name(rs.getString("efba_emba_name"));
				model.setEspa_type(rs.getString("emsp_type"));
				model.setEmsp_id(rs.getString("emsp_id"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取公司列表数据
	public List<EmPay_BaseListModel> getCoPay_Baselist(String ownmonth,String paynum,String stype)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmPay_BaseListModel> list = new ArrayList<EmPay_BaseListModel>();
		String sqlstr = "select a.cid,coba_shortname,ownmonth,EmSp_paynum,emsp_type,cast(sum(emsp_ss_fee) as varchar(50)) ss_fee,cast(sum(emsp_fee) as varchar(50)) fee,cast(sum(emsp_fee)-sum(emsp_ss_fee) as varchar(50)) emsp_dis_fee from EmShouldPay a left join CoBase b on b.cid=a.cid where ownmonth="+ownmonth+" and EmSp_paynum='"+paynum+"' group by a.cid,coba_shortname,ownmonth,EmSp_paynum,emsp_type";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {

				EmPay_BaseListModel model = new EmPay_BaseListModel();
				model.setCid(rs.getString("cid"));
				model.setCoba_company(rs.getString("coba_shortname"));
				model.setEspa_ss_fee(rs.getString("ss_fee"));
				model.setEspa_sf_fee(rs.getString("fee"));
				model.setEspa_dis_fee(rs.getString("emsp_dis_fee"));
				model.setOwnmonth(rs.getString("ownmonth"));
				model.setEspa_type(rs.getString("emsp_type"));
				model.setEspa_paynum(rs.getString("EmSp_paynum"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取员工费用明细
	public List<EmPay_BaseListModel> getEmPay_Feelist(String emsp_id,
			String gid, String ownmonth, String stype) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		String str1 = "";
		String str2 = "";
		String str3 = "";
		String sqlstr = "";

		List<EmPay_BaseListModel> list = new ArrayList<EmPay_BaseListModel>();
		if (!gid.equals("")) {
			str1 = " and a.gid=" + gid;
		} else {
			str1 = " and a.gid=1";
		}
		if (!ownmonth.equals("")) {
			str2 = " and a.ownmonth=" + ownmonth;
		}
		if (!emsp_id.equals("")) {
			str3 = " and a.emsp_id=" + emsp_id;
		}
		sqlstr = "select top 1 coba_company efba_coba_company,emba_name efba_emba_name,0 ylcp,0 ylop,0 syecp,0 syeop,0 syucp,0 syuop,0 jlcp,0 jlop,0 gscp,0 gsop,0 housecp,0 houseop,a.gid,EmSp_fee from EmShouldPay a left join embase b on b.gid=a.gid left join cobase c on c.cid=b.cid where 1=1"
				+ str1 + str3;

		if (stype.equals("社会保险") || stype.equals("住房公积金")) {
			sqlstr = "select top 1 efba_coba_company,efba_emba_name,a.gid,cast (isnull(efsb_esiu_YLCP,0.00) as varchar(50)) ylcp,cast(isnull(efsb_esiu_YLOP,0.00) as varchar(50)) ylop,cast(isnull(efsb_esiu_SyuCP,0.00) as varchar(50)) syucp,cast(isnull(efsb_esiu_SyuOP,0.00) as varchar(50)) syuop,cast(isnull(efsb_esiu_SyeCP,0.00) as varchar(50)) syecp,cast(isnull(efsb_esiu_SyeOP,0.00) as varchar(50)) syeop,cast(isnull(efsb_esiu_GSCP,0.00) as varchar(50)) gscp,cast(isnull(efsb_esiu_GSOP,0.00) as varchar(50)) gsop,cast(isnull(efsb_esiu_JLCP,0.00) as varchar(50)) jlcp,cast(isnull(efsb_esiu_JLOP,0.00) as varchar(50)) jlop,cast(isnull(efsb_esiu_JLOP,0.00) as varchar(50)) jlop,cast(isnull(efhg_emhu_cp,0.00) as varchar(50)) housecp,cast(isnull(efhg_emhu_op,0.00) as varchar(50)) houseop,0 EmSp_fee from EmFinanceSheBao a left join EmFinanceBase b on b.gid=a.gid and a.ownmonth=b.ownmonth left join EmFinanceHouseGjj c on c.gid=a.gid and c.ownmonth=a.ownmonth where 1=1"
					+ str1 + str2;
		}
		if (stype.equals("委托外地")) {
			sqlstr = "select top 1 efba_coba_company,efba_emba_name,a.gid,cast (isnull(efsb_esiu_YLCP,0.00) as varchar(50)) ylcp,cast(isnull(efsb_esiu_YLOP,0.00) as varchar(50)) ylop,cast(isnull(efsb_esiu_SyuCP,0.00) as varchar(50)) syucp,cast(isnull(efsb_esiu_SyuOP,0.00) as varchar(50)) syuop,cast(isnull(efsb_esiu_SyeCP,0.00) as varchar(50)) syecp,cast(isnull(efsb_esiu_SyeOP,0.00) as varchar(50)) syeop,cast(isnull(efsb_esiu_GSCP,0.00) as varchar(50)) gscp,cast(isnull(efsb_esiu_GSOP,0.00) as varchar(50)) gsop,cast(isnull(efsb_esiu_JLCP,0.00) as varchar(50)) jlcp,cast(isnull(efsb_esiu_JLOP,0.00) as varchar(50)) jlop,cast(isnull(efsb_esiu_JLOP,0.00) as varchar(50)) jlop,cast(isnull(efhg_emhu_cp,0.00) as varchar(50)) housecp,cast(isnull(efhg_emhu_op,0.00) as varchar(50)) houseop,0 EmSp_fee from EmFinanceSheBao a left join EmFinanceBase b on b.gid=a.gid and a.ownmonth=b.ownmonth left join EmFinanceHouseGjj c on c.gid=a.gid and c.ownmonth=a.ownmonth where 1=1"
					+ str1 + str2;
		}

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmPay_BaseListModel model = new EmPay_BaseListModel();
				model.setCoba_company(rs.getString("efba_coba_company"));
				model.setYlcp(rs.getString("ylcp"));
				model.setYlop(rs.getString("ylop"));
				model.setSyecp(rs.getString("syecp"));
				model.setSyeop(rs.getString("syeop"));
				model.setSyucp(rs.getString("syucp"));
				model.setSyuop(rs.getString("syuop"));
				model.setJlcp(rs.getString("jlcp"));
				model.setJlop(rs.getString("jlop"));
				model.setGscp(rs.getString("gscp"));
				model.setGsop(rs.getString("gsop"));
				model.setGid(rs.getString("gid"));
				model.setHousecp(rs.getString("housecp"));
				model.setHouseop(rs.getString("houseop"));
				model.setEmba_name(rs.getString("efba_emba_name"));
				model.setDa_fee("0");
				model.setTj_fee("0");
				model.setSb_fee("0");
				model.setGz_fee("0");
				model.setGs_fee("0");
				model.setHj_fee("0");
				model.setZj_fee("0");
				model.setHd_fee("0");
				model.setOther_fee("0");
				model.setFw_fee("0");
				if (stype.equals("档案保管费")) {
					model.setDa_fee(rs.getString("EmSp_fee"));
				}
				if (stype.equals("员工体检")) {
					model.setTj_fee(rs.getString("EmSp_fee"));
				}
				if (stype.equals("商保费")) {
					model.setSb_fee(rs.getString("EmSp_fee"));
				}
				if (stype.equals("代发工资")) {
					model.setGz_fee(rs.getString("EmSp_fee"));
				}
				if (stype.equals("代报个税")) {
					model.setGs_fee(rs.getString("EmSp_fee"));
				}
				if (stype.equals("户籍管理")) {
					model.setHj_fee(rs.getString("EmSp_fee"));
				}
				if (stype.equals("社保卡")) {
					model.setZj_fee(rs.getString("EmSp_fee"));
				}
				if (stype.equals("员工活动")) {
					model.setHd_fee(rs.getString("EmSp_fee"));
				}
				if (stype.equals("其他")) {
					model.setOther_fee(rs.getString("EmSp_fee"));
				}
				if (stype.equals("服务费")) {
					model.setFw_fee(rs.getString("EmSp_fee"));
				}
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}
	
	// 获取公司费用明细
		public List<EmPay_BaseListModel> getCoPay_Feelist(String cid,
				String ownmonth,String paynum, String stype) throws SQLException {
			dbconn db = new dbconn();
			ResultSet rs = null;
			String str1 = "";
			String str2 = "";
			String str3 = "";
			String str4 = "";
			String sqlstr = "";

			List<EmPay_BaseListModel> list = new ArrayList<EmPay_BaseListModel>();
			if (!cid.equals("")) {
				str1 = " and a.cid=" + cid;
			} else {
				str1 = " and a.cid=1";
			}
			if (!ownmonth.equals("")) {
				str2 = " and a.ownmonth=" + ownmonth;
			}
			if (!paynum.equals("")) {
				str3 = " and a.EmSp_paynum='" + paynum+"' group by a.cid,coba_company";
				str4 = " and a.EmSp_paynum='" + paynum+"' group by efba_coba_company,a.cid";
			}
			sqlstr = "select top 1 a.cid,coba_company efba_coba_company,0 ylcp,0 ylop,0 syecp,0 syeop,0 syucp,0 syuop,0 jlcp,0 jlop,0 gscp,0 gsop,0 housecp,0 houseop,sum(EmSp_fee) EmSp_fee from EmShouldPay a left join embase b on b.gid=a.gid left join cobase c on c.cid=b.cid where 1=1"
					+ str1 + str3;

			if (stype.equals("社会保险") || stype.equals("住房公积金")) {
				sqlstr = "select top 1 a.cid,efba_coba_company,cast (isnull(sum(efsb_esiu_YLCP),0.00) as varchar(50)) ylcp,cast(isnull(sum(efsb_esiu_YLOP),0.00) as varchar(50)) ylop,cast(isnull(sum(efsb_esiu_SyuCP),0.00) as varchar(50)) syucp,cast(isnull(sum(efsb_esiu_SyuOP),0.00) as varchar(50)) syuop,cast(isnull(sum(efsb_esiu_SyeCP),0.00) as varchar(50)) syecp,cast(isnull(sum(efsb_esiu_SyeOP),0.00) as varchar(50)) syeop,cast(isnull(sum(efsb_esiu_GSCP),0.00) as varchar(50)) gscp,cast(isnull(sum(efsb_esiu_GSOP),0.00) as varchar(50)) gsop,cast(isnull(sum(efsb_esiu_JLCP),0.00) as varchar(50)) jlcp,cast(isnull(sum(efsb_esiu_JLOP),0.00) as varchar(50)) jlop,cast(isnull(sum(efsb_esiu_JLOP),0.00) as varchar(50)) jlop,cast(isnull(sum(efhg_emhu_cp),0.00) as varchar(50)) housecp,cast(isnull(sum(efhg_emhu_op),0.00) as varchar(50)) houseop,0 EmSp_fee from EmFinanceSheBao a left join EmFinanceBase b on b.gid=a.gid and a.ownmonth=b.ownmonth left join EmFinanceHouseGjj c on c.gid=a.gid and c.ownmonth=a.ownmonth where 1=1"
						+ str1 + str2+ str4;
			}
			if (stype.equals("委托外地")) {
				sqlstr = "select top 1 a.cid,efba_coba_company,cast (isnull(sum(efsb_esiu_YLCP),0.00) as varchar(50)) ylcp,cast(isnull(sum(efsb_esiu_YLOP),0.00) as varchar(50)) ylop,cast(isnull(sum(efsb_esiu_SyuCP),0.00) as varchar(50)) syucp,cast(isnull(sum(efsb_esiu_SyuOP),0.00) as varchar(50)) syuop,cast(isnull(sum(efsb_esiu_SyeCP),0.00) as varchar(50)) syecp,cast(isnull(sum(efsb_esiu_SyeOP),0.00) as varchar(50)) syeop,cast(isnull(sum(efsb_esiu_GSCP),0.00) as varchar(50)) gscp,cast(isnull(sum(efsb_esiu_GSOP),0.00) as varchar(50)) gsop,cast(isnull(sum(efsb_esiu_JLCP),0.00) as varchar(50)) jlcp,cast(isnull(sum(efsb_esiu_JLOP),0.00) as varchar(50)) jlop,cast(isnull(sum(efsb_esiu_JLOP),0.00) as varchar(50)) jlop,cast(isnull(sum(efhg_emhu_cp),0.00) as varchar(50)) housecp,cast(isnull(sum(efhg_emhu_op),0.00) as varchar(50)) houseop,0 EmSp_fee from EmFinanceSheBao a left join EmFinanceBase b on b.gid=a.gid and a.ownmonth=b.ownmonth left join EmFinanceHouseGjj c on c.gid=a.gid and c.ownmonth=a.ownmonth where 1=1"
						+ str1 + str2+ str4;
			}

			try {
				rs = db.GRS(sqlstr);
				while (rs.next()) {
					EmPay_BaseListModel model = new EmPay_BaseListModel();
					model.setCoba_company(rs.getString("efba_coba_company"));
					model.setYlcp(rs.getString("ylcp"));
					model.setYlop(rs.getString("ylop"));
					model.setSyecp(rs.getString("syecp"));
					model.setSyeop(rs.getString("syeop"));
					model.setSyucp(rs.getString("syucp"));
					model.setSyuop(rs.getString("syuop"));
					model.setJlcp(rs.getString("jlcp"));
					model.setJlop(rs.getString("jlop"));
					model.setGscp(rs.getString("gscp"));
					model.setGsop(rs.getString("gsop"));
					model.setCid(rs.getString("cid"));
					model.setHousecp(rs.getString("housecp"));
					model.setHouseop(rs.getString("houseop"));
					model.setDa_fee("0");
					model.setTj_fee("0");
					model.setSb_fee("0");
					model.setGz_fee("0");
					model.setGs_fee("0");
					model.setHj_fee("0");
					model.setZj_fee("0");
					model.setHd_fee("0");
					model.setOther_fee("0");
					model.setFw_fee("0");
					if (stype.equals("档案保管费")) {
						model.setDa_fee(rs.getString("EmSp_fee"));
					}
					if (stype.equals("员工体检")) {
						model.setTj_fee(rs.getString("EmSp_fee"));
					}
					if (stype.equals("商保费")) {
						model.setSb_fee(rs.getString("EmSp_fee"));
					}
					if (stype.equals("代发工资")) {
						model.setGz_fee(rs.getString("EmSp_fee"));
					}
					if (stype.equals("代报个税")) {
						model.setGs_fee(rs.getString("EmSp_fee"));
					}
					if (stype.equals("户籍管理")) {
						model.setHj_fee(rs.getString("EmSp_fee"));
					}
					if (stype.equals("社保卡")) {
						model.setZj_fee(rs.getString("EmSp_fee"));
					}
					if (stype.equals("员工活动")) {
						model.setHd_fee(rs.getString("EmSp_fee"));
					}
					if (stype.equals("其他")) {
						model.setOther_fee(rs.getString("EmSp_fee"));
					}
					if (stype.equals("服务费")) {
						model.setFw_fee(rs.getString("EmSp_fee"));
					}
					list.add(model);
				}
			} catch (Exception e) {
				System.out.print(e.toString());
			} finally {
				db.Close();
			}
			return list;
		}

	// 财务审核
	public List<EmPay_BaseListModel> getEmPay_Autok(String ownmonth,
			String paynum) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmPay_BaseListModel> list = new ArrayList<EmPay_BaseListModel>();
		String sqlstr = "update EmShouldPayAut set espa_state=1 where ownmonth="
				+ ownmonth + " and espa_paynum='" + paynum + "'";

		try {
			rs = db.GRS(sqlstr);
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 总经理审核
	public List<EmPay_BaseListModel> getEmPay_Mgautok(String ownmonth,
			String paynum) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmPay_BaseListModel> list = new ArrayList<EmPay_BaseListModel>();
		String sqlstr = "update EmShouldPayAut set espa_state=2 where ownmonth="
				+ ownmonth + " and espa_paynum='" + paynum + "'";

		try {
			rs = db.GRS(sqlstr);
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 添加支付明细
	public List<EmPay_BaseListModel> getEmPay_Payok(String em1, String em2,
			String em3, String em4, String em5, String em6, String em7,
			String ownmonth, String paynum) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		ResultSet rs2 = null;
		List<EmPay_BaseListModel> list = new ArrayList<EmPay_BaseListModel>();
		String sqlstr = "update EmShouldPayAut set espa_state=3 where ownmonth="
				+ ownmonth + " and espa_paynum='" + paynum + "'";
		String sqlstr2 = "insert into EmShouldPaynumber (ownmonth,emsn_number,emsn_bankname,emsn_bankAcc,emsn_pay,emsn_addname,emsn_paytime,emsn_remark,emsn_type) values ('"
				+ ownmonth
				+ "','"
				+ paynum
				+ "','"
				+ em2
				+ "','"
				+ em3
				+ "','"
				+ em5 + "','1','" + em4 + "','" + em7 + "','" + em6 + "')";

		try {
			rs = db.GRS(sqlstr);
			rs2 = db.GRS(sqlstr2);
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}
}
