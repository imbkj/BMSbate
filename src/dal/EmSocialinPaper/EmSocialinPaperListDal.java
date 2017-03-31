package dal.EmSocialinPaper;

import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.EmSocialinPaperModel;
import Model.EmbaseModel;

public class EmSocialinPaperListDal {

	public List<EmbaseModel> getEmbaseList(Object... objs) {
		List<EmbaseModel> list = new ArrayList<EmbaseModel>();
		dbconn db = new dbconn();
		String sql = "select emba_id,coba_shortname coba_company,emba_name,emba_idcard,"
				+ " gid,a.cid,emba_computerid"
				+ " from EmBase a inner join CoBase b on a.cid=b.CID"
				+ " where emba_state=1 and gid not in(select gid from EmSocialinPaper "
				+ " where espa_state between 2 and 8) order by cid";

		try {
			list = db.find(sql, EmbaseModel.class,
					dbconn.parseSmap(EmbaseModel.class), objs);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public EmbaseModel getEmbaseInfo(Object... objs) {
		List<EmbaseModel> list = new ArrayList<EmbaseModel>();
		dbconn db = new dbconn();
		String sql = "select coba_shortname coba_company,emba_name,emba_idcard,"
				+ " gid,a.cid"
				+ " from EmBase a inner join CoBase b on a.cid=b.CID"
				+ " where emba_state=1 and emba_id=? order by cid";

		try {
			list = db.find(sql, EmbaseModel.class,
					dbconn.parseSmap(EmbaseModel.class), objs);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list.get(0);
	}

	public boolean isExists(String str, Object... objs) {
		List<EmSocialinPaperModel> list = new ArrayList<EmSocialinPaperModel>();
		dbconn db = new dbconn();
		String sql = "select espa_id from EmSocialinPaper where gid=?" + str;

		try {
			list = db.find(sql, EmSocialinPaperModel.class,
					dbconn.parseSmap(EmSocialinPaperModel.class), objs);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list.size() > 0 ? true : false;
	}

	public List<EmSocialinPaperModel> getEmSocialinPaperState(String str) {
		List<EmSocialinPaperModel> list = new ArrayList<EmSocialinPaperModel>();
		dbconn db = new dbconn();
		String sql = "select distinct stateid,statename from EmSocialinPaperState where state=1"
				+ str + " order by stateid";

		try {
			list = db.find(sql, EmSocialinPaperModel.class,
					dbconn.parseSmap(EmSocialinPaperModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<EmSocialinPaperModel> getqdEmSocailinPaperList() {
		List<EmSocialinPaperModel> list = new ArrayList<EmSocialinPaperModel>();
		dbconn db = new dbconn();
		String sql = "select espa_id,a.cid,a.gid,name,coba_shortname,ownmonth,espa_type,"
				+ " espa_idcard,emba_computerid,espa_state,"
				+ " case when espa_isback=1 and espa_isrevoke=0 then '退回' "
				+ " when espa_isback=0 and espa_isrevoke=1 then '已撤销' else statename end as statename,"
				+ " espa_feetype,espa_addname,convert(nvarchar(10),espa_addtime,120) espa_addtime,"
				+ " convert(nvarchar(10),espa_finaltime,120) espa_finaltime,"
				+ " espa_modname,convert(nvarchar(10),espa_modtime,120) espa_modtime,espa_tapr_id,"
				+ " convert(nvarchar(10),espa_filetime,120) espa_filetime,espa_isback,espa_isrevoke"
				+ " from EmSocialinPaper a inner join EmSocialinPaperState b on a.espa_state=b.Stateid"
				+ " inner join EmBase c on a.gid=c.gid inner join CoBase d on a.cid=d.CID"
				+ " where b.Typename='前道状态' and b.state=1 order by espa_id desc";
		
		System.out.println(sql);
		try {
			list = db.find(sql, EmSocialinPaperModel.class,
					dbconn.parseSmap(EmSocialinPaperModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmSocialinPaperModel> gethdEmSocailinPaperList() {
		List<EmSocialinPaperModel> list = new ArrayList<EmSocialinPaperModel>();
		dbconn db = new dbconn();
		String sql = "select espa_id,a.cid,a.gid,name,coba_shortname,ownmonth,espa_type,"
				+ " espa_idcard,emba_computerid,espa_state,"
				+ " case when espa_isback=1 and espa_isrevoke=0 then '退回' "
				+ " when espa_isback=0 and espa_isrevoke=1 then '已撤销' else statename end as statename,"
				+ " espa_feetype,espa_addname,convert(nvarchar(10),espa_addtime,120) espa_addtime,"
				+ " convert(nvarchar(10),espa_finaltime,120) espa_finaltime,"
				+ " espa_modname,convert(nvarchar(10),espa_modtime,120) espa_modtime,espa_tapr_id,"
				+ " convert(nvarchar(10),espa_filetime,120) espa_filetime,espa_isback,espa_isrevoke"
				+ " from EmSocialinPaper a inner join EmSocialinPaperState b on a.espa_state=b.Stateid"
				+ " inner join EmBase c on a.gid=c.gid inner join CoBase d on a.cid=d.CID"
				+ " where b.Typename='后道状态' and b.state=1 order by espa_id desc";
		//System.out.println(sql);
		try {
			list = db.find(sql, EmSocialinPaperModel.class,
					dbconn.parseSmap(EmSocialinPaperModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmSocialinPaperModel> getEmSocailinPaperList() {
		List<EmSocialinPaperModel> list = new ArrayList<EmSocialinPaperModel>();
		dbconn db = new dbconn();
		String sql = "select espa_id,a.cid,a.gid,name,coba_shortname,ownmonth,espa_type,"
				+ " espa_idcard,emba_computerid,espa_state,"
				+ " case when espa_isback=1 and espa_isrevoke=0 then '退回' "
				+ " when espa_isback=0 and espa_isrevoke=1 then '已撤销' else statename end as statename,"
				+ " espa_feetype,espa_addname,convert(nvarchar(10),espa_addtime,120) espa_addtime,"
				+ " convert(nvarchar(10),espa_finaltime,120) espa_finaltime,"
				+ " espa_modname,convert(nvarchar(10),espa_modtime,120) espa_modtime,espa_tapr_id,"
				+ " convert(nvarchar(10),espa_filetime,120) espa_filetime,espa_isback,espa_isrevoke"
				+ " from EmSocialinPaper a inner join EmSocialinPaperState b on a.espa_state=b.Stateid"
				+ " inner join EmBase c on a.gid=c.gid inner join CoBase d on a.cid=d.CID"
				+ " where b.Type=1 and b.state=1 order by espa_id desc";

		try {
			list = db.find(sql, EmSocialinPaperModel.class,
					dbconn.parseSmap(EmSocialinPaperModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmSocialinPaperModel> getEmSocailinPaperInfo(Object... objs) {
		List<EmSocialinPaperModel> list = new ArrayList<EmSocialinPaperModel>();
		dbconn db = new dbconn();
		String sql = "select a.cid,a.gid,name,coba_shortname,ownmonth,espa_type,"
				+ " espa_idcard,espa_state,statename,espa_feetype,espa_feestate,"
				+ " convert(nvarchar(10),espa_filetime,120) espa_filetime"
				+ " from EmSocialinPaper a inner join EmSocialinPaperState b on a.espa_state=b.Stateid"
				+ " inner join EmBase c on a.gid=c.gid inner join CoBase d on a.cid=d.CID"
				+ " where b.type=1 and b.state=1 and espa_id=?";

		try {
			list = db.find(sql, EmSocialinPaperModel.class,
					dbconn.parseSmap(EmSocialinPaperModel.class), objs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmSocialinPaperModel> getStateRelList(Object... objs) {
		List<EmSocialinPaperModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select epsr_addname,convert(nvarchar(16),epsr_addtime,120) epsr_addtime,"
				+ " convert(nvarchar(16),epsr_statetime,120) epsr_statetime,statename"
				+ " from EmSocialinPaperStateRel a inner join EmSocialinPaperState b"
				+ " on a.epsr_stateid=b.Stateid"
				+ " where epsr_espa_id=? and type=1 and state=1"
				+ " order by stateid";

		try {
			list = db.find(sql, EmSocialinPaperModel.class,
					dbconn.parseSmap(EmSocialinPaperModel.class), objs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
