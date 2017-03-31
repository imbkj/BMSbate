package dal.Embase;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Controller.EmSheBaocard.newExcelImpl;
import Model.CoglistModel;
import Util.UserInfo;

public class CoglistDal {

	// 查询所属项目员工列表
	public List<CoglistModel> getembaseList(Integer id) {
		List<CoglistModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select cgli_id,a.gid,a.cid,emba_name,cgli_coli_id "
				+ "from coglist a "
				+ "inner join embase b on a.gid=b.gid "
				+ "where a.cgli_stopdate is null and emba_state=1 and cgli_coli_id=?"
				+ " order by emba_name";
		try {
			list = db.find(sql, CoglistModel.class, null, id);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return list;
	}

	// 查询员工报价单项目信息(分配报价单)
	public List<CoglistModel> getEmpItemList(Integer cid) {
		List<CoglistModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select distinct a.gid,coli_name"
				+ " from coglist a inner join coofferlist b on a.cgli_coli_id=b.coli_id"
				+ " inner join embase c on a.gid=c.gid and emba_state=1"
				+ " where a.gid in (select gid from embase where cid=?) and cgli_stopdate is null"
				+ " order by a.gid";
		try {
			list = db.find(sql, CoglistModel.class, null, cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询员工是否选择某个有效项目
	public List<CoglistModel> getitemlist(Integer gid, Integer ownmonth,
			String type) {
		List<CoglistModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select a.gid,cgli_startdate,cgli_startdate2,cgli_stopdate,"
				+ "coli_rspaykind,coli_hjpaykind,coli_flpaykind,coli_rsinvoice,coli_hjinvoice"
				+ " from CoGList a inner join CoOfferList b on a.cgli_coli_id=b.coli_id"
				+ " where coli_pclass=? and isnull(cgli_stopdate,204912)>=cgli_startdate2"
				+ " and ? between cgli_startdate2 and isnull(cgli_stopdate,204912)"
				+ " and gid=?";
		System.out.println(sql);
		try {
			list = db.find(sql, CoglistModel.class, null, type, ownmonth, gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询员工是否选中某个报价单内项目
	public List<CoglistModel> haveItem(Integer id) {
		List<CoglistModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select 1 from coglist a"
				+ " inner join coofferlist b on a.cgli_coli_id=b.coli_id"
				+ " inner join cooffer c on b.coli_coof_id=c.coof_id"
				+ " where coof_id=? and cgli_stopdate is null";
		System.out.println(sql);
		System.out.println(id);
		try {
			list = db.find(sql, CoglistModel.class, null, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询员工报价单列表
	public List<CoglistModel> itemList(Integer cid, Integer coid,
			Integer coofId, String pclass, String name, boolean datestate) {
		List<CoglistModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select a.gid,a.cid,cgli_id,coli_id,coof_id,coco_id,cgli_startdate,cgli_startdate2,"
				+ "coco_compactid,coof_name,coli_name,coli_pclass,emba_name,emba_idcard"
				+ " from coglist a inner join CoOfferList b on a.cgli_coli_id=b.coli_id"
				+ " inner join CoOffer c on b.coli_coof_id=c.coof_id"
				+ " inner join CoCompact d on c.coof_coco_id=d.coco_id"
				+ " inner join embase e on a.gid=e.gid"
				+ " where cgli_startdate<=isnull(cgli_stopdate,204912)";
		if (cid != null && !cid.equals("")) {
			sql += " and a.cid=" + cid;
		}
		if (coid != null && !coid.equals("")) {
			sql += " and coco_id=" + coid;
		}
		if (coofId != null && !coofId.equals("")) {
			sql += " and coof_id=" + coofId;
		}
		if (pclass != null && !pclass.equals("")) {
			sql += " and coli_pclass='" + pclass + "'";
		}
		if (name != null && !name.equals("")) {
			sql += " and coli_name like '%" + name + "%'";
		}
		if (datestate) {
			sql += " and cgli_stopdate is null";
		}

		sql += " order by coco_compactid,coof_name,coli_pclass,coli_name";
		System.out.println(sql);
		try {
			list = db.find(sql, CoglistModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// 查询员工是否已有所选项目
	public List<CoglistModel> getCoglistInfo(Integer gid, Integer coliId) {
		dbconn db = new dbconn();
		String sql = "select cgli_id from coglist a where cgli_state=1 and gid=? and cgli_coli_id=? and cgli_startdate <=isnull(cgli_stopdate,204912)";
		List<CoglistModel> list = new ListModelList<>();
		try {
			list = db.find(sql, CoglistModel.class, null, gid, coliId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询员工报价单项目
	public List<CoglistModel> getcoglist(Integer cid, Integer gid, String name) {
		List<CoglistModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select ? cid,? gid,cgli_id,coli_id cgli_coli_id,coli_group_id cgli_group_id,coli_group_count cgli_group_count,"
				+ "coco_compactid,coof_name,copc_name,coli_name+'('+convert(varchar(50),coli_fee)+coli_cpfc_name+')' coli_name,"
				+ "cgli_startdate,cgli_startdate2,cgli_stopdate,"
				+ "case when cgli_id>0 then convert(bit,1) else convert(bit,0) end checked "
				+ "from CoCompact a "
				+ "inner join CoOffer b on a.coco_id=b.coof_coco_id "
				+ "inner join CoOfferList c on b.coof_id=c.coli_coof_id "
				+ "inner join CoProduct d on c.coli_copr_id=d.Copr_id "
				+ "inner join CoPclass e on d.copr_copc_id=e.Copc_id "
				+ "left join CoGList f on c.coli_id=f.cgli_coli_id and f.gid=? "
				+ "where a.cid=? and coli_name like '%"
				+ name
				+ "%' and coco_state>3 and coof_state=3 and coli_state=1 "
				+ "order by coco_compactid,coof_name,Copc_name";

		System.out.println(sql);
		try {
			list = db.find(sql, CoglistModel.class, null, cid, gid, gid, cid);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return list;

	}

	// 获取档案服务起始日期
	public List<CoglistModel> coglistInfo(Integer gid) {
		List<CoglistModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select gid,cgli_startdate from coglist "
				+ "where gid=? and cgli_startdate <=isnull(cgli_stopdate,204912) "
				+ "and cgli_coli_id in (select coli_id from coofferlist where coli_name ='档案管理')";
		try {
			list = db.find(sql, CoglistModel.class, null, gid);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return list;
	}

	// 获取员工公积金项目信息
	public List<CoglistModel> coglistGjj(Integer gid) {
		List<CoglistModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select distinct cgli_id,coli_name,gid,cgli_coli_id,cgli_startdate,cgli_startdate2,coco_house"
				+ " from coglist a"
				+ " inner join coofferlist b on a.cgli_coli_id=b.coli_id"
				+ " inner join cooffer c on b.coli_coof_id=c.coof_id"
				+ " inner join cocompact d on c.coof_coco_id=d.coco_id"
				+ " where cgli_state=1 and gid=? and coli_name in ('住房公积金服务')";
		try {
			list = db.find(sql, CoglistModel.class, null, gid);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return list;
	}

	// 根据员工查询当前公积金月份
	public List<CoglistModel> gjjOwnmonth(Integer gid) {
		List<CoglistModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select distinct a.gid,a.cid,d.coco_house,isnull(f.cohf_lastday,isnull(e.cohf_lastday,isnull(g.cohf_lastday,0)))gjjlastday,"
				+ "onAir gjjOnAir,h.LastDay gjjzzlastday "
				+ " from coglist a"
				+ " inner join CoOfferList b on a.cgli_coli_id=b.coli_id and b.coli_name='住房公积金服务'"
				+ " inner join CoOffer c on b.coli_coof_id=c.coof_id"
				+ " inner join CoCompact d on c.coof_coco_id=d.coco_id"
				+ " inner join EmHouseSetup h on 1=1"
				+ " inner join cobase z on a.cid=z.cid"
				+ " inner join login y on z.coba_client=y.log_name and log_inure=1"
				+ " left join (select * from CoHousingFund where cid is null)e on d.coco_house='中智开户' and convert(varchar(50),d.coco_cpp)=convert(varchar(50),e.cohf_cpp) and ((y.dep_id=2 and e.cohf_bankjc='中信银行') or (y.dep_id!=2 and e.cohf_bankjc='中国银行'))"
				+ " left join (select * from CoHousingFund where cid>0)f on d.coco_house='独立开户' and d.cid=f.cid and convert(varchar(50),d.coco_cpp)=convert(varchar(50),f.cohf_cpp)"
				+ " left join (select cohf_lastday,cohf_bankjc from CoHousingFund where cid is null group by cohf_lastday,cohf_bankjc)g on d.coco_house='中智开户'  and convert(varchar(50),d.coco_cpp)='浮动比例' and ((y.dep_id=2 and g.cohf_bankjc='中信银行') or (y.dep_id!=2 and g.cohf_bankjc='中国银行'))"
				+ " where a.cgli_state=1 and gid=?";
		try {
			list = db.find(sql, CoglistModel.class, null, gid);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return list;
	}

	// 获取员工社保项目信息
	public List<CoglistModel> coglistsb(Integer gid) {
		List<CoglistModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select distinct cgli_id,coli_name,gid,cgli_coli_id,cgli_startdate,cgli_startdate2,coco_shebao"
				+ " from coglist a"
				+ " inner join coofferlist b on a.cgli_coli_id=b.coli_id"
				+ " inner join cooffer c on b.coli_coof_id=c.coof_id"
				+ " inner join cocompact d on c.coof_coco_id=d.coco_id"
				+ " where cgli_state=1 and gid=? and coli_name in ('社会保险服务')";
		try {
			list = db.find(sql, CoglistModel.class, null, gid);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return list;
	}

	// 获取社保公积金信息
	public List<CoglistModel> coglistSBGJJ(Integer gid) {
		List<CoglistModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select cgli_id,coli_name,a.cid,gid,cgli_coli_id,cgli_startdate,cgli_startdate2,coco_shebao,coco_house "
				+ "from coglist a "
				+ "inner join coofferlist b on a.cgli_coli_id=b.coli_id "
				+ "inner join cooffer c on b.coli_coof_id=c.coof_id "
				+ "inner join CoCompact d on c.coof_coco_id=d.coco_id "
				+ "where cgli_state=1 and gid=? and coli_name in ('社会保险服务','住房公积金服务')";
		try {
			list = db.find(sql, CoglistModel.class, null, gid);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return list;
	}

	public List<CoglistModel> coglistinfo(Integer gid) {
		List<CoglistModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select distinct cgli_coli_id,coli_coof_id,cgli_startdate,cgli_startdate2,"
				+ "coof_id,coof_name,coli_copr_id,coli_pclass,f.city,"
				+ "coli_name,coli_fee coli_fee2,coli_group_id,coli_group_count,coli_parid,coli_cpfc_name,"
				+ "case coli_copr_id when 7 then '('+coco_shebao+')' end coco_shebao,"
				+ "case coli_copr_id when 8 then '('+coco_house+')' end coco_house,coco_cpp,"
				+ "case when charindex('税',coli_name)>0 then '('+coco_gs+')' end coco_gs,"
				+ "Copc_id,coco_compacttype"
				+ " from coglist a "
				+ " inner join coofferlist b on a.cgli_coli_id=b.coli_id and coli_stoptime is null"
				+ " inner join cooffer c on b.coli_coof_id=c.coof_id"
				+ " inner join cocompact d on c.coof_coco_id=d.coco_id"
				+ " left join CoProduct h on b.coli_copr_id=h.Copr_id"
				+ " left join CoAgencyBaseCityRel_view f on h.copr_cabc_id=f.cabc_id"
				+ " left join CoPclass g on b.coli_pclass=g.Copc_name"

				+ " where gid=? and cgli_startdate <=isnull(cgli_stopdate,204912)";
		System.out.println(sql);
		try {
			list = db.find(sql, CoglistModel.class, null, gid);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return list;

	}

	/*
	 * public List<CoglistModel> getCompactInfoByColiId(Integer id){
	 * List<CoglistModel> list = new ListModelList<>(); dbconn db = new
	 * dbconn(); String sql="";
	 * 
	 * return list; }
	 */

	// 查询员工公积金所属合同的公积金缴交比例,开户类型
	public List<CoglistModel> getCompactInfoByGid(Integer gid, Integer ownmonth) {
		List<CoglistModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select distinct coco_housefee,coco_house,coco_cpp,isnull(coco_houseid,isnull(f.cohf_houseid,e.cohf_houseid))coco_houseid,isnull(f.cohf_bankjc,e.cohf_bankjc)jc"
				+ " from CoGList a"
				+ " inner join CoOfferList b on a.cgli_coli_id=b.coli_id"
				+ " inner join CoOffer c on c.coof_id=b.coli_coof_id"
				+ " inner join CoCompact d on c.coof_coco_id=d.coco_id"
				+ " inner join cobase z on a.cid=z.CID"
				+ " inner join login y on z.coba_client=y.log_name and y.log_inure=1"
				+ " left join (select cohf_cpp,cohf_houseid,cohf_bankjc from CoHousingFund where cid is null)e on d.coco_cpp=convert(varchar(10),e.cohf_cpp) and coco_house='中智开户' and ((y.dep_id=2 and e.cohf_bankjc='中信银行') or (y.dep_id!=2 and e.cohf_bankjc='中国银行'))"
				+ " left join (select cid,cohf_cpp,cohf_houseid,cohf_bankjc from CoHousingFund where cid>0)f on d.coco_cpp=convert(varchar(10),f.cohf_cpp) and coco_house='独立开户' and a.cid=f.cid"
				+ " where a.gid=? and coli_name ='住房公积金服务' and cgli_state=1"
				+ " and coof_state=3 and coco_state>3"
				+ " and coli_state=1 and isnull(cgli_stopdate,204912)>=cgli_startdate2";

		try {

			list = db.find(sql, CoglistModel.class,
					dbconn.parseSmap(CoglistModel.class), gid);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return list;
	}

	// 读取系统公司报价单项目
	public List<CoglistModel> getcgList(Integer cid) {
		List<CoglistModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select '全部' coli_name,0 sort,0 cpac_id"
				+ " union all select distinct coli_name,1 sort,cpac_id"
				+ " from CoCompact a inner join CoOffer b on a.coco_id=b.coof_coco_id"
				+ " inner join CoOfferList c on b.coof_id=c.coli_coof_id"
				+ " inner join CoPAccount d on coli_account=d.cpac_name"
				+ " where a.cid=?" + " order by sort,cpac_id,coli_name";
		try {
			list = db.find(sql, CoglistModel.class, null, cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public Integer add(CoglistModel cm) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "insert into coglist(gid,cid,cgli_coli_id,cgli_startdate,cgli_stopdate,cgli_startdate2,"
				+ "cgli_state,cgli_addname,cgli_addtime,cgli_group_id,cgli_group_count)values(?,?,?,?,?,?,?,?,getdate(),?,?)";
		try {
			i = db.insertAndReturnKey(sql, cm.getGid(), cm.getCid(),
					cm.getCgli_coli_id(), cm.getCgli_startdate(),
					cm.getCgli_stopdate(), cm.getCgli_startdate2(), 1,
					UserInfo.getUsername(), cm.getCgli_group_id(),
					cm.getCgli_group_count());
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return i;
	}

	/**
	 * @Title: updateCoglist
	 * @Description: TODO(更新员工报价单项目)
	 * @param cm
	 * @param id
	 * @param gid
	 * @param cid
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer updateCoglist(CoglistModel cm, Integer id, Integer coliId,
			Integer gid, Integer cid, Integer dataState) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "update coglist set cgli_modtime=getdate(),cgli_modname='"
				+ UserInfo.getUsername() + "'";
		if (cm.getCgli_startdate() != null) {
			if (cm.getCgli_startdate().equals(0)) {
				sql = sql + ",cgli_startdate=null";
			} else {
				sql = sql + ",cgli_startdate=" + cm.getCgli_startdate();
			}
		}
		if (cm.getCgli_startdate2() != null) {
			if (cm.getCgli_startdate2().equals(0)) {
				sql = sql + ",cgli_startdate2=null";
			} else {
				sql = sql + ",cgli_startdate2=" + cm.getCgli_startdate2();
			}
		}
		if (cm.getCgli_stopdate() != null) {
			if (cm.getCgli_stopdate().equals(0)) {
				sql = sql + ",cgli_stopdate=null";
			} else {
				sql = sql + ",cgli_stopdate=" + cm.getCgli_stopdate();
			}
		}
		if (cm.getCgli_state() != null) {
			if (!cm.getCgli_state().equals("")) {
				sql = sql + ",cgli_state=" + cm.getCgli_state();
			}
		}

		sql = sql + " where 1=1";
		if (id != null) {
			if (!id.equals("")) {
				sql = sql + " and cgli_id=" + id;
			}
		}

		if (coliId != null) {
			if (!coliId.equals("")) {
				sql = sql + " and cgli_coli_id=" + coliId;
			}
		}

		if (cid != null) {
			if (!cid.equals("")) {
				sql = sql + " and cid=" + cid;
			}
		}
		if (gid != null) {
			if (!gid.equals("")) {
				sql = sql + " and gid=" + gid;
			}
		}

		if (dataState != null) {
			if (dataState.equals(1)) {
				sql = sql + " and cgli_stopdate is null";
			}
		}

		try {
			i = db.updatePrepareSQL(sql);
		} catch (SQLException e) {

			System.out.println("更新报价单项目出错.");
		}
		return i;

	}

	public Integer mod(CoglistModel cm) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "update coglist set cgli_modtime=getdate(),cgli_modname='"
				+ UserInfo.getUsername() + "'";

		if (cm.getCgli_startdate() != null) {
			if (cm.getCgli_startdate().equals(0)) {
				sql = sql + ",cgli_startdate=null";
			} else {
				sql = sql + ",cgli_startdate=" + cm.getCgli_startdate();
			}
		}
		if (cm.getCgli_startdate2() != null) {
			if (cm.getCgli_startdate2().equals(0)) {
				sql = sql + ",cgli_startdate2=null";
			} else {
				sql = sql + ",cgli_startdate2=" + cm.getCgli_startdate2();
			}
		}
		if (cm.getCgli_stopdate() != null) {
			if (cm.getCgli_stopdate().equals(0)) {
				sql = sql + ",cgli_stopdate=null";
			} else {
				sql = sql + ",cgli_stopdate=" + cm.getCgli_stopdate();
			}
		}

		sql = sql + " where cgli_id=?";

		System.out.println(sql);
		System.out.println(cm.getCgli_id());
		try {
			i = db.updatePrepareSQL(sql, cm.getCgli_id());
		} catch (SQLException e) {

			System.out.println("更新报价单项目出错.");
		}
		return i;
	}

	public Integer delEmp(Integer gid) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "delete from coglist where gid=?";
		try {
			i = db.updatePrepareSQL(sql, gid);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return i;
	}

	// 删除当前生效报价单
	public Integer delupdate(Integer gid) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "delete from coglist where cgli_state=1 and cgli_stopdate is null and gid=?";
		try {
			i = db.updatePrepareSQL(sql, gid);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return i;
	}

	// 重新分配报价单的时候只能删除相同部门的人员录入的项目
	public Integer resetItem(Integer gid, Integer deptId) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "delete from coglist where cgli_stopdate is null and gid=?"
				+ " and cgli_addname in ("
				+ "	select log_name from login where dep_id =?)";
		try {
			i = db.updatePrepareSQL(sql, gid, deptId);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return i;
	}

	public Integer del(Integer id) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "delete from coglist where cgli_id=?";
		System.out.println(sql);
		System.out.println(id);
		try {
			i = db.updatePrepareSQL(sql, id);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return i;
	}

	public int UpdateTaprid(int daid, int taprid) throws SQLException {
		dbconn db = new dbconn();
		String sql = "update coglist set cgli_tapr_id=? where cgli_id=?";
		Integer i = db.updatePrepareSQL(sql, taprid, daid);
		return i;
	}

}
