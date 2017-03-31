package dal.Taskflow;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.EmbaseBusinessCenterModel;
import Model.EmbaseModel;
import Util.UserInfo;

public class EmBaseMenulistDal {
	// 员工基本信息修改
	public Integer EmbaseUpdate(EmbaseModel em) {
		Integer i = 0;
		dbconn db = new dbconn();
		System.out.println("em.getEmba_id()="+em.getEmba_id());
		try {
			i = Integer
					.valueOf(db
							.callWithReturn(
									"{?=call EmBaseUpdate_P_cyj(" +
									"?,?,?,?,?,?,?,?,?,?," +
									"?,?,?,?,?,?,?,?,?,?," +
									"?,?,?,?,?,?,?,?,?,?," +
									"?,?,?,?,?,?,?,?,?,?," +
									"?,?,?,?,?,?,?,?,?,?," +
									"?,?,?,?,?,?,?,?,?,?," +
									"?,?,?,?,?,?,?,?,?,?," +
									"?,?,?,?,?,?,?,?,?,?," +
									"?,?,?,?,?,?,?,?,?,?," +
									"?,?,?,?,?,?,?,?,?,?," +
									"?,?,?,?,?,?,?,?,?,?," +
									"?,?,?,?,?,?,?,?,?,?," +
									"?,?,?,?,?,?,?,?,?,?," +
									"?,?,?,?,?,?,?,?,?,?," +
									"?,?,?,?,?)}",
									Types.INTEGER,
									em.getEmba_id(),
									em.getCid(), 
									em.getEmba_name(),
									em.getEmba_sex(), 
									em.getEmba_idcard(),
									em.getEmba_idcardclass(),
									em.getEmba_hjadd(),
									em.getEmba_native(),
									em.getEmba_hjtype(),
									em.getEmba_nationality(),
									em.getEmba_folk(),
									em.getEmba_birth(),
									em.getEmba_marital(),
									em.getEmba_phone(),
									em.getEmba_mobile(),
									em.getEmba_epname(),
									em.getEmba_epmobile(),
									em.getEmba_address(),
									em.getEmba_email(),
									em.getEmba_privateemail(),
									em.getEmba_party(),
									em.getEmba_status(),
									em.getEmba_degree(),
									em.getEmba_school(),
									em.getEmba_specialty(),
									em.getEmba_graduation(),
									em.getEmba_fileplace(),
									em.getEmba_fileinclass(),
									em.getEmba_filereason(),
									em.getEmba_filedebts(),
									em.getEmba_filedebtsmonth(),
									em.getEmba_filehj(),
									em.getEmba_computerid(),
									em.getEmba_hand(),
									em.getEmba_sbcard(), 
									em.getEmba_houseid(),
									em.getEmba_excompanystate(),
									em.getEmba_title(),
									em.getEmba_wifename(),
									em.getEmba_wifeidcard(),
									em.getEmba_gz_email(),
									em.getEmba_gz_cemail(),
									em.getEmba_gz_account(),
									em.getEmba_gz_bank(),
									em.getEmba_writeoff_bank(),
									em.getEmba_writeoff_account(),
									em.getEmba_housecode(),
									em.getEmba_housetime(),
									em.getEmba_housetype(),
									em.getEmba_houseclass(),
									em.getEmba_skilllevel(),
									em.getEmba_worktime(),
									em.getEmba_sztime(),
									em.getEmba_hjtime(),
									em.getEmba_regtype(),
									em.getEmba_compactlimit(),
									em.getEmba_compactstart(),
									em.getEmba_compactend(),
									em.getEmba_companystart(),
									em.getEmba_station(),
									em.getEmba_birthcontrol(),
									em.getEmba_photonum(),
									em.getEmba_sy_account(),
									em.getEmba_sy_bank(),
									em.getEmba_sbname1(),
									em.getEmba_sbname2(), 
									em.getEmba_sbname3(),
									em.getEmba_sbname4(),
									em.getEmba_sbage1(),
									em.getEmba_sbage2(),
									em.getEmba_sbage3(),
									em.getEmba_sbage4(),
									em.getEmba_sbidcard1(),
									em.getEmba_sbidcard2(),
									em.getEmba_sbidcard3(),
									em.getEmba_sbidcard4(),
									em.getEmba_sbbirth1(),
									em.getEmba_sbbirth2(),
									em.getEmba_sbbirth3(),
									em.getEmba_sbbirth4(),
									em.getEmba_sbrelation1(),
									em.getEmba_sbrelation2(),
									em.getEmba_sbrelation3(),
									em.getEmba_sbrelation4(),
									em.getEmba_hospital(),
									em.getEmba_bcaddress(),
									em.getEmba_bctime(),
									em.getEmba_worktime1(),
									em.getEmba_worktime2(),
									em.getEmba_worktime3(),
									em.getEmba_worktime4(),
									em.getEmba_worktime5(),
									em.getEmba_worktime6(),
									em.getEmba_workcompany1(),
									em.getEmba_workcompany2(),
									em.getEmba_workcompany3(),
									em.getEmba_workcompany4(),
									em.getEmba_workcompany5(),
									em.getEmba_workcompany6(),
									em.getEmba_workjob1(),
									em.getEmba_workjob2(),
									em.getEmba_workjob3(),
									em.getEmba_workjob4(),
									em.getEmba_workjob5(),
									em.getEmba_workjob6(),
									em.getEmba_f1(),
									em.getEmba_f2(),
									em.getEmba_f3(),
									em.getEmba_f4(),
									em.getEmba_f5(),
									em.getEmba_f6(), 
									em.getEmba_fn1(),
									em.getEmba_fn2(),
									em.getEmba_fn3(),
									em.getEmba_fn4(),
									em.getEmba_fn5(),
									em.getEmba_fn6(),
									em.getEmba_fag1(),
									em.getEmba_fag2(), 
									em.getEmba_fag3(),
									em.getEmba_fag4(),
									em.getEmba_fag5(),
									em.getEmba_fag6(),
									em.getEmba_fw1(),
									em.getEmba_fw2(), 
									em.getEmba_fw3(),
									em.getEmba_fw4(),
									em.getEmba_fw5(),
									em.getEmba_fw6(),
									em.getEmba_fr1(),
									em.getEmba_fr2(), 
									em.getEmba_fr3(),
									em.getEmba_fr4(),
									em.getEmba_fr5(),
									em.getEmba_fr6(),
									em.getEmba_remark(),
									em.getEmba_addname(),
									em.getEmba_excompanyid(),
									em.getEmba_excompany(),
									UserInfo.getUsername(),
									em.getEmba_csid(),
									em.getEmba_cpid(),
									em.getEmba_englishname(),
									em.getEmba_costcenter(),em.getEmba_sbemail()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return i;
	}

	// 员工入职时查询左侧菜单
	public List<EmbaseBusinessCenterModel> treeList(Integer type, Integer gid) {
		List<EmbaseBusinessCenterModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select distinct emce_id,emce_pid,emce_menuname, emce_menuurl, emce_wfbu_id, emce_order, emce_state, emce_addname, emce_addtime,emce_onboard "
				+ "from EmbaseBusinessCenter " + "where emce_state=1 ";
		
		/*
		switch (Integer.valueOf(UserInfo.getDepID())) {
		case 2:
		case 3:
		case 6:
		case 16:
			break;
		default:
			//str=" and emce_id!=70";
			break;
		}
		*/
		switch (type) {
		case 1:
			sql = sql + " and emce_onboard in(1,2) ";
			break;
		case 2:
			
			sql = sql
					+ " and emce_id in (select embo_pid from EmOnBoardList where gid="
					+ gid + ")";
			
			break;

		default:
			break;
		}
		sql = sql + " order by emce_pid,emce_order";
		
		try {
			list = db.find(sql, EmbaseBusinessCenterModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	//入职业务菜单
	public List<EmbaseBusinessCenterModel> btree(Integer gid){
		List<EmbaseBusinessCenterModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select * from EmbaseBusinessCenter " +
				"where emce_state=1 and (emce_onboard in (0,1) or " +
				"(emce_onboard=2 and emce_id in (select embo_pid from EmOnBoardList where gid=?)))";
		try {
			list = db.find(sql, EmbaseBusinessCenterModel.class, null,gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 把选择的菜单Id添加到EmbaseMenuList表
	public Integer EmbaseMenuListAdd(Integer gid, Integer menuid) {
		Integer k = 0;
		try {
			String sql = "";
			dbconn db = new dbconn();
			sql = "insert into  EmbaseMenuList(gid,menu_id) values(" + gid
					+ "," + menuid + ")";
			k = db.execQuery(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}

	// 根据gid闪淳信息
	public Integer EmbaseMenuListdelete(Integer gid) {
		Integer k = 0;
		try {
			String sql = "";
			dbconn db = new dbconn();
			sql = "delete from  EmbaseMenuList where gid=" + gid;
			k = db.execQuery(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}
}
