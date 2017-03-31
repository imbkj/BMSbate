package dal.EmFinanceManage;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.CoBaseModel;
import Model.CoFinanceCollectModel;
import Model.CoFinanceMonthlyBillSortAccountModel;
import Model.CollectAmountModel;
import Model.GatherInfoModel;
import Util.UserInfo;

public class GatherInfoDal {
	public List<GatherInfoModel> getOwnmonthBillList(String str) {
		List<GatherInfoModel> list = new ArrayList<GatherInfoModel>();
		String sql = "select distinct(cfsa_id),cpac_id,cpac_af,cpac_fs,cpac_fsaf,cfsa_cfmb_number,cfsa_cpac_name,"
				+ "coba_shortname,coba_ufid,coco_gzmonth,ownmonth,cfsa_PaidIn,coba_ufclass,cpac_depid,"
				+ "cpac_yyid,coba.cid from dbo.CoFinanceMonthlyBillSortAccount a inner "
				+ " join CoFinanceMonthlyBill b "
				+ "on a.cfsa_cfmb_number=b.cfmb_number inner join CoFinanceTotalAccount c "
				+ "on b.cfmb_cfta_id=c.cfta_id inner join cobase coba on c.cid=coba.cid "
				+ " left join (select cid,coco_gzmonth from CoCompact where coco_compacttype='CS' and (coco_state >3 and coco_state<>9) GROUP BY cid ,coco_gzmonth) coco on coba.cid=coco.cid and coco_gzmonth is not null and coco_gzmonth<>'' "
				+ " inner join CoPAccount copa on a.cfsa_cpac_name=copa.cpac_name where 1=1"
				+ str;
		sql = sql
				+ " and cfsa_cfmb_number in(select cfmb_number "
				+ " from CoFinanceMonthlyBill "
				+ "where (cfmb_TotalPaidIn+cfmb_LoanBalance+cfmb_CarryForwardEx-(cfmb_PersonnelReceivable+cfmb_FinanceReceivable))=0)"
				+ " and cfsa_PaidIn<>0 order by coba.cid";
		System.out.println(sql);
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				GatherInfoModel m = new GatherInfoModel();
				m.setCpac_id(rs.getInt("cpac_id"));
				m.setCpac_af(rs.getString("cpac_af"));
				m.setCpac_fs(rs.getString("cpac_fs"));
				m.setCpac_fsaf(rs.getString("cpac_fsaf"));
				m.setCfsa_cfmb_number(rs.getString("cfsa_cfmb_number"));
				m.setCfsa_cpac_name(rs.getString("cfsa_cpac_name"));
				m.setCoba_shortname(rs.getString("coba_shortname"));
				m.setCoba_ufid(rs.getString("coba_ufid"));
				m.setCoco_gzmonth(rs.getString("coco_gzmonth"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setCfsa_paidin(rs.getBigDecimal("cfsa_PaidIn"));
				m.setCoba_ufclass(rs.getString("coba_ufclass"));
				m.setDepnumber(rs.getString("cpac_depid"));
				m.setProjectnumber(rs.getString("cpac_yyid"));
				m.setCpac_name(UserInfo.getUsername());
				m.setCfsa_id(rs.getInt("cfsa_id"));
				m.setCid(rs.getInt("cid"));
				list.add(m);
			}
		} catch (Exception e) {

		}
		return list;
	}

