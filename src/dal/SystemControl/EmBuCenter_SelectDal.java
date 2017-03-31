package dal.SystemControl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.EmbaseBusinessCenterModel;
import Model.EmbaseModel;
import Model.MenuListModel;
import Util.UserInfo;

public class EmBuCenter_SelectDal {

	// 获取业务中心菜单名称
	public List<EmbaseBusinessCenterModel> getEmbaseBusinessCenterInfo(
			String str) {
		List<EmbaseBusinessCenterModel> fidlist = new ArrayList<EmbaseBusinessCenterModel>();
		dbconn db = new dbconn();
		boolean flag = isManager();
		String strs = "";
		if(flag==false)
		{
			strs = " and emce_id in(select embu_id from EmbuGroupRel ";
			strs = strs
				+ " where rol_id in(select a.rol_id from logingroup a,Login b "
				+ "where a.log_id=b.log_id and log_name='"
				+ UserInfo.getUsername() + "'))";
		}
		String sql = "select * from EmbaseBusinessCenter where 1=1 and emce_state=1 "
				+ str +strs+ " order by emce_order ";
		try {
			fidlist = db.find(sql, EmbaseBusinessCenterModel.class,
					dbconn.parseSmap(EmbaseBusinessCenterModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fidlist;
	}
	
	// 获取业务中心菜单名称
		public List<EmbaseBusinessCenterModel> getEmbaseBusinessCenterInfos(
				String str) {
			List<EmbaseBusinessCenterModel> fidlist = new ArrayList<EmbaseBusinessCenterModel>();
			dbconn db = new dbconn();
			String strs = "";
			String sql = "select * from EmbaseBusinessCenter where 1=1 and emce_state=1 "
					+ str +strs+ " order by emce_order ";
			try {
				fidlist = db.find(sql, EmbaseBusinessCenterModel.class,
						dbconn.parseSmap(EmbaseBusinessCenterModel.class));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return fidlist;
		}

	// 菜单角色分配表的某条信息
	public List<EmbaseBusinessCenterModel> getbumenupub(String str) {
		ResultSet rs = null;
		List<EmbaseBusinessCenterModel> roleinfo = new ArrayList<EmbaseBusinessCenterModel>();
		if (!roleinfo.isEmpty())
			roleinfo.clear();
		try {
			dbconn db = new dbconn();
			String sqlstr = "select * from EmbuGroupRel where 1=1 " + str;
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmbaseBusinessCenterModel menumodel = new EmbaseBusinessCenterModel();
				menumodel.setEmce_id(rs.getInt("embu_id"));
				roleinfo.add(menumodel);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return roleinfo;
	}

	// 菜单角色分配表的某条信息
	public List<EmbaseBusinessCenterModel> getEmOnBoardList(int gid) {
		ResultSet rs = null;
		List<EmbaseBusinessCenterModel> roleinfo = new ArrayList<EmbaseBusinessCenterModel>();
		if (!roleinfo.isEmpty())
			roleinfo.clear();
		try {
			dbconn db = new dbconn();
			String sqlstr = "select distinct(embo_pid2) from EmOnBoardList where gid= "
					+ gid + " " + "and embo_pid2 is not null and embo_state=0";
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmbaseBusinessCenterModel menumodel = new EmbaseBusinessCenterModel();
				menumodel.setEmce_id(rs.getInt("embo_pid2"));
				roleinfo.add(menumodel);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return roleinfo;
	}

	// 根据员工编号查询员工基本信息表是否有电脑号和个人公积金号
	public EmbaseModel getEmbaseID(Integer gid) {
		EmbaseModel model = new EmbaseModel();
		String sql = "select isnull(emba_computerid,'') emba_computerid,isnull(emba_houseid,'') emba_houseid,"
				+ "isnull(emba_emhb_ownmonth,'') emba_emhb_ownmonth,isnull(emba_emsb_ownmonth,'') emba_emsb_ownmonth,"
				+ "isnull(emba_idcardclass,'') emba_idcardclass,emba_emsb_foreigner,emba_emsb_r1," +
				"emba_emsb_r2,emba_emsb_r3,emba_emsb_m1,emba_emsb_m2,emba_emsb_m3,emba_emhb_startdate,emba_emhb_stopdate" +
				",emba_emhb_radix from EmBase where gid="
				+ gid;
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				model.setEmba_computerid(rs.getString("emba_computerid"));
				model.setEmba_houseid(rs.getString("emba_houseid"));
				model.setEmba_emhb_ownmonth(rs.getString("emba_emhb_ownmonth"));// 公积金补缴月份
				model.setEmba_emsb_ownmonth(rs.getString("emba_emsb_ownmonth"));// 社保补缴月份
				model.setEmba_idcardclass(rs.getString("emba_idcardclass"));// 身份证类型
				model.setEmba_emsb_foreigner(rs.getString("emba_emsb_foreigner"));// 是否是外籍人
				model.setEmba_emsb_m1(rs.getString("emba_emsb_m1"));
				model.setEmba_emsb_m2(rs.getString("emba_emsb_m2"));
				model.setEmba_emsb_m3(rs.getString("emba_emsb_m3"));
				model.setEmba_emsb_r1(rs.getInt("emba_emsb_r1"));
				model.setEmba_emsb_r2(rs.getInt("emba_emsb_r2"));
				model.setEmba_emsb_r3(rs.getInt("emba_emsb_r3"));
				model.setEmba_emhb_startdate(rs.getString("emba_emhb_startdate"));
				model.setEmba_emhb_stopdate(rs.getString("emba_emhb_stopdate"));
				
				model.setEmba_emhb_radix(rs.getBigDecimal("emba_emhb_radix"));
			}
		} catch (Exception e) {

		}
		return model;
	}

	// 获取员工的gid
	public Integer getgid(int id) {
		Integer gid = null;
		ResultSet rs = null;
		try {
			dbconn db = new dbconn();
			String sqlstr = "select top 1 gid from EmOnBoardList a,TaskBatch b,TaskBatchRelBusiness c "
					+ "where a.embo_id=c.tbrb_data_id and b.taba_id=c.tbrb_taba_id and b.taba_id="
					+ id;
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				gid = rs.getInt("gid");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gid;
	}

	// 查询是否是系统管理员
	private boolean isManager() {
		String sql = "select * from role a inner join logingroup b on a.rol_id=b.rol_id "
				+ "inner join Login c on b.log_id=c.log_id "
				+ "where log_name='"
				+ UserInfo.getUsername()
				+ "' and rol_name='系统管理员'";
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

}
