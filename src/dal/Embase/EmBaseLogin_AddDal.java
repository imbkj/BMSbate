package dal.Embase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.EmBaseRequiredModel;
import Model.EmBcSetupAddressModel;
import Model.EmBcSetupModel;
import Model.EmbaseModel;
import Model.Emcontactinfo;
import Model.PubCodeConversionModel;

public class EmBaseLogin_AddDal {

	/**
	 * 查询确认姓名，身份证，户籍
	 * 
	 * @param gid
	 * @return
	 */
	public Emcontactinfo getEmzt(int gid) {
		dbconn db = new dbconn();
		Emcontactinfo m = new Emcontactinfo();
		String sql = "SELECT emzt_t_name,emzt_t_idcard,emzt_hjadd FROM Emcontactinfo WHERE gid = ?";
		PreparedStatement pstmt = db.getpre(sql);
		try {
			pstmt.setInt(1, gid);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				m.setEmzt_t_name(rs.getString("emzt_t_name"));
				m.setEmzt_t_idcard(rs.getString("emzt_t_idcard"));
				m.setEmzt_hjadd(rs.getString("emzt_hjadd"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return m;
	}

	public List<PubCodeConversionModel> getPubCodeList(Object... objs) {
		List<PubCodeConversionModel> list = new ArrayList<PubCodeConversionModel>();
		dbconn db = new dbconn();
		String sql = "SELECT pcco_id, pucl_id, pcco_name, pcco_cn, pcco_code, "
				+ "pcco_code+'.'+pcco_cn name "
				+ "FROM PubCodeConversion where pcco_name=?";

		try {
			list = db.find(sql, PubCodeConversionModel.class,
					dbconn.parseSmap(PubCodeConversionModel.class), objs);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<EmBcSetupModel> getBcSetupList(Object... objs) {
		List<EmBcSetupModel> list = new ArrayList<EmBcSetupModel>();
		dbconn db = new dbconn();
		ResultSet rs = null;
		String sql = "select ebcs_id, ebcs_hospital, ebcs_name, ebcs_phone, "
				+ "ebcs_inuredate, ebcs_indate, ebcs_flow, ebcs_balance, ebcs_remark, ebcs_formula, "
				+ "ebcs_tips, ebcs_state, ebcs_pstate, "
				+ "ebcs_addname from embcsetup where ebcs_state=1";

		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				EmBcSetupModel m = new EmBcSetupModel();
				m.setEbcs_id(rs.getInt("ebcs_id"));
				m.setEbcs_hospital(rs.getString("ebcs_hospital"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<EmBcSetupAddressModel> getBcSetupAddressList(Object... objs) {
		List<EmBcSetupAddressModel> list = new ArrayList<EmBcSetupAddressModel>();
		dbconn db = new dbconn();
		ResultSet rs = null;
		String sql = "SELECT ebsa_id, ebsa_ebcs_id, ebsa_address, ebsa_istate, ebsa_ystate, "
				+ "ebsa_state, ebsa_addname, ebsa_modname "
				+ "FROM EmBcSetupAddress WHERE ebsa_state = 1";

		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				EmBcSetupAddressModel m = new EmBcSetupAddressModel();
				m.setEbsa_ebcs_id(rs.getInt("ebsa_ebcs_id"));
				m.setEbsa_address(rs.getString("ebsa_address"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<String> getFolkList(Object... objs) {
		List<String> list = new ArrayList<String>();
		dbconn db = new dbconn();
		ResultSet rs = null;
		String sql = "select * from pubfolk";

		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				list.add(rs.getString("pufo_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public boolean isExists(int emba_id) {
		boolean is = false;
		dbconn db = new dbconn();
		ResultSet rs = null;
		String sql = "select count(*) num from EmBaseLogin where emba_id="
				+ emba_id;

		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				is = rs.getInt("num") > 0 ? true : false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return is;
	}

	public List<String> getRequiredList(int emba_id) {
		List<String> list = new ArrayList<String>();
		dbconn db = new dbconn();
		ResultSet rs = null;
		String sql = "select * from EmBaseRequired where embr_emba_id="
				+ emba_id;

		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				list.add(rs.getString("embr_column"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<EmbaseModel> getEmbaseLoginInfo(Object... objs) {
		List<EmbaseModel> list = new ArrayList<EmbaseModel>();
		dbconn db = new dbconn();
		String sql = "select cid, emba_name,emba_sex,emba_type,emba_idcard,emba_idcardclass,emba_hjadd,"
				+ " emba_native,emba_hjtype,emba_nationality,emba_folk,convert(varchar(10),emba_birth,120) emba_birth,"
				+ " emba_marital,emba_phone,emba_mobile,emba_epname,emba_epmobile,"
				+ " emba_address,emba_email,emba_privateemail,emba_party,emba_status,"
				+ " emba_degree,emba_school,emba_specialty,convert(varchar(10),emba_graduation,120) emba_graduation,emba_fileplace,"
				+ " emba_fileinclass,emba_filereason,emba_filedebts,emba_filedebtsmonth ,emba_filehj,"
				+ " emba_computerid ,	emba_hand ,	emba_sbcard ,	emba_houseid ,	emba_excompanystate ,"
				+ " emba_title ,	emba_wifename ,	emba_wifeidcard ,	emba_gz_email,"
				+ " emba_gz_account ,	emba_gz_bank ,Emba_writeoff_bank, Emba_writeoff_account,emba_housecode ,convert(varchar(10),emba_housetime,120) emba_housetime,"
				+ " emba_housetype ,	emba_houseclass ,	emba_skilllevel ,convert(varchar(10),emba_worktime,120) emba_worktime,"
				+ " convert(varchar(10),emba_sztime,120) emba_sztime,convert(varchar(10),emba_hjtime,120) emba_hjtime,emba_regtype,emba_compactlimit,"
				+ " convert(varchar(10),emba_compactstart,120) emba_compactstart,convert(varchar(10),emba_compactend,120) emba_compactend,"
				+ " convert(varchar(10),emba_companystart,120) emba_companystart,emba_station ,"
				+ " emba_birthcontrol ,	emba_photonum ,	emba_sy_account,emba_sy_bank,emba_sbname1,"
				+ " emba_sbname2,emba_sbname3,emba_sbname4,emba_sbage1,emba_sbage2,"
				+ " emba_sbage3,emba_sbage4,emba_sbidcard1,emba_sbidcard2 ,emba_sbidcard3 ,"
				+ " emba_sbidcard4 ,emba_sbrelation1 ,emba_sbrelation2 ,emba_sbrelation3 ,"
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
				+ " emba_addtime,120) emba_addtime,emba_state,emba_tapr_id,emba_excompanyid,"
				+ " emba_excompany,emba_ifcomputerid,emba_sbcard_notice,emba_service_place,emba_ifhouse"
				+ " from embasenotin where emba_id=?";
		System.out.println(sql);
		try {
			list = db.find(sql, EmbaseModel.class,
					dbconn.parseSmap(EmbaseModel.class), objs);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<EmbaseModel> getembaseInfo(Integer embaId) {
		List<EmbaseModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select emba_gjjuname,emba_gjjidcard,emba_type,emba_sbuname,emba_sbidcard,emba_id,a.cid,gid, emba_name,emba_sex,emba_idcard,emba_idcardclass,emba_hjadd,"
				+ " emba_native,emba_hjtype,emba_nationality,emba_folk,convert(varchar(10),emba_birth,120) emba_birth,"
				+ " emba_marital,emba_phone,emba_mobile,emba_epname,emba_epmobile,emba_emsb_foreigner,"
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
				+ " emba_sbidcard4 ,emba_sbrelation1 ,emba_sbrelation2 ,emba_sbrelation3 ,"
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
				+ " emba_addtime,120) emba_addtime,emba_state,emba_tapr_id,emba_excompanyid,"
				+ " emba_excompany,emba_ifcomputerid,emba_sbcard_notice,emba_service_place,emba_ifhouse,"
				+ "	emba_sb_place,emba_house_place,emba_sb_radix,emba_formula,emba_sb_hj,emba_house_radix,emba_house_cpp,"
				+ " emba_emsb_ownmonth,emba_emsb_feeownmonth,emba_emsb_m1,emba_emsb_r1,emba_emsb_m2,emba_emsb_r2,emba_emsb_m3,"
				+ " emba_emsb_r3,emba_emhb_ownmonth,emba_emhb_feeownmonth,emba_emhb_startdate,emba_emhb_stopdate,emba_emhb_reason,"
				+ " emba_emhb_radix,emba_emhb_total,emba_gjjuname,emba_gjjidcard,emba_sbuname,emba_sbidcard,"
				+ " coba_company,emba_emsb_jlbj1,emba_emsb_jlbj2,emba_emsb_jlbj3,emba_modname "
				+ " from embase a inner join cobase b on a.cid=b.cid where emba_id=?";
		try {
			list = db.find(sql, EmbaseModel.class, null, embaId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public int EmbaseRequiredAdd(EmBaseRequiredModel m) {
		Integer i = 0;
		dbconn db = new dbconn();

		try {
			i = Integer.valueOf(db.callWithReturn(
					"{?=call EmBaseRequiredAdd_P_ply(?,?)}", Types.INTEGER,
					m.getEmbr_emba_id(), m.getEmbr_column()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	public int EmbaseloginAdd(EmbaseModel em) {
		Integer i = 0;
		dbconn db = new dbconn();
		try {
			i = Integer
					.valueOf(db
							.callWithReturn(
									"{?=call EmBaseNotinAdd_P_ply(?,?,?,?,?,?,?,?,?" +
									",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?" +
									",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?" +
									",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
									"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
									"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
									"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, em.getCid(),
									em.getEmba_type(), em.getEmba_name(),
									em.getEmba_sex(), em.getEmba_idcard(),
									em.getEmba_idcardclass(),
									em.getEmba_hjadd(), em.getEmba_native(),
									em.getEmba_hjtype(),
									em.getEmba_nationality(),
									em.getEmba_folk(), em.getEmba_birth(),
									em.getEmba_marital(), em.getEmba_phone(),
									em.getEmba_mobile(), em.getEmba_epname(),
									em.getEmba_epmobile(),
									em.getEmba_address(), em.getEmba_email(),
									em.getEmba_privateemail(),
									em.getEmba_party(), em.getEmba_status(),
									em.getEmba_degree(), em.getEmba_school(),
									em.getEmba_specialty(),
									em.getEmba_graduation(),
									em.getEmba_fileplace(),
									em.getEmba_fileinclass(),
									em.getEmba_filereason(),
									em.getEmba_filedebts(),
									em.getEmba_filedebtsmonth(),
									em.getEmba_filehj(),
									em.getEmba_computerid(), em.getEmba_hand(),
									em.getEmba_sbcard(), em.getEmba_houseid(),
									em.getEmba_excompanystate(),
									em.getEmba_title(), em.getEmba_wifename(),
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
									em.getEmba_worktime(), em.getEmba_sztime(),
									em.getEmba_hjtime(), em.getEmba_regtype(),
									em.getEmba_compactlimit(),
									em.getEmba_compactstart(),
									em.getEmba_compactend(),
									em.getEmba_companystart(),
									em.getEmba_station(),
									em.getEmba_birthcontrol(),
									em.getEmba_photonum(),
									em.getEmba_sy_account(),
									em.getEmba_sy_bank(), em.getEmba_sbname1(),
									em.getEmba_sbname2(), em.getEmba_sbname3(),
									em.getEmba_sbname4(), em.getEmba_sbage1(),
									em.getEmba_sbage2(), em.getEmba_sbage3(),
									em.getEmba_sbage4(),
									em.getEmba_sbidcard1(),
									em.getEmba_sbidcard2(),
									em.getEmba_sbidcard3(),
									em.getEmba_sbidcard4(),
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
									em.getEmba_workjob6(), em.getEmba_f1(),
									em.getEmba_f2(), em.getEmba_f3(),
									em.getEmba_f4(), em.getEmba_f5(),
									em.getEmba_f6(), em.getEmba_fn1(),
									em.getEmba_fn2(), em.getEmba_fn3(),
									em.getEmba_fn4(), em.getEmba_fn5(),
									em.getEmba_fn6(), em.getEmba_fag1(),
									em.getEmba_fag2(), em.getEmba_fag3(),
									em.getEmba_fag4(), em.getEmba_fag5(),
									em.getEmba_fag6(), em.getEmba_fw1(),
									em.getEmba_fw2(), em.getEmba_fw3(),
									em.getEmba_fw4(), em.getEmba_fw5(),
									em.getEmba_fw6(), em.getEmba_fr1(),
									em.getEmba_fr2(), em.getEmba_fr3(),
									em.getEmba_fr4(), em.getEmba_fr5(),
									em.getEmba_fr6(), em.getEmba_remark(),
									em.getEmba_addname(), em.getEmba_state(),
									em.getEmba_excompanyid(),
									em.getEmba_excompany(), em.getEmba_zytid(),
									em.getEmba_zytwtgid(),
									em.getEmba_ifcomputerid(),
									em.getEmba_sbcard_notice(),
									em.getEmba_service_place(),
									em.getEmba_ifhouse(),em.getEmba_form()).toString()
									
									);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return i;
	}

	public int EmbaseloginUpdate(EmbaseModel em) {
		Integer i = 0;
		dbconn db = new dbconn();
		try {
			i = Integer
					.valueOf(db
							.callWithReturn(
									"{?=call EmBaseNotinUpdate_P_ply(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?" +
									",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
									"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, em.getEmba_id(),
									em.getCid(), em.getEmba_name(),
									em.getEmba_sex(), em.getEmba_idcard(),
									em.getEmba_idcardclass(),
									em.getEmba_hjadd(), em.getEmba_native(),
									em.getEmba_hjtype(),
									em.getEmba_nationality(),
									em.getEmba_folk(), em.getEmba_birth(),
									em.getEmba_marital(), em.getEmba_phone(),
									em.getEmba_mobile(), em.getEmba_epname(),
									em.getEmba_epmobile(),
									em.getEmba_address(), em.getEmba_email(),
									em.getEmba_privateemail(),
									em.getEmba_party(), em.getEmba_status(),
									em.getEmba_degree(), em.getEmba_school(),
									em.getEmba_specialty(),
									em.getEmba_graduation(),
									em.getEmba_fileplace(),
									em.getEmba_fileinclass(),
									em.getEmba_filereason(),
									em.getEmba_filedebts(),
									em.getEmba_filedebtsmonth(),
									em.getEmba_filehj(),
									em.getEmba_computerid(), em.getEmba_hand(),
									em.getEmba_sbcard(), em.getEmba_houseid(),
									em.getEmba_excompanystate(),
									em.getEmba_title(), em.getEmba_wifename(),
									em.getEmba_wifeidcard(),
									em.getEmba_gz_email(),
									em.getEmba_gz_cemail(),
									em.getEmba_gz_account(),
									em.getEmba_gz_bank(),
									em.getEmba_gz_bxbank(),
									em.getEmba_gz_bxaccount(),
									em.getEmba_housecode(),
									em.getEmba_housetime(),
									em.getEmba_housetype(),
									em.getEmba_houseclass(),
									em.getEmba_skilllevel(),
									em.getEmba_worktime(), em.getEmba_sztime(),
									em.getEmba_hjtime(), em.getEmba_regtype(),
									em.getEmba_compactlimit(),
									em.getEmba_compactstart(),
									em.getEmba_compactend(),
									em.getEmba_companystart(),
									em.getEmba_station(),
									em.getEmba_birthcontrol(),
									em.getEmba_photonum(),
									em.getEmba_sy_account(),
									em.getEmba_sy_bank(), em.getEmba_sbname1(),
									em.getEmba_sbname2(), em.getEmba_sbname3(),
									em.getEmba_sbname4(), em.getEmba_sbage1(),
									em.getEmba_sbage2(), em.getEmba_sbage3(),
									em.getEmba_sbage4(),
									em.getEmba_sbidcard1(),
									em.getEmba_sbidcard2(),
									em.getEmba_sbidcard3(),
									em.getEmba_sbidcard4(),
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
									em.getEmba_workjob6(), em.getEmba_f1(),
									em.getEmba_f2(), em.getEmba_f3(),
									em.getEmba_f4(), em.getEmba_f5(),
									em.getEmba_f6(), em.getEmba_fn1(),
									em.getEmba_fn2(), em.getEmba_fn3(),
									em.getEmba_fn4(), em.getEmba_fn5(),
									em.getEmba_fn6(), em.getEmba_fag1(),
									em.getEmba_fag2(), em.getEmba_fag3(),
									em.getEmba_fag4(), em.getEmba_fag5(),
									em.getEmba_fag6(), em.getEmba_fw1(),
									em.getEmba_fw2(), em.getEmba_fw3(),
									em.getEmba_fw4(), em.getEmba_fw5(),
									em.getEmba_fw6(), em.getEmba_fr1(),
									em.getEmba_fr2(), em.getEmba_fr3(),
									em.getEmba_fr4(), em.getEmba_fr5(),
									em.getEmba_fr6(), em.getEmba_remark(),
									em.getEmba_addname(),
									em.getEmba_excompanyid(),
									em.getEmba_excompany(),
									em.getEmba_ifcomputerid(),
									em.getEmba_sbcard_notice(),
									em.getEmba_service_place(),
									em.getEmba_ifhouse(),em.getEmba_state(),em.getEmba_type()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return i;
	}

	public int UpdateTaprid(int daid, int taprid) {
		dbconn db = new dbconn();
		PreparedStatement psmt = null;
		int row = 0;
		String sql = "update embasenotin set emba_tapr_id=? where emba_id=?";

		try {
			psmt = db.getpre(sql);

			psmt.setInt(1, taprid);
			psmt.setInt(2, daid);
			row = psmt.executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
		}
		return row;
	}
	

	public int EmbaseloginUpdateembatype(int embatype,int emba_id) {
		dbconn db = new dbconn();
		PreparedStatement psmt = null;
		int row = 0;
		String sql = "update embasenotin set emba_type=? where emba_id=?";

		try {
			psmt = db.getpre(sql);

			psmt.setInt(1, embatype);
			psmt.setInt(2, emba_id);
			row = psmt.executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
		}
		return row;
	}
	
	public int EmbaseloginUpdatestate(int embastate,int emba_id) {
		dbconn db = new dbconn();
		PreparedStatement psmt = null;
		int row = 0;
		String sql = "update embasenotin set emba_state=? where emba_id=?";

		try {
			psmt = db.getpre(sql);

			psmt.setInt(1, embastate);
			psmt.setInt(2, emba_id);
			row = psmt.executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
		}
		return row;
	}
}
