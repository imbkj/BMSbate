package dal.CoFinanceManage;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.CoFinanceMonthlyBillModel;
import Model.EmFinanceDataByCPModel;
import Model.EmFinanceZYTModel;

public class EmFinanceWt_SelDal {
	private dbconn conn = new dbconn();

	// 按合同编号采集台账数据
	public List<EmFinanceDataByCPModel> getEmFinanceDataByCP(int ownmonth,
			int coco_id) {
		List<EmFinanceDataByCPModel> list = new ArrayList<EmFinanceDataByCPModel>();
		try {
			ResultSet rs = conn.GRS("exec EmFinanceDataByCP_p_lwj " + ownmonth
					+ "," + coco_id);
			EmFinanceDataByCPModel m;
			while (rs.next()) {
				m = new EmFinanceDataByCPModel();
				m.setGid(rs.getInt("gid"));
				m.setEmba_name(rs.getString("efba_emba_name"));
				m.setEmba_idcard(rs.getString("efba_emba_idcard"));
				m.setItemname(rs.getString("name"));
				m.setOtherEx(rs.getBigDecimal("other"));
				m.setReceivable(rs.getBigDecimal("Receivable"));
				m.setEfsb_ylao(rs.getBigDecimal("efsb_ylao"));// 养老
				m.setEfsb_yliao(rs.getBigDecimal("efsb_yliao"));// 医疗
				m.setEfsb_syu(rs.getBigDecimal("efsb_syu"));// 生育
				m.setEfsb_sye(rs.getBigDecimal("efsb_sye"));// 失业
				m.setEfsb_gs(rs.getBigDecimal("efsb_gs"));// 工伤
				m.setOwnmonth(ownmonth);
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 按合同编号采集台账数据应收总额
	public BigDecimal[] getEmFinanceTotalByCP(int ownmonth, int coco_id) {
		BigDecimal[] bd = new BigDecimal[2];
		try {
			ResultSet rs = conn.GRS("exec EmFinanceTotalByCP_p_lwj " + ownmonth
					+ "," + coco_id);
			if (rs.next()) {
				bd[0] = rs.getBigDecimal("total");
				bd[1] = rs.getBigDecimal("emfz_total");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bd;
	}

	// 查询智翼通数据和系统台帐数据
	public List<EmFinanceZYTModel> getEmFinanceTotalByCP_cyj(int ownmonth,// hh
			String coab_city, String coab_name, String str) {
		String namestr = "";
		if (coab_name != null && !coab_name.equals("")) {
			namestr = " and coab_name='" + coab_name + "'";
		}
		List<EmFinanceZYTModel> list = new ArrayList<EmFinanceZYTModel>();
		try {

			String sql = "select cid,ownmonth" + " into #emfiba"
					+ " from EmFinanceBase where ownmonth=" + ownmonth
					+ " group by cid,ownmonth;";
			sql += "select a.cid,coco_id,cabc_id"
					+ " into #coglist"
					+ " from CoGList a"
					+ " inner join CoOfferList b on a.cgli_coli_id=b.coli_id and coli_state=1"
					+ " inner join CoOffer coof on b.coli_coof_id=coof.coof_id and coof_state=3"
					+ " inner join CoCompact c on coof.coof_coco_id=c.coco_id and coco_compacttype not like '%CS%' and coco_state>3"
					+ " where cgli_startdate2<=isnull(cgli_stopdate,204912)"
					+ " and cgli_startdate<=" + ownmonth
					+ " and isnull(cgli_stopdate,204912)>=" + ownmonth
					+ " group by a.cid,coco_id,cabc_id;";
			sql += "select a.cid,a.gid,coco_id,cabc_id"
					+ " into #coglist2"
					+ " from CoGList a"
					+ " inner join CoOfferList b on a.cgli_coli_id=b.coli_id and coli_state=1"
					+ " inner join CoOffer coof on b.coli_coof_id=coof.coof_id and coof_state=3"
					+ " inner join CoCompact c on coof.coof_coco_id=c.coco_id and coco_compacttype not like '%CS%' and coco_state>3"
					+ " where  cgli_startdate2<=isnull(cgli_stopdate,204912)"
					+ " and cgli_startdate<=" + ownmonth
					+ " and isnull(cgli_stopdate,204912)>=" + ownmonth
					+ " group by a.cid,a.gid,coco_id,cabc_id;";
			sql += "select distinct cid,ownmonth,emfz_company,cabc_id,name,coab_name,coba_client,coco_id"
					+ " into #coba"
					+ " from ("
					+ "select a.cid,ownmonth,coba_company emfz_company,cabc_id,coab_city name,coab_name,coba_client,coco_id "
					+ " from EmFinanceZYT a"
					+ " inner join CoBase b on a.cid=b.cid"
					+ " inner join #coglist c on b.CID=c.cid"
					+ " inner join StAgencyBase_view d on c.cabc_id=d.coab_id and coab_city='"
					+ coab_city
					+ "'"
					+ namestr
					+ " where a.ownmonth="
					+ ownmonth
					+ " union all"
					+ " select a.cid,ownmonth,coba_company,cabc_id,coab_city name,coab_name, coba_client,coco_id"
					+ " from #emfiba a"
					+ " inner join CoBase b on a.cid=b.cid"
					+ " inner join #coglist c on b.CID=c.cid"
					+ " inner join StAgencyBase_view d on c.cabc_id=d.coab_id and coab_city='"
					+ coab_city
					+ "'"
					+ namestr
					+ " where a.ownmonth="
					+ ownmonth + "" + ")z;";
			/*
			 * sql +=
			 * " select sum(emfz_total) emfz_total,coco_id,a.cid,cabc_id into #zyt"
			 * + " from EmFinanceZYT a" +
			 * " inner join #coglist2 b on a.gid=b.gid" + " where a.ownmonth=" +
			 * ownmonth + " group by emfz_total,coco_id,a.cid,cabc_id;";
			 */
			sql += " select sum(emfz_total) emfz_total,a.cid into #zyt"
					+ " from EmFinanceZYT a" + " where a.ownmonth=" + ownmonth
					+ " group by a.cid;";
			sql += "select SUM(Receivable) Receivable,coco_id"
					+ " into #b"
					+ " from (select ISNULL(efsb_Receivable,0) as Receivable, efsb_coco_id coco_id"
					+ " from EmFinanceSheBao" + " where ownmonth="
					+ ownmonth
					+ " and efsb_state=1"
					+ " union all"
					+ " select ISNULL(efhg_Receivable,0) ,efhg_coco_id"
					+ " from EmFinanceHouseGjj"
					+ " where ownmonth="
					+ ownmonth
					+ " and efhg_state=1"
					+ " union all"
					+ " select ISNULL(efpr_Receivable,0),efpr_coco_id"
					+ " from EmFinanceProduct"
					+ " where ownmonth="
					+ ownmonth
					+ " and efpr_state=1 and efpr_cpac_name not in('税后工资','个调税','财务服务费')"
					+ " union all"
					+ " select ISNULL(efdi_Receivable,0),efdi_coco_id"
					+ " from EmFinanceDisposable"
					+ " where ownmonth="
					+ ownmonth
					+ " and efdi_state=1"
					+ " union all"
					+ " select ISNULL(cfpr_Receivable,0),cfpr_coco_id"
					+ " from CoFinanceProduct"
					+ " where ownmonth="
					+ ownmonth
					+ " and cfpr_state=1"
					+ " union all"
					+ " select ISNULL(cfdi_Receivable,0),cfdi_coco_id"
					+ " from CoFinanceDisposable"
					+ " where ownmonth="
					+ ownmonth + " and cfdi_state=1)z" + " group by coco_id;";
			sql += "select a.*,sum(isnull(emfz_total,0))emfz_total ,sum(isnull(emfz_total,0)) emfi_emfz_total from ("
					+ " select coba.cid,ownmonth, emfz_company,coba.cabc_id,name name,coab_name,coba_client,sum(isnull(Receivable,0))total,isnull(cfmb_PersonnelConfirm,-1)stateid"

					// sql +=
					// "select coba.cid,ownmonth, emfz_company,coba.cabc_id,name name,coab_name,coba_client,sum(isnull(Receivable,0))total,sum(isnull(emfz_total,0))emfz_total ,sum(isnull(emfz_total,0)) emfi_emfz_total,isnull(cfmb_PersonnelConfirm,-1)stateid"
					+ " from #coba coba"
					// +
					// " left join (select max(coco_id)coco_id,SUM(emfz_total)emfz_total from #zyt group by cid,cabc_id) zyt on coba.coco_id=zyt.coco_id"
					// +
					// " left join (select cid,SUM(emfz_total)emfz_total from #zyt group by cid) zyt on coba.cid=zyt.cid"
					+ " left join #b b on b.coco_id=coba.coco_id"
					+ " left join ("
					+ "select distinct cfmb_PersonnelConfirm,cfmb_code"
					+ " from CoFinanceMonthlyBill"
					+ " where cfmb_prefix='CP' and ownmonth="
					+ ownmonth
					+ ") bill on coba.coco_id=bill.cfmb_code"
					+ " where 1=1 "
					+ str
					+ " group by coba.cid ,ownmonth,coba.cabc_id,emfz_company,name,coab_name,coba_client,isnull(cfmb_PersonnelConfirm,-1)"
					+ ")a left join (select cid,SUM(emfz_total)emfz_total from #zyt group by cid) zyt on a.cid=zyt.cid"
					+ " group by a.cid,ownmonth, emfz_company,a.cabc_id,name ,coab_name,coba_client,total,stateid"
					+ " order by emfi_emfz_total desc";

			System.out.println(sql);
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				EmFinanceZYTModel m = new EmFinanceZYTModel();
				m.setCid(rs.getInt("cid"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setEmfz_company(rs.getString("emfz_company"));
				m.setCabc_id(rs.getInt("cabc_id"));
				m.setName(rs.getString("name"));
				m.setCoab_name(rs.getString("coab_name"));
				m.setCoba_client(rs.getString("coba_client"));
				m.setStateid(rs.getInt("stateid"));
				m.setEmfi_total(rs.getBigDecimal("total"));// 系统应收
				m.setTotal(rs.getBigDecimal("emfz_total"));// 智翼通应收
				m.setEmfi_emfz_total(rs.getBigDecimal("emfi_emfz_total"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据合同查询账单是否已确认（委托入账单）
	// 返回值（0未找到账单1账单已确认2账单未确认3出错）
	public int existsBillConfirmByCp(int ownmonth, int coco_id) {
		int i = 0;
		String sql = "select cfmb_PersonnelConfirm from CoFinanceMonthlyBill where cfmb_prefix='CP' and ownmonth=? and cfmb_code=?";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, ownmonth);
			psmt.setInt(2, coco_id);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				i = 1;
				if (rs.getInt("cfmb_PersonnelConfirm") == 0) {
					return 2;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			i = 3;
		}
		return i;
	}

	// 根据合同获取账单号（找不到账单时返回0）
	public String getBillNumberByCp(int ownmonth, int coco_id) {
		String billNo = "0";
		String sql = "select cfmb_number from CoFinanceMonthlyBill where cfmb_prefix='CP' and ownmonth=? and cfmb_code=?";
		// sql=sql+" and cfmb_PersonnelConfirm=0 and cfmb_FinanceConfirm=0";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, ownmonth);
			psmt.setInt(2, coco_id);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				billNo = rs.getString("cfmb_number");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return billNo;
	}

	// 根据合同获取账单号（找不到账单时返回0）
	public CoFinanceMonthlyBillModel getBillNumberByCpModel(int ownmonth,
			int coco_id) {
		CoFinanceMonthlyBillModel m = new CoFinanceMonthlyBillModel();
		String sql = "select * from CoFinanceMonthlyBill where cfmb_prefix='CP' and ownmonth=? and cfmb_code=?";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, ownmonth);
			psmt.setInt(2, coco_id);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				m.setCfmb_number(rs.getString("cfmb_number"));
				m.setCfmb_PersonnelConfirm(rs.getInt("cfmb_PersonnelConfirm"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}
}
