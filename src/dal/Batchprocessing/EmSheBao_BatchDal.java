package dal.Batchprocessing;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.EmShebaoChangeUploadModel;
import Model.EmShebaoUpdateModel;
import Util.UserInfo;

public class EmSheBao_BatchDal {
	private dbconn conn;

	public EmSheBao_BatchDal() {
		conn = new dbconn();
	}

	// 社保批量导入数据库
	public int addBatchUpload(EmShebaoChangeUploadModel m, String username,
			String uploadfilename, int emsu_type) {
		try {
			CallableStatement c = conn
					.getcall("EMSI_BatchUpload_p_lwj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getGid());
			c.setInt(2, m.getOwnmonth());
			c.setString(3, m.getEmsu_name());
			c.setInt(4, emsu_type);
			c.setString(5, m.getEmsu_idcard());
			c.setString(6, m.getEmsu_computerid());
			c.setInt(7, m.getEmsu_radix());
			c.setString(8, m.getEmsu_stopreason());
			c.setString(9, uploadfilename);
			c.setString(10, username);
			c.setString(11, m.getEmsu_hj());
			c.setString(12, m.getEmsu_Folk());
			c.setString(13, m.getEmsu_Worker());
			c.setString(14, m.getEmsu_Hand());
			c.setString(15, m.getEmsu_YL());
			c.setString(16, m.getEmsu_GS());
			c.setString(17, m.getEmsu_Sye());
			c.setString(18, m.getEmsu_Syu());
			c.setString(19, m.getEmsu_YLType());
			c.registerOutParameter(20, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(20);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 社保批量导入数据库(删除)
	public int delBatchUpload(int emsu_id) {
		try {
			CallableStatement c = conn
					.getcall("EMSI_BatchUploadDelete_p_lwj(?,?)");
			c.setInt(1, emsu_id);
			c.registerOutParameter(2, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(2);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 社保批量导入数据库(修改状态)
	public void upBatchUpload(int emsu_id, int emsu_state, String emsu_err) {
		try {
			CallableStatement c = conn
					.getcall("EMSI_BatchUploadUpdate_p_lwj(?,?,?)");
			c.setInt(1, emsu_id);
			c.setInt(2, emsu_state);
			c.setString(3, emsu_err);
			c.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 根据用户获取导入的社保数据(修改工资)
	public List<EmShebaoChangeUploadModel> getShebaoUploadSalaryUpdateByUser(
			String username) {
		List<EmShebaoChangeUploadModel> list = new ArrayList<EmShebaoChangeUploadModel>();
		EmShebaoChangeUploadModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select co.CID,co.coba_shortname,co.coba_client,eu.*,es.*,ef.emsf_soin_title,addtime= CONVERT(varchar(100), emsu_addtime, 120) from EmShebaoChangeUpload eu ");
		sql.append("left join EmBase em on eu.gid = em.gid ");
		sql.append("left join CoBase co on em.cid=co.CID ");
		sql.append("left join EmShebaoUpdate es on es.GID=eu.gid ");
		sql.append("left join EmShebaoFormula ef on es.Esiu_formulaid=ef.id ");
		sql.append("where (emsu_addname=? or coba_client=?) and emsu_addtime>dateadd(MM,-1,getdate()) and eu.emsu_type=3 order by emsu_id desc ");
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setString(1, username);
			psmt.setString(2, username);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				try {
					m = new EmShebaoChangeUploadModel();
					m.setEmsu_id(rs.getInt("emsu_id"));
					m.setOwnmonth(rs.getInt("ownmonth"));
					m.setCid(rs.getInt("cid"));
					m.setCoba_shortname(rs.getString("coba_shortname"));
					m.setCoba_client(rs.getString("coba_client"));
					m.setGid(rs.getInt("gid"));
					if ("6".equals(UserInfo.getDepID())) {
						m.setEmsu_name(rs.getString("esiu_name"));
						m.setEmsu_idcard(rs.getString("esiu_idcard"));
					}else {
						m.setEmsu_name(rs.getString("emsu_name"));
						m.setEmsu_idcard(rs.getString("emsu_idcard"));
					}
					m.setEmsu_computerid(rs.getString("emsu_computerid"));
					m.setEmsu_type(rs.getInt("emsu_type"));
					m.setEmsu_radix(rs.getInt("emsu_radix"));
					m.setEmsu_old_radix(rs.getInt("emsu_old_radix"));
					m.setEmsu_state(rs.getInt("emsu_state"));
					m.setEmsu_addname(rs.getString("emsu_addname"));
					m.setEmsu_addtime(rs.getString("addtime"));
					m.setEmsu_err(rs.getString("emsu_err"));
					m.setEmsu_stopreason(rs.getString("emsu_stopreason"));
					m.setEsiu_idcard(rs.getString("esiu_idcard"));
					if (m.getGid() != 0 && m.getEmsu_state() == 0
							&& rs.getInt("id") != 0) {
						m.setEuModel(new EmShebaoUpdateModel(rs.getInt("id"),
								rs.getInt("cid"), rs.getInt("gid"), rs
										.getInt("ownmonth"), rs
										.getString("esiu_company"), rs
										.getString("esiu_name"), rs
										.getString("esiu_computerid"), rs
										.getString("esiu_idcard"), rs
										.getString("esiu_hj"), rs
										.getInt("esiu_radix"), rs
										.getString("esiu_yl"), rs
										.getString("esiu_gs"), rs
										.getString("esiu_sye"), rs
										.getString("esiu_syu"), rs
										.getString("esiu_yltype"), rs
										.getString("esiu_house"), rs
										.getBigDecimal("esiu_ylcp"), rs
										.getBigDecimal("esiu_ylop"), rs
										.getBigDecimal("esiu_jlcp"), rs
										.getBigDecimal("esiu_jlop"), rs
										.getBigDecimal("esiu_syucp"), rs
										.getBigDecimal("esiu_syuop"), rs
										.getBigDecimal("esiu_syecp"), rs
										.getBigDecimal("esiu_syeop"), rs
										.getBigDecimal("esiu_gscp"), rs
										.getBigDecimal("esiu_gsop"), rs
										.getBigDecimal("esiu_housecp"), rs
										.getBigDecimal("esiu_houseop"), rs
										.getBigDecimal("esiu_totalcp"), rs
										.getBigDecimal("esiu_totalop"), rs
										.getInt("esiu_ifinure"), rs
										.getString("esiu_remark"), rs
										.getString("esiu_addname"), rs
										.getString("esiu_addtime"), rs
										.getInt("esiu_single"), rs
										.getString("esiu_worker"), rs
										.getString("esiu_officialrank"), rs
										.getString("esiu_hand"), rs
										.getString("esiu_folk"), rs
										.getInt("esiu_ifstop"), rs
										.getInt("esiu_ifsame"), rs
										.getString("esiu_client"), rs
										.getString("esiu_lbid"), rs
										.getInt("esiu_shebaoid"), rs
										.getString("esiu_stopreason"), rs
										.getString("emsf_soin_title")));
					}
					list.add(m);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据用户获取导入的错误提示(修改工资)
	public List<EmShebaoChangeUploadModel> getShebaoUploadSalaryUpdateErr(
			String username) {
		List<EmShebaoChangeUploadModel> list = new ArrayList<EmShebaoChangeUploadModel>();
		EmShebaoChangeUploadModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct emsu_err from EmShebaoChangeUpload eu ");
		sql.append("left join EmBase em on eu.gid = em.gid ");
		sql.append("left join CoBase co on em.cid=co.CID ");
		sql.append("where emsu_err is not null and (emsu_addname=? or coba_client=?) and emsu_addtime>dateadd(MM,-1,getdate()) and eu.emsu_type=3 order by emsu_err desc ");
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setString(1, username);
			psmt.setString(2, username);
			ResultSet rs = psmt.executeQuery();

			m = new EmShebaoChangeUploadModel();
			m.setEmsu_err("");
			list.add(m);

			while (rs.next()) {
				try {
					m = new EmShebaoChangeUploadModel();
					m.setEmsu_err(rs.getString("emsu_err"));

					list.add(m);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据用户获取导入的社保数据(社保新增)
	public List<EmShebaoChangeUploadModel> getShebaoUploadAddByUser(
			String username) {
		List<EmShebaoChangeUploadModel> list = new ArrayList<EmShebaoChangeUploadModel>();
		EmShebaoChangeUploadModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select co.CID,co.coba_shortname,co.coba_client,em.emba_mobile,eu.*,ef.emsf_soin_title,addtime= CONVERT(varchar(100), emsu_addtime, 120) from EmShebaoChangeUpload eu ");
		sql.append("left join EmBase em on eu.gid = em.gid ");
		sql.append("left join CoBase co on em.cid=co.CID ");
		sql.append("left join EmShebaoFormula ef on eu.Emsu_formulaid=ef.id ");
		sql.append("where (emsu_addname=? or coba_client=?) and emsu_addtime>dateadd(MM,-1,getdate()) and (eu.emsu_type=1 or eu.emsu_type=2 or eu.emsu_type=4 or eu.emsu_type=5 or eu.emsu_type=6) order by emsu_id desc ");
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setString(1, username);
			psmt.setString(2, username);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				try {
					m = new EmShebaoChangeUploadModel();
					m.setEmsu_id(rs.getInt("emsu_id"));
					m.setOwnmonth(rs.getInt("ownmonth"));
					m.setCid(rs.getInt("cid"));
					m.setCoba_shortname(rs.getString("coba_shortname"));
					m.setCoba_client(rs.getString("coba_client"));
					m.setGid(rs.getInt("gid"));
					m.setEmsu_idcard(rs.getString("emsu_idcard"));
					m.setEmsu_computerid(rs.getString("emsu_computerid"));
					m.setEmsu_type(rs.getInt("emsu_type"));
					m.setEmsu_name(rs.getString("emsu_name"));
					m.setEmsu_radix(rs.getInt("emsu_radix"));
					m.setEmsu_old_radix(rs.getInt("emsu_old_radix"));
					m.setEmsu_state(rs.getInt("emsu_state"));
					m.setEmsu_addname(rs.getString("emsu_addname"));
					m.setEmsu_addtime(rs.getString("addtime"));
					m.setEmsu_err(rs.getString("emsu_err"));
					m.setEmsu_formulaid(rs.getInt("emsu_formulaid"));
					m.setEmsu_formula(rs.getString("emsf_soin_title"));
					m.setMobile(rs.getString("emba_mobile"));
					m.setEmsu_YL(rs.getString("emsu_YL"));
					m.setEmsu_YLType(rs.getString("emsu_YLType"));
					m.setEmsu_GS(rs.getString("emsu_GS"));
					m.setEmsu_Sye(rs.getString("emsu_Sye"));
					m.setEmsu_Syu(rs.getString("emsu_Syu"));
					m.setEmsu_Folk(rs.getString("emsu_Folk"));
					m.setEmsu_Hand(rs.getString("emsu_Hand"));
					m.setEmsu_Worker(rs.getString("emsu_Worker"));
					list.add(m);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据用户获取导入的错误提示(社保新增)
	public List<EmShebaoChangeUploadModel> getShebaoUploadAddErr(String username) {
		List<EmShebaoChangeUploadModel> list = new ArrayList<EmShebaoChangeUploadModel>();
		EmShebaoChangeUploadModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct emsu_err from EmShebaoChangeUpload eu ");
		sql.append("left join EmBase em on eu.gid = em.gid ");
		sql.append("left join CoBase co on em.cid=co.CID ");
		sql.append("where emsu_err is not null and (emsu_addname=? or coba_client=?) and emsu_addtime>dateadd(MM,-1,getdate()) and (eu.emsu_type=1 or eu.emsu_type=2 or eu.emsu_type=4 or eu.emsu_type=5 or eu.emsu_type=6) order by emsu_err desc ");
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setString(1, username);
			psmt.setString(2, username);
			ResultSet rs = psmt.executeQuery();

			m = new EmShebaoChangeUploadModel();
			m.setEmsu_err("");
			list.add(m);

			while (rs.next()) {
				try {
					m = new EmShebaoChangeUploadModel();
					m.setEmsu_err(rs.getString("emsu_err"));
					list.add(m);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
