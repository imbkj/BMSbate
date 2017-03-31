package dal.EmHouseCard;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.EmHouseMakeCardInfoModel;
import Model.EmHouseUpdateModel;
import Model.EmbaseModel;

public class EmHouse_MakeCardInfoSelectDal {
	// 查询制卡信息
	public List<EmHouseMakeCardInfoModel> getEmhouseMakeCardInfo(String str) {
		List<EmHouseMakeCardInfoModel> list = new ArrayList<EmHouseMakeCardInfoModel>();
		dbconn db = new dbconn();
		String sql = "select  id, a.gid, username, a.cid, cname, idcard, gjj_no, gjj_cno, gjj_client,"
				+ "convert(varchar(10),gjj_clienttime,120) as gjj_clienttime, "
				+ "gjj_clientassistant,convert(varchar(10),gjj_clienttime,120) as gjj_clientassistanttime, gjj_welfreassistant,"
				+ "convert(varchar(10),gjj_welfreassistanttime,120) as gjj_welfreassistanttime, "
				+ "gjj_insertblank, gjj_makebank, convert(varchar(10),gjj_tobanktime,120) as gjj_tobanktime,"
				+ "convert(varchar(10),gjj_maketime,120) as gjj_maketime,"
				+ "convert(varchar(10),gjj_receivetime,120) as gjj_receivetime, gjj_case, gjj_cartstate,"
				+ " gjj_addname, gjj_maker, gjj_allno, backtype, fltype, cshortname,convert(varchar(10),addtime,120) as addtime, "
				+ "bfltype, bzltype, ifread, dept,tarpid,"
				+ " ownmonth, coba_shortname, coba_client, State_Name from EmHouseMakeCardInfo a inner join cobase c "
				+ " on a.cid=c.cid,MakeOrTakeCardState b where a.Gjj_CartState=b.State_Id and b.State_type=1"
				+ str;
		sql = sql + " order by id desc";
		try {
			list = db.find(sql, EmHouseMakeCardInfoModel.class,
					dbconn.parseSmap(EmHouseMakeCardInfoModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取单条制卡数据
	public EmHouseMakeCardInfoModel getMakeCardInfo(String id) {
		EmHouseMakeCardInfoModel model = new EmHouseMakeCardInfoModel();
		String sql = "select Gjj_AddName,addtime,idcard,id,a.gid,state_Name,Gjj_CartState,tarpid,username,gjj_case from EmHouseMakeCardInfo a inner join cobase c "
				+ " on a.cid=c.cid,MakeOrTakeCardState b where a.Gjj_CartState=b.State_Id and b.State_type=1 and id="
				+ id;
		ResultSet rs = null;
		dbconn db = new dbconn();
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				model.setId(rs.getInt("id"));
				model.setGid(rs.getInt("gid"));
				model.setState_Name(rs.getString("state_Name"));
				model.setUsername(rs.getString("username"));
				model.setGjj_cartstate(rs.getInt("Gjj_CartState"));
				model.setTarpid(rs.getInt("tarpid"));
				model.setGjj_addname(rs.getString("gjj_addname"));
				model.setAddtime(rs.getString("addtime"));
				model.setIdcard(rs.getString("idcard"));
				model.setGjj_case(rs.getString("gjj_case"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	// 根据员工Gid查询公积金在册表
	public EmHouseUpdateModel getEmhouseInfo(String gid) {
		EmHouseUpdateModel model = new EmHouseUpdateModel();
		String sql = "select top 1 emhu_companyid,emhu_houseid,emba_name,cohf_banklk,cohf_company from EmHouseUpdate a"
				+ " inner join CoHousingFund b "
				+ " on a.emhu_companyid=b.cohf_houseid inner join embase d on a.gid=d.gid where a.gid="
				+ gid;
		ResultSet rs = null;
		dbconn db = new dbconn();
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				model.setEmhu_companyid(rs.getString("emhu_companyid"));
				model.setEmhu_houseid(rs.getString("emhu_houseid"));
				model.setEmhu_name(rs.getString("emba_name"));
				model.setEmhu_company(rs.getString("cohf_company"));
				model.setEmhu_remark(rs.getString("cohf_banklk"));// 在这里使用备注的字段存公积金缴存银行的信息
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	// 查询员工列表
	public List<EmbaseModel> getEmBaseInfo(String str) {
		List<EmbaseModel> list = new ArrayList<EmbaseModel>();
		String sql = "select top 300 a.cid,a.gid,emba_name,emba_idcard,coba_shortname,num,emba_state "
				+ " from embase a left join (select COUNT(*) num,gid from EmHouseUpdate group by gid) c  "
				+ " on a.gid=c.gid,CoBase b where a.cid=b.cid "
				+ str
				+ " order by emba_state desc,a.cid desc";
		dbconn db = new dbconn();
		try {
			list = db.find(sql, EmbaseModel.class,
					dbconn.parseSmap(EmbaseModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取制卡银行信息
	public List<String> getBankInfo() {
		List<String> list = new ArrayList<String>();
		String sql = "select * from EmhouseGjjCardBank";
		ResultSet rs = null;
		dbconn db = new dbconn();
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				list.add(rs.getString("card_bankname"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据条件查询员工信息
	public EmbaseModel getEmBaseModel(String str) {
		EmbaseModel model = new EmbaseModel();
		String sql = "select a.*,coba_company,coba_shortname,coba_client from EmBase a,CoBase b where a.cid=b.cid "
				+ str;
		dbconn db = new dbconn();
		try {
			ResultSet rs=db.GRS(sql);
			while(rs.next())
			{
				model.setCid(rs.getInt("cid"));
				model.setGid(rs.getInt("gid"));
				model.setEmba_name(rs.getString("emba_name"));
				model.setEmba_idcard(rs.getString("emba_idcard"));
				model.setCoba_shortname(rs.getString("coba_shortname"));
				model.setCoba_company(rs.getString("coba_company"));
				model.setCoba_client(rs.getString("coba_client"));
				model.setEmba_state(rs.getInt("emba_state"));
				model.setEmba_mobile(rs.getString("emba_mobile"));
				model.setEmba_email(rs.getString("emba_email"));
				model.setEmba_id(rs.getInt("emba_id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return model;
	}

	// 获取公积金账户类型
	public String getZhType(Integer gid) {
		String zhtype = "";
		String sql = "select emhc_single from emhousechange where gid=" + gid;
		try {
			dbconn db = new dbconn();
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				if (rs.getInt("emhc_single") == 0) {
					zhtype = "中智大户";
				} else if (rs.getInt("emhc_single") == 1) {
					zhtype = "独立开户";
				} else if (rs.getInt("emhc_single") == 2) {
					zhtype = "中智大户（委托）";
				}
			}
		} catch (Exception e) {

		}

		return zhtype;
	}

	// 获取公积金账户类型
	public String getZhTypes(Integer gid) {
		String zhtype = "";
		String sql = "select emhu_single from emhouseupdate where gid=" + gid;
		try {
			dbconn db = new dbconn();
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				if (rs.getInt("emhu_single") == 0) {
					zhtype = "中智大户";
				} else if (rs.getInt("emhu_single") == 1) {
					zhtype = "独立开户";
				} else if (rs.getInt("emhu_single") == 2) {
					zhtype = "中智大户（委托）";
				}
			}
		} catch (Exception e) {

		}

		return zhtype;
	}

	// 获取客服信息
	public List<String> getLoginInfo() {
		List<String> loginlist = new ArrayList<String>();
		ResultSet rs = null;
		String sql = "select * from View_loginrole";
		try {
			dbconn db = new dbconn();
			rs = db.GRS(sql);
			loginlist.add("");
			while (rs.next()) {
				loginlist.add(rs.getString("log_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginlist;
	}
}
