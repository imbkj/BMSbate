package dal.Embase;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.CoOfferListModel;
import Model.EmHouseBJModel;
import Model.EmShebaoBJModel;
import Model.EmbaseModel;
import Model.LoginModel;

public class Embase_DimissionDal {
	private dbconn conn = new dbconn();

	// 获取报价单项目id
	public List<Integer> getCgliId(Integer gid) {
		String sql = "select cgli_id from CoGList where cgli_state=1 and cgli_stopdate is null and gid="
				+ gid;
		List<Integer> list = new ArrayList<Integer>();

		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				list.add(rs.getInt("cgli_id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取报价单项目id
	public List<Integer> getCgliId2(Integer gid) {
		String sql = "select cgli_id from CoGList where cgli_state=1 and gid="
				+ gid;
		List<Integer> list = new ArrayList<Integer>();

		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				list.add(rs.getInt("cgli_id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取报价单项目id(关联合同类型)
	public List<Integer> getCgliId3(Integer gid, String str) {
		String sql = "select cgli_id from CoGList a left join CoOfferList b on a.cgli_coli_id=b.coli_id where cgli_state=1 and a.gid="
				+ String.valueOf(gid) + str;
		List<Integer> list = new ArrayList<Integer>();
//System.out.println(sql);
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				list.add(rs.getInt("cgli_id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取报价单项目列表
	public List<CoOfferListModel> getCoofferlistByCglifId(List<Integer> l)
			throws SQLException {
		List<CoOfferListModel> list = new ListModelList<CoOfferListModel>();
		dbconn db = new dbconn();
		StringBuilder sql = new StringBuilder();

		sql.append("select coof_id,f.cgli_id as coli_id,coof_name,cgli_stopdate as stopdate,coli_pclass,e.city city,coli_name,coli_fee coli_fee2,coli_group_id,coli_group_count,coli_cpfc_name,convert(smalldatetime,convert(varchar(8),cgli_startdate)+'01')cgli_startdate,convert(smalldatetime,convert(varchar(8),cgli_startdate2)+'01')cgli_startdate2,convert(smalldatetime,convert(varchar(8),cgli_stopdate)+'01')cgli_stopdate");
		sql.append(" from CoGList f  left join CoOfferList b on f.cgli_coli_id=b.coli_id left join CoOffer a on a.coof_id=b.coli_coof_id");
		sql.append(" left join CoPclass c on b.coli_pclass=c.Copc_name ");
		sql.append(" left join CoProduct d on b.coli_copr_id=d.Copr_id");
		sql.append(" left join CoAgencyBaseCityRel_view e on d.copr_cabc_id=e.cabc_id");
		sql.append(" where (b.coli_copr_id>0 or b.coli_isfwf>0)");
		if (l.size() > 0) {
			sql.append(" and f.cgli_id in (");
			for (Integer i = 0; i < l.size(); i++) {
				sql.append("?");
				if ((i + 1) < l.size()) {
					sql.append(",");
				}
			}
			sql.append(")");
		} else {
			sql.append(" and 1=2");
		}
		sql.append(" order by coli_group_id,coli_group_count desc,Copc_id,coli_copr_id, coli_name,coof_name");

		list = db.find(sql.toString(), CoOfferListModel.class,
				dbconn.parseSmap(CoOfferListModel.class), l);

		return list;
	}

	// 员工离职
	public int emBaseDimission(EmbaseModel m) {
		try {
			CallableStatement c = conn
					.getcall("EmBaseDimission_P_lsb(?,?,?,?)");
			c.setInt(1, m.getGid());
			c.setString(2, m.getEmba_outdate());
			c.setString(3, m.getEmba_outreason());
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}

	// 更新报价单终止时间
	public int stopCoGlist(Integer gid, String cgli_id, String stopdate,
			String username) {
		try {
			CallableStatement c = conn.getcall("CoGlistStop_P_lsb(?,?,?,?)");
			c.setString(1, cgli_id);
			c.setString(2, stopdate);
			c.setString(3, username);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}

	// 获取员工档案的存档机构
	public String getArchiveType(Integer gid) {
		String type = "";
		String sql = "select top 1 emar_archivetype from emarchive where emar_state=1 and gid="
				+ gid + " order by emar_archivetype desc";

		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				type = rs.getString("emar_archivetype");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return type;
	}

	// 获取员工档案存放机构
	public String getArchivePlace(Integer gid, Integer cid) {
		String place = "";
		String sql = "select emar_archiveplace from emarchive a left join (select eada_id id,cid,gid from EmArchiveDatum where eada_type=5 and eada_final in (0,1,2,4) and eada_state=1) b on a.cid=b.cid and a.gid=b.gid where a.emar_state=1 and a.gid="
				+ gid + " AND a.cid=" + cid;

		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				place = rs.getString("emar_archiveplace");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return place;
	}

	// 获取对应客服信息
	public List<LoginModel> getClientInfo(Integer gid) {
		String sql = "select case c.log_sex when 'F' THEN '小姐' when '女' then '小姐' else '先生' end sex,isnull(coba_client,coba_assistant)client,c.log_tel,c.log_email from EmBase as a inner join CoBase as b on a.cid=b.cid left join login c on b.coba_client=c.log_name where gid="
				+ gid;

		List<LoginModel> list = new ArrayList<LoginModel>();

		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				LoginModel m = new LoginModel();
				m.setLog_name(rs.getString("client"));
				m.setLog_sex(rs.getString("sex"));
				m.setLog_tel(rs.getString("log_tel"));
				m.setLog_email(rs.getString("log_email"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取社保补缴所属月份
	public List<EmHouseBJModel> getGjjBjOwnmonthList(String str) {
		List<EmHouseBJModel> list = new ArrayList<EmHouseBJModel>();

		String sql = "select distinct ownmonth from emhousebj bj where 1=1"
				+ str + " order by bj.ownmonth";

		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				EmHouseBJModel m = new EmHouseBJModel();
				m.setOwnmonth(rs.getInt("ownmonth"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取住房公积金补缴所属月份
	public List<EmShebaoBJModel> getSBBjOwnmonthList(String str) {
		List<EmShebaoBJModel> list = new ArrayList<EmShebaoBJModel>();
		String sql = "select distinct ownmonth from emshebaobj bj where 1=1"
				+ str + " order by bj.ownmonth";
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				EmShebaoBJModel m = new EmShebaoBJModel();
				m.setOwnmonth(rs.getInt("ownmonth"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取住房公积金补缴所属月份
		public boolean chkGJJChange(Integer gid) {
			boolean chk=false;
			String sql = "select count(*)cou from emhousechange where gid="
					+ gid + " and emhc_ifdeclare=4";
			try {
				ResultSet rs = conn.GRS(sql);
				while (rs.next()) {
					if (rs.getInt("cou")>0) {
						chk=true;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return chk;
			
		}
}
