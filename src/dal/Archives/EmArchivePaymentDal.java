package dal.Archives;

import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.EmArchivePaymentModel;
import Util.UserInfo;

public class EmArchivePaymentDal {

	// 查询档案续费信息
	public List<EmArchivePaymentModel> getBaseList(EmArchivePaymentModel em) {
		List<EmArchivePaymentModel> list = new ListModelList<EmArchivePaymentModel>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		dbconn db = new dbconn();
		String sql = "select symr_readstate,emap_id,a.cid,a.gid,emap_idcard,emap_fileplace,emap_sdate,emap_cdate,emap_client,emap_company,ownmonth,emap_name,emap_file,emap_hj,emap_op,emap_lname,emap_itime,emap_state,emap_lstate"
				+ ",convert(varchar(10),emap_sdate,120)+' ~ '+convert(varchar(10),emap_cdate,120) cddate"
				+ ",case emap_lstate when 0 then '已撤回' when 1 then '未确认' when 2 then '待确认' when 3 then '可借款' when 4 then '待借款' when 5 then '已借款' when 6 then '已清帐' end lstatename,"
				+ "emap_fpay,emap_hpay,emap_finvoice,emap_hinvoice,emba_outdate outtime,emap_addtime,emap_addname,emap_idlist"
				+ " from emarchivepayment a "
				+ " left join embase b on a.gid=b.gid " +
				" left join (select smwr_tid, case when syme_log_id="+UserInfo.getUserid() +
				" then 2 when symr_log_id="+UserInfo.getUserid()+" then symr_readstate end " +
				" symr_readstate from View_Message where EXISTS(select syme_id " +
				"from (select smwr_tid,MAX(syme_id)syme_id from View_Message " +
				"where syme_state=1 and (symr_log_id= "+UserInfo.getUserid()+" " +
				"or syme_log_id= "+UserInfo.getUserid()+") and  smwr_table='emarchivepayment' " +
				"group by smwr_tid)msg where View_Message.syme_id=msg.syme_id)) msg on a.emap_id=msg.smwr_tid " +
				" where emap_state=1";
		if (em.getEmap_company() != null) {
			if (!em.getEmap_company().equals("")) {
				sql = sql + " and (emap_company like '%" + em.getEmap_company()
						+ "%' or a.cid like '" + em.getEmap_company()
						+ "%' or emap_comspell like '" + em.getEmap_company()
						+ "%')";
			}
		}

		if (em.getOwnmonth() != null) {
			if (!em.getOwnmonth().equals("")) {
				sql = sql + " and ownmonth=" + em.getOwnmonth();
			}
		}

		if (em.getEmap_lname() != null) {
			if (!em.getEmap_lname().equals("")) {
				sql = sql + " and emap_lname='" + em.getEmap_lname() + "'";
			}
		}

		if (em.getEmap_name() != null) {
			if (!em.getEmap_name().equals("")) {
				sql = sql + " and (emap_name like '%" + em.getEmap_name()
						+ "%' or a.gid like '" + em.getEmap_name()
						+ "%' or emap_spell like '" + em.getEmap_name() + "%')";
			}
		}

		if (em.getEmap_fileplace() != null) {
			if (!em.getEmap_fileplace().equals("")) {
				sql = sql + " and emap_fileplace='" + em.getEmap_fileplace()
						+ "'";
			}
		}

		if (em.getEmap_idlist() != null) {
			if (!em.getEmap_idlist().equals("")) {
				sql = sql + " and emap_idlist='" + em.getEmap_idlist() + "'";
			}
		}

		if (em.getCd1() != null && em.getCd2() != null) {
			sql = sql + " and emap_sdate between '" + sdf.format(em.getCd1())
					+ "' and '" + sdf.format(em.getCd2()) + "'";
		}

		if (em.getEmap_ltime() != null) {
			if (!em.getEmap_ltime().equals("")) {
				sql = sql + " and emap_ltime = '" + em.getEmap_ltime() + "'";
			}

		}

		if (em.getEmap_itime() != null) {
			if (!em.getEmap_itime().equals("")) {
				sql = sql + " and emap_itime = '" + em.getEmap_itime() + "'";
			}

		}

		if (em.getEmap_lstate() != null) {
			if (!em.getEmap_lstate().equals("")) {
				sql = sql + " and emap_lstate=" + em.getEmap_lstate();
			}
		}
		if (em.getGid() != null) {
			sql = sql + " and a.gid=" + em.getGid();
		}
		System.out.println(sql);
		try {
			list = db.find(sql, EmArchivePaymentModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询档案续费所属月份列表
	public List<EmArchivePaymentModel> getOwnmonth() throws SQLException {
		List<EmArchivePaymentModel> list = new ListModelList<EmArchivePaymentModel>();
		dbconn db = new dbconn();

		String sql = "select distinct ownmonth from emarchivepayment where emap_state=1 order by ownmonth desc";
		list = db.find(sql, EmArchivePaymentModel.class,
				dbconn.parseSmap(EmArchivePaymentModel.class));
		return list;
	}

	// 查询档案续费借款人列表
	public List<EmArchivePaymentModel> getLoanMan() throws SQLException {
		List<EmArchivePaymentModel> list = new ListModelList<EmArchivePaymentModel>();
		dbconn db = new dbconn();

		String sql = "select distinct emap_lname from emarchivepayment where emap_state=1 order by emap_lname";
		list = db.find(sql, EmArchivePaymentModel.class,
				dbconn.parseSmap(EmArchivePaymentModel.class));

		return list;
	}

	// 查询档案信息列表(续费)
	public List<EmArchivePaymentModel> getEmarchiveList(
			EmArchivePaymentModel em, String columns, Boolean disinct,
			Integer topNum) {
		List<EmArchivePaymentModel> list = new ListModelList<>();
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
					+ "emar_id emap_emar_id,a.cid,a.gid,emar_company emap_company,emar_name emap_name,emar_idcard emap_idcard,"
					+ "emar_xs emap_xs,emar_fid emap_fid,emar_archivetype emap_archivetype,emar_archivearea emap_area,"
					+ "isnull(emar_archivearea,'')+emar_fid emap_fid2,emar_archiveplace emap_fileplace,"
					+ "isnull(emar_continuedeadline,emar_firstdeadline)emap_sdate,"
					+ "emar_client emap_client,emar_rspaykind emap_fpay,emar_hjpaykind emap_hpay,"
					+ "emar_rsinvoice emap_finvoice,emar_hjinvoice emap_hinvoice,"
					+ "emar_remark emap_remark,emar_colhj emap_colhj,emba_indate intime,eare_trid,0 emap_file,0 emap_hj,0 emap_op,"
					+ "convert(int,(convert(varchar(10),year(GETDATE()))+REPLICATE('0',2-len(month(getdate())))+convert(varchar(10),MONTH(GETDATE())))) ownmonth "
					+ "from emarchive a "
					+ "left join embase b on a.gid=b.gid "
					+ "left join (select distinct eare_trid from EmArchiveRemark where eare_tid=2) c on a.emar_id=c.eare_trid  "
					+ "where not exists(select 1 from EmArchivePayment where gid=a.gid and emap_state=1 and emap_lstate between 1 and 5)";
		}

		if (em.getCid() != null) {
			if (!em.getCid().equals("")) {
				sql = sql + " and a.cid=" + em.getCid();
			}
		}

		if (em.getEmap_archivetype() != null) {
			if (!em.getEmap_archivetype().equals("")) {
				sql = sql + " and emar_archivetype='"
						+ em.getEmap_archivetype() + "'";
			}
		}
		if (em.getEmap_colhj() != null) {
			if (!em.getEmap_colhj().equals("")) {
				sql = sql + " and emar_colhj=" + em.getEmap_colhj();
			}
		}
		if (em.getEmap_fileplace() != null) {
			if (!em.getEmap_fileplace().equals("")) {
				sql = sql + " and emar_archiveplace='" + em.getEmap_fileplace()
						+ "'";
			}
		}

		if (em.getEmap_name() != null) {
			if (!em.getEmap_name().equals("")) {
				sql = sql + " and emar_name like '%" + em.getEmap_name() + "%'";
			}
		}
		if (em.getEmap_state() != null) {
			if (!em.getEmap_state().equals("")) {
				sql = sql + " and a.emar_state=" + em.getEmap_state();
			}
		}
		if (em.getEmap_client() != null) {
			if (!em.getEmap_client().equals("")) {
				sql = sql + " and a.emar_client='" + em.getEmap_client() + "'";
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
					+ " and isnull(emar_continuedeadline,emar_firstdeadline)>='"
					+ sdf.format(em.getCd1()) + "'";
		} else if (em.getCd2() != null) {
			sql = sql
					+ " and isnull(emar_continuedeadline,emar_firstdeadline)<='"
					+ sdf.format(em.getCd2()) + "'";
		}

		sql = sql
				+ " order by emar_archiveType,emar_state,emar_xs,emar_company,emar_name";

		System.out.println(sql);
		try {
			list = db.find(sql, EmArchivePaymentModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 添加续费信息
	public Integer add(EmArchivePaymentModel em) {
		Integer i = 0;
		dbconn db = new dbconn();
		try {
			i = (Integer) db.callWithReturn(
					"{?=call EmArchivePaymentAdd_P_py(?,?,?,?,?,?,?,?,?,?,"
							+ "?,?,?,?,?,?,?,?,?,?," + "?,?,?,?)}",
					Types.INTEGER, em.getOwnmonth(), em.getEmap_emar_id(),
					em.getCid(), em.getGid(), em.getEmap_company(),
					em.getEmap_name(), em.getEmap_archivetype(),
					em.getEmap_area(), em.getEmap_fid(), em.getEmap_xs(),
					em.getEmap_idcard(), em.getEmap_fileplace(),
					em.getEmap_sdate(), em.getEmap_cdate(), em.getEmap_file(),
					em.getEmap_hj(), em.getEmap_op(), em.getEmap_lname(),
					em.getEmap_client(), em.getEmap_addname(),
					em.getEmap_fpay(), em.getEmap_hpay(),
					em.getEmap_finvoice(), em.getEmap_hinvoice());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return i;
	}

	// 修改续费信息
	public Integer mod(EmArchivePaymentModel em) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "update EmArchivePayment set emap_modname='"
				+ UserInfo.getUsername() + "',emap_modtime=getdate()";
		if (em.getEmap_file() != null) {
			if (!em.getEmap_file().equals("")) {
				sql = sql + ",emap_file=" + em.getEmap_file();
			}
		}
		if (em.getEmap_hj() != null) {
			if (!em.getEmap_hj().equals("")) {
				sql = sql + ",emap_hj=" + em.getEmap_hj();
			}
		}
		if (em.getEmap_op() != null) {
			if (!em.getEmap_op().equals("")) {
				sql = sql + ",emap_op=" + em.getEmap_op();
			}
		}
		if (em.getEmap_idlist()!=null) {
			if (!em.getEmap_idlist().equals("")) {
				sql = sql + ",emap_idlist='" + em.getEmap_idlist()+"'";
			}
		}
		if (em.getEmap_idname()!=null) {
			if (!em.getEmap_idname().equals("")) {
				sql = sql + ",emap_idname='" + em.getEmap_idname()+"'";
			}
		}
		if (em.getEmap_idtime()!=null) {
			if (!em.getEmap_idtime().equals("")) {
				sql = sql + ",emap_idtime='" + em.getEmap_idtime()+"'";
			}
		}
		if (em.getEmap_lstate()!=null) {
			if (!em.getEmap_lstate().equals("")) {
				sql = sql + ",emap_lstate=" + em.getEmap_lstate();
			}
		}

		sql = sql + " where emap_id=" + em.getEmap_id();

		try {
			i = db.updatePrepareSQL(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return i;
	}

	// 删除续费信息
	public Integer del(Integer id) {
		dbconn db = new dbconn();
		String sql = "delete from EmArchivePayment where emap_id=?";
		Integer i = 0;
		try {
			i = db.updatePrepareSQL(sql, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;

	}

}
