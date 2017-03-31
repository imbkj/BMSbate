package dal.EmFinanceManage;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.CoFinanceSortAccountssModel;
import Model.FinanceInvoiceModel;

public class CoFinanceSortAccountssNewDal {
	private CoFinanceSortAccountssNewDal() {
	}

	private static final CoFinanceSortAccountssNewDal single = new CoFinanceSortAccountssNewDal();

	// 静态工厂方法
	public static CoFinanceSortAccountssNewDal getInstance() {
		return single;
	}

	public List<CoFinanceSortAccountssModel> getList(String cfso_id) {
		dbconn db = new dbconn();
		List<CoFinanceSortAccountssModel> list = new ListModelList<>();
		String sql = "select cfss_id,cfss_cfso_id,cid,ownmonth,cfss_cpac_name,cfss_Receivable,cfss_type,cfss_allin,cfss_state"
				+ " from CoFinanceSortAccountss"
				+ " where cfss_state=1 and cfss_cfso_id=? and cfss_Receivable!=0";
		try {
			list = db.find(sql, CoFinanceSortAccountssModel.class, null,
					cfso_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// 查询发票信息
	public List<FinanceInvoiceModel> getInvoiceList(String cfso_id) {
		List<FinanceInvoiceModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select a.*,total,case when b.djh!='' then 1 else 0 end openState"
				+ " from openquery([invoice],'select * from bills') a"
				+ " left join (select * from openquery([invoice],'select * from billinvoice'))b on a.djh=b.djh"
				+ " left join (select djh,SUM(je)total from openquery([invoice],'select * from billdetails') group by djh)c on a.djh=c.djh"
				+ " where a.djh like '" + cfso_id + "-%'";
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				FinanceInvoiceModel m = new FinanceInvoiceModel();
				m.setDjh(rs.getString("djh"));
				m.setRq(rs.getDate("rq"));
				m.setTotal(rs.getBigDecimal("total"));
				m.setKpr(rs.getString("kpr"));
				m.setFplxName(rs.getInt("fplx") > 0 ? "专票" : "普票");
				list.add(m);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// 往批量打印录入数据
	public Integer addinfo(String djh, String fplx, String gfmc, String gfsh,
			String gfyhzh, String gfdzdh, String bz, String addname) {
		Integer i = 0;
		dbconn db = new dbconn();

		if (bz != null) {
			if (bz.equals("null")) {
				bz = "";
			}
		}else {
			bz="";
		}
		i = db.execQuery("insert into openquery([invoice],'select * from zz.bills')"
				+ "select '"
				+ djh
				+ "',getdate(),'"
				+ fplx
				+ "','"
				+ gfmc
				+ "','"
				+ gfsh
				+ "','"
				+ gfyhzh
				+ "','"
				+ gfdzdh
				+ "','平安银行深圳长城支行11002875321101','深圳市福田区深南中路1002号新闻大厦31楼82092417','"
				+ bz
				+ "','"
				+ addname
				+ "','邓彩燕','何柳萍'");
	
		
		return i;
	}

	// 明细表
	public Integer addDetail(String djh, Integer xh, String spmc, String ggxh,
			String jldw, String je, String slv, String spsm, String ssbm) {
		Integer i = 0;
		dbconn db = new dbconn();

		if (ggxh != null) {
			if (ggxh.equals("null") || ggxh.equals("")) {
				ggxh = null;
			}
		}
		if (jldw != null) {
			if (jldw.equals("null") || jldw.equals("")) {
				jldw = null;
			}
		}
		if (je != null) {
			if (je.equals("null") || je.equals("")) {
				je = "0";
			} else {
				BigDecimal bd = new BigDecimal(je);
				bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
				je = bd.toString();
			}
		}
		if (slv != null) {
			if (slv.equals("null") || slv.equals("")) {
				slv = null;
			}
		}
		if (spsm != null) {
			if (spsm.equals("null") || spsm.equals("")) {
				spsm = null;
			}
		}
		if (ssbm != null) {
			if (ssbm.equals("null") || ssbm.equals("")) {
				ssbm = null;
			}
		}
	
		i = db.execQuery("insert into openquery([invoice],'select * from zz.billdetails')"
				+ "select isnull(MAX(convert(int,id)),0)+1,'"
				+ djh
				+ "','"
				+ xh
				+ "','"
				+ spmc
				+ "',"
				+ ggxh
				+ ","
				+ jldw
				+ ","
				+ je
				+ ","
				+ slv
				+ ","
				+ spsm
				+ ","
				+ ssbm
				+ " from openquery([invoice],'select * from zz.billdetails')");
		
		
		return i;
	}
}
