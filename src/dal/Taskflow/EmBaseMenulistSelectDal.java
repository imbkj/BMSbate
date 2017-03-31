package dal.Taskflow;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.CobasedepartmentModel;
import Model.EmbaseModel;
import Model.cobasepositionModel;

public class EmBaseMenulistSelectDal {

	public List<EmbaseModel> getEmbaseLoginInfo(Integer gid) {
		List<EmbaseModel> list = new ArrayList<EmbaseModel>();
		dbconn db = new dbconn();
		String sql = "select Embase.cid,gid,emba_id,coba_company,emba_indate,emba_outreason, emba_name,emba_sex,emba_outdate,emba_idcard,emba_idcardclass,emba_hjadd,"
				+ " emba_native,emba_hjtype,emba_nationality,emba_folk,convert(varchar(10),emba_birth,120) emba_birth,"
				+ " emba_marital,emba_phone,emba_mobile,emba_epname,emba_epmobile,"
				+ " emba_address,emba_email,emba_privateemail,emba_party,emba_status,"
				+ " emba_degree,emba_school,emba_specialty,convert(varchar(10),emba_graduation,120) emba_graduation,emba_fileplace,"
				+ " emba_fileinclass,emba_filereason,emba_filedebts,emba_filedebtsmonth ,emba_filehj,"
				+ " emba_computerid ,	emba_hand ,	emba_sbcard ,	emba_houseid ,	emba_excompanystate ,"
				+ " emba_title ,	emba_wifename ,	emba_wifeidcard ,	emba_gz_email,"
				+ " emba_gz_account ,	emba_gz_bank ,emba_writeoff_bank, emba_writeoff_account,emba_housecode ,convert(varchar(10),emba_housetime,120) emba_housetime,"
				+ " emba_housetype ,	emba_houseclass ,	emba_skilllevel ,convert(varchar(10),emba_worktime,120) emba_worktime,"
				+ " convert(varchar(10),emba_sztime,120) emba_sztime,convert(varchar(10),emba_hjtime,120) emba_hjtime,emba_regtype,emba_compactlimit,"
				+ " convert(varchar(10),emba_compactstart,120) emba_compactstart,convert(varchar(10),emba_compactend,120) emba_compactend,"
				+ " convert(varchar(10),emba_companystart,120) emba_companystart,emba_station ,"
				+ " emba_birthcontrol ,	emba_photonum ,	emba_sy_account,emba_sy_bank,emba_sbname1,"
				+ " emba_sbname2,emba_sbname3,emba_sbname4,emba_sbage1,emba_sbage2,"
				+ " emba_sbage3,emba_sbage4,emba_sbidcard1,emba_sbidcard2 ,emba_sbidcard3 ,"
				+ " emba_sbidcard4,emba_sbbirth1 emba_sbbirth1_d,emba_sbbirth2 emba_sbbirth2_d,emba_sbbirth3 emba_sbbirth3_d,emba_sbbirth4 emba_sbbirth4_d ,"
				+ " emba_sbrelation1 ,emba_sbrelation2 ,emba_sbrelation3 ,"
				+ " emba_sbrelation4 ,emba_hospital ,emba_bcaddress ,convert(varchar(10),emba_bctime,120) emba_bctime,"
				+ " emba_worktime1 ,emba_worktime2 ,emba_worktime3 ,emba_worktime4 ,"
				+ " emba_worktime5 ,emba_worktime6 ,emba_workcompany1 ,emba_workcompany2 ,"
				+ " emba_workcompany3 ,emba_workcompany4 ,emba_workcompany5 ,	emba_workcompany6 ,"
				+ " emba_workjob1 ,	emba_workjob2 ,	emba_workjob3 ,emba_workjob4 ,emba_workjob5 ,"
				+ " emba_workjob6 ,emba_f1 ,emba_f2 ,emba_f3 ,emba_f4 ,"
				+ " emba_f5 ,emba_f6 ,emba_fn1 ,emba_fn2 ,emba_fn3 ,emba_fn4 ,"
				+ " emba_fn5 ,emba_fn6 ,emba_fag1 ,emba_fag2 ,emba_fag3 ,emba_fag4 ,"
				+ " emba_fag5 ,emba_fag6 ,emba_fw1 ,emba_fw2 ,emba_fw3 ,"
				+ " emba_fw4 ,emba_fw5 ,emba_fw6 ,emba_fr1 ,emba_fr2 ,"
				+ " emba_fr3 ,emba_fr4 ,emba_fr5 ,emba_fr6 ,emba_remark ,"
				+ " emba_addname,	emba_spell,	emba_pinyin,convert(varchar(10),"
				+ " emba_addtime,120) emba_addtime,emba_state,emba_excompanyid,emba_sb_hj,emba_csid,emba_cpid,emba_englishname,emba_costcenter,"
				+ " emba_excompany,emba_sbemail from embase left join cobase on embase.cid=cobase.cid "
				+ " where gid=?";
		try {
			list = db.find(sql, EmbaseModel.class,
					dbconn.parseSmap(EmbaseModel.class), gid);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<Integer> getEmbaseMenuList(Integer gid) {
		List<Integer> list = new ArrayList<Integer>();
		dbconn db = new dbconn();
		String sql = " select menu_id from EmbaseMenuList where gid=" + gid;
		ResultSet rs = null;
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				list.add(rs.getInt("menu_id"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public Integer getEmbaId(Integer gid) {
		Integer k = 0;
		dbconn db = new dbconn();
		String sql = " select emba_id from EmBaseNotIn where gid=" + gid;
		ResultSet rs = null;
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				k = rs.getInt("emba_id");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}

	// 职位表
	public List<cobasepositionModel> getcsidList(String str) {
		List<cobasepositionModel> list = new ArrayList<cobasepositionModel>();
		cobasepositionModel m = null;
		dbconn db = new dbconn();
		String sql;
		try {
			sql = "select * from cobaseposition where 1=1 " + str
					+ " order by coba_positionname";
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				// 向Model插入数据
				m = new cobasepositionModel();
				m.setCid(rs.getInt("cid"));
				m.setCoba_positionid(rs.getInt("coba_positionid"));
				m.setCoba_positionname(rs.getString("coba_positionname"));
				list.add(m);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}

	// 部门表
	public List<CobasedepartmentModel> getcpidList(String str) {
		List<CobasedepartmentModel> list = new ArrayList<CobasedepartmentModel>();
		CobasedepartmentModel m = null;
		dbconn db = new dbconn();
		String sql;
		try {
			sql = "select * from Cobasedepartment where 1=1 " + str
					+ " order by coba_pname";
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				// 向Model插入数据
				m = new CobasedepartmentModel();
				m.setCid(rs.getInt("cid"));
				m.setCoba_pid(rs.getInt("coba_pid"));
				m.setCoba_pname(rs.getString("coba_pname"));
				m.setCoba_ppid(rs.getInt("coba_ppid"));
				list.add(m);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}

}
