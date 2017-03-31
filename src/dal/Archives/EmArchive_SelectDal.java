package dal.Archives;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zhtml.I;
import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.EmActySuppilerGiftInfoModel;
import Model.EmArchiveDatumModel;
import Model.EmArchiveModel;
import Model.EmArchiveRemarkModel;
import Model.TaskProcessViewModel;
import Util.UserInfo;

public class EmArchive_SelectDal {
	// 根据传入的查询条件获取档案信息
	public List<EmArchiveModel> getEmArchiveInfo(String str) {
		List<EmArchiveModel> emarchiveinfo = new ArrayList<EmArchiveModel>();
		if (!emarchiveinfo.isEmpty())
			emarchiveinfo.clear();
		try {
			dbconn db = new dbconn();
			String sqlstr = "SELECT  emar_id, a.cid, a.gid, emar_company, emar_name, emar_sex, emar_idcard, emar_link, emar_xs, emar_fid, emar_archivetype, emar_archivearea,"
					+ "emar_surrogateid, emar_surrogatecardid, emar_transferorderid, emar_archivesource, emar_archiveplace, emar_promisesdate, emar_prodate, emar_firstdeadline,"
					+ "emar_continuedeadline, emar_client, emar_grouptype, emar_censusregister, emar_crbelongs, emar_specialty, emar_degree, emar_caste, emar_casteassessdate,"
					+ "emar_party, emar_partydate, emar_partybelongs, emar_marrystate, emar_fertilitystate, emar_school, emar_gradate, emar_workdate, emar_szresume, emar_colhj,"
					+ "emar_wtmode, emar_inciicdate, emar_archivefolddate, emar_archivefoldreason, emar_archivefoldmode, emar_archiveclass, emar_peoplefoldmode,"
					+ "emar_archiveremovedate, emar_archiveremovereason, emar_archiveremovermode, emar_archiveremoveplace, emar_rsetup, emar_rdate, emar_address,"
					+ "emar_rspaykind, emar_hjpaykind, emar_rsinvoice, emar_hjinvoice, emar_addname,"
					+ "emar_SpecialData, emar_state,emar_addtime,emar_modtime,emar_remark,"
					+ "case emar_state when 0 then '已调离' when 1 then '在册'  end state,emba_state,"
					+ "num" + " from EmArchive a";
			sqlstr = sqlstr
					+ " left join (SELECT distinct(gid)gid,count(*) num FROM EmArchiveRemark group by gid) b on a.gid=b.gid "
					+ " left join EmBase c on a.gid=c.gid ";
			sqlstr = sqlstr + " where 1=1 " + str;
			/*
			sqlstr += " and a.CID in ( select cid from DataPopedom where log_id="
					+ UserInfo.getUserid() + " and dat_selected=1 )";
			*/
			sqlstr = sqlstr + " order by emar_fid";
			System.out.println(sqlstr);
			try {
				emarchiveinfo = db.find(sqlstr, EmArchiveModel.class,
						dbconn.parseSmap(EmArchiveModel.class));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return emarchiveinfo;
	}

	// 根据传入的查询条件获取档案备注信息
	public List<EmArchiveRemarkModel> getEmArchiveRemarkInfo(String str) {
		ResultSet rs = null;
		List<EmArchiveRemarkModel> remarkinfo = new ArrayList<EmArchiveRemarkModel>();
		if (!remarkinfo.isEmpty())
			remarkinfo.clear();
		try {
			dbconn db = new dbconn();
			String sqlstr = "SELECT * FROM EmArchiveRemark where 1=1" + str;
			rs = db.GRS(sqlstr);
			System.out.println(sqlstr);
			while (rs.next()) {

				EmArchiveRemarkModel m = new EmArchiveRemarkModel();
				m.setEare_id(rs.getInt("eare_id"));
				m.setEare_tid(rs.getInt("eare_tid"));
				m.setEare_trid(rs.getInt("eare_trid"));
				m.setEare_content(rs.getString("eare_content"));
				m.setEare_addtime(rs.getDate("eare_addtime"));
				m.setEare_addname(rs.getString("eare_addname"));
				m.setEare_state(rs.getInt("eare_state"));
				m.setEare_remark(rs.getString("eare_remark"));
				remarkinfo.add(m);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return remarkinfo;
	}

	// 获取档案业务类型表的信息
	public List<EmArchiveDatumModel> getEmArchiveDatumInfo(String str) {
		List<EmArchiveDatumModel> list = new ListModelList<EmArchiveDatumModel>();
		dbconn db = new dbconn();
		String sql = "select  eada_finaname,eada_dnfee,eada_hkfee,eada_jyfee,eada_backcase,eada_id,a.cid as cid,a.gid,ownmonth,eada_name,eada_filetype,eada_filearea,eada_fid,"
				+ "eada_finaldate,eada_feeenddate,"
				+ "eada_idcard,symr_readstate,case eada_type when 0 then '档案调入' when 1 then '查借材料' when 2 then '出具证明'"
				+ " when 3 then '材料归档' when 4 then '转正定级' when 5 then '档案调出'  when 6 then '欠费查询' end eada_type,"

				+ "eada_fileplace,eada_rspaykind,eada_rsinvoice,eada_hjpaykind,emba_idcard,emba_name,"

				+ "eada_hjinvoice,eada_colhj,convert(char(10),eada_deadline,120) as eada_deadline,eada_savefiledate,eada_savefiledate savefiledate,eada_wtmode,"

				+ "eada_sdh,eada_sdhdate,eada_orderdata,eada_starddate,eada_stopdate,"

				+ "eada_archivename,eada_lendcause,eada_lenddate,eada_lendpeople,eada_returnarchivedate,"

				+ "eada_prove,eada_cause,eada_drawprovedate,eada_drawprovepeople,eada_file,eada_filedate,eada_zg,"

				+ "eada_bf,eada_dms,eada_rsetup,eada_rdate,eada_drawformdate,eada_drawformpeople,eada_returnformdate,"

				+ "eada_transactdate,eada_charge,eada_chargedate,eada_deputy,eada_checkoutdate,eada_checkoutmode,"

				+ "eada_checkoutreason,eada_checkoutsetup,eada_paydate,eada_iffee,eada_arrearageinfo,eada_fileplacefull,eada_client,"

				+ "case eada_final when 0 then '待确定' when 1 then '待处理' when 2 then '处理中' when 3 then '已完成'  when 4 then '已退回'  end eada_final,"

				+ "eada_finaldate,eada_state,eada_addtime,eada_promisefee,eada_returndate,eada_returnname,"

				+ "eada_addname,eada_modtime,eada_modname,eada_senddate,eada_backdate,eada_ifArrears,"

				+ "eada_msg,eada_doc,eada_hj,eada_tapr_id,coba_company,coba_shortname,coba_client,num,"
				+ "emba_outdate,emba_indate,emba_indate indate,emba_id,emba_mobile,emba_email"
				+ " from EmArchiveDatum a"
				+ " inner join cobase b on a.cid=b.cid"

				+ " left join (SELECT distinct(gid)gid,count(*) num FROM EmArchiveRemark group by gid) c on a.gid=c.gid"
				+ " left join EmBase d on a.gid=d.gid and a.cid=d.cid "
				+ " left join (select smwr_tid,MIN(symr_readstate)symr_readstate from View_Message where smwr_table='EmArchiveDatum' group by smwr_tid)f on a.eada_id=f.smwr_tid"
				+ " where 1=1 "
				+ str

				+ " and a.CID in ( select cid from DataPopedom where log_id="
				+ UserInfo.getUserid() + " and dat_selected=1 )"

				+ " order by eada_id desc";
		try {
			list = db.find(sql, EmArchiveDatumModel.class,
					dbconn.parseSmap(EmArchiveDatumModel.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("错误信息：" + e.getMessage());
			e.printStackTrace();
		}
		return list;

	}

	public List<TaskProcessViewModel> getLastId(String id) {
		List<TaskProcessViewModel> list = new ListModelList<TaskProcessViewModel>();
		dbconn db = new dbconn();
		String sql = "select top 1 wfno_id,wfde_id,wfde_name,wfno_name,wfno_url,tacl_id,tali_id,tacl_name,";
		sql = sql
				+ "tali_name,tali_wfde_id,tapr_id,tapr_wfno_id,tapr_dataid,tapr_starname,tapr_starttime,tapr_endname,";
		sql = sql
				+ "tapr_endtime,tapr_state,tapr_statename,wfno_step from View_WfNode_TaskProcess ";
		sql = sql + "where wfno_state=1 and tapr_id=? order by wfno_step desc";
		try {
			list = db.find(sql, TaskProcessViewModel.class,
					dbconn.parseSmap(TaskProcessViewModel.class), id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据gid查找员工的档案号
	public List<EmArchiveModel> getFidInfo(int gid) {
		List<EmArchiveModel> fidlist = new ArrayList<EmArchiveModel>();
		dbconn db = new dbconn();
		// String
		// sql="select (emar_archivetype+'('+emar_fid+')') as emar_fid from EmArchive where gid="+gid;
		String sql = "select convert(char(10),emar_archivefolddate,120) as emar_archivefolddate,"
				+ "convert(char(10),emar_rdate,120) as emar_rdate,"
				+ "convert(char(10),emar_workdate,120) as emar_workdate,"
				+ "convert(char(10),emar_firstdeadline,120) as emar_firstdeadline,"
				+ "convert(char(10),emar_modtime,120) as emar_modtime,"
				+ "convert(char(10),emar_continuedeadline,120) as emar_continuedeadline,"
				+ "convert(char(10),emar_addtime,120) as emar_addtime,"
				+ "convert(char(10),emar_gradate,120) as emar_gradate,"
				+ "convert(char(10),emar_partydate,120) as emar_partydate,"
				+ "convert(char(10),emar_prodate,120) as emar_prodate,"
				+ "convert(char(16),emar_promisesdate,120) as emar_promisesdate,"
				+ "convert(char(16),emar_casteassessdate,120) as emar_casteassessdate,"
				+ "convert(char(16),emar_inciicdate,120) as emar_inciicdate,* from EmArchive where gid="
				+ gid + " and emar_state=1 and emar_fid is not null";
		try {
			fidlist = db.find(sql, EmArchiveModel.class,
					dbconn.parseSmap(EmArchiveModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return fidlist;
	}

	// 获取档案信息
	public EmArchiveModel getEmArchiveInfo(Integer gid) {
		ResultSet rs = null;
		EmArchiveModel model = new EmArchiveModel();
		dbconn db = new dbconn();
		String sql = "select * from EmArchive where gid=" + gid
				+ " and emar_state=1";
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				model.setEmar_id(rs.getInt("emar_id"));
				model.setCid(rs.getInt("cid"));
				model.setGid(rs.getInt("gid"));
				model.setEmar_company(rs.getString("emar_company"));
				model.setEmar_name(rs.getString("emar_name"));
				model.setEmar_idcard(rs.getString("emar_idcard"));
				model.setEmar_fid(rs.getString("emar_fid"));
				model.setEmar_archivearea(rs.getString("emar_archivearea"));
				model.setEmar_archivetype(rs.getString("emar_archivetype"));
				model.setEmar_colhj(rs.getInt("emar_colhj"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return model;
	}

	// 查询是否有某员工的档案信息
	public Integer getIfEmArchiveInfo(Integer gid) {
		Integer k = 0;
		ResultSet rs = null;
		dbconn db = new dbconn();
		String sql = "select count(*) num from EmArchive where gid=" + gid
				+ " and emar_state=1";
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				k = rs.getInt("num");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}

	// 查询员工的收费结束时间
	public String getstopdate(Integer eada_id) {
		String val = "";
		String sql = "select cgli_startdate,cgli_stopdate "
				+ "from coglist a  where a.gid in (select gid from EmArchiveDatum "
				+ "where eada_id="
				+ eada_id
				+ ") and cgli_id in (select max(cgli_id)id "
				+ "from coglist a inner join coofferlist b "
				+ "on a.cgli_coli_id=b.coli_id where coli_name like '%档案%' "
				+ "and isnull(cgli_stopdate,204912)>=cgli_startdate group by gid)";
		dbconn db = new dbconn();
		ResultSet rs = null;
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				val = rs.getString("cgli_stopdate");
			}
		} catch (Exception e) {

		}
		return val;
	}

	// 获取客服信息
	public List<String> getLoginInfo() {
		List<String> loginlist = new ArrayList<String>();
		ResultSet rs = null;
		String sql = "select distinct(log_name) log_name from View_loginrole order by log_name";
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

	// 根据档案号查询该档案是否已有档案调出信息
	public Integer ifExistCheckOutInfo(String fid, Integer gid) {
		Integer k = 0;
		String sql = "select * from EmArchiveDatum where eada_fid='" + fid
				+ "' and eada_type=5 and gid=" + gid;
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				k = k + 1;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}

}
