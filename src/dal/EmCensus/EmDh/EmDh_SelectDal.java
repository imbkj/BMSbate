package dal.EmCensus.EmDh;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.EmArchiveRemarkModel;
import Model.EmDhFileModel;
import Model.EmDhModel;
import Model.EmbaseModel;
import Util.UserInfo;
import Z.SB;

public class EmDh_SelectDal {
	// 根据条件查找员工信息(调户申请列表)
	public List<EmbaseModel> getEmbaseInfo(String str) {
		List<EmbaseModel> fidlist = new ArrayList<EmbaseModel>();
		dbconn db = new dbconn();
		String sql = "select top 150 a.cid,b.coba_shortname as coba_name,a.gid,a.emba_name,a.emba_sex,b.coba_client,emba_mobile,emba_email,";
		sql = sql
				+ "a.emba_wt,a.emba_state,a.emba_spell,a.emba_idcard from EmBase as a inner join CoBase as b on a.cid=b.cid";
		sql = sql
				+ " where gid not in(select gid from emdh where emdh_state not in(0,6) and gid is not null) ";
		sql = sql + str;
		sql = sql + " order by a.emba_state desc,a.emba_name asc";
		try {
			fidlist = db.find(sql, EmbaseModel.class,
					dbconn.parseSmap(EmbaseModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fidlist;
	}

	// 查询报价单是否有档案托管或者户口挂靠
	public List<String> getCogListInfo(int gid, int ownmonth, String coli_name) {
		ResultSet rs = null;
		List<String> list = new ArrayList<String>();
		try {
			dbconn db = new dbconn();
			String sql = "select coli_name from CoGList a inner join CoOfferList b on a.cgli_coli_id=b.coli_id"
					+ " where "
					+ ownmonth
					+ " between cgli_startdate and ISNULL(cgli_stopdate,204912) "
					+ " and ISNULL(cgli_stopdate,204912)>=cgli_startdate"
					+ " and coli_name='" + coli_name + "' and gid=" + gid;
			rs = db.GRS(sql);
			while (rs.next()) {
				list.add(rs.getString("coli_name"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// 根据条件查找调户信息
	public List<EmDhModel> getEmDhInfo(String str) {
		List<EmDhModel> fidlist = new ArrayList<EmDhModel>();
		dbconn db = new dbconn();
		String sql = "select a.id,a.cid,a.gid,emdh_company,emdh_name,coba_shortname,emdh_zhtype,emdh_tel,emdh_mail,emdh_dhtype,emdh_hjtaprid,"
				+ "b.coba_client as emdh_client,emdh_govefee,emdh_linktime,emdh_linktype,emdh_linkname,emdh_linkcontent,emdh_newhj,"
				+ "emdh_govetar,emdh_servefee,emdh_servetar,emdh_servetype,emdh_isbackfee,emdh_backreason,emdh_remark,emdh_fee,emdh_totalfee,"
				+ "emdh_state,case emdh_state when 1 then '待审查' when 2 then '条件审查' when 3 then '信息预审' when 4 "
				+ "then '预审通过' when 5 then '审批中' when 6 then '审批通过' when 0 then '取消办理 '  else '' end states,emdh_goveservetype,"
				+ "emdh_time1,emdh_time2,emdh_time3,emdh_time4,emdh_time5,emdh_time6,emdh_time7,emdh_time8,emdh_time9,"
				+ "emdh_time10,emdh_state1,emdh_taprid,emdh_doc,emdh_idcard,emdh_marital,emdh_education,emdh_remark,emdh_time11,"
				+ "emdh_time12,emdh_endreason,emdh_ifdn,emdh_ifhk,emdh_fistfeetype, emdh_secondfeetype,esiu_single, "
				+ "emdh_inhjtype, emdh_proxytime,  emdh_proxyname, emdh_shebaotype, emdh_feetime, emdh_feeer,"
				+ " emdh_feegeter, emdh_ifcompact, emdh_feestep,c.num from emdh a inner join cobase b on a.cid=b.cid"
				+ " left join (SELECT emdh_id,count(*) num FROM EmDHRemark group by emdh_id) c on a.id=c.emdh_id "
				+ " left join EmShebaoUpdate sb on a.gid=sb.gid "
				+ " where 1=1";
		sql = sql + str;
		/*
		 * sql = sql +
		 * " order by case emdh_state when 1 then 0 when 2 then 1 when 3 then 2 when 4 then 3 when 5 then 4 when "
		 * + "6 then 5 end desc";
		 */
		sql = sql + " order by emdh_addtime desc";
		System.out.println(sql);
		try {
			fidlist = db.find(sql, EmDhModel.class,
					dbconn.parseSmap(EmDhModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fidlist;
	}

	// 判断任务单流程的步骤
	public List<String> getStateId(String str) {
		ResultSet rs = null;
		List<String> list = new ArrayList<String>();
		try {
			dbconn db = new dbconn();
			String sql = "select distinct(wfno_step) from View_WfNode_TaskProcess"
					+ " where wfno_state=1 and tapr_id in(select emdh_taprid "
					+ " from emdh where id in("
					+ str
					+ ")) order by wfno_step desc";
			rs = db.GRS(sql);
			while (rs.next()) {
				list.add(rs.getString("wfno_step"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 获取调户备注
	public List<EmArchiveRemarkModel> getDhRemark(int emdh_id) {
		ResultSet rs = null;
		List<EmArchiveRemarkModel> list = new ArrayList<EmArchiveRemarkModel>();
		try {
			dbconn db = new dbconn();
			String sql = "select * from EmDHRemark where emdh_id=" + emdh_id;
			rs = db.GRS(sql);
			while (rs.next()) {
				EmArchiveRemarkModel model = new EmArchiveRemarkModel();
				model.setEare_id(rs.getInt("id"));
				model.setEare_addname(rs.getString("addname"));
				model.setEare_addtime(rs.getDate("addtime"));
				model.setEare_content(rs.getString("content"));
				model.setEare_tablename(rs.getString("tablename"));
				list.add(model);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public String ishaveService(Integer gid, String coli_name) {
		String name = new String();
		ResultSet rs = null;
		try {
			dbconn db = new dbconn();
			// String
			// sql="select * from CoGList a,CoOfferList b where a.cgli_coli_id=b.coli_id and gid="+gid;
			// sql=sql+" and coli_name in('户口管理','档案管理','社会保险服务','住房公积金服务')";
			String sql = "select * from CoGList a,CoOfferList b where a.cgli_coli_id=b.coli_id and gid="
					+ gid;
			sql = sql + " and coli_name='" + coli_name + "'";
			rs = db.GRS(sql);
			while (rs.next()) {
				name = rs.getString("coli_name");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name;
	}

	public List<String> getClient() {
		List<String> list = new ArrayList<String>();
		String sql = "select distinct(log_name) log_name from Login a,department b "
				+ " where a.dep_id=b.dep_id and dep_name in('客户服务部','全国项目部') and log_inure=1";
		try {
			dbconn db = new dbconn();
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				list.add("log_name");
			}
		} catch (Exception e) {

		}
		return list;
	}

	// 获取客服信息
	public List<String> getLoginInfo() {
		List<String> loginlist = new ArrayList<String>();
		ResultSet rs = null;
		String sql = "select * from View_loginrole order by log_name";
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

	// 查询员工是否已有调户信息
	public boolean ifExistDhInfo(Integer gid) {
		boolean flag = false;
		String sql = "select gid from emdh where emdh_state not in(0,6) and gid="
				+ gid;
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

	// 查询材料归档材料
	public List<EmDhFileModel> getFileing() {
		List<EmDhFileModel> list = new ArrayList<EmDhFileModel>();
		String sql = "select * from EmDhFileing";
		dbconn db = new dbconn();
		try {
			list = db.find(sql, EmDhFileModel.class,
					dbconn.parseSmap(EmDhFileModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询材料归档材料
	public List<EmDhFileModel> getDhFile(Integer emdh_id) {
		List<EmDhFileModel> list = new ArrayList<EmDhFileModel>();
		String sql = "select  case file_finame when '其他' then case ISNULL('',dhfl_file_content) "
				+ "when '' then '其他' else dhfl_file_content end else file_finame "
				+ "end filename,case when file_finame =dhfl_file_content then '' else '['+dhfl_file_content+']' end dhfl_file_content,"
				+ "* from EmDhFileing a left join EmDhFile b on a.file_id=b.dhfl_file_id where dhfl_dh_id="
				+ emdh_id;
		dbconn db = new dbconn();
		try {
			list = db.find(sql, EmDhFileModel.class,
					dbconn.parseSmap(EmDhFileModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据cid查询社保所属账户
	public boolean getShebaoType(Integer cid) {
		boolean flag = false;
		String sql = "select * from coshebao where cosb_state<>1 and cid="
				+ cid;
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