	// 查询明细
	public List<CoFinanceCollectModel> getCollectListss(int ownmonth, int cid,
			String cfss_cfso_id) {
		List<CoFinanceCollectModel> list = new ArrayList<CoFinanceCollectModel>();
		String sql = "select CONVERT(varchar(10),cfss_addtime,120) cfss_addtime"
				+ ",* from CoFinanceSortAccountss a "
				+ "where cfss_cfso_id is not null and ownmonth="
				+ ownmonth
				+ " and cid=" + cid + " and cfss_cfso_id='" + cfss_cfso_id;
		sql = sql + "'  order by a.cfss_addtime desc";
		// System.out.println(sql);
		try {
			dbconn db = new dbconn();
			PreparedStatement pstmt = db.getpre(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CoFinanceCollectModel m = new CoFinanceCollectModel();
				m.setCfsa_cpac_name(rs.getString("cfss_cpac_name"));
				m.setCfsa_PaidIn(rs.getBigDecimal("cfss_Receivable"));
				m.setCfta_addtime(rs.getString("cfss_addtime"));
				m.setCfta_addname(rs.getString("cfss_addname"));
				m.setCfta_modname(rs.getString("cfss_modname"));
				m.setCfmb_remark(rs.getString("cfss_remark"));
				m.setCfss_cfso_id(rs.getString("cfss_cfso_id"));

				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<GatherInfoModel> getOwnmonthBillListss(String str) {
		List<GatherInfoModel> list = new ArrayList<GatherInfoModel>();
		String sql = "SELECT cpac_id,cpac_af,cpac_fs,cpac_fsaf,cfss_cpac_name,coba_shortname,cpac_depid,"
				+ "coba_ufid,coco_gzmonth,cfss_cfso_id,ownmonth,cfss_Receivable,coba_ufclass,"
				+ "cpac_depid,cpac_yyid,a.cid FROM cobase a "
				+ "inner join CoFinanceSortAccountss cf  on a.cid=cf.cid "
				+ "left join (select cid,coco_gzmonth from CoCompact where coco_compacttype='CS' and (coco_state >3 and coco_state<>9) GROUP BY cid ,coco_gzmonth)     coco on a.cid=coco.cid "
				+ "inner join CoPAccount copa on cf.cfss_cpac_name=copa.cpac_name "
				+ "where cfss_cfso_id is not null " + str;
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				GatherInfoModel m = new GatherInfoModel();
				m.setCpac_id(rs.getInt("cpac_id"));
				m.setCpac_af(rs.getString("cpac_af"));
				m.setCpac_fs(rs.getString("cpac_fs"));
				m.setCpac_fsaf(rs.getString("cpac_fsaf"));
				m.setCfsa_cpac_name(rs.getString("cfss_cpac_name"));
				m.setCoba_shortname(rs.getString("coba_shortname"));
				m.setCoba_ufid(rs.getString("coba_ufid"));
				m.setCoco_gzmonth(rs.getString("coco_gzmonth"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setCfsa_paidin(rs.getBigDecimal("cfss_Receivable"));
				m.setCoba_ufclass(rs.getString("coba_ufclass"));
				m.setDepnumber(rs.getString("cpac_depid"));
				m.setProjectnumber(rs.getString("cpac_yyid"));
				m.setCpac_name(UserInfo.getUsername());
				m.setCfss_cfso_id(rs.getString("cfss_cfso_id"));
				m.setCid(rs.getInt("cid"));
				list.add(m);
			}
		} catch (Exception e) {

		}
		return list;
	}

	public List<GatherInfoModel> getOwnmonthBillListssnew(String str) {
		List<GatherInfoModel> list = new ArrayList<GatherInfoModel>();
		String sql = "SELECT cpac_id,cpac_af,cpac_fs,cpac_affs,cpac_fsaf,cfss_cpac_name,coba_shortname,cpac_depid,"
				+ "coba_ufid,coco_gzmonth,cfss_cfso_id,ownmonth,cfss_Receivable,coba_ufclass,"
				+ "cpac_depid,cpac_yyid,a.cid,cfss_type,coba_ufclass FROM cobase a "
				+ "inner join CoFinanceSortAccountssnew cf  on a.cid=cf.cid "
				+ "left join (select cid,coco_gzmonth from CoCompact where coco_compacttype='CS' AND coco_gzmonth<>'' and (coco_state >3 and coco_state<>9) GROUP BY cid ,coco_gzmonth)     coco on a.cid=coco.cid "
				+ "inner join CoPAccount copa on cf.cfss_cpac_name=copa.cpac_name "
				+ " LEFT JOIN (	select stct_km,a.cpac_name,cpac_orderaf,cpac_orderfs from CoPAccount a "
				+ " left join  Salestaxcompute b ON a.cpac_stct_id=b.stct_id ) cpa ON cf.cfss_cpac_name=cpa.cpac_name "
				+ " where cfss_cfso_id is not null "
				+ str
				+ "    ORDER BY  CASE cf.cfss_type+a.coba_ufclass when '非派遣224105' THEN   cpa.cpac_orderaf "
				+ "when '非派遣224106'  THEN cpa.cpac_orderfs   when '派遣224105' then cpa.cpac_orderaf  "
				+ "  when '派遣224106' then cpa.cpac_orderaf end";
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				GatherInfoModel m = new GatherInfoModel();
				m.setCpac_id(rs.getInt("cpac_id"));
				m.setCpac_af(rs.getString("cpac_af"));
				m.setCpac_fs(rs.getString("cpac_fs"));
				m.setCfss_type(rs.getString("cfss_type"));
				m.setCpac_affs(rs.getString("cpac_affs"));
				m.setCpac_fsaf(rs.getString("cpac_fsaf"));
				m.setCfsa_cpac_name(rs.getString("cfss_cpac_name"));
				m.setCoba_shortname(rs.getString("coba_shortname"));
				m.setCoba_ufid(rs.getString("coba_ufid"));
				m.setCoco_gzmonth(rs.getString("coco_gzmonth"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setCfsa_paidin(rs.getBigDecimal("cfss_Receivable"));
				m.setCoba_ufclass(rs.getString("coba_ufclass"));
				m.setDepnumber(rs.getString("cpac_depid"));
				m.setProjectnumber(rs.getString("cpac_yyid"));
				m.setCpac_name(UserInfo.getUsername());
				m.setCfss_cfso_id(rs.getString("cfss_cfso_id"));
				m.setCid(rs.getInt("cid"));
				m.setCoba_ufclass(rs.getString("coba_ufclass"));

				list.add(m);
			}
		} catch (Exception e) {

		}
		return list;
	}

	public GatherInfoModel getOwnmonthBillListss2(String str) {
		GatherInfoModel m = new GatherInfoModel();
		String sql = "SELECT distinct(cfss_cfso_id),sum(cfss_Receivable) cfss_Receivable,"
				+ "coba_shortname,coba_ufid,coco_gzmonth,cfss_cfso_id,ownmonth,coba_ufclass,cfss_type "
				+ "FROM cobase a inner join CoFinanceSortAccountssnew cf  on a.cid=cf.cid "
				+ "left join (select cid,coco_gzmonth from CoCompact where coco_compacttype='CS' and (coco_state >3 and coco_state<>9) GROUP BY cid ,coco_gzmonth) coco on a.cid=coco.cid inner join CoPAccount copa "
				+ "on cf.cfss_cpac_name=copa.cpac_name where cfss_cfso_id is not null "
				+ str
				+ "group by cfss_cfso_id,coba_shortname,coba_ufid,coco_gzmonth,cfss_cfso_id,"
				+ "ownmonth,coba_ufclass,cfss_type";
		System.out.println(sql);
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {

				m.setCoba_shortname(rs.getString("coba_shortname"));
				m.setCoba_ufid(rs.getString("coba_ufid"));
				m.setCoco_gzmonth(rs.getString("coco_gzmonth"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setCpac_name(UserInfo.getUsername());
				m.setCfss_cfso_id(rs.getString("cfss_cfso_id"));
				m.setCfss_type(rs.getString("cfss_type"));
				m.setSumpin(rs.getBigDecimal("cfss_Receivable"));
				m.setCfsa_paidin(getskjs(rs.getString("cfss_type"),
						rs.getString("cfss_cfso_id"),
						rs.getString("coba_ufclass")));
				m.setCoba_ufclass(rs.getString("coba_ufclass"));

			}
		} catch (Exception e) {

		}
		return m;
	}

	// 用友收款计算 AFAF/AFFS/FS
	public BigDecimal getskjs(String cfss_type, String cfss_cfso_id,
			String coba_ufclass) {
		BigDecimal sum = BigDecimal.ZERO;
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT SUM(cfss_Receivable) cfss_Receivable FROM CoFinanceSortAccountssnew");
		sql.append(" where cfss_cpac_name <>'税金' and cfss_cfso_id='");
		sql.append(cfss_cfso_id);
		sql.append("'  and cfss_cpac_name NOT in (SELECT  b.cpac_name FROM Salestaxcompute  a INNER JOIN");
		sql.append("  CoPAccount b  on a.stct_id=b.cpac_stct_id WHERE  a.stct_km IN ( ");

		if (cfss_type.equals("派遣")) {

			if (!"".equals(coba_ufclass) && coba_ufclass.equals("224105")) // 派遣AF
			{
				sql.append("'服务类'))");
			} else if (!"".equals(coba_ufclass)
					&& coba_ufclass.equals("224106")) // 派遣FS
			{
				sql.append("'服务类'))");
			} else {
				sql.append("''))");
			}

		}

		else if (cfss_type.equals("非派遣"))// 非派遣
		{

			if (!"".equals(coba_ufclass) && coba_ufclass.equals("224105")) // 派遣AF
			{
				sql.append("'服务类','福利类'))");
			} else if (!"".equals(coba_ufclass)
					&& coba_ufclass.equals("224106")) // 派遣FS
			{
				sql.append("''))");
			} else {
				sql.append("''))");
			}
		} else if (cfss_type.equals("全外包")) {
			sql.append("''))");
		}

		System.out.print(sql.toString());
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql.toString());
			while (rs.next()) {
				sum = rs.getBigDecimal("cfss_Receivable");
			}
		} catch (Exception e) {

		}

		return sum;

	}

	public CoBaseModel getClientClass(Integer cid) {
		CoBaseModel m = new CoBaseModel();
		String sql = "select * from cobase where cid=" + cid;
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				m.setCoba_company(rs.getString("coba_company"));
				m.setCoba_shortname(rs.getString("coba_shortname"));
				m.setCoba_ufid(rs.getString("coba_ufid"));
				m.setCoba_ufid2(rs.getString("coba_ufid2"));
				m.setCoba_ufclass(rs.getString("coba_ufclass"));
				m.setCid(rs.getInt("cid"));
			}
		} catch (Exception e) {

		}
		return m;
	}

