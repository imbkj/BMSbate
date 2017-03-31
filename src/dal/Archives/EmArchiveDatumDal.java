package dal.Archives;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.EmArchiveDatumModel;
import Model.EmArchiveModel;
import Model.EmArchiveRemarkModel;
import Util.UserInfo;

public class EmArchiveDatumDal {
	// 添加档案调入申请
	public Integer addfile(Object... obj) throws SQLException {
		Integer i = 0;
		dbconn db = new dbconn();
		i = Integer
				.valueOf(db
						.callWithReturn(
								"{?=call EmArchive_ServerAdd_P_py(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
								Types.INTEGER, obj).toString());

		return i;
	}

	// 查询档案业务基本信息
	public List<EmArchiveDatumModel> getInfoById(Integer id)
			throws SQLException {
		List<EmArchiveDatumModel> list = new ListModelList<EmArchiveDatumModel>();
		dbconn db = new dbconn();
		String sql = "select eada_id,a.cid,a.gid,ownmonth,eada_name,eada_filetype,eada_filearea,"
				+ "eada_fid,eada_idcard,convert(varchar(50),eada_type)eada_type,eada_fileplace,"
				+ "eada_rspaykind,eada_rsinvoice,eada_hjpaykind,eada_hjinvoice,"
				+ "isnull(eada_colhj,emar_colhj)eada_colhj,eada_hj,convert(varchar(10),eada_deadline,120)eada_deadline,"
				+ "eada_wtmode,eada_sdh,eada_sdhdate,eada_charge,eada_orderdata,"
				+ "eada_archivename,eada_lendcause,eada_lenddate,eada_lendpeople,eada_returnarchivedate,"
				+ "eada_prove,eada_cause,eada_drawprovedate,eada_drawprovepeople,eada_file,eada_filedate,"
				+ "eada_zg,eada_bf,eada_dms,eada_rsetup,eada_rdate,eada_drawformdate,eada_drawformpeople,"
				+ "eada_savefiledate savefiledate,eada_remark,eada_backcase,eada_doc,"
				+ "coba_company,coba_client,emar_idcard,emba_indate,emar_prodate,isnull(emar_continuedeadline,emar_firstdeadline)emar_continuedeadline"
				+ ",emba_indate indate,emba_id,emba_mobile,emba_email"
				+ ",case eada_colhj when 1 then '是' else '否' end eada_colhjName,eada_tapr_id,emba_sex,emba_hjadd,emba_party,emba_degree,emba_school,emba_specialty,emba_marital,emba_graduation,coba_client,emba_mobile,emba_native"
				+ " from EmArchiveDatum a"
				+ " inner join cobase b on a.cid=b.cid"
				+ " inner join embase c on a.gid=c.gid"
				+ " left join emarchive d on a.gid=d.gid and a.eada_filetype=d.emar_archivetype and a.eada_filearea=d.emar_archivearea and eada_fid=d.emar_fid"
				+ " where eada_id =?";
		list = db.find(sql.toString(), EmArchiveDatumModel.class,
				dbconn.parseSmap(EmArchiveDatumModel.class), id);
		return list;
	}

