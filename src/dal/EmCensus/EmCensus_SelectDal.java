package dal.EmCensus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.EmCensusModel;
import Model.EmHJBorrowCardModel;
import Model.EmbaseModel;

public class EmCensus_SelectDal {

	// 根据条件查找员工信息
	public List<EmbaseModel> getEmbaseInfo(String str) {
		List<EmbaseModel> fidlist = new ArrayList<EmbaseModel>();
		dbconn db = new dbconn();
		String sql = "select a.cid,b.coba_shortname as coba_name,a.gid,a.emba_name,a.emba_sex,b.coba_client,";
		sql = sql
				+ "a.emba_wt,a.emba_state,emba_idcard from EmBase as a inner join CoBase as b on a.cid=b.cid where 1=1";
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

	// 根据条件查找户口信息
	public List<EmCensusModel> getEmCensusInfo(String str) {
		List<EmCensusModel> list = new ArrayList<EmCensusModel>();
		dbconn db = new dbconn();
		String sql = "select convert(varchar(10),emhj_in_time,120) as emhj_in_time,convert(varchar(10),emhj_outime,120) as emhj_outime,a.*,"
				+ "convert(decimal(18,1),emhj_fee) as emhj_fees,b.coba_shortname,coba_client,case emhj_state when 0 then '待确认' when 1 "
				+ "then '已确认' when 2 then '已借卡' when 8 then '待确定' "
				+ " when 3 then '未还齐' when 4 then '退回' when 5 then '在册' "
				+ "when 6 then '已迁出' when 7 then '已打印' else '' end states,emhj_place "
				+ " from EmCensus a inner join Cobase b on a.cid=b.cid where 1=1";
		sql = sql + str;
		sql = sql + " order by emhj_state";
		try {
			list = db.find(sql, EmCensusModel.class,
					dbconn.parseSmap(EmCensusModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据条件查找户口卡信息
	public List<EmHJBorrowCardModel> getEmHJBorrowCardInfo(String str) {
		List<EmHJBorrowCardModel> list = new ArrayList<EmHJBorrowCardModel>();
		dbconn db = new dbconn();
		String sql = "select convert(decimal(18,0),ehbc_backfee) as ehbc_backfee,convert(decimal(18,0),ehbc_fee) as ehbc_fee,emhj_id,emhj_no,coba_shortname,ehbc_grhk, ehbc_sy,ehbc_syfy,ehbc_backname,ehbc_id ,ehbc_tarpid,"
				+ " ehbc_handin_name,ehbc_addtime,ehbc_state,emhj_name,coba_company,emhj_idcard,convert(varchar(10),emhj_in_time,120) as emhj_in_time,emhj_in_class,"
				+ " emhj_type,convert(varchar(10),ehbc_backtime,120) as ehbc_backtime, convert(varchar(10),ehbc_outime,120) as ehbc_outime,ehbc_backname,convert(decimal(18,0),(ehbc_fee-ehbc_backfee)) as ehbc_fees,emhj_state,"
				+ " case emba_state when 1 then '在职' when 0 then '离职' else  '亲属' end as emba_state,ehbc_cashpledname,ehbc_cashpledtime,"
				+ "case ehbc_state when 1 then '已审核' when 2 then '未还卡' when 3 then '未还齐' when 4 then '已还卡' when 0 then '借卡申请' when 5 then '取消办理' end states"
				+ " from EmHJBorrowCard a "
				+ "inner join (select emhj_no,gid,emhj_id, cid,emhj_name,emhj_idcard,emhj_type,emhj_state emhj_state,emhj_in_class,convert(varchar(10),emhj_in_time,120) as emhj_in_time from EmCensus "
				+ " where emhj_state>4 ) b on a.ehbc_tableid=b.emhj_id"
				+ " left join cobase c on c.cid=b.cid"
				+ " left join embase d on d.gid=b.gid" + "  where 1=1  ";
		sql = sql + str;
		sql = sql + " order by ehbc_state,ehbc_addtime desc";
		System.out.println(sql);
		try {
			list = db.find(sql, EmHJBorrowCardModel.class,
					dbconn.parseSmap(EmHJBorrowCardModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据条件查找户口卡表信息
	public List<EmHJBorrowCardModel> getEmHJBorrowCardInfos(String str) {
		List<EmHJBorrowCardModel> list = new ArrayList<EmHJBorrowCardModel>();
		dbconn db = new dbconn();
		String sql = "select convert(char(10),ehbc_outime,120) as ehbc_outime,emhj_name,emhj_no,emhj_idcard,"
				+ "case emba_state when 0 then '离职' when 1 then '在职'when 2 then '入职中' when 3 then '入职中' "
				+ " when 4 then '取消入职' when 5 then '入职中' end emba_statename,b.cid,b.gid,coba_shortname,"
				+ "emba_name,emba_idcard, a.* from EmHJBorrowCard a "
				+ " inner join EmCensus b on a.ehbc_tableid=b.emhj_id "
				+ " left join embase c on b.gid=c.gid "
				+ " left join cobase co on c.cid=co.cid where 1=1 ";
		sql = sql + str;
		sql = sql + " order by ehbc_state,ehbc_addtime desc";
		System.out.println(sql);
		try {
			list = db.find(sql, EmHJBorrowCardModel.class,
					dbconn.parseSmap(EmHJBorrowCardModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取中智集体户最大的户口编号
	public String getMaxHjNo() {
		String hjno = "";
		dbconn db = new dbconn();
		String sql = "select MAX(emhj_no) emhj_no from EmCensus where Emhj_type='中智集体户' and emhj_no is not null and isnumeric(emhj_no)=1";
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				hjno = rs.getString("emhj_no");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hjno;
	}

	// 获取独立户户最大的户口编号
	public String getMaxHjNos(String str, Integer cid) {
		String hjno = "";
		dbconn db = new dbconn();
		String sql = " select max(Replace(emhj_no,'"
				+ str
				+ "','')) emhjno from EmCensus where Emhj_type!='中智集体户' and emhj_no is not null and cid ="
				+ cid + " and emhj_no like '%" + str
				+ "%' and emhj_no not like '%C'";
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				hjno = rs.getString("emhjno");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hjno;
	}

	// 根据gid和cid 查询某员工已有户口编号
	public String getEmHjNo(Integer cid, Integer gid) {
		String hjno = "";
		dbconn db = new dbconn();
		String sql = "select * from EmCensus where cid=" + cid + " and gid="
				+ gid + " and emhj_no is not null";
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				hjno = rs.getString("emhj_no");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hjno;
	}

	// 根据gid查询员工的户口编号
	public String getHjNoByGid(Integer gid) {
		String hjno = "";
		dbconn db = new dbconn();
		String sql = "select * from EmCensus where  gid=" + gid
				+ " and emhj_no is not null and right(emhj_no,1)!='C'";
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				hjno = rs.getString("emhj_no");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hjno;
	}

	// 查询合同id是否已经存在
	public boolean ifexist(String hjno) {
		boolean flag = false;
		dbconn db = new dbconn();
		String sql = "select * from EmCensus where  emhj_no='" + hjno + "'";
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public EmbaseModel getEmbaId(Integer gid) {
		EmbaseModel model = new EmbaseModel();
		String sql = "select * from embase where gid=" + gid;
		try {
			dbconn db = new dbconn();
			ResultSet rs = db.GRS(sql);
			while (rs.next())
				model.setEmba_id(rs.getInt("emba_id"));
		} catch (Exception e) {

		}
		return model;
	}

	public EmCensusModel getEmCensusId(Integer gid) {
		EmCensusModel model = new EmCensusModel();
		List<EmCensusModel> list = new ArrayList<EmCensusModel>();
		String sql = "select emba_name emhj_name,isnull(emhj_no,'') emhj_no,a.gid,isnull(emba_idcard,emhj_idcard) emhj_idcard from embase a left join EmCensus b on a.gid=b.gid"
				+ " and emhj_no not like '%C' where a.gid=" + gid;
		try {
			dbconn db = new dbconn();
			list = db.find(sql, EmCensusModel.class,
					dbconn.parseSmap(EmCensusModel.class));
			if (list.size() > 0) {
				model = list.get(0);
			}
		} catch (Exception e) {

		}
		return model;
	}

	public boolean ifSyIsOut(Integer gid) {
		String sql = "select * from EmHJBorrowCard a inner join EmCensus b on a.ehbc_tableid=b.emhj_id where ehbc_sy=1 and gid="
				+ gid;
		boolean flag = false;
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

	public boolean ifHKKIsOut(Integer gid, String emhj_no) {
		String sql = "select * from EmHJBorrowCard a inner join EmCensus b "
				+ " on a.ehbc_tableid=b.emhj_id where ehbc_grhk=1 and ehbc_state<4 "
				+ " and gid=" + gid + " and emhj_no='" + emhj_no + "'";
		boolean flag = false;
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

	// 查询家属
	public List<EmCensusModel> SelectEmCensusList(int gid) {
		List<EmCensusModel> list = new ArrayList<EmCensusModel>();
		dbconn db = new dbconn();
		String sql = "select * from EmCensus where emhj_no like '%C' and gid="
				+ gid;
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				EmCensusModel m = new EmCensusModel();
				m.setCid(rs.getInt("cid"));
				m.setGid(rs.getInt("gid"));
				m.setEmhj_name(rs.getString("emhj_name"));
				m.setEmhj_idcard(rs.getString("emhj_idcard"));
				m.setEmhj_no(rs.getString("emhj_no"));
				m.setEmhj_id(rs.getInt("emhj_id"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据gid和家属姓名查询
}
