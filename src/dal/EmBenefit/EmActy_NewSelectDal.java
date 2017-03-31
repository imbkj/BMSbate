package dal.EmBenefit;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.EmActyProduceModel;
import Model.EmActySuppilerGiftInfoModel;
import Model.EmActyWarehouseModel;
import Model.EmWelfareModel;
import Model.EmactyUseHouseLogModel;

public class EmActy_NewSelectDal {

	// 查询批次编号
	public List<EmActySuppilerGiftInfoModel> getEmActyGiftInfo(int gift_id) {
		List<EmActySuppilerGiftInfoModel> list = new ArrayList<EmActySuppilerGiftInfoModel>();
		dbconn db = new dbconn();
		String sql = "select gift_id,gift_allprice,gift_auditName,gift_auditTime,gift_buyname,"
				+ " gift_buytime,gift_prepay,gift_nowprice,gift_nownum,gift_invoicedate,gift_projectname,"
				+ " gift_invoicename,gift_invoicenumber,gift_tarpid,gift_takeclient,gift_totalnum,"
				+ " gift_totalprice,gift_type,gift_userhousenum,gift_validdate,gift_paydate,"
				+ " gift_payname,gift_paytype,gift_price,gift_sortid "
				+ " from EmActySuppilerGiftInfo where gift_id=?";
		try {
			list = db.find(sql, EmActySuppilerGiftInfoModel.class,
					dbconn.parseSmap(EmActySuppilerGiftInfoModel.class),
					gift_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据批次编号查询员工福利信息
	public List<EmWelfareModel> getEmWelfareList(String scortId) {
		List<EmWelfareModel> list = new ArrayList<EmWelfareModel>();
		dbconn db = new dbconn();
		String sql = "select emwf_id,cid,gid,emwf_company,emwf_gift_id,emwf_truecharge,"
				+ "emwf_name,emwf_idcard,convert(varchar(10),emwf_intime,120)emwf_intime,"
				+ "embf_name,emwf_paykind,emwf_delivery,isnull(emwf_charge,0) emwf_charge,"
				+ "embf_mold,"
				+ "emwf_content,c.prod_name productName,emwf_paytype,emwf_backcase,"
				+ "convert(varchar(10),emwf_signtime,120) emwf_signtime,emwf_signname,"
				+ "emwf_state,emwf_addname,convert(varchar(19),emwf_addtime,120)emwf_addtime,"
				+ "case when emwf_signtime is null then '未签收' else '已签收' end emwf_signState,"
				+ "emwf_sortid,emwf_embf_id,embf_name,convert(varchar(10),emwf_need,120)emwf_need,"
				+ "emwf_amount,emwf_dept,emwf_client,emwf_remark,emwf_prty_id,c.prod_discount,"
				+ "c.prod_discount*isnull(emwf_producenum,0) emwf_price,emwf_prod_id,emwf_producenum,"
				+ "emwf_produce,c.prod_name,c.prod_name+case isnull(prty_name,'') when '' then '' "
				+ "else '（' end +isnull(prty_name,'')+case isnull(prty_name,'') when '' then '' "
				+ "else '）' end + case c.prod_state when 2 then '' else " +
				" convert(varchar(10),isnull(emwf_producenum,''))+"
				+ "isnull(prod_unit,'') end as emwf_producefo from EmWelfare a" +
				" inner join EmBenefit b "
				+ "on a.emwf_embf_id=b.embf_id left join EmActyProduce c on a.emwf_prod_id=c.prod_id "
				+ "left join EmActyProduceType d on a.emwf_prty_id=d.prty_id "
				+ "where emwf_sortid=?";
		try {
			list = db.find(sql, EmWelfareModel.class,
					dbconn.parseSmap(EmWelfareModel.class), scortId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据批次编号查询员工福利信息
	public List<EmWelfareModel> getEmWelfareInfoList(String scortId) {
		List<EmWelfareModel> list = new ArrayList<EmWelfareModel>();
		dbconn db = new dbconn();
		String sql = "select embf_name,sum(emwf_charge) emwf_charge,embf_mold,emwf_sortid,prod_unit,"
				+ "emwf_prty_id,prod.prod_discount,emwf_prod_id,sum(isnull(emwf_producenum,0)) emwf_producenum,"
				+ "emwf_produce,prod.prod_name,prod.prod_name+case isnull(prty_name,'') "
				+ " when '' then '' else '（' end +isnull(prty_name,'')+case isnull(prty_name,'') "
				+ "when '' then '' else '）' end as emwf_producefo from EmWelfare a "
				+ "inner join EmBenefit b on a.emwf_embf_id=b.embf_id "
				+ "left join EmActyProduce prod on a.emwf_prod_id=prod.prod_id "
				+ "left join EmActyProduceType pmty on a.emwf_prty_id=pmty.prty_id "
				+ "left join EmActySupProductInfo c on a.emwf_gift_id=c.prod_id "
				+ "where emwf_sortid=? group by embf_name,embf_mold,"
				+ "emwf_sortid,emwf_prty_id,prod.prod_discount,emwf_prod_id,emwf_produce,"
				+ "prod.prod_name,prty_name,prod_unit";
		try {
			list = db.find(sql, EmWelfareModel.class,
					dbconn.parseSmap(EmWelfareModel.class), scortId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据批次编号查询员工福利信息_采购礼品
	public List<EmWelfareModel> getEmWelfareInfoListBySortId(String scortId,
			String str) {
		List<EmWelfareModel> list = new ArrayList<EmWelfareModel>();
		dbconn db = new dbconn();
		String sql = "select embf_name,sum(emwf_charge) emwf_charge,embf_mold,emwf_sortid,prod_unit,"
				+ "emwf_prty_id,prod.prod_discount,emwf_prod_id,sum(isnull(emwf_producenum,0)) emwf_producenum,"
				+ "emwf_produce,prod.prod_name,isnull(prty_name,'') prty_name,prod.prod_name+case isnull(prty_name,'') "
				+ " when '' then '' else '（' end +isnull(prty_name,'')+case isnull(prty_name,'') "
				+ "when '' then '' else '）' end as emwf_producefo from EmWelfare a "
				+ "inner join EmBenefit b on a.emwf_embf_id=b.embf_id "
				+ "left join EmActyProduce prod on a.emwf_prod_id=prod.prod_id "
				+ "left join EmActyProduceType pmty on a.emwf_prty_id=pmty.prty_id "
				+ "left join EmActySupProductInfo c on a.emwf_gift_id=c.prod_id "
				+ "where emwf_sortid=? "
				+ str
				+ " group by embf_name,embf_mold,"
				+ "emwf_sortid,emwf_prty_id,prod.prod_discount,emwf_prod_id,emwf_produce,"
				+ "prod.prod_name,prty_name,prod_unit";
		try {
			list = db.find(sql, EmWelfareModel.class,
					dbconn.parseSmap(EmWelfareModel.class), scortId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询库存
	public List<EmActyWarehouseModel> getEmWelfareHouse(String emwf_idstr) {
		List<EmActyWarehouseModel> list = new ArrayList<EmActyWarehouseModel>();
		dbconn db = new dbconn();
		String sql = "select wase_id,wase_prty_id,wase_prod_id,prod_name+case isnull(prty_name,'') when '' then '' "
				+ "else '（' end +isnull(prty_name,'')+case isnull(prty_name,'') when '' then '' "
				+ "else '）' end wase_name,wase_nownum,a.prod_unit as wase_unit from EmActyProduce a "
				+ "inner join EmActyWarehouse c on a.prod_id=c.wase_prod_id "
				+ "left join EmActyProduceType b on a.prod_id=b.prty_prod_id and c.wase_prty_id=b.prty_id "
				+ "where prod_id in(select distinct(emwf_prod_id) from EmWelfare where emwf_id in("
				+ emwf_idstr
				+ ")) "
				+ "and prty_id in(select distinct(emwf_prty_id) from EmWelfare where emwf_id in("
				+ emwf_idstr
				+ ")) "
				+ "or (prod_id in(select distinct(emwf_prod_id) from EmWelfare where emwf_id in("
				+ emwf_idstr + ")) and prty_id is null)";
		try {
			list = db.find(sql, EmActyWarehouseModel.class,
					dbconn.parseSmap(EmActyWarehouseModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//查询库存记录
	public List<EmactyUseHouseLogModel> getEmactyUseHouseLogModel(String useh_sortid) {
		List<EmactyUseHouseLogModel> list = new ArrayList<EmactyUseHouseLogModel>();
		dbconn db = new dbconn();
		String sql = "select * from EmactyUseHouseLog where useh_sortid=?";
		try {
			list = db.find(sql, EmactyUseHouseLogModel.class,
					dbconn.parseSmap(EmactyUseHouseLogModel.class),useh_sortid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询采购总数
	public int getEmwfProdNum(String str) {
		int producenum = 0;
		String sql = "select sum(emwf_producenum) producenum from EmWelfare where 1=1"
				+ str;
		ResultSet rs = null;
		dbconn db = new dbconn();
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				producenum = rs.getInt("producenum");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return producenum;
	}

	public EmActyProduceModel getEmwfProdPrice(int prod_id) {
		EmActyProduceModel m=new EmActyProduceModel();
		String sql = "select isnull(prod_discount,0) prod_discount,prod_unit from EmActyProduce where prod_id="
				+ prod_id;
		ResultSet rs = null;
		dbconn db = new dbconn();
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				m.setProd_discount(rs.getBigDecimal("prod_discount"));
				m.setProd_unit(rs.getString("prod_unit"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return m;
	}
}
