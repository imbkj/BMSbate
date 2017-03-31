package dal.EmSheBaocard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.CoShebaoModel;
import Model.CoshebaobankModel;
import Model.EmSheBaoChangeModel;
import Model.EmShebaoCardInfoModel;
import Model.EmShebaoUpdateModel;
import Model.EmbaseModel;
import Model.EmshebaoCardBankDataInfoModel;
import Model.PubCodeConversionModel;
import Util.UserInfo;

public class EmShebaoCardInfoSelectDal {
	// 根据gid查询员工信息
	public List<EmbaseModel> getEmbaseInfoByGid(String gid) throws SQLException {
		List<EmbaseModel> list = new ListModelList<EmbaseModel>();
		dbconn db = new dbconn();
		String sql = "select coba_company,coba_shortname,esiu_hj,coba_client,coba_address,esiu_computerid emsc_computerid,esiu_single emsc_Single,"
				+ "convert(varchar(10),emba_birth,120) as emba_birth,convert(varchar(16),emba_addtime,120) as emba_addtime,"
				+ "convert(varchar(10),emba_indate,120) as emba_indate,convert(varchar(16),emba_modtime,120) as emba_modtime,"
				+ "a.* from EmBase a  "
				+" left join CoBase b on a.cid=b.cid left join EmShebaoUpdate c  ON  a.gid=c.GID "
				+ "where  a.gid=?";
		list = db.find(sql, EmbaseModel.class,
				dbconn.parseSmap(EmbaseModel.class), gid);
		return list;
	} 

