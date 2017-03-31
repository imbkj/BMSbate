package dal.CoFinanceManage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.CoBaseModel;
import Model.CoCompactModel;
import Model.CoFinanceManualDisposableModel;
import Model.CoOfferListModel;
import Model.CoOfferModel;
import Model.EmbaseModel;
import Util.UserInfo;

/**
 * 手动添加非标dal
 * 
 * @author Administrator
 * 
 */
public class Cfma_ManualDisposableDal extends dbconn {

	public CoBaseModel getCoBase(int cid) {
		String sql = " SELECT cid,coba_company,coba_client FROM CoBase where cid = ? ";
		PreparedStatement pstmt = getpre(sql);
		CoBaseModel m = new CoBaseModel();
		try {
			pstmt.setInt(1, cid);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				m.setCid(rs.getInt("cid"));
				m.setCoba_company(rs.getString("coba_company"));
				m.setCoba_client(rs.getString("coba_client"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return m;
	}

	// 获得公司非标列表信息
	public List<CoFinanceManualDisposableModel> getCoFinanceManualDisposable(
			int cid, int ownmonth) {
		List<CoFinanceManualDisposableModel> list = new ArrayList<CoFinanceManualDisposableModel>();
		Object[] objs = { cid};
//		String sql = " SELECT * FROM CoFinanceManualDisposable WHERE cid = ? and ownmonth = ? and gid = 0 ";
		String sql = " SELECT * FROM CoFinanceManualDisposable WHERE cid = ?  and gid = 0  order by ownmonth desc";
		try {
			list = find(sql, CoFinanceManualDisposableModel.class, null, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获得员工非标列表信息
	public List<CoFinanceManualDisposableModel> getEmFinanceManualDisposable(
			int cid, int ownmonth) {
		List<CoFinanceManualDisposableModel> list = new ArrayList<CoFinanceManualDisposableModel>();
		Object[] objs = {cid};
//		String sql = " SELECT * FROM CoFinanceManualDisposable a left join (select gid,emba_name from EmBase) b on a.gid = b.gid "
//				+ " WHERE cid = ? and ownmonth = ? and a.gid != 0 ";
//		
		String sql = " SELECT * FROM CoFinanceManualDisposable a left join (select gid,emba_name from EmBase) b on a.gid = b.gid "
				+ " WHERE cid = ?   and a.gid != 0 order by ownmonth desc";
		try {
			list = find(sql, CoFinanceManualDisposableModel.class, null, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//根据公司获取 公司产品
	public List<CoOfferListModel> getCoofferList(Integer coco_id) {
		dbconn db = new dbconn();
		List<CoOfferListModel> list = new ListModelList<>();
		String sql = "select coli_id,coli_pclass,coli_name,coli_standard,coli_fee,coli_cpfc_name,"
				+ "coli_amount,coli_remark,coli_content,coli_coco_id,coli_group_id,coli_group_count,"
				+ "coli_coof_id,name city,coab_name"
				+ " from CoOfferList a"
				+ " left join CoProduct b on a.coli_copr_id=b.Copr_id"
				+ " left join CoAgencyBaseCityRel c on b.copr_cabc_id=c.cabc_id"
				+ " left join PubProCity d on cabc_ppc_id=d.id"
				+ " left join CoAgencyBase e on c.cabc_coab_id=e.coab_id"
				+ " left join CoPclass f on a.coli_pclass=f.Copc_name"
				+ "  LEFT JOIN CoOffer cof ON a.coli_coof_id=cof.coof_id "
				+ " LEFT JOIN CoCompact coc on coc.coco_id= cof.coof_coco_id "
				+ " where  coc.coco_id=? and coli_state=1 and (coli_copr_id>0 or coli_fee>0)"
				+ " order by copc_id,coli_group_id,coli_group_count desc";
		try {
			list = db.find(sql, CoOfferListModel.class, null, coco_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	//根据产品大类获取小类（非标）
		public List<CoOfferListModel> getCoofferList(String coco_id) {
			dbconn db = new dbconn();
			List<CoOfferListModel> list = new ListModelList<>();
			String sql = "select cmfc_id coli_id, copr_nameinfo coli_name,capr_name cpac_name  FROM " +
					"CoFinanceManualDisposableclass where copr_name='"+coco_id+"'";
			try {
				list = db.find(sql, CoOfferListModel.class, null, null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return list;
		}
		
	
	
	//根据公司获取 全部产品
	public List<CoOfferListModel> getCoofferListall() {
		dbconn db = new dbconn();
		List<CoOfferListModel> list = new ListModelList<>();
		String sql = "select DISTINCT copr_name coli_name FROM CoFinanceManualDisposableclass";
		try {
			list = db.find(sql, CoOfferListModel.class, null, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	
	//根据产品获取产品的财务科目
	

	// 手动添加公司非标
	public int addCoDispo(CoFinanceManualDisposableModel m) {
		int id = 0;
		try {
			id = Integer.valueOf(callWithReturn(
					"{?=call Cfma_addCoDispo_gxj(?,?,?,?,?,?,?,?,?,?,?)}",
					Types.INTEGER, m.getCid(), m.getGid(), m.getOwnmonth(),
					m.getCfmd_coco_id(), m.getCfmd_cpac_name(),
					m.getCfmd_copr_name(), m.getCfmd_Reason(),
					m.getCfmd_Receivable(),UserInfo.getUsername(),
					m.getCfmd_state(), m.getCfmd_tapr_id()).toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	// 审核手动添加公司非标
	public int checkCoDispo(CoFinanceManualDisposableModel m) {
		int row = 0;
		String sql = " UPDATE CoFinanceManualDisposable SET cfmd_state = ? WHERE cfmd_tapr_id = ? ";
		Object[] objs = { m.getCfmd_state(), m.getCfmd_tapr_id() };
		try {
			row = updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}

	// 任务单退回重新提交
	public int reSubCoDispo(CoFinanceManualDisposableModel m) {
		int row = 0;
		String sql = " UPDATE CoFinanceManualDisposable SET cfmd_state = ?,ownmonth=?,cfmd_coco_id=?,cfmd_cpac_name=?,cfmd_copr_name=?,cfmd_reason=?,cfmd_receivable=? WHERE cfmd_tapr_id = ? ";
		Object[] objs = { m.getCfmd_state(), m.getOwnmonth(),
				m.getCfmd_coco_id(), m.getCfmd_cpac_name(),
				m.getCfmd_copr_name(), m.getCfmd_Reason(),
				m.getCfmd_Receivable(), m.getCfmd_tapr_id() };
		try {
			row = updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}
	
	//根据合同信息获取报价单项目
	
	

	// 退回修改状态
	public int moCoDispo(CoFinanceManualDisposableModel m) {
		int row = 0;
		String sql = " UPDATE CoFinanceManualDisposable SET cfmd_state = ?,cfmd_backreason=? WHERE cfmd_tapr_id = ? ";
		Object[] objs = { m.getCfmd_state(), m.getCfmd_backreason(),
				m.getCfmd_tapr_id() };
		try {
			row = updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}

	// 更新任务单id
	public int updateTaprid(int dataId, int tapr_id) {
		int row = 0;
		Object[] objs = { tapr_id, dataId };
		String sql = " UPDATE CoFinanceManualDisposable SET cfmd_tapr_id = ? where cfmd_id = ? ";
		try {
			row = updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}

	// 根据cid获取合同信息
	public List<CoCompactModel> getCocoinfo(int cid) {
		List<CoCompactModel> list = new ArrayList<CoCompactModel>();
		String sql = " select coco_id,cid,coco_compactid from CoCompact where (coco_stopdate>GETDATE() or coco_stopdate is null) and cid=? ";
		PreparedStatement pstmt = getpre(sql);
		try {
			pstmt.setInt(1, cid);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CoCompactModel m = new CoCompactModel();
				m.setCoco_id(rs.getInt("coco_id"));
				m.setCid(rs.getString("cid"));
				m.setCoco_compactid(rs.getString("coco_compactid"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取财务科目
	public List<String> getCoPA() {
		List<String> list = new ArrayList<String>();
		String sql = " select cpac_name from CoPAccount ";
		try {
			ResultSet rs = GRS(sql);
			while (rs.next()) {
				list.add(rs.getString("cpac_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	//根据公司获取 公司产品
	public List<String> getCoPA(Integer coofId) {
		List<String> list = new ArrayList<String>();
		String sql = "select coli_id,coli_pclass,coli_name,coli_standard,coli_fee,coli_cpfc_name,"
				+ "coli_amount,coli_remark,coli_content,coli_coco_id,coli_group_id,coli_group_count,"
				+ "coli_coof_id,name city,coab_name"
				+ " from CoOfferList a"
				+ " left join CoProduct b on a.coli_copr_id=b.Copr_id"
				+ " left join CoAgencyBaseCityRel c on b.copr_cabc_id=c.cabc_id"
				+ " left join PubProCity d on cabc_ppc_id=d.id"
				+ " left join CoAgencyBase e on c.cabc_coab_id=e.coab_id"
				+ " left join CoPclass f on a.coli_pclass=f.Copc_name"
				+ "  LEFT JOIN CoOffer cof ON a.coli_coof_id=cof.coof_id "
				+ " LEFT JOIN CoCompact coc on coc.coco_id= cof.coof_coco_id "
				+ " where  coc.cid=? and coli_state=1 and (coli_copr_id>0 or coli_fee>0)"
				+ " order by copc_id,coli_group_id,coli_group_count desc";
		try {
			ResultSet rs = GRS(sql);
			while (rs.next()) {
				list.add(rs.getString("coli_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	
	}
	
	
	
	
	
	//根据产品获取财务科目
	public String  getCPAc(Integer coli_id) {
		 String  list="";
		String sql = "select coli_id,coli_pclass,coli_name,coli_standard,coli_fee,coli_cpfc_name,"
				+ "coli_amount,coli_remark,coli_content,coli_coco_id,coli_group_id,coli_group_count,cpac_name,"
				+ "coli_coof_id,name city,coab_name"
				+ " from CoOfferList a"
				+ " left join CoProduct b on a.coli_copr_id=b.Copr_id"
				+ " left join CoAgencyBaseCityRel c on b.copr_cabc_id=c.cabc_id"
				+ " left join PubProCity d on cabc_ppc_id=d.id"
				+ " left join CoAgencyBase e on c.cabc_coab_id=e.coab_id"
				+ " left join CoPclass f on a.coli_pclass=f.Copc_name"
				
				+ " left join CoPAccount cpa on cpa.cpac_id=b.copr_cpac_id"
				+ "  LEFT JOIN CoOffer cof ON a.coli_coof_id=cof.coof_id "
				+ " LEFT JOIN CoCompact coc on coc.coco_id= cof.coof_coco_id "
				+ " where coli_id= "+coli_id 
				+" and coli_state=1 and (coli_copr_id>0 or coli_fee>0)"
				+ " order by copc_id,coli_group_id,coli_group_count desc";
		try {
			ResultSet rs = GRS(sql);
			while (rs.next()) {
				if (rs.getString("cpac_name")==null)
				{
					list="服务费";
				}
				else
				{
					list=rs.getString("cpac_name");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	
	}
	
 
	

	// 根据cfmd_id 查询公司信息
	public CoBaseModel getCoInfo(int cfmd_id) {
		CoBaseModel m = new CoBaseModel();
		String sql = " select * from CoBase where CID = (select cid from  CoFinanceManualDisposable where cfmd_id = ?)";
		PreparedStatement pstmt = getpre(sql);
		try {
			pstmt.setInt(1, cfmd_id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				m.setCid(rs.getInt("cid"));
				m.setCoba_company(rs.getString("coba_company"));
				m.setCoba_client(rs.getString("coba_client"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return m;
	}

	// 根据cfmd_id 查询公司非标审核信息
	public CoFinanceManualDisposableModel getCheckCoInfo(int cfmd_id) {
		List<CoFinanceManualDisposableModel> list = new ArrayList<CoFinanceManualDisposableModel>();
		Object[] objs = { cfmd_id };
		String sql = "select * from CoFinanceManualDisposable a "
				+ " left join (select coco_id,coco_compactid from CoCompact where (coco_stopdate>GETDATE() or coco_stopdate is null)) b "
				+ " on a.cfmd_coco_id = b.coco_id where cfmd_id = ? ";
		try {
			list = find(sql, CoFinanceManualDisposableModel.class, null, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	// 根据coco_id查询可以添加非标的公司
	public List<EmbaseModel> getCanAddDisEm(int coco_id) {
		List<EmbaseModel> list = new ArrayList<EmbaseModel>();
		String sql = " select distinct em.gid,em.emba_name,coco_id from CoGList cogl "
				+ " inner join EmBase em on cogl.gid=em.gid "
				+ " inner join CoOfferList coli on cogl.cgli_coli_id=coli.coli_id "
				+ " inner join CoOffer coof on coli.coli_coof_id=coof.coof_id "
				+ " inner join CoCompact coco on coof.coof_coco_id=coco.coco_id "
				+ " where coco_id=? ";
		PreparedStatement pstmt = getpre(sql);
		try {
			pstmt.setInt(1, coco_id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				EmbaseModel m = new EmbaseModel();
				m.setGid(rs.getInt("gid"));
				m.setEmba_name(rs.getString("emba_name"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据gid查询员工信息
	public EmbaseModel getEminfo(int gid) {
		EmbaseModel m = new EmbaseModel();
		String sql = " SELECT emba_name FROM embase where gid = ? ";
		PreparedStatement pstmt = getpre(sql);
		try {
			pstmt.setInt(1, gid);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				m.setEmba_name(rs.getString("emba_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return m;
	}

	// 根据登录人查询可操作的数据
	public List<CoFinanceManualDisposableModel> getPlCheckInfo(String name) {
		List<CoFinanceManualDisposableModel> list = new ArrayList<CoFinanceManualDisposableModel>();
		String sql = "  select * from CoFinanceManualDisposable a "
				+ " inner join (select CID,coba_company from CoBase where coba_client in (select log_name from login where dep_id = (select dep_id from login where log_name = ?))) b "
				+ " on a.cid = b.CID  inner join (select emba_name,gid from EmBase)c "
				+ " on a.gid = c.gid " + " where cfmd_state = 4 "; // 审核中的数据才显示出来

		Object[] objs = { name };
		try {
			list = find(sql, CoFinanceManualDisposableModel.class, null, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(sql);
		return list;
	}

	// 根据登录人获取到登录人部门的人员
	public List<String> getclients(String name) {
		List<String> list = new ArrayList<String>();
		String sql = " select log_name from login where dep_id = (select dep_id from login where log_name = ? ) ";
		PreparedStatement pstmt = getpre(sql);
		try {
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("log_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 删除手动添加非标数据
	public int delete(int cfmd_id) {
		int row = 0;
		String sql = " delete CoFinanceManualDisposable where cfmd_id = ? ";
		Object[] objs = { cfmd_id };
		try {
			row = updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}
}
