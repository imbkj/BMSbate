package dal.EmBenefit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.EmActyProduceModel;
import Model.EmActyProducetypeModel;
import Model.EmActySuppilerGiftInfoModel;
import Model.EmActyWarehouseHistoryModel;
import Model.EmActyWarehouseModel;
import Model.EmActyWarehouseNoteModel;
import Model.EmWelfareModel;
import Model.EmbaseModel;
import Util.CheckString;
import Util.UserInfo;

public class EmWelfareDal {
	public List<EmWelfareModel> getList(EmWelfareModel ewfm) {
		List<EmWelfareModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		StringBuilder sql = new StringBuilder();
		sql.append("select emwf_id,a.cid,a.gid,coba_shortname,emwf_company,emwf_name,emwf_idcard,emwf_intime,"
				+ "embf_name,emwf_paykind,emwf_delivery,emwf_charge,emwf_remark,emwf_prty_id,"
				+ "emwf_prod_id,emwf_producenum,emwf_produce,prod.prod_name,"
				+ " prod.prod_name+case isnull(prty_name,'') when '' then '' else '（' end +"
				+ " isnull(prty_name,'')+case isnull(prty_name,'') when '' then '' else '）' end "
				+ " +convert(varchar(10),isnull(emwf_producenum,''))+isnull(prod_unit,'') as emwf_producefo,"
				+ "emwf_signtime,emwf_signname,emwf_state,emwf_addname,emwf_addtime,"
				+ "case when emwf_signtime is null then '未签收' else '已签收' end emwf_signState,emwf_sortid,"
				+ "emwf_embf_id,embf_name,emwf_need,emwf_amount,emwf_dept,emwf_client,embf_mold,emwf_family,emwf_standard,"
				+ "c.prod_name productName,supp_name suppName,emba_birth,embf_mold"
				+ " from EmWelfare a inner join EmBenefit b on a.emwf_embf_id=b.embf_id "
				+ " inner join cobase coba on a.cid=coba.cid"
				+ " left join EmActyProduce prod on a.emwf_prod_id=prod.prod_id "
				+ " left join EmActyProduceType pmty on a.emwf_prty_id=pmty.prty_id "
				+ " left join EmActySupProductInfo c on a.emwf_gift_id=c.prod_id"
				+ " left join EmActySupplierInfo d on c.prod_supid=d.supp_id "
				+ " left join embase on a.gid=embase.gid where 1=1 "
				+ "and a.CID in (select cid from DataPopedom where log_id="
				+ UserInfo.getUserid() + " and dat_selected=1 )");
		if (ewfm.getEmwf_id() != null && !ewfm.getEmwf_id().equals("")) {
			sql.append(" and emwf_id=" + ewfm.getEmwf_id());
		}
		if (ewfm.getIdList() != null && !ewfm.getIdList().equals("")) {
			sql.append(" and emwf_id in(" + ewfm.getIdList() + ")");
		}

		if (ewfm.getEmwf_company() != null
				&& !ewfm.getEmwf_company().equals("")) {
			if (CheckString.isNum(ewfm.getEmwf_company())) {
				sql.append(" and a.cid like '%" + ewfm.getEmwf_company() + "%'");
			} else {
				sql.append(" and emwf_company like '%" + ewfm.getEmwf_company()
						+ "%'");
			}
		}
		if (ewfm.getEmwf_name() != null && !ewfm.getEmwf_name().equals("")) {
			if (CheckString.isNum(ewfm.getEmwf_name())) {
				sql.append(" and a.gid like '%" + ewfm.getEmwf_name() + "%'");
			} else {
				sql.append(" and emwf_name like '%" + ewfm.getEmwf_name()
						+ "%'");
			}
		}
		if (ewfm.getEmwf_client() != null && !ewfm.getEmwf_client().equals("")) {

			sql.append(" and emwf_client like '%" + ewfm.getEmwf_client()
					+ "%'");

		}

		if (ewfm.getEmwf_embf_id() != null && ewfm.getEmwf_embf_id() > 0) {
			sql.append(" and emwf_embf_id = " + ewfm.getEmwf_embf_id());
		}

		if (ewfm.getEmwf_delivery() != null
				&& !ewfm.getEmwf_delivery().equals("")) {
			sql.append(" and emwf_delivery like '%" + ewfm.getEmwf_delivery()
					+ "%'");
		}
		if (ewfm.getEmwf_standard() != null
				&& !ewfm.getEmwf_standard().equals("")) {
			sql.append(" and emwf_standard like '%" + ewfm.getEmwf_standard()
					+ "%'");
		}

		if (ewfm.getEmwf_signState() != null
				&& !ewfm.getEmwf_signState().equals("")) {
			if (ewfm.getEmwf_signState().equals("是")) {
				sql.append(" and emwf_signtime is not null");
			} else {
				sql.append(" and emwf_signtime is null");
			}
		}

		if (ewfm.getEmwf_paykind() != null
				&& !ewfm.getEmwf_paykind().equals("")) {
			sql.append(" and emwf_paykind like '%" + ewfm.getEmwf_paykind()
					+ "%'");
		}

		if (ewfm.getEmwf_state() != null) {
			if (ewfm.getEmwf_state() != 2 && ewfm.getEmwf_state() != 4) {
				sql.append(" and emwf_state in(1,3)");
			} else {
				if (ewfm.getEmwf_state() == 2) {
					sql.append(" and emwf_state in(2,16,17)");
				} else {
					sql.append(" and emwf_state=" + ewfm.getEmwf_state());
				}
			}

		}

		if (ewfm.getEmwf_sortid() != null && !ewfm.getEmwf_sortid().equals("")
				&& ewfm.getEmwf_sortid() != "") {
			sql.append(" and emwf_sortid='" + ewfm.getEmwf_sortid() + "'");
		}

		sql.append(" order by emwf_embf_id,emwf_client,emwf_company,emwf_name");
		System.out.println("sal=" + sql);
		try {
			list = db.find(sql.toString(), EmWelfareModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 获取员工福利聚合数据
	public List<EmWelfareModel> getEmBfWfList(EmWelfareModel ewm) {
		List<EmWelfareModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select embf_id,emwf_embf_id,cid,emwf_company,embf_name,embf_mold,emwf_dept,emwf_client,emwf_sortid,COUNT(*)num "
				+ "from EmBenefit a inner join EmWelfare b on a.embf_id=b.emwf_embf_id "
				+ "where embf_state=1 "
				+ "group by embf_id,emwf_embf_id,cid,emwf_company,embf_name,embf_mold,emwf_company,emwf_dept,emwf_client,emwf_sortid";

		sql = sql
				+ " order by emwf_sortid desc,embf_mold,embf_name,emwf_dept,emwf_client,emwf_company";
		try {
			list = db.find(sql, EmWelfareModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

	public List<EmWelfareModel> getCompanyList(String name) {
		List<EmWelfareModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select distinct top 20 cid,emwf_company from EmWelfare "
				+ "where emwf_state in(2,16) and emwf_company like ?";
		name = "%" + name + "%";
		sql += " and CID in ( select cid from DataPopedom where log_id="
				+ UserInfo.getUserid() + " and dat_selected=1 )";
		sql += " order by emwf_company";
		try {
			list = db.find(sql, EmWelfareModel.class, null, name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<EmWelfareModel> getClientList(String name) {
		List<EmWelfareModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select distinct top 20 emwf_client from EmWelfare where emwf_state in(2,16) and emwf_client like ?";
		name = "%" + name + "%";
		sql += " and CID in ( select cid from DataPopedom where log_id="
				+ UserInfo.getUserid() + " and dat_selected=1 )";
		sql += " order by emwf_client";
		try {
			list = db.find(sql, EmWelfareModel.class, null, name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<EmWelfareModel> getNameList(String name) {
		List<EmWelfareModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select distinct top 20 emwf_name from EmWelfare where emwf_state in(2,16) and emwf_name like ?";
		name = "%" + name + "%";
		sql += " and CID in ( select cid from DataPopedom where log_id="
				+ UserInfo.getUserid() + " and dat_selected=1 )";
		sql += " order by emwf_name";
		try {
			list = db.find(sql, EmWelfareModel.class, null, name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 获取享受条件列表
	public List<EmWelfareModel> getstandardlist() {
		List<EmWelfareModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select distinct emwf_standard from EmWelfare order by emwf_standard";
		try {
			list = db.find(sql, EmWelfareModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 更新任务流程ID
	public Integer updateTaprId(Integer dataId, Integer taprId) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "update EmWelfare set emwf_tapr_id=? where emwf_id=?";
		try {
			i = db.updatePrepareSQL(sql, taprId, dataId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	public Integer mod(EmWelfareModel em) {
		Integer i = 0;
		dbconn db = new dbconn();
		if (em.getEmwf_id() != null && em.getEmwf_id() > 0) {

			String sql = "update EmWelfare set emwf_modtime=getdate()";
			if (em.getEmwf_gift_id() != null) {
				sql = sql + ", emwf_gift_id='" + em.getEmwf_gift_id() + "'";
			}

			if (em.getEmwf_paykind() != null) {
				sql = sql + ",emwf_paykind='" + em.getEmwf_paykind() + "'";
			}

			if (em.getEmwf_delivery() != null) {
				sql = sql + ",emwf_delivery='" + em.getEmwf_delivery() + "'";
			}

			if (em.getEmwf_need() != null) {
				sql = sql + ",emwf_need='" + em.getEmwf_need() + "'";
			}

			if (em.getEmwf_state() != null) {
				sql = sql + ",emwf_state=" + em.getEmwf_state();
			}

			if (em.getEmwf_family() != null) {
				sql = sql + ",emwf_family='" + em.getEmwf_family() + "'";
			}
			if (em.getEmwf_standard() != null) {
				sql = sql + ",emwf_standard='" + em.getEmwf_standard() + "'";
			}
			if (em.getEmwf_amount() != null) {
				sql = sql + ",emwf_amount=" + em.getEmwf_amount();
			}
			if (em.getEmwf_remark() != null && !em.getEmwf_remark().equals("")) {
				sql = sql + ",emwf_remark='" + em.getEmwf_remark() + "'";
			}
			if (em.getEmwf_prod_id() != null) {
				sql = sql + ",emwf_prod_id=" + em.getEmwf_prod_id();
			}
			if (em.getEmwf_prty_id() != null) {
				sql = sql + ",emwf_prty_id=" + em.getEmwf_prty_id();
			}

			if (em.getEmwf_produce() != null
					&& !em.getEmwf_produce().equals("")) {
				sql = sql + ",emwf_produce='" + em.getEmwf_produce() + "'";
			}
			if (em.getEmwf_producenum() != null) {
				sql = sql + ",emwf_producenum=" + em.getEmwf_producenum();
			}

			sql = sql + " where emwf_id=?";

			try {
				i = db.updatePrepareSQL(sql, em.getEmwf_id());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;
	}

	public List<EmWelfareModel> getLists(String str) {
		List<EmWelfareModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		StringBuilder sql = new StringBuilder();
		sql.append("select emwf_id,cid,gid,emwf_company,emwf_gift_id,emwf_truecharge,emwf_name,emwf_idcard,convert(varchar(10),emwf_intime,120)emwf_intime,"
				+ "embf_name,emwf_paykind,emwf_delivery,isnull(emwf_charge,0) emwf_charge,embf_mold,emwf_content,c.prod_name productName,emwf_paytype,emwf_backcase,"
				+ " convert(varchar(10),emwf_signtime,120) emwf_signtime,emwf_signname,emwf_state,emwf_addname,convert(varchar(19),emwf_addtime,120)emwf_addtime,"
				+ "case when emwf_signtime is null then '未签收' else '已签收' end emwf_signState,emwf_sortid,prod_discountprice,"
				+ "emwf_embf_id,embf_name,convert(varchar(10),emwf_need,120)emwf_need,emwf_amount,emwf_dept,emwf_client"
				+ ",emwf_remark,emwf_prty_id,prod.prod_discount,prod.prod_discount*isnull(emwf_producenum,0) emwf_price,prod_unit,"
				+ "emwf_prod_id,emwf_producenum,emwf_produce,prod.prod_name,isnull(prty_name,'') prty_name,"
				+ " prod.prod_name+case isnull(prty_name,'') when '' then '' else '（' end +"
				+ " isnull(prty_name,'')+case isnull(prty_name,'') when '' then '' else '）' end "
				+ " + case prod.prod_state when 2 then '' else "
				+ " convert(varchar(10),isnull(emwf_producenum,''))+isnull(prod_unit,'') end as emwf_producefo,"
				+ "prod.prod_name+case isnull(prty_name,'') when '' then '' else '（' end +"
				+ " isnull(prty_name,'')+case isnull(prty_name,'') when '' then '' else '）' end as emwf_prodcontent "
				+ " from EmWelfare a inner join EmBenefit b on a.emwf_embf_id=b.embf_id "
				+ " left join EmActyProduce prod on a.emwf_prod_id=prod.prod_id "
				+ " left join EmActyProduceType pmty on a.emwf_prty_id=pmty.prty_id "
				+ " left join EmActySupProductInfo c on a.emwf_gift_id=c.prod_id where 1=1 "
				+ str);

		sql.append(" order by emwf_embf_id,emwf_client,emwf_company,emwf_name");
		try {
			list = db.find(sql.toString(), EmWelfareModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<EmWelfareModel> getEmwfList(String emwf_sortid) {
		List<EmWelfareModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		StringBuilder sql = new StringBuilder();
		sql.append("select cid,emwf_company,ownmonth,embf_name,embf_mold,"
				+ " sum(emwf_charge)*prod.prod_discount prod_discountprice,sum(emwf_producenum) emwf_producenum,"
				+ " prod.prod_name, prod.prod_name+case isnull(prty_name,'') when '' then "
				+ " '' else '（' end + isnull(prty_name,'')+case isnull(prty_name,'') "
				+ " when '' then '' else '）' end  +convert(varchar(10),isnull(sum(emwf_producenum),"
				+ "''))+isnull(prod_unit,'') as emwf_producefo "
				+ "from EmWelfare a  inner join EmBenefit b on a.emwf_embf_id=b.embf_id "
				+ "left join EmActyProduce prod on a.emwf_prod_id=prod.prod_id "
				+ "left join EmActyProduceType pmty on a.emwf_prty_id=pmty.prty_id "
				+ "where 1=1 and emwf_state=4 and emwf_sortid='"
				+ emwf_sortid
				+ "'"
				+ "group by cid,emwf_company,prod_name,prod_discount,prty_name,"
				+ "prod_unit,ownmonth,embf_name,embf_mold");
		try {
			list = db.find(sql.toString(), EmWelfareModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 财务审核查询
	public List<EmWelfareModel> getEmwfListAudit(String emwf_sortid) {
		List<EmWelfareModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		StringBuilder sql = new StringBuilder();
		sql.append("select cid,emwf_company,ownmonth,embf_name,embf_mold, "
				+ "sum(emwf_producenum)*isnull(prod.prod_discount,1) prod_discountprice,"
				+ "sum(emwf_producenum) emwf_producenum, prod.prod_name,prod_unit,"
				+ "prod_name+case prod.prod_state when 2 then '' else "
				+ "convert(varchar(50),sum(emwf_producenum))+prod_unit end emwf_producefo "
				+ "from EmWelfare a  inner join EmBenefit b on a.emwf_embf_id=b.embf_id "
				+ "left join EmActyProduce prod on a.emwf_prod_id=prod.prod_id "
				+ "left join EmActyProduceType pmty on a.emwf_prty_id=pmty.prty_id "
				+ "where 1=1 and emwf_state=4 and emwf_sortid='"
				+ emwf_sortid
				+ "' "
				+ "group by cid,emwf_company,prod_name,prod_discount,prod_unit,"
				+ "ownmonth,embf_name,embf_mold,prod_state");
		try {
			list = db.find(sql.toString(), EmWelfareModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<EmWelfareModel> getWfList(String str, String strcon) {
		List<EmWelfareModel> list = new ListModelList<EmWelfareModel>();
		dbconn db = new dbconn();
		ResultSet rs = null;
		String sql = "";
		sql = "select cid,emwf_company,count(cid) num,emwf_client,embf_name,embf_mold "
				+ "from EmWelfare a inner join EmBenefit b on a.emwf_embf_id=b.embf_id "
				+ str
				+ "GROUP BY cid,emwf_company,emwf_client,embf_name,embf_mold order by cid,embf_name";
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				EmWelfareModel m = new EmWelfareModel();
				m.setCid(rs.getInt("cid"));
				m.setEmwf_company(rs.getString("emwf_company"));
				m.setNum(rs.getInt("num"));
				m.setEmwf_client(rs.getString("emwf_client"));
				m.setEmbf_name(rs.getString("embf_name"));
				m.setEmbf_mold(rs.getString("embf_mold"));
				m.setList(getEmWelfareList(" and cid=" + m.getCid()
						+ " and embf_name='" + m.getEmbf_name()
						+ "' and (emwf_state=3 or emwf_state=11)" + strcon
						+ str));
				list.add(m);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<EmWelfareModel> getWfLists(String str, String strcon) {
		List<EmWelfareModel> list = new ListModelList<EmWelfareModel>();
		dbconn db = new dbconn();
		ResultSet rs = null;
		String sql = "";
		sql = "select cid,emwf_company,count(cid) num,emwf_client,embf_name,embf_mold,convert(varchar(10),emwf_addtime,120) emwf_addtime "
				+ "from EmWelfare a inner join EmBenefit b on a.emwf_embf_id=b.embf_id where 1=1 "
				+ "and a.CID in ( select cid from DataPopedom where log_id="
				+ UserInfo.getUserid()
				+ " "
				+ " and dat_selected=1 )"
				+ str
				+ "GROUP BY cid,emwf_company,emwf_client,embf_name,embf_mold,convert(varchar(10),emwf_addtime,120) order by cid,embf_name";
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				EmWelfareModel m = new EmWelfareModel();
				m.setCid(rs.getInt("cid"));
				m.setEmwf_company(rs.getString("emwf_company"));
				m.setNum(rs.getInt("num"));
				m.setEmwf_client(rs.getString("emwf_client"));
				m.setEmbf_name(rs.getString("embf_name"));
				m.setEmbf_mold(rs.getString("embf_mold"));
				m.setEmwf_addtime(rs.getString("emwf_addtime"));
				m.setList(getEmWelfareList(" and cid=" + m.getCid()
						+ " and embf_name='" + m.getEmbf_name() + "'"
						+ " and convert(varchar(10),emwf_addtime,120)='"
						+ rs.getString("emwf_addtime") + "'" + strcon + str));
				list.add(m);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<EmWelfareModel> getEmWelfareList(String str) {
		List<EmWelfareModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		StringBuilder sql = new StringBuilder();
		sql.append("select emwf_id,a.cid,gid,emwf_company,emwf_name,emwf_idcard,convert(varchar(10),emwf_intime,120)emwf_intime,"
				+ "embf_name,emwf_paykind,emwf_delivery,emwf_charge,emwf_sendname,prod_discountprice,prod.prod_name,"
				+ "convert(varchar(10),emwf_taketime,120) emwf_taketime,convert(varchar(10),emwf_sendtime,120) emwf_sendtime,"
				+ "convert(varchar(10),emwf_signtime,120)emwf_signtime,emwf_signname,emwf_state,emwf_addname,convert(varchar(19),emwf_addtime,120)emwf_addtime,"
				+ "case when emwf_signtime is null then '未签收' else '已签收' end emwf_signState,emwf_sortid,"
				+ "emwf_embf_id,convert(varchar(10),emwf_need,120)emwf_need,emwf_amount,emwf_dept,emwf_client,embf_mold,emwf_family,emwf_standard,"
				+ "c.prod_name productName,supp_name suppName,emwf_content,case emwf_state when 2 then '未确认' when 3 then '已确认' when 4 then '待审核' "
				+ " when 5 then '已审核' when 6 then '已采购' when 7 then '已发放' when 8 then '已签收' when 9 then '已报名' when 10 then '已付款' when 11 then '退回' "
				+ "when 12 then '审核中' when 15 then '取消' end emwf_statename,emwf_backcase,"
				+ " emwf_remark,emwf_prty_id,emwf_prod_id,emwf_producenum,emwf_produce,coba_shortname,"
				+ " prod.prod_name+case isnull(prty_name,'') when '' then '' else "
				+ " '（' end + isnull(prty_name,'')+case isnull(prty_name,'') when '' then '' "
				+ " else '）' end  + case prod.prod_state when 2 then '' else "
				+ " convert(varchar(10),isnull(emwf_producenum,''))+isnull(prod_unit,"
				+ " '') end as emwf_producefo "
				+ " from EmWelfare a inner join cobase coba on a.cid=coba.cid "
				+ " inner join EmBenefit b on a.emwf_embf_id=b.embf_id "
				+ " left join EmActyProduce prod on a.emwf_prod_id=prod.prod_id "
				+ " left join EmActyProduceType pmty on a.emwf_prty_id=pmty.prty_id  "
				+ " left join EmActySupProductInfo c on a.emwf_gift_id=c.prod_id "
				+ " left join EmActySupplierInfo d on c.prod_supid=d.supp_id where 1=1"
				+ str + " order by emwf_content,emwf_state");
		System.out.println(sql);

		try {
			list = db.find(sql.toString(), EmWelfareModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询客服
	public List<String> clientlist(String str) {
		List<String> list = new ArrayList<String>();
		dbconn db = new dbconn();
		try {
			String sql = "select distinct(emwf_client) emwf_client from EmWelfare a "
					+ "inner join EmBenefit b on a.emwf_embf_id=b.embf_id "
					+ "left join EmActySupProductInfo c on a.emwf_gift_id=c.prod_id "
					+ "where 1=1" + str;
			ResultSet rs = db.GRS(sql);
			list.add("");
			while (rs.next()) {
				list.add(rs.getString("emwf_client"));
			}
		} catch (Exception e) {

		}
		return list;
	}

	// 查询是否还有没有支付费用的员工
	public Integer getIfPay() {
		Integer k = 0;
		dbconn db = new dbconn();
		ResultSet rs = null;
		String sql = "select COUNT(*) num from EmWelfare where emwf_state=7";
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				k = rs.getInt("num");
			}
		} catch (Exception e) {

		}
		return k;
	}

	// 获取福利项目
	public List<String> getEmbfnameList() {
		List<String> list = new ArrayList<String>();
		dbconn db = new dbconn();
		ResultSet rs = null;
		String sql = "select distinct(embf_name) embf_name from EmBenefit";
		try {
			rs = db.GRS(sql);
			list.add("");
			while (rs.next()) {
				list.add(rs.getString("embf_name"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 获取福利类型
	public String getMold(String sorid) {
		String moldname = "";
		String sql = "select embf_mold from EmWelfare a inner join EmBenefit b on a.emwf_embf_id=b.embf_id "
				+ "where emwf_sortid='" + sorid + "'  group by cid, embf_mold";
		dbconn db = new dbconn();
		ResultSet rs = null;
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				moldname = rs.getString("embf_mold");
			}
		} catch (Exception e) {

		}

		return moldname;
	}

	// 获取需审核的数据
	public List<EmWelfareModel> getWfListAudit() {
		List<EmWelfareModel> list = new ListModelList<EmWelfareModel>();
		dbconn db = new dbconn();
		StringBuilder sql = new StringBuilder();
		sql.append("select embf_id,emwf_embf_id,embf_name,embf_mold,emwf_dept,b.emwf_sortid,COUNT(*)num ,ownmonth,"
				+ "sum(emwf_amount) emwf_amount, prod_discountprice from EmBenefit a inner join EmWelfare b "
				+ "on a.embf_id=b.emwf_embf_id left join (select emwf_sortid,"
				+ " sum(isnull(emwf_producenum,0))*isnull(prod_discount,1) prod_discountprice "
				+ " from EmWelfare a left join EmActyProduce prod on a.emwf_prod_id=prod.prod_id "
				+ " left join EmActyProduceType ty on a.emwf_prty_id=ty.prty_id "
				+ " where emwf_sortid is not null group by emwf_sortid,prod_discount) c "
				+ " on b.emwf_sortid=c.emwf_sortid "
				+ "where emwf_state=4 group by embf_id,emwf_embf_id,embf_name,embf_mold,emwf_dept,ownmonth,"
				+ "b.emwf_sortid,prod_discountprice order by b.emwf_sortid desc,embf_mold,embf_name,emwf_dept");
		System.out.println(sql);
		try {
			list = db.find(sql.toString(), EmWelfareModel.class, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询礼品表EmActySuppilerGiftInfo 的信息
	public EmActySuppilerGiftInfoModel getEmActySuppilerGiftInfo(String str) {
		EmActySuppilerGiftInfoModel model = new EmActySuppilerGiftInfoModel();
		dbconn db = new dbconn();
		ResultSet rs = null;
		String sql = "select * from EmActySuppilerGiftInfo where 1=1" + str;
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				model.setGift_tarpid(rs.getInt("gift_tarpid"));
				model.setGift_id(rs.getInt("gift_id"));
			}
		} catch (Exception e) {

		}

		return model;
	}

	// 获取福利项目
	public List<String> getEmbfname() {
		List<String> list = new ArrayList<String>();
		dbconn db = new dbconn();
		ResultSet rs = null;
		String sql = " select distinct(embf_name) from EmWelfare a ,EmBenefit b "
				+ "where a.emwf_embf_id=b.embf_id and (emwf_state=3 or emwf_state=11) ";
		try {
			rs = db.GRS(sql);
			list.add("");
			while (rs.next()) {
				list.add(rs.getString("embf_name"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 获取福利内容
	public List<String> getEmwfnameList() {
		List<String> list = new ArrayList<String>();
		dbconn db = new dbconn();
		ResultSet rs = null;
		String sql = "select distinct(prod_name) prod_name from EmWelfare a left join EmActySupProductInfo c "
				+ "on a.emwf_gift_id=c.prod_id,EmBenefit b where a.emwf_embf_id=b.embf_id "
				+ "and (emwf_state=3 or emwf_state=11) GROUP BY embf_name,prod_name";
		try {
			rs = db.GRS(sql);
			list.add("");
			while (rs.next()) {
				list.add(rs.getString("prod_name"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
		sql = "select prod_id,prod_name,prod_price,prod_discountprice,sum(emwf_amount) num from EmWelfare a "
				+ " left join EmActySupProductInfo c on a.emwf_gift_id=c.prod_id,EmBenefit b "
				+ " where a.emwf_embf_id=b.embf_id and prod_name is not null  "
				+ str
				+ " GROUP BY prod_name,prod_price,prod_discountprice,prod_id ";
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				EmWelfareModel m = new EmWelfareModel();
				m.setNum(rs.getInt("num"));
				m.setOldnum(rs.getInt("num"));
				m.setProd_name(rs.getString("prod_name"));
				m.setProd_discountprice(rs.getBigDecimal("prod_discountprice"));
				m.setProd_price(rs.getBigDecimal("prod_price"));
				m.setProd_id(rs.getInt("prod_id"));
				list.add(m);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询库存信息
	public List<EmActyWarehouseModel> getEmActyWarehouse(String str) {
		List<EmActyWarehouseModel> list = new ListModelList<EmActyWarehouseModel>();
		dbconn db = new dbconn();
		ResultSet rs = null;
		String sql = "";
		sql = "select convert(varchar(20),wase_addtime,120) wase_addtime,"
				+ "convert(varchar(20),wase_modtime,120) wase_modtime,* from EmActyWarehouse a"
				+ " left join (select COUNT(*) num,hsry_wase_id from EmActyWarehouseHistory where hsry_state=1 "
				+ " group by hsry_wase_id) b on a.wase_id=b.hsry_wase_id where 1=1 "
				+ str;
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				EmActyWarehouseModel m = new EmActyWarehouseModel();
				m.setWase_id(rs.getInt("wase_id"));
				m.setWase_addname(rs.getString("wase_addname"));
				m.setWase_addtime(rs.getString("wase_addtime"));
				m.setWase_name(rs.getString("wase_name"));
				m.setWase_nownum(rs.getInt("wase_nownum"));
				m.setWase_totalnum(rs.getInt("wase_totalnum"));
				m.setWase_modtime(rs.getString("wase_modtime"));
				m.setNum(rs.getInt("num"));
				list.add(m);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 获取客服
	public List<String> getClientList() {
		List<String> list = new ArrayList<String>();
		dbconn db = new dbconn();
		ResultSet rs = null;
		String sql = "select distinct(coba_client) coba_client from CoBase "
				+ "where coba_client is not null and coba_client<>'' and CID in ( "
				+ "select cid from DataPopedom where log_id="
				+ UserInfo.getUserid() + " and dat_selected=1 ) "
				+ " order by coba_client";
		try {
			rs = db.GRS(sql);
			list.add("");
			while (rs.next()) {
				list.add(rs.getString("coba_client"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 获取库存历史数据
	public List<EmActyWarehouseHistoryModel> getEmActyWarehouseHistory(
			String sqlstr) {
		List<EmActyWarehouseHistoryModel> list = new ListModelList<EmActyWarehouseHistoryModel>();
		dbconn db = new dbconn();
		String sql = "select hsry_addname, convert(varchar(16),hsry_addtime,120) hsry_addtime,"
				+ " hsry_price,sum(hsry_num) hsry_num from EmActyWarehouseHistory where 1=1"
				+ sqlstr
				+ " group by hsry_num, hsry_addname, convert(varchar(16),hsry_addtime,120), "
				+ "hsry_price order by hsry_addtime";
		try {
			list = db.find(sql.toString(), EmActyWarehouseHistoryModel.class,
					null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 获取库存记录数据
	public List<EmActyWarehouseNoteModel> getEmActyWarehouseNote(String sqlstr) {
		List<EmActyWarehouseNoteModel> list = new ListModelList<EmActyWarehouseNoteModel>();
		dbconn db = new dbconn();
		String sql = "select * from EmActyWarehouseNote where 1=1" + sqlstr;
		try {
			list = db
					.find(sql.toString(), EmActyWarehouseNoteModel.class, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public boolean ifBackCase(String idstr) {
		boolean flag = true;
		dbconn db = new dbconn();
		int k = 0;
		String sql = "select count(distinct(emwf_backcase)) num from EmWelfare where 1=1 and emwf_id in("
				+ idstr + ")";
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				k = k + 1;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (k > 1) {
			flag = false;
		}
		return flag;
	}

	public Integer add(EmbaseModel m) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "insert into EmWelfare(cid,gid,emwf_embf_id,emwf_company,emwf_name,emwf_idcard,"
				+ "emwf_intime,emwf_content,emwf_charge,emwf_amount,emwf_dept,emwf_client,emwf_addname,"
				+ "emwf_addtime,emwf_standard,emwf_paykind)values(?,?,?,?,?,?,?,?,?,?,?,?,?,getdate(),?,?)";
		try {
			i = db.insertAndReturnKey(sql, m.getCid(), m.getGid(),
					m.getEmbf_id(), m.getCoba_company(), m.getEmba_name(),
					m.getEmba_idcard(), m.getEmba_indate(), m.getColi_remark(),
					m.getColm_fee(), 1, m.getDept(), m.getCoba_client(),
					UserInfo.getUsername(), m.getColi_standard(),
					m.getColi_flpaykind());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	public int isSameEmbenefit(String idList) {
		int num = 0;
		dbconn db = new dbconn();
		String sql = "select count(distinct(Emwf_embf_id)) num from EmWelfare where emwf_id in("
				+ idList + ")";
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				num = rs.getInt("num");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return num;
	}

	// 查询享受方式
	public List<String> getCopStandard() {
		List<String> list = new ArrayList<>();
		String sql = "select * from CoPStandard where cpst_type=1";
		try {
			dbconn db = new dbconn();
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				list.add(rs.getString("cpst_name"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询产品
	public List<EmActyProduceModel> getEmActyProduce() {
		List<EmActyProduceModel> list = new ArrayList<EmActyProduceModel>();
		String sql = "select * from EmActyProduce where prod_state=1";
		try {
			dbconn db = new dbconn();
			list = db.find(sql.toString(), EmActyProduceModel.class, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询产品
	public List<EmActyProduceModel> getEmActyProduceBySupId(int prod_supp_id) {
		List<EmActyProduceModel> list = new ArrayList<EmActyProduceModel>();
		String sql = "select * from EmActyProduce where prod_state=2 "
				+ " and prod_supp_id=" + prod_supp_id;
		try {
			dbconn db = new dbconn();
			list = db.find(sql.toString(), EmActyProduceModel.class, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询单个产品
	public EmActyProduceModel getEmActyProduceInfo(int prod_id) {
		List<EmActyProduceModel> list = new ArrayList<EmActyProduceModel>();
		String sql = "select * from EmActyProduce where prod_id=" + prod_id;
		try {
			dbconn db = new dbconn();
			list = db.find(sql.toString(), EmActyProduceModel.class, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		EmActyProduceModel m = new EmActyProduceModel();
		if (list.size() > 0) {
			m = list.get(0);
		}
		return m;
	}

	// 查询产品
	public List<EmActyProduceModel> getEmActyProduceList(int prod_supp_id) {
		List<EmActyProduceModel> list = new ArrayList<EmActyProduceModel>();
		String sql = "select prod_supp_id,prty_name,prod_unit,prod_id,prod_name+case ISNULL(prty_id,'') " +
				" when '' then '' else '('+prty_name+')' end prod_name,prod_discount,prod_pricetype," +
				" prty_id,prty_discount from EmActyProduce a left join EmActyProduceType b " +
				" on a.prod_id=b.prty_prod_id " +
				" where prod_supp_id=" + prod_supp_id;
		try {
			dbconn db=new dbconn();
			ResultSet rs=db.GRS(sql);
			while (rs.next()) {
				EmActyProduceModel m=new EmActyProduceModel();
				EmActyProducetypeModel pm=new EmActyProducetypeModel();
				pm.setPrty_id(rs.getInt("prty_id"));
				pm.setPrty_name(rs.getString("prty_name"));
				pm.setPrty_discount(rs.getBigDecimal("prty_discount"));
				m.setPm(pm);
				m.setProd_discount(rs.getBigDecimal("prty_discount")==null?rs.getBigDecimal("prod_discount"):rs.getBigDecimal("prty_discount"));
				m.setProd_id(rs.getInt("prod_id"));
				m.setProd_name(rs.getString("prod_name"));
				m.setProd_pricetype(rs.getString("prod_pricetype"));
				m.setProd_supp_id(rs.getInt("prod_supp_id"));
				m.setProd_unit(rs.getString("prod_unit"));
				list.add(m);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询产品类型
	public List<EmActyProducetypeModel> getEmActyProduceType(int prod_id) {
		List<EmActyProducetypeModel> list = new ArrayList<EmActyProducetypeModel>();
		String sql = "select * from EmActyProducetype where prty_state=1 and prty_prod_id=?";
		try {
			dbconn db = new dbconn();
			list = db.find(sql.toString(), EmActyProducetypeModel.class, null,
					prod_id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询单个产品类型
	public EmActyProducetypeModel getEmActyProduceTypeInfo(int prty_id) {
		List<EmActyProducetypeModel> list = new ArrayList<EmActyProducetypeModel>();
		String sql = "select * from EmActyProducetype where prty_id=?";
		try {
			dbconn db = new dbconn();
			list = db.find(sql.toString(), EmActyProducetypeModel.class, null,
					prty_id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		EmActyProducetypeModel m = new EmActyProducetypeModel();
		if (list.size() > 0) {
			m = list.get(0);
		}
		return m;
	}

	// 分类统计产品信息
	public List<EmWelfareModel> getEmWelfareCount(String strs) {
		List<EmWelfareModel> list = new ArrayList<EmWelfareModel>();
		String sql = "select sum(emwf_producenum)emwf_num,emwf_prod_id,emwf_prty_id,prod_name,"
				+ "prty_name,prod_unit,prod_name+case isnull(prty_name,'') when '' "
				+ "then '' else '（' end + isnull(prty_name,'')+case isnull(prty_name,'') "
				+ "when '' then '' else '）' end  +'【'+convert(varchar(10),"
				+ "isnull(sum(emwf_producenum),''))+'】'+isnull(prod_unit,'') as emwf_producefo "
				+ "from EmWelfare a left join EmActyProduce b on a.emwf_prod_id=b.prod_id "
				+ "left join EmActyProduceType c on a.emwf_prty_id=c.prty_id "
				+ "where a.emwf_prod_id is not null"
				+ strs
				+ " group by emwf_prod_id,emwf_prty_id,"
				+ "prod_name,prty_name,prod_unit";
		try {
			dbconn db = new dbconn();
			list = db.find(sql.toString(), EmWelfareModel.class, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 删除数据
	public int deleteEmbenefit(int id) {
		dbconn db = new dbconn();
		String sql = "delete from EmWelfare where emwf_id=" + id;
		int k = db.execQuery(sql);
		return k;
	}

	// 查询是否是活动
	public boolean isGift(String idStr) {
		boolean flag = false;
		String sql = "select count(embf_mold) num from EmWelfare a "
				+ " inner join EmBenefit b on a.emwf_embf_id=b.embf_id "
				+ " where emwf_id in(" + idStr + ") and embf_mold='活动'";
		ResultSet rs = null;
		dbconn db = new dbconn();
		int num = 0;
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				num = rs.getInt("num");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (num == 1) {
			flag = true;// 表示全部都是活动
		}
		return flag;
	}

	// 更新福利信息
	public int editWm(int emwf_prod_id, int emwf_id) {
		String sql = "update EmWelfare set emwf_prod_id=" + emwf_prod_id
				+ ",emwf_producenum=1 where emwf_id=" + emwf_id;
		dbconn db = new dbconn();
		int k = db.execQuery(sql);
		return k;
	}
}
