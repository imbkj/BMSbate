package dal.Archives;

import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.EmArchiveModel;

public class EmArchiveDal {

	// 添加新档案
	public Integer addfile(Object... objs) throws SQLException {
		Integer i = 0;
		dbconn db = new dbconn();
		i = Integer
				.valueOf(db
						.callWithReturn(
								"{?=call EmArchive_Add_P_py(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
								Types.INTEGER, objs).toString());

		return i;
	}

	// 查询档案信息列表
	public List<EmArchiveModel> getInfoList(EmArchiveModel em, String columns,
			Boolean disinct, Integer topNum) {
		List<EmArchiveModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select ";
		if (disinct != null && disinct) {
			sql = sql + "distinct ";
		}
		if (topNum != null && !topNum.equals("")) {
			sql = sql + "top " + topNum + " ";
		}
		if (columns != null && !columns.equals("")) {
			sql = sql + columns + " ";
		} else {
			sql = sql
					+ "emar_id,a.cid,a.gid,emar_company,emar_name,emar_sex,emar_idcard,"
					+ "emar_link,emar_xs,emar_fid,isnull(emar_archivearea,'')+emar_fid emar_fid2,emar_archivetype,emar_archivearea,emar_surrogateid,"
					+ "emar_surrogatecardid,emar_transferorderid,emar_archivesource,emar_archiveplace,"
					+ "emar_promisesdate emar_promisesdate,emar_prodate,"
					+ "emar_firstdeadline,emar_continuedeadline,"
					+ "isnull(emar_continuedeadline,emar_firstdeadline)cddate,"
					+ "emar_client,emar_grouptype,emar_censusregister,"
					+ "emar_crbelongs,emar_specialty,emar_degree,emar_caste,"
					+ "emar_casteassessdate,"
					+ "emar_party,emar_partydate,"
					+ "emar_partybelongs,emar_marrystate,emar_fertilitystate,"
					+ "emar_school,convert(varchar(10),emar_gradate,120)emar_gradate,"
					+ "emar_workdate,emar_szresume,emar_colhj,"
					+ "emar_wtmode,emar_inciicdate,emar_archivefolddate,"
					+ "emar_archivefoldreason,emar_archivefoldmode,emar_archiveclass,"
					+ "emar_peoplefoldmode,emar_archiveremovedate,"
					+ "emar_archiveremovereason,emar_archiveremovermode,emar_archiveremoveplace,"
					+ "emar_rsetup,emar_rdate,emar_address,"
					+ "emar_rspaykind,emar_hjpaykind,emar_rsinvoice,emar_hjinvoice,emar_remark,"
					+ "emar_addtime,emar_addname,"
					+ "emar_modtime,emar_modname,"
					+ "emar_SpecialData,emar_state,eare_id,emba_indate "
					+ "from emarchive a "
					+ "left join embase b on a.gid=b.gid "
					+ "left join EmArchiveRemark c on a.emar_id=c.eare_trid and eare_tid=2 "
					+ "where 1=1";
		}

		if (em.getCid() != null) {
			if (!em.getCid().equals("")) {
				sql = sql + " and cid=" + em.getCid();
			}
		}
		if (em.getEmar_colhj() != null) {
			if (!em.getEmar_colhj().equals("")) {
				sql = sql + " and emar_colhj=" + em.getEmar_colhj();
			}
		}
		if (em.getEmar_archivetype() != null) {
			if (!em.getEmar_archivetype().equals("")) {
				sql = sql + " and emar_archivetype='"
						+ em.getEmar_archivetype() + "'";
			}
		}

		if (em.getEmar_name() != null) {
			if (!em.getEmar_name().equals("")) {
				sql = sql + " and emar_name like '%" + em.getEmar_name() + "%'";
			}
		}

		if (em.getEmar_state() != null) {
			if (!em.getEmar_state().equals("")) {
				sql = sql + " and emar_state=" + em.getEmar_state();
			}
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (em.getCd1() != null && em.getCd2() != null) {
			sql = sql
					+ " and isnull(emar_continuedeadline,emar_firstdeadline) between '"
					+ sdf.format(em.getCd1()) + "' and '"
					+ sdf.format(em.getCd2()) + "'";
		} else if (em.getCd1() != null) {
			sql = sql
					+ " and isnull(emar_continuedeadline,emar_firstdeadline)>'"
					+ sdf.format(em.getCd1()) + "'";
		} else if (em.getCd2() != null) {
			sql = sql
					+ " and isnull(emar_continuedeadline,emar_firstdeadline)<'"
					+ sdf.format(em.getCd2()) + "'";
		}

		sql = sql
				+ " order by emar_archiveType,emar_state,emar_xs,emar_company,emar_name";
		try {
			list = db.find(sql, EmArchiveModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
