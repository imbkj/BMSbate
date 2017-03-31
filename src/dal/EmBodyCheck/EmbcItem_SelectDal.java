package dal.EmBodyCheck;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Controller.EmCensus.EmDh.emdh_remarkController;
import Model.CoBaseModel;
import Model.EmBcItemGroupModel;
import Model.EmBodyCheckCommitModel;
import Model.EmBodyCheckItemModel;
import Model.EmHouseSetupModel;
import Util.UserInfo;

public class EmbcItem_SelectDal {
	// 查询体检项目信息
	public List<EmBodyCheckItemModel> getEmBcItemInfo(String str) {
		List<EmBodyCheckItemModel> fidlist = new ArrayList<EmBodyCheckItemModel>();
		dbconn db = new dbconn();
		String sql = "select ebit_addtime,ebit_modtime,"
				+ "convert(decimal(18,1),ebit_charge) as ebit_charge,convert(decimal(18,1),ISNULL(ebit_discount,0)) as ebit_discount,"
				+ "b.ebcs_hospital,ebcs_id,case ebit_sex when 0 then '无限制' when 1 then '男性' when 2 then '女性' end sex,"
				+ "  case ebit_marry when 0 then '无限制' when 1 then '已婚' end marry,"
				+ " case ebit_state when 1 then '生效' else '终止' end state,"
				+ " a.* from EmBodyCheckItem a inner join EmBcSetup b on a.ebit_hospital=b.ebcs_id";
		sql = sql + " where 1=1";
		sql = sql + str;
		sql = sql + " order by ebit_hospital,ebit_package desc,ebit_id desc";
		try {
			fidlist = db.find(sql, EmBodyCheckItemModel.class,
					dbconn.parseSmap(EmBodyCheckItemModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fidlist;
	}

	// 查询体检项目列表
	public List<EmBodyCheckItemModel> getItemList(EmBodyCheckItemModel ebcm) {
		List<EmBodyCheckItemModel> list = new ListModelList<>();
		dbconn db = new dbconn();

		String sql = "select ";
		if (ebcm.isTop()) {
			sql = sql + " top " + ebcm.getTopNum();
		}
		sql = sql
				+ " ebit_id,case ebit_sex when 0 then '无限制' when 1 then '男性' when 2 "
				+ "then '女性' end sex,  case ebit_marry when 0 then '无限制' when 1 then '已婚' end marry,"
				+ "ebit_type,ebit_name,ebit_charge,ebit_info,ebit_remark,"
				+ "ebit_hospital,ebit_bmain,ebit_blood,ebit_sex,ebit_marry,ebit_state,ebit_package,"
				+ "ebit_frequency,ebit_discount,ebit_addname,ebit_addtime,"
				+ "ebcs_hospital "
				+ " from EmBodyCheckItem a inner join EmBcSetup b on a.ebit_hospital=b.ebcs_id where 1=1";

		if (ebcm.getEbit_id() != null) {
			if (!ebcm.getEbit_id().equals("")) {
				if (ebcm.getCheckItem()) {
					sql = sql + " and ebit_id!=" + ebcm.getEbit_id();
				} else {
					sql = sql + " and ebit_id=" + ebcm.getEbit_id();
				}
			}
		}

		if (ebcm.getEbcs_state() != null) {
			if (!ebcm.getEbcs_state().equals("")) {
				sql = sql + " and ebcs_state=" + ebcm.getEbcs_state();
			}
		}

		if (ebcm.getEbit_state() != null) {
			if (!ebcm.getEbit_state().equals("")) {
				sql = sql + " and ebit_state=" + ebcm.getEbit_state();
			}
		}
		if (ebcm.getEbit_hospital() != null) {
			if (!ebcm.getEbit_hospital().equals("")) {
				sql = sql + " and ebit_hospital=" + ebcm.getEbit_hospital();
			}
		}

		if (ebcm.getEbit_bmain() != null) {
			if (!ebcm.getEbit_bmain().equals("")) {
				sql = sql + " and ebit_bmain=" + ebcm.getEbit_bmain();
			}
		}

		if (ebcm.getEbit_blood() != null) {
			if (!ebcm.getEbit_blood().equals("")) {
				sql = sql + " and ebit_blood=" + ebcm.getEbit_blood();
			}
		}

		if (ebcm.getEbit_package() != null) {
			if (!ebcm.getEbit_package().equals("")) {
				sql = sql + " and ebit_package=" + ebcm.getEbit_package();
			}
		}

		if (ebcm.getIdList() != null) {
			if (!ebcm.getIdList().equals("")) {
				sql = sql + " and ebit_id in(" + ebcm.getIdList() + ")";
			} else {
				if (ebcm.isFlag()) {
					sql = sql + " and 1=2";
				}

			}
		}

		if (ebcm.getEbit_name() != null) {
			if (!ebcm.getEbit_name().equals("")) {
				if (ebcm.getCheckItem() != null && ebcm.getCheckItem()) {
					sql = sql + " and ebit_name = '" + ebcm.getEbit_name()
							+ "'";
				} else {
					sql = sql + " and ebit_name like '%"
							+ ebcm.getEbit_name().replace("'", "''") + "%'";
				}

			}
		}

		if (ebcm.getEigl_ebig_id() != null) {
			if (!ebcm.getEigl_ebig_id().equals("")) {
				sql = sql
						+ " and ebit_id in (select eigl_ebit_id from EmbcIGList where eigl_ebig_id="
						+ ebcm.getEigl_ebig_id() + " )";
			}
		}
		if (ebcm.getIdstr() != null && !ebcm.getIdstr().equals("")) {
			sql = sql + " and ebit_id not in(" + ebcm.getIdstr() + ")";
		}

		sql = sql + " order by ebit_package desc,ebit_name";
		System.out.println(sql);
		try {
			list = db.find(sql, EmBodyCheckItemModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询体检项目列表
	public List<EmBodyCheckItemModel> getItemList(String itemNums) {
		List<EmBodyCheckItemModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql="select isnull(ebit_name2,ebit_name)ebit_name" +
				" from EmBodyCheckItem where ebit_id in (?)";
		try {
			list=db.find(sql, EmBodyCheckItemModel.class, null, itemNums);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询员工年度体检项目
	public List<EmBodyCheckItemModel> bcItem(Integer gid, String sex,
			String marry, Integer hospital) {
		List<EmBodyCheckItemModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select ebit_id,ebit_name"
				+ " from coglist a"
				+ " inner join CoOfferList b on a.cgli_coli_id=b.coli_id and coli_pclass='体检' and coli_name not like '入职体检%'"
				+ " inner join EmBodyCheckItem c on ebit_state=1 and ebit_package=1 and ebit_hospital=?"
				+ " and b.coli_name+'-'+case ? WHEN '男' then '男性' when '女' then case ? when '已婚' then '已婚女性' else '未婚女性' end end=c.ebit_name"
				+ " where cgli_stopdate is null and a.gid=?";
		try {
			list = db.find(sql, EmBodyCheckItemModel.class, null, hospital,
					sex, marry, gid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	// 查询项目是否已经存在
	public boolean ifExistItem(Integer ebit_id, String ebit_name,
			Integer ebcs_id) {
		String sql = "select * from EmBodyCheckItem a inner join EmBcSetup b "
				+ "on a.ebit_hospital=b.ebcs_id where ebcs_state=1 and ebit_state=1 "
				+ "and ebit_name ='" + ebit_name + "' and ebcs_id=" + ebcs_id;
		if (ebit_id != null) {
			sql = sql + " and ebit_id!=" + ebit_id;
		}
		boolean flag = false;
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				flag = true;
			}
		} catch (Exception e) {

		}
		return flag;
	}

	// 查询体检项目组合信息
	public List<EmBcItemGroupModel> getEmBcItemGroupInfo(String str) {
		List<EmBcItemGroupModel> fidlist = new ArrayList<EmBcItemGroupModel>();
		dbconn db = new dbconn();
		String sql = "select coba_client,coba_spell,coba_shortname,ebcs_hospital,convert(varchar(20),ebig_addtime,120) as ebig_addtime,"
				+ "convert(varchar(20),ebig_modtime,120) as ebig_modtime,convert(decimal(18,1),ebig_charge) as ebig_charge,"
				+ "a.* from EmBcItemGroup a "
				+ " inner join EmBcSetup b on a.ebig_hospital=b.ebcs_id "
				+ " inner join cobase c on a.cid=c.cid where 1=1 ";
		sql = sql + str;
		sql = sql + " order by ebig_state desc,ebig_id desc";
		System.out.println(sql);
		try {
			fidlist = db.find(sql, EmBcItemGroupModel.class,
					dbconn.parseSmap(EmBcItemGroupModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fidlist;
	}

	// 体检组合信息查询
	public List<EmBcItemGroupModel> getGroupList(EmBcItemGroupModel eigm) {
		List<EmBcItemGroupModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select ebig_id,cid,ebig_name,ebig_charge,ebig_discount,ebig_hospital,ebig_state,ebig_addname,ebig_addtime "
				+ " from EmBcItemGroup where 1=1";
		if (eigm.getEbig_id() != null) {
			if (!eigm.getEbig_id().equals("")) {
				sql = sql + " and ebig_id=" + eigm.getEbig_id();
			}
		}

		if (eigm.getCid() != null) {
			if (!eigm.getCid().equals("")) {
				sql = sql + " and cid=" + eigm.getCid();
			}
		}
		if (eigm.getEbig_state() != null) {
			if (!eigm.getEbig_state().equals("")) {
				sql = sql + " and ebig_state=" + eigm.getEbig_state();
			}
		}
		if (eigm.getEbig_hospital() != null) {
			if (!eigm.getEbig_hospital().equals("")) {
				sql = sql + " and ebig_hospital=" + eigm.getEbig_hospital();
			}
		}

		if (eigm.getCoba_shortname() != null) {
			if (!eigm.getCoba_shortname().equals("")) {
				sql = sql + " and cid in ("
						+ "select cid from cobase where cid like '%"
						+ eigm.getCoba_shortname()
						+ "%' or coba_company  like '%"
						+ eigm.getCoba_shortname()
						+ "%' or coba_shortname like '%"
						+ eigm.getCoba_shortname()
						+ "%' or coba_namespell like '%"
						+ eigm.getCoba_shortname() + "%')";

			}
		}
		if (eigm.getCoba_client() != null) {
			if (!eigm.getCoba_client().equals("")) {
				sql = sql
						+ " and cid in (select cid from cobase where coba_client='"
						+ eigm.getCoba_client() + "')";
			}
		}
		if (eigm.getEmba_name() != null) {
			if (!eigm.getEmba_name().equals("")) {
				sql = sql
						+ " and cid in (select cid from embase where (a.gid like '%"
						+ eigm.getEmba_name() + "%' or emba_name  like '%"
						+ eigm.getEmba_name() + "%' or emba_spell like '%"
						+ eigm.getEmba_name() + "%'))";
			}
		}
		System.out.println(sql);
		try {
			list = db.find(sql, EmBcItemGroupModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// 查询体检组合项目明细
	public List<EmBodyCheckItemModel> getEbItemList(EmBodyCheckItemModel em) {
		List<EmBodyCheckItemModel> list = new ListModelList<>();
		String sql = "select ebit_id,ebit_name,ebit_info,ebit_remark,"
				+ " case ebit_sex when 0 then '无限制' when 1 then '男性' when 1 then '女性' end sex,"
				+ " case ebit_marry when 0 then '无限制' when 1 then '已婚' end marry,"
				+ " ebit_hospital,ebit_charge,ebit_discount"
				+ " from EmBcItemGroup a "
				+ " inner join EmbcIGList b on a.ebig_id=b.eigl_ebig_id "
				+ " inner join EmBodyCheckItem c on b.eigl_ebit_id=c.ebit_id "
				+ " where 1=1";
		if (em.getEigl_ebig_id() != null && !em.getEigl_ebig_id().equals("")) {
			sql = sql + " and ebig_id =" + em.getEigl_ebig_id();
		}

		if (em.getIdList() != null && !em.getIdList().equals("")) {
			sql = sql + " and ebit_id in (" + em.getIdList() + ")";
		}
		System.out.println("自选项目：" + sql);
		dbconn db = new dbconn();
		try {
			list = db.find(sql, EmBodyCheckItemModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询体检项目组合信息
	public List<EmBodyCheckItemModel> getEmBcItemGruopInfo(String str) {
		List<EmBodyCheckItemModel> fidlist = new ArrayList<EmBodyCheckItemModel>();
		dbconn db = new dbconn();
		String sql = "select ebit_id,ebit_name from EmBcItemGroup a,EmBodyCheckItem b,EmbcIGList c  where a.ebig_id=c.eigl_ebig_id and b.ebit_id=c.eigl_ebit_id ";
		sql = sql + str;

		try {
			fidlist = db.find(sql, EmBodyCheckItemModel.class,
					dbconn.parseSmap(EmBodyCheckItemModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fidlist;
	}

	// 查询公司列表(年度体检)
	public List<CoBaseModel> getcompanylist(String company, String emp,
			String client, Integer hospital) {
		List<CoBaseModel> list = new ArrayList<CoBaseModel>();
		dbconn db = new dbconn();
		String sql = "select distinct a.cid,coba_shortname"
				+ " from EmBcItemGroup a"
				+ " inner join cobase b on a.cid=b.cid"
				+ " inner join EmBodyCheckCommit c on a.cid=c.cid and ebcc_state=0 and ebcc_deleteState=0 "
				+ " where a.cid in ("
				+ "select cid from DataPopedom where log_id="
				+ UserInfo.getUserid() + " and  Dat_edited=1" + ")";
		if (hospital != null) {
			if (!hospital.equals("")) {
				sql = sql + " and ebig_hospital=" + hospital;
			}
		}

		if (company != null) {
			if (!company.equals("")) {
				sql = sql + " and a.cid in ("
						+ "select cid from cobase where cid like '%" + company
						+ "%' or coba_company  like '%" + company
						+ "%' or coba_shortname like '%" + company
						+ "%' or coba_namespell like '%" + company + "%')";

			}
		}
		if (client != null) {
			if (!client.equals("")) {
				sql = sql
						+ " and a.cid in (select cid from cobase where coba_client='"
						+ client + "')";
			}
		}
		if (emp != null) {
			if (!emp.equals("")) {
				sql = sql
						+ " and a.cid in (select cid from embase where gid like '%"
						+ emp + "%' or emba_name  like '%" + emp
						+ "%' or emba_spell like '%" + emp + "%')";
			}
		}

		System.out.println(sql);
		try {
			list = db.find(sql, CoBaseModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// 查询公司信息
	public List<CoBaseModel> getCobaseInfo(String str) {
		List<CoBaseModel> fidlist = new ArrayList<CoBaseModel>();
		dbconn db = new dbconn();
		String sql = "select cid,coba_shortname,coba_client,coba_spell from Cobase where 1=1";
		sql = sql + str;
		try {
			fidlist = db.find(sql, CoBaseModel.class,
					dbconn.parseSmap(CoBaseModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fidlist;
	}

	// 计算新建组费用
	public BigDecimal[] getfee(String stritem) {
		BigDecimal[] i = new BigDecimal[2];
		ResultSet rs = null;
		dbconn db = new dbconn();
		String sql = "select convert(decimal(18,1),sum(ebit_charge)) as charge,convert(decimal(18,1),"
				+ "sum(ebit_discount)) as discount from EmBodyCheckItem where ebit_id in("
				+ stritem + ")";
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				i[0] = rs.getBigDecimal("charge");
				i[1] = rs.getBigDecimal("discount");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	// 获取某个项目的费用
	public BigDecimal[] getfeeStr(String str) {
		BigDecimal[] i = new BigDecimal[2];
		ResultSet rs = null;
		dbconn db = new dbconn();
		String sql = "select convert(decimal(18,1),sum(ebit_charge)) as charge,"
				+ "convert(decimal(18,1),sum(ebit_discount)) as discount,ebit_id "
				+ "from EmBodyCheckItem where 1=1 " + str;
		sql = sql + "  GROUP BY ebit_id";
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				i[0] = rs.getBigDecimal("charge");
				i[1] = rs.getBigDecimal("ebit_id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	// 获取组合的费用
	public BigDecimal[] getgroupfee(String str) {
		BigDecimal[] i = new BigDecimal[2];
		ResultSet rs = null;
		dbconn db = new dbconn();
		String sql = "select CONVERT(decimal(18,1),EBIG_CHARGE) as EBIG_CHARGE FROM EmbcITEMGROUP where 1=1 "
				+ str;
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				i[0] = rs.getBigDecimal("EBIG_CHARGE");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	// 查询项目是否在某个组合中
	public List<Integer> getitemid(String str) {
		List<Integer> intlist = new ArrayList<Integer>();
		ResultSet rs = null;
		dbconn db = new dbconn();
		String sql = "select eigl_ebit_id from EmbcIGList where 1=1" + str;
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				intlist.add(rs.getInt("eigl_ebit_id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return intlist;
	}

	public void createExcel() {

	}

	// 获取客服
	public List<String> getClientList() {
		List<String> list = new ArrayList<String>();
		String sql = "select distinct coba_client  from cobase where 1=1 "
				+ "and coba_servicestate=1 and coba_client is not null "
				+ "and coba_client in(select distinct(log_name) from login where log_inure=1) order by coba_client";
		try {
			dbconn db = new dbconn();
			ResultSet rs = db.GRS(sql);
			list.add("");
			while (rs.next()) {
				list.add(rs.getString("coba_client"));
			}
		} catch (Exception e) {
		}
		return list;
	}

	// 获取客服
	public List<CoBaseModel> getCobaseList(String str) {
		List<CoBaseModel> list = new ArrayList<CoBaseModel>();
		String sql = "select distinct coba_client,cid,coba_shortname,coba_company"
				+ " from cobase where 1=1 and coba_servicestate=1"
				+ str
				+ " order by coba_client,coba_company";
		try {
			dbconn db = new dbconn();
			list = db.find(sql, CoBaseModel.class,
					dbconn.parseSmap(CoBaseModel.class));
		} catch (Exception e) {
		}
		return list;
	}

	// 查询EmBodyCheckList是否已有员工使用组合
	public boolean getEmbaGroup(Integer groupid) {
		boolean flag = false;
		String sql = "select * from EmBodyCheckList where ebcl_state<>0 and ebcl_itemgroup="
				+ groupid;
		try {
			dbconn db = new dbconn();
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				flag = true;
			}
		} catch (Exception e) {

		}
		return flag;
	}

	// 查询EmBodyCheckCommit是否已有员工使用组合
	public boolean getCommitGroup(Integer groupid) {
		boolean flag = false;
		String sql = "select * from EmBodyCheckCommit where ebcc_groupid="
				+ groupid;
		try {
			dbconn db = new dbconn();
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				flag = true;
			}
		} catch (Exception e) {

		}
		return flag;
	}
}
