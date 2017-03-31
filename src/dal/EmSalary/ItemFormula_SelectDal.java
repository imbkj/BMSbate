package dal.EmSalary;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.CoFormulaInfoModel;
import Model.CoOwnmonthModel;
import Model.CoSalaryItemAttributeModel;
import Model.CoSalaryItemFormulaModel;
import Model.CoSalaryItemIDInfoModel;
import Model.ConnectionItemInfoModel;
import Model.EmBaseESInCFInModel;
import Model.EmSalaryBaseSel_viewModel;
import Model.EmTXTFileInfoModel;
import Model.EmTXTFileSetModel;

public class ItemFormula_SelectDal {
	private dbconn conn = new dbconn();

	// 查询工资项目信息
	public List<CoSalaryItemFormulaModel> getItemFormulaCSIF(String str) {
		ResultSet rs = null;
		List<CoSalaryItemFormulaModel> itemforList = new ArrayList<CoSalaryItemFormulaModel>();
		if (!itemforList.isEmpty())
			itemforList.clear();

		try {
			String sql = "SELECT * FROM CSII_CFDa_CSIF_V WHERE 1=1" + str
					+ " ORDER BY csii_sequence";
			// System.out.println(sql);
			rs = conn.GRS(sql);
			while (rs.next()) {
				CoSalaryItemFormulaModel model = new CoSalaryItemFormulaModel();
				model.setCsii_id(rs.getInt("csii_id"));
				model.setCsii_col(rs.getString("csii_col"));
				model.setCsii_item_name(rs.getString("csii_item_name"));
				model.setCsii_sequence(rs.getInt("csii_sequence"));
				model.setCsii_csd_state(rs.getInt("csii_csd_state"));
				model.setCsii_fd_state(rs.getInt("csii_fd_state"));
				model.setCiin_id(rs.getInt("ciin_id"));
				model.setCsii_c_connection(rs.getString("csii_c_connection"));
				model.setCsii_addname(rs.getString("csii_addname"));
				model.setCsii_addtime(rs.getString("csii_addtime"));
				model.setCsii_ifzero(rs.getInt("csii_ifzero"));
				model.setCsii_edit_state(rs.getInt("csii_edit_state"));
				model.setCsii_itemid(rs.getString("csii_itemid"));
				model.setCid(rs.getInt("cid"));
				model.setOwnmonth(rs.getInt("ownmonth"));
				model.setCiin_name(rs.getString("ciin_name"));
				model.setCiin_remark(rs.getString("ciin_remark"));
				model.setCsgi_content(rs.getString("csgi_content"));
				model.setChk_cfd(rs.getString("chk_cfd"));
				model.setItem_name("[" + rs.getString("csii_item_name") + "]");
				model.setCsii_item_name2(rs.getString("csii_item_name2"));
				model.setItem_name2("[" + rs.getString("csii_item_name2") + "]");
				model.setCfin_id(rs.getString("cfin_id"));
				model.setCfin_name(rs.getString("cfin_name"));
				model.setCsia_attribute(rs.getString("csia_attribute"));
				model.setCsia_ms(rs.getString("csia_ms"));
				model.setCsia_remark(rs.getString("csia_remark"));
				model.setCsia_id(rs.getInt("csia_id"));
				itemforList.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemforList;
	}

	// 查询工资项目信息
	public List<CoSalaryItemFormulaModel> getItemFormula(String str) {
		ResultSet rs = null;
		List<CoSalaryItemFormulaModel> itemforList = new ArrayList<CoSalaryItemFormulaModel>();
		if (!itemforList.isEmpty())
			itemforList.clear();

		try {
			String sql = "SELECT * FROM CSII_CFDa_V WHERE 1=1" + str
					+ " ORDER BY csii_sequence";

			rs = conn.GRS(sql);
			while (rs.next()) {
				CoSalaryItemFormulaModel model = new CoSalaryItemFormulaModel();
				model.setCsii_id(rs.getInt("csii_id"));
				model.setCsii_col(rs.getString("csii_col"));
				model.setCsii_item_name(rs.getString("csii_item_name"));
				model.setCsii_sequence(rs.getInt("csii_sequence"));
				model.setCsii_csd_state(rs.getInt("csii_csd_state"));
				model.setCsii_fd_state(rs.getInt("csii_fd_state"));
				model.setCiin_id(rs.getInt("ciin_id"));
				model.setCsii_c_connection(rs.getString("csii_c_connection"));
				model.setCsii_addname(rs.getString("csii_addname"));
				model.setCsii_addtime(rs.getString("csii_addtime"));
				model.setCsii_ifzero(rs.getInt("csii_ifzero"));
				model.setCsii_edit_state(rs.getInt("csii_edit_state"));
				model.setCsii_itemid(rs.getString("csii_itemid"));
				model.setCid(rs.getInt("cid"));
				model.setOwnmonth(rs.getInt("ownmonth"));
				model.setCiin_name(rs.getString("ciin_name"));
				model.setCiin_remark(rs.getString("ciin_remark"));
				model.setCsgi_content(rs.getString("csgi_content"));
				model.setChk_cfd(rs.getString("chk_cfd"));
				model.setItem_name("[" + rs.getString("csii_item_name") + "]");
				model.setCsii_item_name2(rs.getString("csii_item_name2"));
				model.setItem_name2("[" + rs.getString("csii_item_name2") + "]");
				itemforList.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemforList;
	}

	// 特殊关联项列表
	public List<ConnectionItemInfoModel> getConnectionItem(String str) {
		ResultSet rs = null;
		List<ConnectionItemInfoModel> conItemList = new ArrayList<ConnectionItemInfoModel>();
		if (!conItemList.isEmpty())
			conItemList.clear();
		try {
			String sql = "SELECT * FROM ConnectionItemInfo WHERE 1=1 " + str
					+ " ORDER BY ciin_id";

			rs = conn.GRS(sql);
			while (rs.next()) {
				ConnectionItemInfoModel model = new ConnectionItemInfoModel();
				model.setCiin_id(rs.getInt("ciin_id"));
				model.setCiin_name(rs.getString("ciin_name"));
				conItemList.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conItemList;
	}

	// 项目属性列表
	public List<CoSalaryItemAttributeModel> getItemAttribute(String str) {
		ResultSet rs = null;
		List<CoSalaryItemAttributeModel> list = new ArrayList<CoSalaryItemAttributeModel>();
		if (!list.isEmpty())
			list.clear();
		try {
			// String sql =
			// "SELECT * FROM CoSalaryItemAttribute WHERE 1=1 AND csia_attribute<>'计算中间项' "
			// + str+ " ORDER BY csia_id";
			String sql = "SELECT * FROM CoSalaryItemAttribute WHERE 1=1 " + str
					+ " ORDER BY csia_id";
			rs = conn.GRS(sql);
			while (rs.next()) {
				CoSalaryItemAttributeModel model = new CoSalaryItemAttributeModel();
				model.setCsia_id(rs.getInt("csia_id"));
				model.setCsia_attribute(rs.getString("csia_attribute"));
				model.setCsia_ms(rs.getString("csia_ms"));
				model.setCsia_remark(rs.getString("csia_remark"));
				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取有工资项目的公司
	public List<CoOwnmonthModel> getCobase(String str) {
		ResultSet rs = null;
		List<CoOwnmonthModel> cobaseList = new ArrayList<CoOwnmonthModel>();
		if (!cobaseList.isEmpty())
			cobaseList.clear();
		try {
			String sql = "SELECT DISTINCT a.cid,b.coba_company,b.coba_shortname FROM CoSalaryItemIDInfo a LEFT JOIN CoBase b ON a.cid=b.cid WHERE 1=1 "
					+ str + " ORDER BY b.coba_shortname";

			rs = conn.GRS(sql);
			while (rs.next()) {
				CoOwnmonthModel model = new CoOwnmonthModel();
				model.setCid(rs.getInt("cid"));
				model.setCompany(rs.getString("coba_company"));
				model.setShortname(rs.getString("coba_shortname"));
				model.setLong_company("[" + String.valueOf(rs.getInt("cid"))
						+ "]  " + rs.getString("coba_shortname"));
				cobaseList.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cobaseList;
	}

	// 获取有工资项目的月份
	public List<CoOwnmonthModel> getOwnmonth(String str) {
		ResultSet rs = null;
		List<CoOwnmonthModel> ownmonthList = new ArrayList<CoOwnmonthModel>();
		if (!ownmonthList.isEmpty())
			ownmonthList.clear();
		try {
			String sql = "SELECT DISTINCT ownmonth FROM CoSalaryItemIDInfo WHERE 1=1 "
					+ str + " ORDER BY ownmonth DESC";

			rs = conn.GRS(sql);
			while (rs.next()) {
				CoOwnmonthModel model = new CoOwnmonthModel();
				model.setOwnmonth(rs.getInt("ownmonth"));
				ownmonthList.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ownmonthList;
	}

	// 获取工资算法信息
	public List<CoFormulaInfoModel> getFormulaInfo(String str) {
		ResultSet rs = null;
		List<CoFormulaInfoModel> list = new ArrayList<CoFormulaInfoModel>();
		if (!list.isEmpty())
			list.clear();
		try {
			String sql = "SELECT * FROM CoFormulaInfo WHERE cfin_delete=0 "
					+ str + " ORDER BY cfin_name";

			rs = conn.GRS(sql);
			while (rs.next()) {
				CoFormulaInfoModel model = new CoFormulaInfoModel();
				model.setCfin_id(rs.getInt("cfin_id"));
				model.setCid(rs.getInt("cid"));
				model.setCsii_itemid(rs.getString("csii_itemid"));
				model.setCfin_name(rs.getString("cfin_name"));
				model.setCfin_state(rs.getInt("cfin_state"));
				model.setCfin_delete(rs.getInt("cfin_delete"));
				model.setCfin_remark(rs.getString("cfin_remark"));
				model.setCfin_addname(rs.getString("cfin_addname"));
				model.setCfin_addtime(rs.getString("cfin_addtime"));
				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询算法内容
	public List<CoSalaryItemFormulaModel> getFormulaData(String str) {
		ResultSet rs = null;
		List<CoSalaryItemFormulaModel> list = new ArrayList<CoSalaryItemFormulaModel>();
		if (!list.isEmpty())
			list.clear();

		try {
			String sql = "SELECT * FROM CFDa_CFIn_V WHERE cfin_id IS NOT NULL"
					+ str + " ORDER BY cfda_sequence";
			// System.out.println(sql);
			rs = conn.GRS(sql);
			while (rs.next()) {
				CoSalaryItemFormulaModel model = new CoSalaryItemFormulaModel();
				model.setCfda_id(rs.getString("cfda_id"));
				model.setCfin_id(rs.getString("cfin_id"));
				model.setCfda_formula(rs.getString("cfda_formula"));
				model.setCfda_t_formula(rs.getString("cfda_t_formula"));
				model.setCfda_range(rs.getString("cfda_range"));
				model.setCfda_t_range(rs.getString("cfda_t_range"));
				model.setCfda_sequence(rs.getString("cfda_sequence"));
				model.setCsii_item_name(rs.getString("csii_item_name"));

				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取CoSalaryItemIDInfo数据
	public List<CoSalaryItemIDInfoModel> getCoSalaryItemID(String str) {
		ResultSet rs = null;
		List<CoSalaryItemIDInfoModel> list = new ArrayList<CoSalaryItemIDInfoModel>();
		if (!list.isEmpty())
			list.clear();

		try {
			String sql = "SELECT * FROM CoSalaryItemIDInfo WHERE 1=1" + str
					+ " ORDER BY ownmonth DESC";

			rs = conn.GRS(sql);
			while (rs.next()) {
				CoSalaryItemIDInfoModel model = new CoSalaryItemIDInfoModel();

				model.setCsii_id(rs.getInt("csii_id"));
				model.setCsii_itemid(rs.getString("csii_itemid"));
				model.setCid(rs.getInt("cid"));
				model.setOwnmonth(rs.getInt("ownmonth"));
				model.setCsii_edit_state(rs.getInt("csii_edit_state"));
				model.setCsii_addname(rs.getString("csii_addname"));
				model.setCsii_addtime(rs.getString("csii_addtime"));
				model.setCsii_tapr_id(rs.getInt("csii_tapr_id"));

				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取算法分配的员工列表
	public List<EmBaseESInCFInModel> getEmBaESInList(String str) {
		ResultSet rs = null;
		List<EmBaseESInCFInModel> list = new ArrayList<EmBaseESInCFInModel>();
		if (!list.isEmpty())
			list.clear();

		try {
			String sql = "SELECT * FROM EmBase_ESIn_CFIn_V WHERE 1=1" + str
					+ " ORDER BY emba_name";

			rs = conn.GRS(sql);
			while (rs.next()) {
				EmBaseESInCFInModel model = new EmBaseESInCFInModel();

				model.setGid(rs.getInt("gid"));
				model.setCid(rs.getInt("cid"));
				model.setEmba_name(rs.getString("emba_name"));
				model.setEmba_state(rs.getInt("emba_state"));
				model.setCfin_id(rs.getString("cfin_id"));
				model.setEsda_ba_name(rs.getString("esda_ba_name"));
				model.setCfin_name(rs.getString("cfin_name"));
				model.setCoba_company(rs.getString("coba_company"));
				model.setCoba_shortname(rs.getString("coba_shortname"));
				model.setCoba_namespell(rs.getString("coba_namespell"));
				model.setCoba_spell(rs.getString("coba_spell"));
				model.setCoba_client(rs.getString("coba_client"));
				model.setEmba_spell(rs.getString("emba_spell"));
				model.setEsda_bcc_email(rs.getString("esda_bcc_email"));
				model.setEsin_addname(rs.getString("esin_addname"));
				model.setEsin_addtime(rs.getString("esin_addtime"));
				model.setEsin_stopmonth(rs.getString("esin_stopmonth"));
				model.setEsin_taxplace(rs.getString("esin_taxplace"));
				model.setEsin_id(rs.getString("esin_id"));

				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取报盘数据txtdate下拉框
	public List<EmTXTFileInfoModel> getTXTDateList() {
		ResultSet rs = null;
		List<EmTXTFileInfoModel> list = new ArrayList<EmTXTFileInfoModel>();
		if (!list.isEmpty())
			list.clear();

		try {
			String sql = "SELECT DISTINCT etfi_txt_date FROM (SELECT CONVERT(varchar(10),GETDATE(),23) AS etfi_txt_date UNION ALL SELECT DISTINCT CONVERT(varchar(10),etfi_txt_date,23) AS etfi_txt_date FROM ETFI_EmBase_CoBase_V)a ORDER BY etfi_txt_date DESC";

			rs = conn.GRS(sql);
			while (rs.next()) {
				EmTXTFileInfoModel model = new EmTXTFileInfoModel();

				model.setEtfi_txt_date(rs.getString("etfi_txt_date"));
				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取报盘数据txtBatch下拉框
	public List<EmTXTFileInfoModel> getTXTBatchList(String txtdate) {
		ResultSet rs = null;
		List<EmTXTFileInfoModel> list = new ArrayList<EmTXTFileInfoModel>();
		if (!list.isEmpty())
			list.clear();

		try {
			String sql = "SELECT etfi_txt_batch FROM EmTXTFileInfo WHERE DATEDIFF(d,etfi_txt_date,'"
					+ txtdate
					+ "')=0 GROUP BY etfi_txt_batch ORDER BY etfi_txt_batch";

			rs = conn.GRS(sql);
			while (rs.next()) {
				EmTXTFileInfoModel model = new EmTXTFileInfoModel();

				model.setEtfi_txt_batch(rs.getString("etfi_txt_batch"));
				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取报盘数据
	public List<EmTXTFileInfoModel> getTXTDataList(String str) {
		ResultSet rs = null;
		List<EmTXTFileInfoModel> list = new ArrayList<EmTXTFileInfoModel>();
		if (!list.isEmpty())
			list.clear();

		try {
			String sql = "SELECT * FROM ETFI_EmBase_CoBase_V WHERE 1=1" + str
					+ " ORDER BY etfi_txt_batch,esda_id";

			rs = conn.GRS(sql);
			while (rs.next()) {

				EmTXTFileInfoModel model = new EmTXTFileInfoModel();

				model.setEtfi_id(rs.getInt("etfi_id"));
				model.setGid(rs.getInt("gid"));
				model.setCid(rs.getInt("cid"));
				model.setEsda_id(rs.getInt("esda_id"));
				model.setEtfi_payment_batch(rs.getString("etfi_payment_batch"));
				model.setEtfi_o_bank(rs.getString("etfi_o_bank"));
				model.setEtfi_bank(rs.getString("etfi_bank"));
				model.setEtfi_ba_name(rs.getString("etfi_ba_name"));
				model.setEtfi_bank_account(rs.getString("etfi_bank_account"));
				model.setEtfi_usage_type(rs.getInt("etfi_usage_type"));
				model.setEtfi_remit_ba(rs.getString("etfi_remit_ba"));
				model.setEtfi_remit_company(rs.getString("etfi_remit_company"));
				model.setEtfi_code(rs.getString("etfi_code"));
				model.setEtfi_pay(rs.getBigDecimal("etfi_pay"));
				model.setEtfi_txt_date(rs.getString("etfi_txt_date"));
				model.setEtfi_txt_people(rs.getString("etfi_txt_people"));
				model.setEtfi_txt_batch(rs.getString("etfi_txt_batch"));
				model.setEtfi_same_ban(rs.getString("etfi_same_ban"));
				model.setEtfi_same_ba(rs.getString("etfi_same_ba"));
				model.setEtfi_state(rs.getInt("etfi_state"));
				model.setOwnmonth(rs.getString("ownmonth"));
				model.setEsda_if_bms(rs.getString("esda_if_bms"));
				model.setEsda_write_off(rs.getBigDecimal("esda_write_off"));
				model.setEmba_name(rs.getString("emba_name"));
				model.setCoba_shortname(rs.getString("coba_shortname"));
				model.setState(rs.getString("state"));
				model.setUsage_type(rs.getString("usage_type"));

				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取报盘数据
	public List<EmTXTFileInfoModel> getTXTEmPayDataList(String txt_date) {
		ResultSet rs = null;
		List<EmTXTFileInfoModel> list = new ArrayList<EmTXTFileInfoModel>();
		if (!list.isEmpty())
			list.clear();

		try {
			String sql = "SELECT 'txt' as etype,etfi_id,gid,cid,esda_id,etfi_remit_ba,etfi_remit_company,etfi_bank_account,etfi_ba_name,etfi_pay,etfi_bank,usage_type,etfi_txt_date"
					+ " FROM ETFI_EmBase_CoBase_V WHERE DATEDIFF(d,etfi_txt_date,'"
					+ txt_date
					+ "')=0"
					+ " union all"
					+ " SELECT 'empay' as etype,a.id,a.cid,a.gid,0,'','',empa_account,empa_ba_name,empa_fee,empa_bank,'报销',empa_cashier_checkdate "
					+ " FROM EmPay a LEFT JOIN cobase b ON a.cid=b.cid "
					+ " WHERE a.empa_payclass='银行支付' AND DATEDIFF(d,a.empa_cashier_checkdate,'"
					+ txt_date + "')=0 " + "order by etfi_txt_date";

			rs = conn.GRS(sql);
			while (rs.next()) {

				EmTXTFileInfoModel model = new EmTXTFileInfoModel();
				model.setEtype(rs.getString("etype"));
				model.setEtfi_id(rs.getInt("etfi_id"));
				model.setGid(rs.getInt("gid"));
				model.setCid(rs.getInt("cid"));
				model.setEsda_id(rs.getInt("esda_id"));
				model.setEtfi_remit_ba(rs.getString("etfi_remit_ba"));
				model.setEtfi_remit_company(rs.getString("etfi_remit_company"));
				model.setEtfi_bank(rs.getString("etfi_bank"));
				model.setEtfi_ba_name(rs.getString("etfi_ba_name"));
				model.setEtfi_bank_account(rs.getString("etfi_bank_account"));
				model.setEtfi_pay(rs.getBigDecimal("etfi_pay"));
				model.setUsage_type(rs.getString("usage_type"));

				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取报盘数据合计
	public String[] getTXTCountPay(String txt_date) {
		ResultSet rs = null;
		String[] count_pay = new String[2];

		try {
			String sql = "SELECT ISNULL(SUM(etfi_pay),0) AS pay,COUNT(*) AS cou FROM EmTXTFileInfo WHERE DATEDIFF(d,etfi_txt_date,'"
					+ txt_date + "')=0";

			rs = conn.GRS(sql);
			while (rs.next()) {
				count_pay[0] = rs.getString("pay");
				count_pay[1] = rs.getString("cou");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count_pay;
	}

	// 查询是否有代发工资、代报个税报价单项目
	public int chkIfGZ(String str) {
		int i = 0;
		String sql = "SELECT COUNT(*)cou FROM CoOfferList a INNER JOIN CoCompact b ON a.coli_coco_id=b.coco_id AND a.coli_name IN('代发工资','代报个税','外国人代发工资','外国人代报税') "
				+ str;
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				i = rs.getInt("cou");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	// 获取存在工资算法的工资项目
	public List<CoSalaryItemFormulaModel> getNotJoinFormula(String csii_itemid) {
		ResultSet rs = null;
		List<CoSalaryItemFormulaModel> list = new ArrayList<CoSalaryItemFormulaModel>();
		if (!list.isEmpty())
			list.clear();

		try {
			String sql = "SELECT csii_id,csii_col,csii_item_name FROM CoSalaryItemInfo WHERE csii_csd_state<>2 AND csii_itemid='"
					+ csii_itemid
					+ "' AND NOT EXISTS(SELECT csii_id FROM (SELECT csii_id FROM CoFormulaData WHERE EXISTS(SELECT cfin_id FROM CoFormulaInfo WHERE cfin_delete=0 AND csii_itemid='"
					+ csii_itemid
					+ "' AND cfin_id=CoFormulaData.cfin_id))a WHERE csii_id=CoSalaryItemInfo.csii_id) ORDER BY csii_sequence";

			rs = conn.GRS(sql);

			while (rs.next()) {
				CoSalaryItemFormulaModel model = new CoSalaryItemFormulaModel();
				model.setCsii_id(rs.getInt("csii_id"));
				model.setCsii_col(rs.getString("csii_col"));
				model.setCsii_item_name(rs.getString("csii_item_name"));
				model.setItem_name("[" + rs.getString("csii_item_name") + "]");
				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// 获取算法内容字符串
	public String getFormulaString(String csii_itemid) {
		String formulaString = "";
		ResultSet rs = null;

		try {
			String sql = "SELECT cfda_formula FROM CoFormulaData WHERE EXISTS(SELECT cfin_id FROM CoFormulaInfo  WHERE cfin_delete=0 AND csii_itemid='"
					+ csii_itemid + "' AND cfin_id=CoFormulaData.cfin_id)";

			rs = conn.GRS(sql);

			while (rs.next()) {
				formulaString = formulaString + ","
						+ rs.getString("cfda_formula");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return formulaString;
	}

	// 获取公司可操作非清零项目数据更新的月份
	public String[] getZeroItemOwnmonth(int cid) {
		ResultSet rs = null;
		String[] monthList = null;
		int i = 0;

		try {
			String sql = "SELECT DISTINCT ownmonth FROM EmSalaryData WHERE esda_payment_state=3 AND cid="
					+ String.valueOf(cid) + " ORDER BY ownmonth DESC";
			rs = conn.GRS(sql);
			rs.last();
			if (rs.getRow() > 0) {
				monthList = new String[rs.getRow()];
				rs.first();
				do {
					monthList[i] = String.valueOf(rs.getInt("ownmonth"));
					i = i + 1;
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return monthList;
	}

	// 获取公司可操作非清零项目数据更新的工资数据
	public List<EmSalaryBaseSel_viewModel> getZeroItemEsdaDataList(int cid,
			int ownmonth) {
		ResultSet rs = null;
		List<EmSalaryBaseSel_viewModel> list = new ArrayList<EmSalaryBaseSel_viewModel>();
		if (!list.isEmpty())
			list.clear();

		try {
			String sql = "select esda_id,emba_name,esda_usage_type from EmSalaryBaseSel_view where esda_usage_type=0 AND esda_payment_state=3 AND cid="
					+ cid + " and ownmonth=" + ownmonth;

			rs = conn.GRS(sql);

			while (rs.next()) {
				EmSalaryBaseSel_viewModel model = new EmSalaryBaseSel_viewModel();
				model.setEsda_id(rs.getInt("esda_id"));
				model.setEmba_name(rs.getString("emba_name"));
				model.setEsda_usage_type(rs.getInt("esda_usage_type"));
				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// 获取公司可操作非清零项目数据更新的工资数据
	public List<EmSalaryBaseSel_viewModel> getZeroItemEsdaDataList(String str) {
		ResultSet rs = null;
		List<EmSalaryBaseSel_viewModel> list = new ArrayList<EmSalaryBaseSel_viewModel>();
		if (!list.isEmpty())
			list.clear();

		try {
			String sql = "select esda_id,emba_name,esda_usage_type,cfin_id,cfin_name from EmSalaryBaseSel_view where esda_usage_type=0 AND esda_payment_state=3 "
					+ str;

			rs = conn.GRS(sql);

			while (rs.next()) {
				EmSalaryBaseSel_viewModel model = new EmSalaryBaseSel_viewModel();
				model.setEsda_id(rs.getInt("esda_id"));
				model.setEmba_name(rs.getString("emba_name"));
				model.setEsda_usage_type(rs.getInt("esda_usage_type"));
				model.setCfin_id(rs.getInt("cfin_id"));
				model.setCfin_name(rs.getString("cfin_name"));
				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// 获取当天最新的一条银行余额数据
	public EmTXTFileSetModel getTXTBalance() {
		ResultSet rs = null;
		EmTXTFileSetModel model = new EmTXTFileSetModel();
		try {
			String sql = "select top 1 * from emtxtfileset where DATEDIFF(D,etfs_addtime,GETDATE())=0 order by etfs_id desc ";
			rs = conn.GRS(sql);
			while (rs.next()) {
				model.setEtfs_id(rs.getInt("etfs_id"));
				model.setEtfs_balance(rs.getBigDecimal("etfs_balance"));
				model.setEtfs_remaining(rs.getBigDecimal("etfs_remaining"));
				model.setEtfs_addname(rs.getString("etfs_addname"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
}