	// 通过员工ID获取档案业务调入待信息
	public List<EmArchiveDatumModel> getInfo(Integer id) {
		List<EmArchiveDatumModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = " select eada_id,a.cid,a.gid,ownmonth,eada_name,eada_filetype,eada_filearea,"
				+ "eada_fid,eada_idcard,convert(varchar(50),eada_type)eada_type,eada_fileplace,"
				+ "eada_rspaykind,eada_rsinvoice,eada_hjpaykind,eada_hjinvoice,"
				+ "isnull(eada_colhj,emar_colhj)eada_colhj,eada_hj,convert(varchar(10),eada_deadline,120)eada_deadline,"
				+ "eada_wtmode,eada_sdh,eada_sdhdate,eada_charge,eada_orderdata,"
				+ "eada_archivename,eada_lendcause,eada_lenddate,eada_lendpeople,eada_returnarchivedate,"
				+ "eada_prove,eada_cause,eada_drawprovedate,eada_drawprovepeople,eada_file,eada_filedate,"
				+ "eada_zg,eada_bf,eada_dms,eada_rsetup,eada_rdate,eada_drawformdate,eada_drawformpeople,"
				+ "eada_savefiledate savefiledate,eada_remark,eada_backcase,"
				+ "coba_company,coba_client,emar_idcard,emba_indate,emar_prodate,isnull(emar_continuedeadline,emar_firstdeadline)emar_continuedeadline"
				+ ",emba_indate indate,emba_id,emba_mobile,emba_email"
				+ ",case eada_colhj when 1 then '是' else '否' end eada_colhjName,eada_tapr_id,emba_sex,emba_hjadd,emba_party,emba_degree,emba_school,emba_specialty,emba_marital,emba_graduation,coba_client,emba_mobile,emba_native"
				+ " from EmArchiveDatum a"
				+ " inner join cobase b on a.cid=b.cid"
				+ " inner join embase c on a.gid=c.gid"
				+ " left join emarchive d on a.gid=d.gid and a.eada_filetype=d.emar_archivetype and a.eada_filearea=d.emar_archivearea and eada_fid=d.emar_fid"
				+ " where c.emba_id="
				+ id
				+ " and eada_type=0 and eada_final in(0)";
		try {
			list = db.find(sql, EmArchiveDatumModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

	// 查询业务表是否有未办理完成的业务
	public Integer checkData(Integer gid, Integer type) throws SQLException {
		Integer i = 0;
		List<EmArchiveDatumModel> list = new ListModelList<EmArchiveDatumModel>();
		dbconn db = new dbconn();
		String sql = "select eada_id from EmArchiveDatum where gid=? and eada_type=? and eada_final<3";
		list = db.find(sql, EmArchiveDatumModel.class,
				dbconn.parseSmap(EmArchiveDatumModel.class), gid, type);
		i = list.size();
		return i;
	}

	// 确认业务提交到后道
	public Integer ConfirmData(EmArchiveDatumModel em) throws SQLException {
		Integer i = 0;
		dbconn db = new dbconn();
		StringBuilder sql = new StringBuilder();
		sql.append("update EmArchiveDatum set");
		sql.append(" eada_state=1,");
		sql.append(" eada_final=1,");
		sql.append(" eada_idcard=?,");
		sql.append(" eada_fileplace=?,");
		sql.append(" eada_charge=?,");
		sql.append(" eada_savefiledate=?,");
		sql.append(" eada_rspaykind=?,");
		sql.append(" eada_rsinvoice=?,");
		sql.append(" eada_hjpaykind=?,");
		sql.append(" eada_hjinvoice=?");
		sql.append(" where eada_id=?");

		i = db.updatePrepareSQL(sql.toString(), em.getEada_idcard(),
				em.getEada_fileplace(), em.getEada_charge(),
				em.getEada_savefiledate(), em.getEada_rspaykind(),
				em.getEada_rsinvoice(), em.getEada_hjpaykind(),
				em.getEada_hjinvoice(), em.getEada_id());
		if (i > 0) {
			i = em.getEada_id();
			if (em.getRemark() != null && !em.getRemark().equals("")) {
				String sql2 = "insert into EmArchiveRemark(eare_tid,eare_trid,eare_content,eare_addtime,eare_addname,eare_state)"
						+ "values(1,?,?,getdate(),?,1)";
				db.insertAndReturnKey(sql2, em.getEada_id(), em.getRemark(),
						UserInfo.getUsername());
			}
		}
		return i;
	}

	// 档案类型分类
	public String[] classifyFile(Object... obj) throws SQLException {
		Integer i = 0;
		String str[] = new String[5];
		dbconn db = new dbconn();
		String sql = "";
		if (obj[0].equals("中智保管")) {
			sql = "update EmArchiveDatum set eada_filetype=? where eada_id=?";
		} else {
			sql = "update EmArchiveDatum set eada_filetype=?,eada_filearea=? where eada_id=?";
		}

		i = db.updatePrepareSQL(sql, obj);
		if (i > 0) {
			if (obj[0].equals("中智保管")) {
				str[0] = obj[1].toString();
				str[1] = obj[0].toString();
			} else {
				str[0] = obj[2].toString();
				str[1] = obj[0].toString();
			}
		}
		return str;

	}

	// 添加业务备注
	public Integer addremark(EmArchiveRemarkModel em) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "insert into emarchiveremark(eare_tid,eare_trid,eare_content,eare_addname,eare_addtime,eare_state,gid)"
				+ "values(?,?,?,?,getdate(),1,?)";
		try {
			i = db.insertAndReturnKey(sql, em.getEare_tid(), em.getEare_trid(),
					em.getEare_remark(), em.getEare_addname(),em.getGid());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	// 更新费用情况
	public Integer updateCharge(Object... obj) throws SQLException {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "update EmArchiveDatum set eada_charge=?,eada_remark=? where eada_id=?";
		i = db.updatePrepareSQL(sql, obj);
		return i;
	}

	// 更新商调函编号
	public Integer updateSDH(Object... obj) throws SQLException {
		Integer i = 0;
		Integer num = 0;
		dbconn db = new dbconn();
		String sql = "update EmArchiveDatum set eada_sdh=?,eada_sdhdate=getdate() where eada_id=?";
		i = db.updatePrepareSQL(sql, obj);
		if (i > 0) {
			num = Integer.valueOf(obj[1].toString());
		}
		return num;
	}

	// 获取节点ID
	public Integer getTaprId(Integer id) throws SQLException {
		Integer i = 0;
		List<EmArchiveDatumModel> list = new ListModelList<EmArchiveDatumModel>();
		dbconn db = new dbconn();
		String sql = "select eada_tapr_id from EmArchiveDatum where eada_id=?";
		list = db.find(sql, EmArchiveDatumModel.class,
				dbconn.parseSmap(EmArchiveDatumModel.class), id);
		i = list.get(0).getEada_tapr_id();

		return i;
	}

	// 更新状态
	public Integer updateState(Integer id, Integer state) throws SQLException {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "update EmArchiveDatum set eada_final=? where eada_id=?";
		i = db.updatePrepareSQL(sql, state, id);
		return i;

	}

	// 更新业务数据
	public Integer updateData(EmArchiveDatumModel m, Integer id) {
		Integer i = 0;
		dbconn db = new dbconn();
		if (id != null && id > 0) {
			String sql = "update EmArchiveDatum set eada_modtime=getdate(),eada_modname='"
					+ m.getEada_modname()+"'";
			if (m.getEada_idcard() != null) {
				sql += ",eada_idcard='" + m.getEada_idcard() + "'";
			}
			if (m.getEada_fileplace() != null) {
				sql += ",eada_fileplace='" + m.getEada_fileplace() + "'";
			}
			if (m.getEada_hj() != null) {
				sql += ",eada_hj='" + m.getEada_hj() + "'";
			}
			if (m.getEada_savefiledate() != null) {
				sql += ",eada_savefiledate='" + m.getEada_savefiledate() + "'";
			}
			if (m.getEada_rspaykind() != null) {
				sql += ",eada_rspaykind='" + m.getEada_rspaykind() + "'";
			}
			if (m.getEada_rsinvoice() != null) {
				sql += ",eada_rsinvoice='" + m.getEada_rsinvoice() + "'";
			}
			if (m.getEada_hjpaykind() != null) {
				sql += ",eada_hjpaykind='" + m.getEada_hjpaykind() + "'";
			}
			if (m.getEada_hjinvoice() != null) {
				sql += ",eada_hjinvoice='" + m.getEada_hjinvoice() + "'";
			}
			if (m.getEada_charge() != null) {
				sql += ",eada_charge='" + m.getEada_charge() + "'";
			}
			if (m.getEada_remark() != null) {
				sql += ",eada_remark='" + m.getEada_remark() + "'";
			}

			if (m.getEada_final() != null) {
				sql += ",eada_final='" + m.getEada_final() + "'";
			}
			if (m.getEada_filetype() != null) {
				sql += ",eada_filetype='" + m.getEada_filetype() + "'";
			}
			if (m.getEada_filearea() != null) {
				sql += ",eada_filearea='" + m.getEada_filearea() + "'";
			}
			if (m.getEada_backcase()!= null) {
				sql += ",eada_backcase='" + m.getEada_backcase() + "'";
			}
			
			sql += " where eada_id=?";

			try {
				i = db.updatePrepareSQL(sql, id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;
	}

	// 更新任务流程ID
	public Integer updateTaprId(Integer dataId, Integer taprId)
			throws SQLException {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "update EmArchiveDatum set eada_tapr_id=? where eada_id=?";
		i = db.updatePrepareSQL(sql, taprId, dataId);
		return i;
	}

	public Integer getCid(Integer gid) {
		Integer cid = 0;
		String sql = "select * from embase where gid=" + gid;
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				cid = rs.getInt("cid");
			}
		} catch (Exception e) {

		}
		return cid;
	}
	
	//使用身份证号码和档案类型来查询员工之前是否有档案
	public EmArchiveModel getEmarchive(String idcard,String archiveType)
	{
		EmArchiveModel m=new EmArchiveModel();
		String sql="select * from EmArchive " +
				" where emar_idcard='"+idcard+"' " +
				" and emar_archivetype='"+archiveType+"' order by emar_id desc";
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				m.setCid(rs.getInt("cid"));
				m.setGid(rs.getInt("gid"));
				m.setEmar_id(rs.getInt("emar_id"));
				m.setEmar_censusregister(rs.getString("emar_censusregister"));
				m.setEmar_crbelongs(rs.getInt("emar_crbelongs"));
				m.setEmar_party(rs.getString("emar_party"));
				m.setEmar_partydate(rs.getString("emar_partydate"));
				m.setEmar_partybelongs(rs.getInt("emar_partybelongs"));
				m.setEmar_degree(rs.getString("emar_degree"));
				m.setEmar_school(rs.getString("emar_school"));
				m.setEmar_specialty(rs.getString("emar_specialty"));
				m.setEmar_gradate(rs.getString("emar_gradate"));
				m.setEmar_marrystate(rs.getString("emar_marrystate"));
				m.setEmar_fertilitystate(rs.getString("emar_fertilitystate"));
				m.setEmar_workdate(rs.getString("emar_workdate"));
				m.setEmar_caste(rs.getString("emar_caste"));
				m.setEmar_peoplefoldmode(rs.getString("emar_peoplefoldmode"));
				m.setEmar_archiveclass(rs.getString("emar_archiveclass"));
				m.setEmar_archivefoldreason(rs.getString("emar_archivefoldreason"));
				m.setEmar_surrogateid(rs.getString("emar_surrogateid"));
				m.setEmar_archivesource(rs.getString("emar_archivesource"));
				m.setEmar_archivefoldmode(rs.getString("emar_archivefoldmode"));
				m.setEmar_archivefolddate(rs.getString("emar_archivefolddate"));
				m.setEmar_prodate(rs.getString("emar_prodate"));
				m.setEmar_transferorderid(rs.getString("emar_transferorderid"));
				m.setEmar_archiveplace(rs.getString("emar_archiveplace"));
				m.setEmar_continuedeadline(rs.getString("emar_continuedeadline"));
				m.setEmar_firstdeadline(rs.getString("emar_firstdeadline"));
			}
		} catch (Exception e) {

		}
		return m;
	}
}