	public String getCocompact(Integer cid) {
		// String sql =
		// "select * from cocompact where coco_gzmonth is not null and coco_gzmonth<>'' and cid="
		// + cid;
		String sql = "select cid,coco_gzmonth from CoCompact where coco_gzmonth is not null and coco_gzmonth<>'' and "
				+ " coco_compacttype='CS' and (coco_state >3 and coco_state<>9)  and cid="
				+ cid + " GROUP BY cid ,coco_gzmonth ";

		dbconn db = new dbconn();
		String coco_gzmonth = "";
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				coco_gzmonth = rs.getString("coco_gzmonth");
			}
		} catch (Exception e) {

		}
		return coco_gzmonth;
	}

	public Integer updateYyOutState(String cfss_cfso_id, int cid, int ownmonth) {
		Integer k = 0;
		String sql = "update CoFinanceSortAccountss set cfss_yystate=1 where cfss_cfso_id='"
				+ cfss_cfso_id
				+ "' and cid="
				+ cid
				+ " and ownmonth="
				+ ownmonth;
		String sql1 = "update CoFinanceSortAccountssnew set cfss_yystate=1 where cfss_cfso_id='"
				+ cfss_cfso_id
				+ "' and cid="
				+ cid
				+ " and ownmonth="
				+ ownmonth;

		dbconn db = new dbconn();
		k = db.execQuery(sql);
		k = db.execQuery(sql1);
		return k;
	}

	public Integer updateCoba(CoBaseModel m) {
		Integer k = 0;
		String sql = "update cobase set coba_ufid='" + m.getCoba_ufid()
				+ "',coba_ufid2='" + m.getCoba_ufid2() + "',coba_ufclass='"
				+ m.getCoba_ufclass() + "' where cid=" + m.getCid();
		dbconn db = new dbconn();
		k = db.execQuery(sql);
		return k;
	}

	// 获取实收账单
	public List<CoFinanceCollectModel> getCollectList(String sqls) {
		List<CoFinanceCollectModel> list = new ArrayList<CoFinanceCollectModel>();
		dbconn db = new dbconn();
		String sql = "select distinct(cfmb_number) as cfco_cfmb_number ,cfmb_TotalPaidIn,"
				+ " b.ownmonth,c.cid,convert(varchar(250),isnull(cfmb_remark,'')) cfmb_remark,"
				+ " convert(varchar(10),cfco_addtime,120) cfta_addtime "
				+ " from  CoFinanceMonthlyBill b inner join CoFinanceTotalAccount c "
				+ " on b.cfmb_cfta_id=c.cfta_id inner join(select max(cfco_addtime) cfco_addtime,"
				+ " cid,cfco_cfmb_number from CoFinanceCollect where cfco_cfmb_number<>'0' "
				+ " GROUP BY cid,cfco_cfmb_number,cfco_addtime) coct "
				+ " on c.cid=coct.cid and b.cfmb_number=coct.cfco_cfmb_number "
				+ " inner join cobase coba on c.cid=coba.cid  "
				+ " left join (select cid,coco_gzmonth from CoCompact where coco_compacttype='CS' and (coco_state >3 and coco_state<>9) GROUP BY cid ,coco_gzmonth) coco on coba.cid=coco.cid and coco_gzmonth is not null and coco_gzmonth<>'' "
				+ " where 1=1 "
				+ sqls
				+ " and cfmb_number "
				+ " in(select cfmb_number from CoFinanceMonthlyBill "
				+ " where (cfmb_TotalPaidIn+cfmb_LoanBalance+cfmb_CarryForwardEx-"
				+ " (cfmb_PersonnelReceivable+cfmb_FinanceReceivable))=0 "
				+ " and ownmonth is not null) order by c.cid";
		System.out.println(sql);
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				CoFinanceCollectModel m = new CoFinanceCollectModel();
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setCid(rs.getInt("cid"));
				m.setCfco_cfmb_number(rs.getString("cfco_cfmb_number"));
				m.setCfmb_TotalPaidIn(rs.getBigDecimal("cfmb_TotalPaidIn"));
				m.setCfmb_remark(rs.getString("cfmb_remark"));
				m.setCfta_addtime(rs.getString("cfta_addtime"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 添加收款页面获取收款信息
	public List<CoBaseModel> getCoinfoListssNew(String sqlstr) {
		List<CoBaseModel> list = new ArrayList<CoBaseModel>();
		String sql = "SELECT a.cid,sum(cfss_Receivable) cfss_Receivable,"
				+ "coba_company,coba_shortname,coba_client,cf.cfss_cfso_id,"
				+ "coba_ufclass,isnull(coba_ufid,'')coba_ufid,ownmonth,cfss_remark,"
				+ "case cfss_yystate when 0 then '未录' else '已录' end outstate,cfss_type, "
				+ "CASE cfss_fpstate WHEN 0 THEN '未开票' ELSE '已开票'  end cfss_fpstate ,"
				+ " CONVERT(varchar(10),cf.cfss_addtime,120)cfss_addtime,cfss_addname,cfss_modname,CONVERT(varchar(10),cf.cfss_modtime,120)cfss_modtime"
				+ ",finanServiceFee,deformityFee,fileManageFee,serviceFee,oMoveFee,Accountfee,activityFee,residencePermitFee,lasscFee,other,businessProtectFee,businessServiceFee,sheBaoFee,bookFee,salaryOfAfterTax,bodyTestFee,recruitServiceFee,houseGjj,houseRestore"
				+ " ,isnull(cfva_sp,0)cfva_sp,isnull(cfva_confirm,0)cfva_confirm"
				+ " FROM cobase a inner join CoFinanceSortAccountss cf  on a.cid=cf.cid"
				+ " left join (select  cfss_cfso_id,[财务服务费]finanServiceFee,[残保金]deformityFee,[档案保管费]fileManageFee,[服务费]serviceFee,[个调税]oMoveFee,[户口]Accountfee,[活动费]activityFee,[居住证]residencePermitFee,[劳动保障卡]lasscFee,[其它]other,[商保费]businessProtectFee,[商务服务费]businessServiceFee,[社保费]sheBaoFee,[书报费]bookFee,[税后工资]salaryOfAfterTax,[体检费]bodyTestFee,[招聘服务费]recruitServiceFee,[住房公积金]houseGjj,[住房返还]houseRestore"
				+ " from(select cfss_cfso_id,cfss_cpac_name,cfss_Receivable from CoFinanceSortAccountss a )p"
				+ " pivot (sum(cfss_receivable) for cfss_cpac_name in ([财务服务费],[残保金],[档案保管费],[服务费],[个调税],[户口],[活动费],[居住证],[劳动保障卡],[其它],[商保费],[商务服务费],[社保费],[书报费],[税后工资],[体检费],[招聘服务费],[住房公积金],[住房返还]))pvt"
				+ ")c on cf.cfss_cfso_id=c.cfss_cfso_id"
				+ " left join (select cid,MAX(cfva_sp)cfva_sp,MAX(cfva_confirm)cfva_confirm from CoFinanceVAT group by cid)d on a.CID=d.cid"
				+ " where cf.cfss_cfso_id is not null " + sqlstr;
		sql = sql
				+ " group by a.cid,coba_company,coba_shortname,coba_client,"
				+ "cf.cfss_cfso_id,coba_ufclass,cfss_yystate,cfss_fpstate,cfss_type,coba_ufid,ownmonth,cfss_remark ,CONVERT(varchar(10),cf.cfss_addtime,120),cfss_addname,cfss_modname,CONVERT(varchar(10),cf.cfss_modtime,120)"
				+ ",finanServiceFee,deformityFee,fileManageFee,serviceFee,oMoveFee,Accountfee,activityFee,residencePermitFee,lasscFee,other,businessProtectFee,businessServiceFee,sheBaoFee,bookFee,salaryOfAfterTax,bodyTestFee,recruitServiceFee,houseGjj,houseRestore,cfva_sp,cfva_confirm"
				+ " order by CONVERT(varchar(10),cf.cfss_addtime,120) desc,MAX(cfss_addtime)desc, ownmonth";
		System.out.println(sql);
		try {
			dbconn db = new dbconn();
			PreparedStatement pstmt = db.getpre(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CoBaseModel m = new CoBaseModel();
				m.setCid(rs.getInt("cid"));
				m.setCoba_company(rs.getString("coba_company"));
				m.setCoba_client(rs.getString("coba_client"));
				m.setCoba_ufclass(rs.getString("coba_ufclass"));
				m.setCoba_ufid(rs.getString("coba_ufid"));
				m.setCoba_shortname(rs.getString("coba_shortname"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setOutstate(rs.getString("outstate"));
				m.setCfss_cfso_id(rs.getString("cfss_cfso_id"));
				m.setCfss_fpstate(rs.getString("cfss_fpstate"));
				m.setCfss_type(rs.getString("cfss_type"));
				m.setTotal(rs.getBigDecimal("cfss_Receivable"));
				m.setCfss_addtime(rs.getString("cfss_addtime"));
				m.setCfss_addname(rs.getString("cfss_addname"));
				m.setCfss_modname(rs.getString("cfss_modname"));
				m.setCfss_modtime(rs.getString("cfss_modtime"));
				m.setCfss_remark(rs.getString("cfss_remark"));
				m.setCfva_sp(rs.getBoolean("cfva_sp"));
				m.setCfva_confirm(rs.getBoolean("cfva_confirm"));
				CollectAmountModel am = new CollectAmountModel();
				if (m.getTotal() != null)
					am.setCfmb_TotalPaidIn(m.getTotal());

				am.setFileManageFee(rs.getBigDecimal("fileManageFee"));
				am.setServiceFee(rs.getBigDecimal("serviceFee"));
				am.setSheBaoFee(rs.getBigDecimal("sheBaoFee"));
				am.setActivityFee(rs.getBigDecimal("activityFee"));
				am.setBodyTestFee(rs.getBigDecimal("bodyTestFee"));
				am.setBusinessProtectFee(rs.getBigDecimal("businessProtectFee"));
				am.setBookFee(rs.getBigDecimal("bookFee"));
				am.setSalaryOfAfterTax(rs.getBigDecimal("salaryOfAfterTax"));
				am.setoMoveFee(rs.getBigDecimal("oMoveFee"));
				am.setAccountfee(rs.getBigDecimal("accountfee"));
				am.setHouseRestore(rs.getBigDecimal("houseRestore"));
				am.setFinanServiceFee(rs.getBigDecimal("finanServiceFee"));
				am.setBusinessServiceFee(rs.getBigDecimal("businessServiceFee"));
				am.setRecruitServiceFee(rs.getBigDecimal("recruitServiceFee"));
				am.setResidencePermitFee(rs.getBigDecimal("residencePermitFee"));
				am.setLasscFee(rs.getBigDecimal("lasscFee"));
				am.setDeformityFee(rs.getBigDecimal("deformityFee"));
				am.setOther(rs.getBigDecimal("other"));
				am.setHouseGjj(rs.getBigDecimal("houseGjj"));

				m.setAmount(am);
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取到cobase的信息
	public List<CoBaseModel> getCoinfoListss(String sqlstr) {
		List<CoBaseModel> list = new ArrayList<CoBaseModel>();
		String sql = "SELECT distinct(a.cid) cid,sum(cfss_Receivable) cfss_Receivable,"
				+ "coba_company,coba_shortname,coba_client,cfss_cfso_id,"
				+ "coba_ufclass,isnull(coba_ufid,'')coba_ufid,ownmonth,cfss_remark,"
				+ "case cfss_yystate when 0 then '未录' else '已录' end outstate,cfss_type, "
				+ "CASE cfss_fpstate WHEN 0 THEN '未开票' ELSE '已开票'  end cfss_fpstate ,"
				+ " CONVERT(varchar(10),cf.cfss_addtime,120) "
				+ " FROM cobase a inner join CoFinanceSortAccountss cf  on a.cid=cf.cid "
				+ " where cfss_cfso_id is not null " + sqlstr;
		sql = sql
				+ " group by a.cid,coba_company,coba_shortname,coba_client,"
				+ "cfss_cfso_id,coba_ufclass,cfss_yystate,cfss_fpstate,cfss_type,coba_ufid,ownmonth,cfss_remark ,CONVERT(varchar(10),cf.cfss_addtime,120)"
				+ " order by CONVERT(varchar(10),cf.cfss_addtime,120)  desc, ownmonth";
		// System.out.println(sql);
		try {
			dbconn db = new dbconn();
			PreparedStatement pstmt = db.getpre(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CoBaseModel m = new CoBaseModel();
				m.setCid(rs.getInt("cid"));
				m.setCoba_company(rs.getString("coba_company"));
				m.setCoba_client(rs.getString("coba_client"));
				m.setCoba_ufclass(rs.getString("coba_ufclass"));
				m.setCoba_ufid(rs.getString("coba_ufid"));
				m.setCoba_shortname(rs.getString("coba_shortname"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setOutstate(rs.getString("outstate"));
				m.setCfss_cfso_id(rs.getString("cfss_cfso_id"));
				m.setCfss_fpstate(rs.getString("cfss_fpstate"));
				m.setCfss_type(rs.getString("cfss_type"));
				// System.out.print("total="+rs.getBigDecimal("cfss_Receivable"));
				m.setTotal(rs.getBigDecimal("cfss_Receivable"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取到cobase的信息
	public List<CoBaseModel> getCoinfoList(String sqlstr, String ownmonth,
			String sqls) {
		List<CoBaseModel> list = new ArrayList<CoBaseModel>();
		if (ownmonth != null && !ownmonth.equals("")) {
			sqls = sqls + " and ownmonth=" + ownmonth;
		}
		String sql = "SELECT a.cid,coba_company,coba_shortname,coba_client,coba_ufclass,"
				+ "isnull(coba_ufid,'')coba_ufid,ownmonth,"
				+ "case cfsa_yyoutstate when 0 then '已录' else '未录' end outstate"
				+ " FROM cobase a "
				+ "  inner join (select distinct(cid) cid,ownmonth,cfsa_yyoutstate "
				+ " from CoFinanceMonthlyBill a inner join CoFinanceTotalAccount c "
				+ " on a.cfmb_cfta_id=c.cfta_id left join(select distinct(cfsa_cfmb_number),"
				+ " cfsa_yyoutstate from CoFinanceMonthlyBillSortAccount where cfsa_yyoutstate=0) cf "
				+ " on a.cfmb_number=cf.cfsa_cfmb_number where (cfmb_TotalPaidIn+cfmb_LoanBalance+"
				+ " cfmb_CarryForwardEx-(cfmb_PersonnelReceivable+cfmb_FinanceReceivable))=0 "
				+ "and ownmonth is not null"
				+ sqls
				+ ") b on a.cid=b.cid "
				+ " where 1=1 " + sqlstr + " order by ownmonth desc";
		System.out.println(sql);
		try {
			dbconn db = new dbconn();
			PreparedStatement pstmt = db.getpre(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CoBaseModel m = new CoBaseModel();
				m.setCid(rs.getInt("cid"));
				m.setCoba_company(rs.getString("coba_company"));
				m.setCoba_client(rs.getString("coba_client"));
				m.setCoba_ufclass(rs.getString("coba_ufclass"));
				m.setCoba_ufid(rs.getString("coba_ufid"));
				m.setCoba_shortname(rs.getString("coba_shortname"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setOutstate(rs.getString("outstate"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据账单号查询项目费用
	public List<CoFinanceMonthlyBillSortAccountModel> getAmountList(
			String number) {
		List<CoFinanceMonthlyBillSortAccountModel> list = new ArrayList<CoFinanceMonthlyBillSortAccountModel>();
		String sql = "SELECT * FROM CoFinanceMonthlyBillSortAccount WHERE cfsa_cfmb_number = ?";
		dbconn db = new dbconn();
		try {
			list = db.find(sql, CoFinanceMonthlyBillSortAccountModel.class,
					null, number);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
