package dal.EmHouse;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.EmHouseBJModel;
import Model.EmHouseChangeModel;
import Model.EmHouseUpdateModel;
import Model.EmShebaoBJModel;
import Model.EmbaseModel;
import Util.UserInfo;

public class EmHouseUpdateDal {

	// 查询当前月份
	public String getOwnmonth() {
		String ownmonth = "";
		List<EmHouseUpdateModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select top 1 ownmonth from emhouseupdate order by emhu_id";
		try {
			list = db.find(sql, EmHouseUpdateModel.class,
					dbconn.parseSmap(EmHouseUpdateModel.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (list.size() > 0) {
			ownmonth = list.get(0).getOwnmonth().toString();
		}

		return ownmonth;
	}

	// 查询是否存在补缴数据
	public boolean getbjInfo(Integer gid, Integer ownmonth) {
		boolean b = false;
		List<EmHouseBJModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select gid from emhousebj where gid=? and ownmonth>=?";
		try {
			list = db.find(sql, EmHouseBJModel.class, null, gid, ownmonth);
			if (list.size() > 0) {
				b = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return b;
	}

	// 查询个人信息
	public List<EmHouseUpdateModel> getListById(Integer cid, Integer gid)
			throws SQLException {
		List<EmHouseUpdateModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		StringBuilder sql = new StringBuilder();
		sql.append("select emhu_id,gid,a.cid,ownmonth,emhu_companyid,emhu_company,emhu_name,emhu_idcard,emhu_idcardclass,emhu_hj,emhu_computerid,emhu_houseid,emhu_mobile,emhu_title,emhu_wifename,emhu_wifeidcard,emhu_degree,emhu_radix,emhu_bonus,emhu_cpp,emhu_opp,emhu_cp,emhu_op,emhu_single,emhu_ifstop,convert(varchar(19),emhu_addtime,120)emhu_addtime,emhu_addname,emhu_remark,emhu_startmonth ");
		sql.append(" ,coba_shortname,coba_client");
		sql.append(" from emhouseupdate a");
		sql.append(" inner join cobase b on a.cid=b.cid");
		sql.append(" where a.cid=? and gid=?");
		list = db
				.find(sql.toString(), EmHouseUpdateModel.class, null, cid, gid);
		return list;

	}
	
	// 查询个人信息
		public List<EmHouseUpdateModel> getListById2(Integer cid, Integer gid)
				throws SQLException {
			List<EmHouseUpdateModel> list = new ListModelList<>();
			dbconn db = new dbconn();
			StringBuilder sql = new StringBuilder();
			sql.append("select emhu_id,gid,a.cid,ownmonth,emhu_companyid,emhu_company,emhu_name,emhu_idcard,emhu_idcardclass,emhu_hj,emhu_computerid,emhu_houseid,emhu_mobile,emhu_title,emhu_wifename,emhu_wifeidcard,emhu_degree,emhu_radix,emhu_bonus,emhu_cpp,emhu_opp,emhu_cp,emhu_op,emhu_single,emhu_ifstop,convert(varchar(19),emhu_addtime,120)emhu_addtime,emhu_addname,emhu_remark,emhu_startmonth ");
			sql.append(" ,coba_shortname,coba_client");
			sql.append(" from emhouseupdate a");
			sql.append(" inner join cobase b on a.cid=b.cid");
			sql.append(" where a.cid=? and gid=? and emhu_ifstop=0");
			list = db
					.find(sql.toString(), EmHouseUpdateModel.class, null, cid, gid);
			return list;

		}

	// 查询在册信息
	public List<EmHouseUpdateModel> getListByGid(Integer gid) {
		List<EmHouseUpdateModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		StringBuilder sql = new StringBuilder();
		sql.append("select emhu_id,a.gid,a.cid,ownmonth,emhu_companyid,emhu_company,emhu_name,"
				+ "emhu_idcard,emhu_idcardclass,emhu_hj,emhu_computerid,emhu_houseid,emhu_mobile,"
				+ "emhu_title,emhu_wifename,emhu_wifeidcard,emhu_degree,emhu_radix,emhu_bonus,"
				+ "emhu_cpp,emhu_opp,emhu_cp,emhu_op,emhu_single,emhu_ifstop,emhu_addtime,emhu_addname,"
				+ "emhu_remark,emhu_startmonth,emba_sex ");
		sql.append(" ,coba_shortname,coba_client");
		sql.append(" from emhouseupdate a");
		sql.append(" inner join cobase b on a.cid=b.cid");
		sql.append(" inner join embase c on a.gid=c.gid");
		sql.append(" where a.gid=? and emhu_ifstop=0");
		System.out.println(sql);
		System.out.println(gid);
		try {
			list = db.find(sql.toString(), EmHouseUpdateModel.class, null, gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询在册信息
	public List<EmHouseUpdateModel> getListByIdcard(Integer gid, String idcard) {
		List<EmHouseUpdateModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select a.gid,a.ownmonth from EmHouseUpdate a"
				+ " inner join embase b on a.gid=b.gid"
				+ " left join EmHouseChange c on a.gid=c.gid and a.ownmonth=c.ownmonth"
				+ " where (emhu_idcard=? or emhu_idcard2=? or a.gid=?)"
				+ " and emhu_ifstop=0 and isnull(emhu_remark,'')!='模拟数据'";
		try {
			list = db.find(sql.toString(), EmHouseUpdateModel.class, null,
					idcard, idcard, gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 比例基数调整获取在册信息
	public List<EmHouseUpdateModel> updateList(Integer gid, Integer ownmonth) {
		List<EmHouseUpdateModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select a.cid,a.gid,ISNULL(c.emhu_radix,b.emhu_radix),ISNULL(c.emhu_cpp,b.emhu_cpp) "
				+ "	from EmBase a"
				+ " left join EmHouseUpdate c on a.gid=c.gid"
				+ " left join emhouse b on case len(dbo.idcard(b.emhu_idcard)) when 18 then dbo.idcard(b.emhu_idcard) else b.emhu_idcard end="
				+ " case len(dbo.idcard(a.emba_idcard)) when 18 then dbo.idcard(a.emba_idcard) else a.emba_idcard end"
				+ " and ownmonth=? and c.emhu_id is null" + " where a.gid=?";
		try {
			list = db.find(sql, EmHouseUpdateModel.class, null, ownmonth, gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询员工公积金首页信息
	public List<EmHouseChangeModel> houseIndex(Integer gid, String houseid,
			String idcard) {
		List<EmHouseChangeModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select * from ("
				+ "select 1 emhc_type,emhc_id,a.ownmonth,coba_company emhc_company,emhc_name,emhc_single,emhc_cpp,emhc_radix,emhc_change,emhc_content,emhc_addtime,"
				+ "case emhc_single when 1 then '独立开户' else '中智开户' end +'-'+convert(varchar(10),emhc_cpp*100)+'%' account,"
				+ "case emhc_ifprogress when 11 then '等待设立' when 12 then '设立完成' when 21 then '等待转移' when 22 then '等待启封' when 23 then '调入完成' when 31 then '等待封存' when 32 then '封存完成' when 41 then '等待调整' when 42 then '调整完成' end emhc_ifprogress2,"
				+ "case emhc_ifdeclare when 0 then '未申报' when 1 then '已申报' when 2 then '申报中' when 3 then '退回' when 4 then '待确认' when 5 then '审核中'  when 6 then '核查失败' end"
				+ "+case emhc_ifdeclare when 3 then case when emhc_tapr_id>0 then case when tapr_appointcon=coba_client or tapr_state=5 then '' else '(中心)' end else '' end else '' end emhc_ifdeclare2,"
				+ "emhc_addname,case when emhc_id=smwr_tid then 1 else 0 end message,symr_readstate readState,emhc_remark,d.emhc_tid,cohf_bankjc jc"
				+ " from EmHouseChange a"
				+ " inner join EmBase b on a.gid=b.gid"
				+ " inner join cobase c on a.cid=c.cid"
				+ " inner join cohousingfund g on a.emhc_companyid=g.cohf_houseid and ((emhc_single=1 and a.cid=g.cid) or (emhc_single=0 and g.cid is null))"
				+ " left join TaskProcess e on a.emhc_tapr_id=e.tapr_id"
				+ " left join (select distinct emhc_tid from emhousechange where emhc_tid>0)d on a.emhc_id=d.emhc_tid"
				+ " left join (select smwr_tid,MIN(symr_readstate)symr_readstate from View_Message where smwr_table='emhousechange' group by smwr_tid)f on a.emhc_id=f.smwr_tid"
				+ " where (a.gid=? or a.emhc_houseid=? or a.emhc_idcard=? or a.emhc_idcard2=?) and a.emhc_tid=0"
				+ ")a"
				+ " union all"
				+ "(select 2,emhb_id,ownmonth,coba_company,emhb_name,null,null,emhb_radix,'补缴公积金',convert(varchar(10),emhb_startmonth)+'~'+convert(varchar(10),emhb_stopmonth),emhb_addtime,null,null,"
				+ "case emhb_ifdeclare when 0 then '未申报' when 1 then '已申報' when 2 then '申报中' when 3 then '退回' when 4 then '待确认' when 5 then '审核中' when 6 then '申报中' else '状态异常' end"
				+ "+case emhb_ifdeclare when 3 then case when emhb_tapr_id >0 then case when tapr_appointcon=coba_client then '' else '(中心)' end else '' end else '' end ,"
				+ "emhb_addname,case when emhb_id=smwr_tid then 1 else 0 end message,symr_readstate,'补缴金额:'+CONVERT(varchar(50),emhb_total)+',收费月份:'+convert(varchar(10),emhb_Feemonth),null,null"
				+ " from EmHouseBJ a"
				+ " inner join EmBase b on a.gid=b.gid"
				+ " inner join cobase c on a.cid=c.cid"
				+ " left join TaskProcess e on a.emhb_tapr_id=e.tapr_id"
				+ " left join (select smwr_tid,MIN(symr_readstate)symr_readstate from View_Message where smwr_table='emhousebj' group by smwr_tid)f on a.emhb_id=f.smwr_tid"
				+ " where (emhb_tapr_id>0 or emhb_tapr_id is null) and (a.gid=? or a.emhb_houseid=? or case LEN(a.emhb_idcard) when 15 then dbo.IDCardUp(a.emhb_idcard) else a.emhb_idcard end=?)"
				+ ")"
				+ "union all"
				+ "(select 3,ehcg_id,ownmonth,coba_company,ehcg_name,null,null,null,ehcg_change,ehcg_content,ehcg_addtime,null,null,"
				+ "case ehcg_ifdeclare when 0 then '未申报' when 1 then '已申报' when 2 then '申报中' when 3 then '退回' when 4 then '待确认' end"
				+ "+case ehcg_ifdeclare when 3 then case when ehcg_tapr_id>0 then case when tapr_appointcon=coba_client then '' else '(中心)' end else '' end else '' end,"
				+ "ehcg_addname,case when ehcg_id=smwr_tid then 1 else 0 end message,symr_readstate,ehcg_remark,null,null"
				+ " from EmHouseChangeGJJ a"
				+ " inner join EmBase b on a.gid=b.gid"
				+ " inner join cobase c on a.cid=c.cid"
				+ " left join TaskProcess e on a.ehcg_tapr_id=e.tapr_id"
				+ " left join (select smwr_tid,MIN(symr_readstate)symr_readstate from View_Message where smwr_table='emhousebj' group by smwr_tid)f on a.ehcg_id=f.smwr_tid"
				+ " where a.gid=?)"
				+ "order by ownmonth desc,emhc_type,emhc_addtime desc";
		System.out.println(sql);
		try {
			list = db.find(sql, EmHouseChangeModel.class, null, gid, houseid,
					idcard, idcard, gid, houseid, idcard, gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询在册表
	public List<EmHouseUpdateModel> houseupdateInfoByGid(Integer gid,
			Integer stop) {
		List<EmHouseUpdateModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select emhu_id,a.gid,b.cid,a.ownmonth,emhu_companyid,coba_company emhu_company,emhu_name,emhu_idcard,emhu_idcardclass,emhu_hj,emhu_computerid,emhu_houseid,emhu_mobile,emhu_title,emhu_wifename,emhu_wifeidcard,emhu_degree,emhu_radix,emhu_bonus,emhu_cpp,emhu_opp,emhu_cp,emhu_op,emhu_single,emhu_ifstop,emhu_addtime,emhu_addname,emhu_remark,emhu_startmonth "
				+ " ,coba_shortname,coba_client,cohf_bankjc jc"
				+ " from emhouseupdate a"
				+ " inner join embase b on a.gid=b.gid"
				+ " inner join cobase c on b.cid=c.cid"
				+ " inner join cohousingfund d on a.emhu_companyid=d.cohf_houseid and ((emhu_single=1 and a.cid=d.cid) or (emhu_single=0 and d.cid is null))"
				+ " where a.gid=?";
		if (stop != null) {
			if (!stop.equals("")) {
				sql += " and emhu_ifstop=" + stop;
			}
		}
		try {
			list = db.find(sql.toString(), EmHouseUpdateModel.class, null, gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

	public List<EmHouseUpdateModel> gethouselist(EmHouseUpdateModel em) {
		List<EmHouseUpdateModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select emhu_id,gid,cid,ownmonth,emhu_companyid,emhu_company,emhu_name,emhu_idcard,"
				+ "emhu_idcardclass,emhu_hj,emhu_computerid,emhu_houseid,emhu_mobile,emhu_title,emhu_wifename,"
				+ "emhu_wifeidcard,emhu_degree,emhu_radix,emhu_bonus,emhu_cpp,emhu_opp,emhu_cp,emhu_op,"
				+ "emhu_single,case emhu_single when 1 then '独立开户' when 0 then '中智开户' when 2 then '中智开户' end emhu_single2,"
				+ "emhu_ifstop,convert(varchar(19),emhu_addtime,120)emhu_addtime,emhu_addname,emhu_remark,emhu_startmonth "
				+ "from emhouseupdate " + "where 1=1";
		if (em.getEmhu_ifstop() != null) {
			if (!em.getEmhu_ifstop().equals("")) {
				sql = sql + " and emhu_ifstop=" + em.getEmhu_ifstop();
			}
		}
		sql = sql + " order by emhu_company,emhu_name";
		try {
			list = db.find(sql, EmHouseUpdateModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @Title: shebaoOnboard
	 * @Description: TODO(判断员工入职是否完成,公积金在册数据)
	 * @return
	 * @return boolean 返回类型
	 * @throws
	 */
	public boolean houseOnboard(Integer gid) {
		boolean b = false;
		List<EmbaseModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select gid from EmBase a where gid=?"
				+ " and exists (select 1 from emhousechange where GID=a.gid)";
		try {
			list = db.find(sql, EmbaseModel.class, null, gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (list.size() > 0) {
			b = true;
		}
		return b;
	}

	/**
	 * @Title: shebaobjOnboard
	 * @Description: TODO(判断员工入职是否完成,公积金补缴数据)
	 * @param gid
	 * @return
	 * @return boolean 返回类型
	 * @throws
	 */
	public boolean housebjOnboard(Integer gid) {
		boolean b = false;
		List<EmbaseModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select gid from EmBase a where gid=?"
				+ " and exists (select 1 from emhousebj where GID=a.gid and (emhb_tapr_id is null or emhb_tapr_id>0))";
		try {
			list = db.find(sql, EmbaseModel.class, null, gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (list.size() > 0) {
			b = true;
		}
		return b;
	}

	// 查询员工是否存在在册数据
	public boolean getupdateInfo(Integer gid) {
		boolean b = false;
		List<EmHouseUpdateModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select gid from EmHouseUpdate where gid=?";
		try {
			list = db.find(sql, EmHouseUpdateModel.class, null, gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (list.size() > 0) {
			b = true;
		}
		return b;
	}

	// 修改在册表,变更表,补缴表,合同表单位公积金号
	public Integer modHouseId(Integer cid, String houseID, Double cpp) {
		Integer i = 0;
		Integer j = 0;
		dbconn db = new dbconn();
		String sql = "update emhouseupdate set emhu_companyid=?,emhu_cpp=?,emhu_opp=?,emhu_cp=emhu_radix*?,emhu_op=emhu_radix*?"
				+ " where gid in ("
				+ "select distinct gid from coglist a inner join coofferlist b on a.cgli_coli_id=b.coli_id"
				+ " inner join cooffer c on b.coli_coof_id=c.coof_id"
				+ " inner join cocompact d on c.coof_coco_id=d.coco_id"
				+ " where ownmonth>=201508 and a.cid=? and coco_state>3 and coof_state=3 and coco_house='独立开户'"
				+ ") and isnull(emhu_companyid,'')=''";
		try {
			i = db.updatePrepareSQL(sql, houseID, cpp, cpp, cpp, cpp, cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		j += i;
		sql = "update emhousechange set emhc_companyid=?,emhc_cpp=?"
				+ " where gid in ("
				+ "select distinct gid from coglist a inner join coofferlist b on a.cgli_coli_id=b.coli_id"
				+ " inner join cooffer c on b.coli_coof_id=c.coof_id"
				+ " inner join cocompact d on c.coof_coco_id=d.coco_id"
				+ " where ownmonth>=201508 and a.cid=? and coco_state>3 and coof_state=3 and coco_house='独立开户'"
				+ ") and isnull(emhc_companyid,'')=''";
		try {
			i = db.updatePrepareSQL(sql, houseID, cpp, cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		j += i;
		sql = "update emhousebj set emhb_companyid=?,emhb_cpp=?,"
				+ "emhb_total=emhb_radix*?*2*(DATEDIFF(m,substring(convert(varchar(10),emhb_startmonth),1,4)+'-'+substring(convert(varchar(10),emhb_startmonth),5,2)+'-01',substring(convert(varchar(10),emhb_stopmonth),1,4)+'-'+substring(convert(varchar(10),emhb_stopmonth),5,2)+'-01')+1)"
				+ " where ownmonth>=201508 and  gid in ("
				+ "select distinct gid from coglist a inner join coofferlist b on a.cgli_coli_id=b.coli_id"
				+ " inner join cooffer c on b.coli_coof_id=c.coof_id"
				+ " inner join cocompact d on c.coof_coco_id=d.coco_id"
				+ " where a.cid=? and coco_state>3 and coof_state=3 and coco_house='独立开户'"
				+ ") and isnull(emhb_companyid,'')=''";
		try {
			i = db.updatePrepareSQL(sql, houseID, cpp, cpp, cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		j += i;

		sql = "update cocompact set coco_houseid=?,coco_cpp=?,coco_opp=?"
				+ " where cid=? and coco_house='独立开户' and coco_state>3";
		try {
			i = db.updatePrepareSQL(sql, houseID, cpp, cpp, cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		j += i;
		return i;
	}

	// 修改在册信息
	public Integer mod(EmHouseUpdateModel em) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "update emhouseupdate set emhu_modtime=getdate(),emhu_modname='"
				+ UserInfo.getUsername() + "'";
		if (em.getEmhu_houseid() != null) {
			if (!em.getEmhu_houseid().equals("")) {
				sql = sql + ",emhu_houseid=" + em.getEmhu_houseid();
			}
		}
		if (em.getEmhu_idcard() != null) {
			if (!em.getEmhu_idcard().equals("")) {
				sql = sql + ",emhu_idcard='" + em.getEmhu_idcard() + "'";
			}
		}

		if (em.getEmhu_company() != null) {
			if (!em.getEmhu_company().equals("")) {
				sql = sql + ",emhu_company='" + em.getEmhu_company() + "'";
			}
		}

		if (em.getEmhu_companyid() != null) {
			if (!em.getEmhu_companyid().equals("")) {
				sql = sql + ",emhu_companyid='" + em.getEmhu_companyid() + "'";
			}
		}

		if (em.getEmhu_radix() != null) {
			if (!em.getEmhu_radix().equals("")) {
				sql = sql + ",emhu_radix=" + em.getEmhu_radix();
			}
		}
		if (em.getEmhu_hj() != null) {
			if (!em.getEmhu_hj().equals("")) {
				sql = sql + ",emhu_hj='" + em.getEmhu_hj() + "'";
			}
		}
		if (em.getEmhu_mobile() != null) {
			if (!em.getEmhu_mobile().equals("")) {
				sql = sql + ",emhu_mobile='" + em.getEmhu_mobile() + "'";
			}
		}
		if (em.getEmhu_title() != null) {
			if (!em.getEmhu_title().equals("")) {
				sql = sql + ",emhu_title='" + em.getEmhu_title() + "'";
			}
		}
		if (em.getEmhu_degree() != null) {
			if (!em.getEmhu_degree().equals("")) {
				sql = sql + ",emhu_degree='" + em.getEmhu_degree() + "'";
			}
		}
		if (em.getEmhu_wifename() != null) {
			if (!em.getEmhu_wifename().equals("")) {
				sql = sql + ",emhu_wifename='" + em.getEmhu_wifename() + "'";
			}
		}
		if (em.getEmhu_wifeidcard() != null) {
			if (!em.getEmhu_wifeidcard().equals("")) {
				sql = sql + ",emhu_wifeidcard='" + em.getEmhu_wifeidcard()
						+ "'";
			}
		}

		if (em.getEmhu_ifstop() != null) {
			if (!em.getEmhu_ifstop().equals("")) {
				sql = sql + ",emhu_ifstop=" + em.getEmhu_ifstop();
			}
		}

		if (em.getEmhu_computerid() != null) {
			if (!em.getEmhu_computerid().equals("")) {
				sql = sql + ",emhu_computerid=" + em.getEmhu_computerid();
			}
		}

		sql = sql + " where 1=1";
		if (em.getEmhu_id() != null) {
			if (!em.getEmhu_id().equals("")) {
				sql = sql + " and emhu_id=" + em.getEmhu_id();
				i = db.execQuery(sql);
			}
		} else if (em.getGid() != null) {
			if (!em.getGid().equals("")) {
				sql = sql + " and gid=" + em.getGid();
				i = db.execQuery(sql);
			}
		}

		return i;
	}

	// 员工入职模拟生成在册数据
	public Integer addData(Integer gid, String username, Integer ownmonth) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "insert into emhouseupdate(gid,cid,ownmonth,emhu_companyid,emhu_company,emhu_name,emhu_idcard,emhu_idcardclass,emhu_computerid,emhu_mobile,"
				+ "emhu_radix,emhu_cpp,emhu_opp,emhu_cp,emhu_op,emhu_single,EMHU_IFSTOP,emhu_addtime,emhu_addname,emhu_remark)"
				+ "SELECT a.gid,a.cid,?,isnull(g.cohf_houseid,h.cohf_houseid),coba_company,emba_name,emba_idcard,emba_idcardclass,'00000000000',emba_mobile,"
				+ "emba_house_radix,emba_house_cpp,emba_house_cpp,emba_house_radix*emba_house_cpp,emba_house_radix*emba_house_cpp,"
				+ "case f.coco_house when '独立开户' then 1 else 0 end,0,getdate(),?,'模拟数据'"
				+ " FROM EmBase a"
				+ " inner join cobase b on a.cid=b.cid"
				+ " inner join coglist c on a.gid=c.gid and c.cgli_state=1"
				+ " inner join CoOfferList d on c.cgli_coli_id=d.coli_id"
				+ " inner join CoOffer e on d.coli_coof_id=e.coof_id"
				+ " inner join CoCompact f on e.coof_coco_id=f.coco_id"
				+ " inner join login y on b.coba_client=y.log_name and y.log_inure=1"
				+ " left join (select cohf_cpp,cohf_houseid,cohf_bankjc from CoHousingFund where cid is null)g on f.coco_house='中智开户' and a.emba_house_cpp=g.cohf_cpp and ((y.dep_id=2 and g.cohf_bankjc='中信银行') or (y.dep_id!=2 and g.cohf_bankjc='中国银行'))"
				+ " left join (select cid,cohf_cpp,cohf_houseid,cohf_bankjc from CoHousingFund where cid>0 and cohf_state=1)h on a.cid=h.cid and f.coco_house='独立开户' and a.emba_house_cpp=g.cohf_cpp"
				+ " WHERE a.gid=? and emba_house_place='本地' and emba_house_radix is not null and coli_name='住房公积金服务'";
		try {
			i = db.insertAndReturnKey(sql, ownmonth, username, gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	// 员工入职模拟生成补缴数据
	public Integer addBjData(Integer gid, Integer ownmonth, String username) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "INSERT INTO EMHOUSEBJ(CID,GID,EMHB_NAME,EMHB_COMPANY,OWNMONTH,EMHB_FEEMONTH,EMHB_STARTMONTH,EMHB_STOPMONTH,EMHB_HOUSEID,EMHB_COMPANYID,EMHB_IDCARD,EMHB_RADIX,EMHB_REASON,EMHB_TOTAL,"
				+ "EMHB_CPP,EMHB_OPP,EMHb_ADDNAME,EMHB_ADDTIME,EMHB_IFDECLARE,EMHB_SINGLE,emhb_tapr_id)"
				+ "SELECT a.cid,a.gid,emba_name,coba_company,?,emba_emhb_feeownmonth,emba_emhb_startdate,emba_emhb_stopdate,emba_houseid,isnull(g.cohf_houseid,h.cohf_houseid),emba_idcard,emba_emhb_radix,EMBA_EMHB_REASON,emba_emhb_total,"
				+ "emba_house_cpp,emba_house_cpp,?,getdate(),4,case f.coco_house when '独立开户' then 1 else 0 end,0"
				+ " FROM EmBase a"
				+ " inner join cobase b on a.cid=b.cid"
				+ " inner join coglist c on a.gid=c.gid and c.cgli_state=1"
				+ " inner join CoOfferList d on c.cgli_coli_id=d.coli_id"
				+ " inner join CoOffer e on d.coli_coof_id=e.coof_id"
				+ " inner join CoCompact f on e.coof_coco_id=f.coco_id"
				+ " inner join login y on b.coba_client=y.log_name and y.log_inure=1"
				+ " left join (select cohf_cpp,cohf_houseid,cohf_bankjc from CoHousingFund where cid is null)g on f.coco_house='中智开户' and a.emba_house_cpp=g.cohf_cpp and ((y.dep_id=2 and g.cohf_bankjc='中信银行') or (y.dep_id!=2 and g.cohf_bankjc='中国银行'))"
				+ " left join (select cid,cohf_cpp,cohf_houseid from CoHousingFund where cid>0 and cohf_state=1)h on a.cid=h.cid and f.coco_house='独立开户' and a.cid=h.cid and a.emba_house_cpp=g.cohf_cpp"
				+ " WHERE a.gid=? and emba_house_place='本地' and emba_house_radix is not null and coli_name='住房公积金服务'";
		try {
			i = db.insertAndReturnKey(sql, ownmonth, username, gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	public Integer delData(Integer emhcId) {
		dbconn db = new dbconn();
		String sql = "delete from emhouseupdate where gid in ("
				+ "select gid from emhousechange where emhc_id=?)";
		Integer i = 0;
		try {
			i = db.updatePrepareSQL(sql, emhcId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	public Integer delByGid(Integer gid) {
		dbconn db = new dbconn();
		String sql = "delete from emhouseupdate where gid=?";
		Integer i = 0;
		try {
			i = db.updatePrepareSQL(sql, gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	// 年度调基数
	public Integer salery(EmHouseUpdateModel em) {
		Integer i = 0;
		dbconn db = new dbconn();
		Integer ifdeclare = 0;
		if (em.isConfirm()) {
			ifdeclare = 4;
		}
		try {
			i = Integer.valueOf(db.callWithReturn(
					"{?=call EmHouse_Salery_P_py(?,?,?,?,?,?,?)}",
					Types.INTEGER, em.getGid(), em.getOwnmonth(),
					em.getEmhu_radix(), em.getEmhu_cpp(), ifdeclare,
					em.getReason(), UserInfo.getUsername()).toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	// 根据变更数据判断在册数据处理情况
	public Integer controlData(EmHouseChangeModel em, Integer stop) {
		Integer i = 0;
		dbconn db = new dbconn();
		try {
			i = Integer.valueOf(db.callWithReturn(
					"{?=call EmhouseUpdate_Change_P_py(?,?,?,?)}",
					Types.INTEGER, em.getEmhc_id(), em.getOwnmonth(),
					em.getEmhc_addname(), stop).toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	// 根据变更数据判断在册数据处理情况
	public Integer DeleteData(EmHouseChangeModel em, Integer stop) {
		Integer i = 0;
		dbconn db = new dbconn();
		try {
			i = Integer.valueOf(db.callWithReturn(
					"{?=call EmhouseUpdate_Delete_P_py(?,?,?,?)}",
					Types.INTEGER, em.getEmhc_id(), em.getOwnmonth(),
					em.getEmhc_addname(), stop).toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	// 查找刷新员工在册数据
	public Integer flashData(String idcard) {
		Integer i = 0;
		dbconn db = new dbconn();
		try {
			i = Integer.valueOf(db.callWithReturn(
					"{?=call EmhouseUpdate_check_P_py(?)}", Types.INTEGER,
					idcard).toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	// 重新更新当月在册数据
	public Integer updateData(Integer gid) {
		Integer i = 0;
		dbconn db = new dbconn();
		try {
			i = Integer.valueOf(db.callWithReturn(
					"{?=call EmhouseUpdate_update_P_py(?,?)}", Types.INTEGER,
					gid, UserInfo.getUsername()).toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;

	}

	/**
	 * @Title: del
	 * @Description: TODO(入职删除模拟在册数据)
	 * @param gid
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer delupdate(Integer gid) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "delete from EmhouseUpdate where gid=?";
		try {
			i = db.updatePrepareSQL(sql, gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return i;
	}

	/**
	 * @Title: del
	 * @Description: TODO(入职删除模拟在册数据)
	 * @param gid
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer delbj(Integer gid) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "delete from EmhouseBJ where gid=?";
		try {
			i = db.updatePrepareSQL(sql, gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
}