	// 查询员工信息列表
	public List<EmbaseModel> getEmbaseList(String str) {
		List<EmbaseModel> list = new ArrayList<EmbaseModel>();
		dbconn db = new dbconn();
		String sql = "select distinct top 1000 (a.gid) gid,b.cid,emba_name,emba_idcard,esiu_computerid emsc_computerid,"
				+ "coba_shortname,coba_client from EmBase a inner join CoBase b  on a.cid=b.cid "
				+ "LEFT join emshebaoupdate c on a.gid=c.gid where 1=1" + str;
		try {
			list = db.find(sql, EmbaseModel.class,
					dbconn.parseSmap(EmbaseModel.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询社保卡信息
	public List<EmShebaoCardInfoModel> getEmShebaoCardInfoList(String str) {
		List<EmShebaoCardInfoModel> list = new ListModelList<EmShebaoCardInfoModel>();
		dbconn db = new dbconn();
		String sql = "select isnull(msgnum,0) msgnum,isnull(msgsnum,0) msgsnum,picnum,convert(varchar(10),sbcd_birthday,120) as sbcd_birthday,"
				+ "convert(varchar(10),sbcd_addtime,120) as sbcd_addtime,convert(varchar(10),sbcd_flaccpettime,120) as sbcd_flaccpettime,"
				+ "convert(varchar(10),sbcd_tobanktime,120) as sbcd_tobanktime,convert(varchar(10),sbcd_fltaketime,120) as sbcd_fltaketime,"
				+ "convert(varchar(10),sbcd_centertaketime,120) as sbcd_centertaketime,convert(varchar(10),sbcd_stafftime,120) as sbcd_stafftime,"
				+ "convert(varchar(10),sbcd_clienttaketime,120) as sbcd_clienttaketime,convert(varchar(10),sbcd_stafftime,120) as sbcd_stafftime,"
				+ "convert(varchar(16),sbcd_addtime,120) as sbcd_addtime,convert(varchar(20),sbcd_backtime,120) as sbcd_backtime,"
				+ "a.*,b.*,gidnum,c.coba_client,coba_shortname,cosp_card_caliname,cosp_bsal_caliname,cosp_card_data_caliname from EmShebaoCardInfo a "
				+ " left join CoBaseServePromise prom on a.cid=prom.cid "
				+ " left join (select COUNT(*) picnum,gid from empic where empi_class "
				+ " like '%身份证%' group by gid) empc on a.Gid=empc.gid "
				+ " left join ( select COUNT(*) msgnum,smwr_tid "
				+ "from View_Message where syme_state=1 and symr_readstate=0 and symr_log_id="
				+ UserInfo.getUserid()
				+ " and "
				+ "smwr_table='EmShebaoCardInfo'  group by smwr_tid) d on a.sbcd_id=smwr_tid  "
				+ "left join ( select COUNT(*) msgsnum,smwr_tid from View_Message where syme_state=1 "
				+ "and symr_log_id=8 and smwr_table='EmShebaoCardInfo'  group by smwr_tid) k "
				+ "on a.sbcd_id=k.smwr_tid  inner join cobase c on a.cid=c.cid left join "
				+ "(select COUNT(gid) gidnum,gid from EmShebaoCardInfo group by gid) g "
				+ "on a.Gid=g.gid ,EmShebaoCardState b "
				+ "where a.sbcd_stateid=b.cdst_id" + str + " order by cdst_id";
		System.out.println(sql);
		try {
			list = db.find(sql, EmShebaoCardInfoModel.class,
					dbconn.parseSmap(EmShebaoCardInfoModel.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 获取客服
	public List<String> getClientList() {
		List<String> list = new ArrayList<String>();
		dbconn db = new dbconn();
		String sql = "select distinct(coba_client) coba_client from EmBase a, CoBase b,EmShebaoChange c "
				+ "where a.cid=b.cid and a.gid=c.GID and b.CID=c.cid ";
		try {
			ResultSet rs = db.GRS(sql);
			list.add("");
			while (rs.next()) {
				list.add(rs.getString("coba_client"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 获取客服
	public List<String> getClientsList() {
		List<String> list = new ArrayList<String>();
		dbconn db = new dbconn();
		String sql = "select distinct(coba_client) coba_client from EmShebaoCardInfo a "
				+ "inner join cobase b on a.cid=b.cid where coba_client "
				+ "in(select distinct(log_name) log_name from Login where log_inure=1)  order by coba_client";
		try {
			ResultSet rs = db.GRS(sql);
			list.add("");
			while (rs.next()) {
				list.add(rs.getString("coba_client"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 从PubCodeConversion表查询信息
	public List<PubCodeConversionModel> getPubCodeConversionList(
			Integer pucl_id, String pcco_name) {
		List<PubCodeConversionModel> list = new ListModelList<PubCodeConversionModel>();
		dbconn db = new dbconn();
		String sql = "select pcco_id,pucl_id,pcco_name,pcco_code+'.'+pcco_cn as pcco_cn,"
				+ "pcco_code,pcco_cn as pcco_cnname"
				+ " from PubCodeConversion where pucl_id=? and pcco_name=?";
		try {
			list = db.find(sql, PubCodeConversionModel.class,
					dbconn.parseSmap(PubCodeConversionModel.class), pucl_id,
					pcco_name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询民族列表
	public List<PubCodeConversionModel> getFolkList(String str) {
		List<PubCodeConversionModel> list = new ListModelList<PubCodeConversionModel>();
		dbconn db = new dbconn();
		String sql = "select pufo_id as pcco_id,pufo_name as pcco_cn,pufo_name as pcco_cnname"
				+ " from PubFolk where 1=1 and pufo_state=1" + str;
		try {
			list = db.find(sql, PubCodeConversionModel.class,
					dbconn.parseSmap(PubCodeConversionModel.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询省列表
	public List<PubCodeConversionModel> getPubProvinceList(String str) {
		List<PubCodeConversionModel> list = new ListModelList<PubCodeConversionModel>();
		dbconn db = new dbconn();
		String sql = "select code as pcco_code,name as pcco_cn,name as pcco_cnname"
				+ " from PubProvince where 1=1" + str;
		try {
			list = db.find(sql, PubCodeConversionModel.class,
					dbconn.parseSmap(PubCodeConversionModel.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询城市列表
	public List<PubCodeConversionModel> getCityList(String str) {
		List<PubCodeConversionModel> list = new ListModelList<PubCodeConversionModel>();
		dbconn db = new dbconn();
		String sql = "select a.code as pcco_code,a.name as pcco_cn,a.name as pcco_cnname "
				+ " from PubProCity a,PubProvince b where a.provinceid=b.id "
				+ str;
		try {
			list = db.find(sql, PubCodeConversionModel.class,
					dbconn.parseSmap(PubCodeConversionModel.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 根据条件获取状态信息列表
	public List<EmShebaoCardInfoModel> getStateList(String str) {
		List<EmShebaoCardInfoModel> list = new ArrayList<EmShebaoCardInfoModel>();
		dbconn db = new dbconn();
		ResultSet rs = null;
		String sql = "select * from EmShebaoCardState where cdst_state=1 "
				+ str + " order by cdst_order";
		try {
			rs = db.GRS(sql);
			list.add(null);
			while (rs.next()) {
				EmShebaoCardInfoModel model = new EmShebaoCardInfoModel();
				model.setCdst_id(rs.getInt("cdst_id"));
				model.setCdst_statename(rs.getString("cdst_statename"));
				model.setCdst_state(rs.getInt("cdst_state"));
				list.add(model);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 根据cid查询银行
	public CoShebaoModel getCoshebaoInfo(Integer cid) {
		CoShebaoModel model = new CoShebaoModel();
		String sql = "select * from coshebao where cid=" + cid;
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				model.setCosb_cardbank(rs.getString("cosb_cardbank"));
				model.setCosb_branchbank(rs.getString("cosb_branchbank"));
				model.setCosb_comname(rs.getString("cosb_Comname"));
				model.setCosb_sorid(rs.getString("cosb_sorid"));
			}
		} catch (Exception e) {

		}
		return model;
	}

	// 根据开户代码查询银行
	public CoShebaoModel getCoshebaoBankInfo(String cosb_sorid) {
		CoShebaoModel model = new CoShebaoModel();
		String sql = "select * from coshebao where cosb_sorid='" + cosb_sorid
				+ "'";
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				model.setCosb_cardbank(rs.getString("cosb_cardbank"));
				model.setCosb_branchbank(rs.getString("cosb_branchbank"));
				model.setCosb_comname(rs.getString("cosb_Comname"));
				model.setCosb_sorid(rs.getString("cosb_sorid"));
			}
		} catch (Exception e) {

		}
		return model;
	}

	// 查询银行
	public List<CoshebaobankModel> getBankInfoList(String str) {
		List<CoshebaobankModel> list = new ArrayList<CoshebaobankModel>();
		dbconn db = new dbconn();
		String sql = "select convert(varchar(10),bank_addtime,120) as bank_addtime,"
				+ "* from Coshebaobank where 1=1" + str;
		try {
			list = db.find(sql, CoshebaobankModel.class,
					dbconn.parseSmap(CoshebaobankModel.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询银行支行
	public List<String> getBankBranchInfoList() {
		List<String> list = new ArrayList<String>();
		dbconn db = new dbconn();
		String sql = "select * from CoshebaobankBranch where bran_state=1";
		try {
			ResultSet rs = db.GRS(sql);
			list.add("");
			while (rs.next()) {
				list.add(rs.getString("bran_name"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询材料
	public List<EmshebaoCardBankDataInfoModel> getDataList(String str) {
		List<EmshebaoCardBankDataInfoModel> list = new ArrayList<EmshebaoCardBankDataInfoModel>();
		dbconn db = new dbconn();
		String sql = "select convert(varchar(10),data_addtime,120) as data_addtime,"
				+ " convert(varchar(10),data_modtime,120) as data_modtime,"
				+ "* from EmshebaoCardBankDataInfo where 1=1" + str;
		try {
			list = db.find(sql, EmshebaoCardBankDataInfoModel.class,
					dbconn.parseSmap(EmshebaoCardBankDataInfoModel.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 根据gid查询员工是否有社保信息
	public EmSheBaoChangeModel getshebaoindo(Integer gid) {
		EmSheBaoChangeModel model = new EmSheBaoChangeModel();
		dbconn db = new dbconn();
		String sql = " select * from emshebaoupdate where gid=" + gid;
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				model.setId(rs.getInt("id"));
				model.setGid(rs.getInt("gid"));
				model.setEmsc_computerid(rs.getString("Esiu_ComputerID"));
				model.setCid(rs.getInt("cid"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
	}

	public boolean ifExistShebaoInfo(Integer gid) {
		boolean flag = false;
		dbconn db = new dbconn();
		String sql = " select * from EmShebaoUpdate where gid=" + gid;
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				flag = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	public EmShebaoUpdateModel ShebaoInfo(Integer gid) {
		EmShebaoUpdateModel model = new EmShebaoUpdateModel();
		dbconn db = new dbconn();
		String sql = "select * from EmShebaoUpdate where gid=" + gid;
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				model.setEsiu_computerid(rs.getString("esiu_computerid"));
				model.setId(rs.getInt("id"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
	}

	public boolean ifExists(Integer gid) {
		boolean flag = false;
		String sql = "select sbcd_stateid,* from emshebaocardinfo "
				+ "where sbcd_stateid<>8 and sbcd_stateid<>9 and gid=" + gid;
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

	public boolean ifSameClass(String idstr) {
		boolean flag = false;
		String sql = "select count(distinct(sbcd_content)) num from EmShebaoCardInfo where sbcd_id in("
				+ idstr + ")";
		dbconn db = new dbconn();
		Integer num = 0;
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				num = num + rs.getInt("num");
			}
		} catch (Exception e) {

		}
		if (num == 1 || num.equals(1)) {
			flag = true;
		}
		return flag;
	}

	// 获取社保卡所有添加人
	public List<String> getAddList() {
		List<String> list = new ArrayList<String>();
		String sql = "select distinct(sbcd_addname) sbcd_addname from EmShebaoCardInfo"
				+ " where sbcd_addname in(select log_name from login where log_inure=1) "
				+ " order by sbcd_addname";
		dbconn db = new dbconn();
		Integer num = 0;
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				list.add(rs.getString("sbcd_addname"));
			}
		} catch (Exception e) {

		}
		return list;
	}

	// 根据身份证查询员工信息
	public EmbaseModel getEmbaseInfoList(EmbaseModel m) {
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS("exec EmBase_SheBaoUpdate_p_cyj '"
					+ m.getEmba_idcard() + "'");

			while (rs.next()) {
				m.setGid(rs.getInt("gid"));
				m.setCid(rs.getInt("cid"));
				if (rs.getString("emba_idcard") != null) {
					m.setEmba_idcard(rs.getString("emba_idcard"));
				}
				m.setEmba_name(rs.getString("emba_name"));
				m.setCoba_company(rs.getString("coba_company"));
				m.setCoba_client(rs.getString("coba_client"));
				m.setEsiu_computerid(rs.getString("esiu_computerid"));
				m.setEmba_sex(rs.getString("emba_sex"));
				m.setEmba_mobile(rs.getString("emba_mobile"));
				m.setSbcd_id(rs.getInt("sbcd_id"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return m;
	}

}
