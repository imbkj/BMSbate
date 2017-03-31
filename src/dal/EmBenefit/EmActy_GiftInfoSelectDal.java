package dal.EmBenefit;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.EmActyGiftBackInfoModel;
import Model.EmActyGiftOutInfoModel;
import Model.EmActySuppilerGiftInfoModel;
import Model.EmActySupplierInfoModel;
import Model.EmWelfareModel;

public class EmActy_GiftInfoSelectDal {
	// 查询员工活动——礼品库存信息supp_id
	public List<EmActySuppilerGiftInfoModel> getEmActyGiftInfo(String str) {
		List<EmActySuppilerGiftInfoModel> list = new ArrayList<EmActySuppilerGiftInfoModel>();
		dbconn db = new dbconn();
		String sql = "select gift_id,gift_name,gift_brand,gift_color,gift_production,gift_class,gift_inaddress,gift_paytype,"
				+ "convert(char(10),gift_intime,120) as gift_intime,gift_totalnum,gift_supid,gift_remark,gift_type,"
				+ "gift_nownum,gift_state,gift_addname,convert(char(16),gift_addtime,120) as gift_addtime,"
				+ "supp_name, gift_price,gift_prepay,gift_nowprice,ownmonth,gift_allprice,convert(char(10),gift_validdate,120) as gift_validdate,"
				+ "case gift_state when 0 then '未审核' when 1 then '已审核' when 2 then '已采购' when 3 then '发放中' when 4 then '退回'"
				+ " when 5 then '发放完' when 6 then '待付款' when 7 then '已付款' when 8 then '报名结束'  end statename,gift_realpay,"
				+ "gift_tarpid,gift_totalprice,gift_auditname,convert(char(16),gift_audittime,120) as gift_audittime,gift_buyname,"
				+ "convert(char(10),gift_invoicedate,120) as gift_invoicedate,convert(char(10),gift_paydate,120) as gift_paydate,"
				+ "convert(char(10),gift_buytime,120) as gift_buytime,gift_inname,countnum,bnum,gift_invoicenumber,gift_realinmoney,"
				+ "gift_realinname,convert(char(10),gift_realintime,120) as gift_realintime,gift_userhousenum,"
				+ "convert(char(10),gift_InvoiceUpDate,120) as gift_invoiceupdate,gift_InvoiceName,gift_sortid  "
				+ " from EmActySuppilerGiftInfo a left join  EmActySupplierInfo b on a.gift_supid=b.supp_id "
				+ "left join (select gout_giftid,count(*) as countnum from EmActyGiftOutInfo group by gout_giftid) c on a.gift_id=c.gout_giftid "
				+ "left join (select gtbk_giftid,count(*) as bnum from EmActyGiftBackInfo group by gtbk_giftid) d on a.gift_id=d.gtbk_giftid "
				+ " where 1=1 " + str;
		sql = sql + "";
		System.out.println(sql);
		try {
			list = db.find(sql, EmActySuppilerGiftInfoModel.class,
					dbconn.parseSmap(EmActySuppilerGiftInfoModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取供应商名单
	public List<EmActySupplierInfoModel> getSupName() {
		List<EmActySupplierInfoModel> list = new ArrayList<EmActySupplierInfoModel>();
		ResultSet rs = null;
		dbconn db = new dbconn();
		String sql = "select distinct(supp_Name) supp_name,supp_Id,supp_Address from EmActySupplierInfo";
		try {
			rs = db.GRS(sql);
			EmActySupplierInfoModel m = new EmActySupplierInfoModel();
			list.add(m);
			while (rs.next()) {
				EmActySupplierInfoModel model = new EmActySupplierInfoModel();
				model.setSupp_name(rs.getString("supp_name"));
				model.setSupp_id(rs.getInt("supp_id"));
				model.setSupp_address(rs.getString("supp_address"));
				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取礼品类型
	public List<String> getGiftType() {
		List<String> list = new ArrayList<String>();
		ResultSet rs = null;
		dbconn db = new dbconn();
		String sql = "select distinct(gift_class) gift_class from EmActySuppilerGiftInfo";
		try {
			rs = db.GRS(sql);
			list.add("");
			while (rs.next()) {
				list.add(rs.getString("gift_class"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询员工活动——出库信息
	public List<EmActyGiftOutInfoModel> getEmActyGiftOutInfo(String str) {
		List<EmActyGiftOutInfoModel> list = new ArrayList<EmActyGiftOutInfoModel>();
		dbconn db = new dbconn();
		String sql = "select convert(char(16),gout_time,120) as gout_time,* from EmActyGiftOutInfo where 1=1"
				+ str;
		try {
			list = db.find(sql, EmActyGiftOutInfoModel.class,
					dbconn.parseSmap(EmActyGiftOutInfoModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询员工活动——采购申请退回信息
	public List<EmActyGiftBackInfoModel> getEmActyGiftBackInfo(String str) {
		List<EmActyGiftBackInfoModel> list = new ArrayList<EmActyGiftBackInfoModel>();
		dbconn db = new dbconn();
		String sql = "select convert(char(16),gtbk_time,120) as gtbk_time,* from EmActyGiftBackInfo where 1=1"
				+ str;
		try {
			list = db.find(sql, EmActyGiftBackInfoModel.class,
					dbconn.parseSmap(EmActyGiftBackInfoModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取统计数据
	public List<EmWelfareModel> getWfCount(String str) {
		List<EmWelfareModel> list = new ListModelList<EmWelfareModel>();
		dbconn db = new dbconn();
		ResultSet rs = null;
		String sql = "";
		sql = "select distinct(prod_name),sum(emwf_amount) num,embf_name,prod_name as productName from EmWelfare a "
				+ "left join EmActySupProductInfo c on a.emwf_gift_id=c.prod_id,EmBenefit b "
				+ " where a.emwf_embf_id=b.embf_id  "
				+ str
				+ " GROUP BY embf_name,prod_name  order by embf_name";
		System.out.println(sql);
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				EmWelfareModel m = new EmWelfareModel();
				m.setNum(rs.getInt("num"));
				m.setEmbf_name(rs.getString("embf_name"));
				m.setProductName(rs.getString("productName"));
				list.add(m);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 获取统计数据
	public List<EmWelfareModel> getWfCounts(String str) {
		List<EmWelfareModel> list = new ListModelList<EmWelfareModel>();
		dbconn db = new dbconn();
		ResultSet rs = null;
		String sql = "";
		sql = "select embf_name,sum(emwf_amount) num from EmWelfare a "
				+ "left join EmActySupProductInfo c on a.emwf_gift_id=c.prod_id,EmBenefit b "
				+ " where a.emwf_embf_id=b.embf_id  " + str
				+ " GROUP BY embf_name  order by embf_name";
		System.out.println(sql);
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				EmWelfareModel m = new EmWelfareModel();
				m.setNum(rs.getInt("num"));
				m.setEmbf_name(rs.getString("embf_name"));
				list.add(m);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 获取EmActySuppilerGiftInfo 表的id
	public Integer getTarpId(String str) {
		Integer k = 0;
		dbconn db = new dbconn();
		ResultSet rs = null;
		String sql = " select * from EmActySuppilerGiftInfo where 1=1 " + str;
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				k = rs.getInt("gift_tarpid");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}

	// 获取 EmActySuppilerGiftInfo 表的id
	public String getSortId(String str) {
		String k = "";
		dbconn db = new dbconn();
		ResultSet rs = null;
		String sql = " select * from EmActySuppilerGiftInfo where 1=1 " + str;
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				k = rs.getString("gift_sortid");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}

	// 获取EmWelfare表的任务id
	public Integer getEmWelfare(String str) {
		Integer k = 0;
		dbconn db = new dbconn();
		ResultSet rs = null;
		String sql = " select count(emwf_tapr_id) num from EmWelfare where 1=1 "
				+ str;
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				k = rs.getInt("num");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}

	// 根据cid查询账单号
	public String getBillByCid(int cid, int ownmonth) {
		String billnumber ="";
		dbconn db = new dbconn();
		ResultSet rs = null;
		String sql = "select * from CoFinanceMonthlyBill cb "
				+ "inner join CoFinanceTotalAccount ct on cb.cfmb_cfta_id=ct.cfta_id"
				+ " where cid=" + cid + " and ownmonth=" + ownmonth;
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				billnumber= rs.getString("cfmb_number");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return billnumber;
	}
}
